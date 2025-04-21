// Copyright 2016-2022, Pulumi Corporation.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package remote

import (
	"context"
	"reflect"

	p "github.com/pulumi/pulumi-go-provider"
	"github.com/pulumi/pulumi-go-provider/infer"

	"github.com/pulumi/pulumi/sdk/v3/go/common/resource"

)

// These are not required. They indicate to Go that Command implements the following interfaces.
// If the function signature doesn't match or isn't implemented, we get nice compile time errors in this file.
var _ = (infer.CustomResource[CommandInputs, CommandOutputs])((*Command)(nil))
var _ = (infer.CustomUpdate[CommandInputs, CommandOutputs])((*Command)(nil))
var _ = (infer.CustomDelete[CommandOutputs])((*Command)(nil))
var _ = (infer.CustomDiff[CommandInputs, CommandOutputs])((*Command)(nil))

var PREVIOUS_READ_STDOUT = make(map[string]string)

// This is the Create method. This will be run on every Command resource creation.
func (*Command) Create(ctx context.Context, name string, input CommandInputs, preview bool) (string, CommandOutputs, error) {
	state := CommandOutputs{CommandInputs: input}
	var err error
	id, err := resource.NewUniqueHex(name, 8, 0)
	if err != nil {
		return "", state, err
	}
	if preview {
		return id, state, nil
	}

	if state.Create == nil {
		return id, state, nil
	}
	cmd := ""
	if state.Create != nil {
		cmd = *state.Create
	}

	if !preview {
		err = state.run(ctx, cmd, input.Logging)
		// Read
		if input.Read != nil {
			readState := CommandOutputs{CommandInputs: input}
			readState.run(ctx, *input.Read, input.Logging)
			state.ReadStdOut = readState.BaseOutputs.Stdout
		}
	}

	return id, state, err
}

// The Update method will be run on every update.
func (*Command) Update(ctx context.Context, id string, olds CommandOutputs, news CommandInputs, preview bool) (CommandOutputs, error) {
	state := CommandOutputs{CommandInputs: news, BaseOutputs: olds.BaseOutputs}
	if preview {
		if news.Read != nil {
			readState := CommandOutputs{CommandInputs: news}
			readState.run(ctx, *news.Read, news.Logging)
			state.ReadStdOut = readState.BaseOutputs.Stdout
		} else {
		  state.ReadStdOut = ""
		}

		return state, nil
	}
	var err error
	if !preview {
		if news.Update != nil {
			err = state.run(ctx, *news.Update, news.Logging)
		} else if news.Create != nil {
			err = state.run(ctx, *news.Create, news.Logging)
		}
		if news.Read != nil {
			readState := CommandOutputs{CommandInputs: news}
			readState.run(ctx, *news.Read, news.Logging)
			state.ReadStdOut = readState.BaseOutputs.Stdout
		} else {
		  state.ReadStdOut = ""
		}
	}
	return state, err
}

// The Delete method will run when the resource is deleted.
func (*Command) Delete(ctx context.Context, id string, props CommandOutputs) error {
	if props.Delete == nil {
		return nil
	}
	return props.run(ctx, *props.Delete, props.Logging)
}

// The Read method will run when the resource is refreshed.
func (c *Command) Read(ctx context.Context, id string, input CommandInputs, state CommandOutputs) (string, CommandInputs, CommandOutputs, error) {
	if state.Read == nil {
		// No read command defined
		return id, input, state, nil
	}
	var err error

	if state.Read != nil {
		readState := CommandOutputs{CommandInputs: input}
		readState.run(ctx, *state.Read, state.Logging)
		PREVIOUS_READ_STDOUT[id] = state.ReadStdOut
		state.ReadStdOut = readState.BaseOutputs.Stdout
	} else {
		state.ReadStdOut = ""
	}
	return id, input, state, err
}


func (c *Command) Diff (ctx context.Context, id string, state CommandOutputs, input CommandInputs) (p.DiffResponse, error) {
	DetailedDiff := map[string]p.PropertyDiff{}
	if len(PREVIOUS_READ_STDOUT) > 0 {
	  // we are in refresh mode
	  // just compare readstdout
		value, ok := PREVIOUS_READ_STDOUT[id];
		if ok && value != state.ReadStdOut {
			DetailedDiff["readStdout" ] = p.PropertyDiff{
        Kind: "update",
      }
    }
	} else {
		// WE are in update mode (not read)
		// compare eveyrthing besides the readstdou/tmpreadstdout
		if !reflect.DeepEqual(input.Create, state.Create) {
			DetailedDiff["create"] = p.PropertyDiff{
				Kind: "update",
			}
		}

		if !reflect.DeepEqual(input.Update, state.Update) {
			DetailedDiff["update"] = p.PropertyDiff{
				Kind: "update",
			}
		}

		if !reflect.DeepEqual(input.Delete, state.Delete) {
			DetailedDiff["delete"] = p.PropertyDiff{
				Kind: "update",
			}
		}

		if !reflect.DeepEqual(input.Read, state.Read) {
			DetailedDiff["read"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	  if !reflect.DeepEqual(input.Triggers, state.Triggers) {
			DetailedDiff["triggers"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	  if !reflect.DeepEqual(input.Stdin, state.Stdin) {
			DetailedDiff["stdin"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	  if !reflect.DeepEqual(input.Logging, state.Logging) {
			DetailedDiff["loggin"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	  if !reflect.DeepEqual(input.Connection, state.Connection) {
			DetailedDiff["connection"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	  if !reflect.DeepEqual(input.Environment, state.Environment) {
			DetailedDiff["environment"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	  if !reflect.DeepEqual(input.AddPreviousOutputInEnv, state.AddPreviousOutputInEnv) {
			DetailedDiff["addPreviousOutputInEnv"] = p.PropertyDiff{
				Kind: "update",
			}
		}
	}

	hasChanged := false
	if len(DetailedDiff) > 0{
		hasChanged = true
	}
	response := p.DiffResponse{
		DeleteBeforeReplace: false,
		HasChanges: hasChanged,
		DetailedDiff: DetailedDiff,
	}

	return response, nil
}
