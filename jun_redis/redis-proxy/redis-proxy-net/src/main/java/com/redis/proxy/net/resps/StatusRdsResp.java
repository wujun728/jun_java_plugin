package com.redis.proxy.net.resps;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 *
 * @author zhanggaofeng
 */
public class StatusRdsResp extends RdsResp<String> {

        public static final char MARKER = '+';
        public static final StatusRdsResp OK = new StatusRdsResp("OK");
        public static final StatusRdsResp QUIT = new StatusRdsResp("OK");
        private final String status;
        private final byte[] statusBytes;

        public StatusRdsResp(String status) {
                this.status = status;
                this.statusBytes = status.getBytes(Charsets.UTF_8);
        }

        @Override
        public String data() {
                return status;
        }

        @Override
        public void write(ByteBuf os) throws IOException {
                os.writeByte(MARKER);
                os.writeBytes(statusBytes);
                os.writeBytes(CRLF);
        }

}
