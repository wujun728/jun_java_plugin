package com.redis.proxy.net.resps;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author zhanggaofeng
 */
public class IntegerRdsResp extends RdsResp<Long> {

        public static final char MARKER = ':';
        private final long integer;

        private static IntegerRdsResp[] replies = new IntegerRdsResp[512];

        static {
                for (int i = -255; i < 256; i++) {
                        replies[i + 255] = new IntegerRdsResp(i);
                }
        }

        public static IntegerRdsResp integer(long integer) {
                if (integer > -256 && integer < 256) {
                        return replies[((int) (integer + 255))];
                } else {
                        return new IntegerRdsResp(integer);
                }
        }

        public IntegerRdsResp(long integer) {
                this.integer = integer;
        }

        public IntegerRdsResp(boolean integer) {
                this.integer = integer ? 1 : 0;
        }

        @Override
        public Long data() {
                return integer;
        }

        @Override
        public void write(ByteBuf os) throws IOException {
                os.writeByte(MARKER);
                os.writeBytes(RdsResp.numToBytes(integer, true));
        }

        public String toString() {
                return data().toString();
        }
}
