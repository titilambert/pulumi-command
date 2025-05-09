// *** WARNING: this file was generated by pulumi-language-java. ***
// *** Do not edit by hand unless you're certain you know what you are doing! ***

package com.pulumi.command.remote;

import com.pulumi.asset.AssetOrArchive;
import com.pulumi.command.remote.inputs.ConnectionArgs;
import com.pulumi.core.Output;
import com.pulumi.core.annotations.Import;
import com.pulumi.exceptions.MissingRequiredPropertyException;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;


public final class CopyToRemoteArgs extends com.pulumi.resources.ResourceArgs {

    public static final CopyToRemoteArgs Empty = new CopyToRemoteArgs();

    /**
     * The parameters with which to connect to the remote host.
     * 
     */
    @Import(name="connection", required=true)
    private Output<ConnectionArgs> connection;

    /**
     * @return The parameters with which to connect to the remote host.
     * 
     */
    public Output<ConnectionArgs> connection() {
        return this.connection;
    }

    /**
     * The destination path on the remote host. The last element of the path will be created if it doesn&#39;t exist but it&#39;s an error when additional elements don&#39;t exist. When the remote path is an existing directory, the source file or directory will be copied into that directory. When the source is a file and the remote path is an existing file, that file will be overwritten. When the source is a directory and the remote path an existing file, the copy will fail.
     * 
     */
    @Import(name="remotePath", required=true)
    private Output<String> remotePath;

    /**
     * @return The destination path on the remote host. The last element of the path will be created if it doesn&#39;t exist but it&#39;s an error when additional elements don&#39;t exist. When the remote path is an existing directory, the source file or directory will be copied into that directory. When the source is a file and the remote path is an existing file, that file will be overwritten. When the source is a directory and the remote path an existing file, the copy will fail.
     * 
     */
    public Output<String> remotePath() {
        return this.remotePath;
    }

    /**
     * An [asset or an archive](https://www.pulumi.com/docs/concepts/assets-archives/) to upload as the source of the copy. It must be path-based, i.e., be a `FileAsset` or a `FileArchive`. The item will be copied as-is; archives like .tgz will not be unpacked. Directories are copied recursively, overwriting existing files.
     * 
     */
    @Import(name="source", required=true)
    private Output<AssetOrArchive> source;

    /**
     * @return An [asset or an archive](https://www.pulumi.com/docs/concepts/assets-archives/) to upload as the source of the copy. It must be path-based, i.e., be a `FileAsset` or a `FileArchive`. The item will be copied as-is; archives like .tgz will not be unpacked. Directories are copied recursively, overwriting existing files.
     * 
     */
    public Output<AssetOrArchive> source() {
        return this.source;
    }

    /**
     * Trigger replacements on changes to this input.
     * 
     */
    @Import(name="triggers")
    private @Nullable Output<List<Object>> triggers;

    /**
     * @return Trigger replacements on changes to this input.
     * 
     */
    public Optional<Output<List<Object>>> triggers() {
        return Optional.ofNullable(this.triggers);
    }

    private CopyToRemoteArgs() {}

    private CopyToRemoteArgs(CopyToRemoteArgs $) {
        this.connection = $.connection;
        this.remotePath = $.remotePath;
        this.source = $.source;
        this.triggers = $.triggers;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static Builder builder(CopyToRemoteArgs defaults) {
        return new Builder(defaults);
    }

    public static final class Builder {
        private CopyToRemoteArgs $;

        public Builder() {
            $ = new CopyToRemoteArgs();
        }

        public Builder(CopyToRemoteArgs defaults) {
            $ = new CopyToRemoteArgs(Objects.requireNonNull(defaults));
        }

        /**
         * @param connection The parameters with which to connect to the remote host.
         * 
         * @return builder
         * 
         */
        public Builder connection(Output<ConnectionArgs> connection) {
            $.connection = connection;
            return this;
        }

        /**
         * @param connection The parameters with which to connect to the remote host.
         * 
         * @return builder
         * 
         */
        public Builder connection(ConnectionArgs connection) {
            return connection(Output.of(connection));
        }

        /**
         * @param remotePath The destination path on the remote host. The last element of the path will be created if it doesn&#39;t exist but it&#39;s an error when additional elements don&#39;t exist. When the remote path is an existing directory, the source file or directory will be copied into that directory. When the source is a file and the remote path is an existing file, that file will be overwritten. When the source is a directory and the remote path an existing file, the copy will fail.
         * 
         * @return builder
         * 
         */
        public Builder remotePath(Output<String> remotePath) {
            $.remotePath = remotePath;
            return this;
        }

        /**
         * @param remotePath The destination path on the remote host. The last element of the path will be created if it doesn&#39;t exist but it&#39;s an error when additional elements don&#39;t exist. When the remote path is an existing directory, the source file or directory will be copied into that directory. When the source is a file and the remote path is an existing file, that file will be overwritten. When the source is a directory and the remote path an existing file, the copy will fail.
         * 
         * @return builder
         * 
         */
        public Builder remotePath(String remotePath) {
            return remotePath(Output.of(remotePath));
        }

        /**
         * @param source An [asset or an archive](https://www.pulumi.com/docs/concepts/assets-archives/) to upload as the source of the copy. It must be path-based, i.e., be a `FileAsset` or a `FileArchive`. The item will be copied as-is; archives like .tgz will not be unpacked. Directories are copied recursively, overwriting existing files.
         * 
         * @return builder
         * 
         */
        public Builder source(Output<AssetOrArchive> source) {
            $.source = source;
            return this;
        }

        /**
         * @param source An [asset or an archive](https://www.pulumi.com/docs/concepts/assets-archives/) to upload as the source of the copy. It must be path-based, i.e., be a `FileAsset` or a `FileArchive`. The item will be copied as-is; archives like .tgz will not be unpacked. Directories are copied recursively, overwriting existing files.
         * 
         * @return builder
         * 
         */
        public Builder source(AssetOrArchive source) {
            return source(Output.of(source));
        }

        /**
         * @param triggers Trigger replacements on changes to this input.
         * 
         * @return builder
         * 
         */
        public Builder triggers(@Nullable Output<List<Object>> triggers) {
            $.triggers = triggers;
            return this;
        }

        /**
         * @param triggers Trigger replacements on changes to this input.
         * 
         * @return builder
         * 
         */
        public Builder triggers(List<Object> triggers) {
            return triggers(Output.of(triggers));
        }

        /**
         * @param triggers Trigger replacements on changes to this input.
         * 
         * @return builder
         * 
         */
        public Builder triggers(Object... triggers) {
            return triggers(List.of(triggers));
        }

        public CopyToRemoteArgs build() {
            if ($.connection == null) {
                throw new MissingRequiredPropertyException("CopyToRemoteArgs", "connection");
            }
            if ($.remotePath == null) {
                throw new MissingRequiredPropertyException("CopyToRemoteArgs", "remotePath");
            }
            if ($.source == null) {
                throw new MissingRequiredPropertyException("CopyToRemoteArgs", "source");
            }
            return $;
        }
    }

}
