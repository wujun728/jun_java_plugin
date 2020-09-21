package com.redis.proxy.net.resps;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * redis响应父类
 *
 * @author zhanggaofeng
 * @param <T>
 */
public abstract class RdsResp<T> {

        public static final byte[] NEG_ONE = convert(-1, false);
        public static final byte[] NEG_ONE_WITH_CRLF = convert(-1, true);
        public static final char LF = '\n';
        public static final char CR = '\r';
        public final byte[] CRLF = "\r\n".getBytes();
        private static final int NUM_MAP_LENGTH = 256;

        private static final byte[][] numMap = new byte[NUM_MAP_LENGTH][];

        static {
                for (int i = 0; i < NUM_MAP_LENGTH; i++) {
                        numMap[i] = convert(i, false);
                }
        }

        private static final byte[][] numMapWithCRLF = new byte[NUM_MAP_LENGTH][];

        static {
                for (int i = 0; i < NUM_MAP_LENGTH; i++) {
                        numMapWithCRLF[i] = convert(i, true);
                }
        }

        public static byte[] numToBytes(long value, boolean withCRLF) {
                if (value >= 0 && value < NUM_MAP_LENGTH) {
                        int index = (int) value;
                        return withCRLF ? numMapWithCRLF[index] : numMap[index];
                } else if (value == -1) {
                        return withCRLF ? NEG_ONE_WITH_CRLF : NEG_ONE;
                }
                return convert(value, withCRLF);
        }

        private static byte[] convert(long value, boolean withCRLF) {
                boolean negative = value < 0;
                long abs = Math.abs(value);
                int index = (value == 0 ? 0 : (int) Math.log10(abs)) + (negative ? 2 : 1);
                // Append the CRLF if necessary
                byte[] bytes = new byte[withCRLF ? index + 2 : index];
                if (withCRLF) {
                        bytes[index] = CR;
                        bytes[index + 1] = LF;
                }
                // Put the sign in the slot we saved
                if (negative) {
                        bytes[0] = '-';
                }
                long next = abs;
                while ((next /= 10) > 0) {
                        bytes[--index] = (byte) ('0' + (abs % 10));
                        abs = next;
                }
                bytes[--index] = (byte) ('0' + abs);
                return bytes;
        }

        public abstract T data();

        public abstract void write(ByteBuf os) throws IOException;
}
