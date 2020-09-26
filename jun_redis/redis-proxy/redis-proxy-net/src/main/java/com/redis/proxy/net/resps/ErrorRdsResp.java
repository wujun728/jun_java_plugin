package com.redis.proxy.net.resps;

import java.io.IOException;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author zhanggaofeng
 */
public class ErrorRdsResp extends RdsResp<String> {

        public static final char MARKER = '-';
        public static final ErrorRdsResp NYI_RESP = new ErrorRdsResp("Not yet implemented");
        public static final ErrorRdsResp NO_TARGETS_RESP = new ErrorRdsResp("no found redis targets");
        public static final ErrorRdsResp SHARD_DISABLE_RESP = new ErrorRdsResp("select shard disable");
        private final String error;

        public ErrorRdsResp(String error) {
                this.error = error;
        }

        public ErrorRdsResp(Exception e) {
                this.error = e.getCause().toString();
        }

        public ErrorRdsResp(String host, int port) {
                this.error = "service : " + host + ":" + port + " disconnect";
        }

        @Override
        public String data() {
                return error;
        }

        @Override
        public void write(ByteBuf os) throws IOException {
                os.writeByte(MARKER);
                os.writeBytes(error.getBytes(Charsets.UTF_8));
                os.writeBytes(CRLF);
        }

        public String toString() {
                return error;
        }
}
