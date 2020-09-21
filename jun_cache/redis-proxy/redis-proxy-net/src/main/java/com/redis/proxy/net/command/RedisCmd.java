package com.redis.proxy.net.command;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;

/**
 * Command serialization. We special case when there are few 4 or fewer
 * parameters since most commands fall into that category. Passing bytes,
 * channelbuffers and strings / objects are all allowed. All strings are assumed
 * to be UTF-8.
 */
public class RedisCmd {

        public static final byte[] EMPTY_BYTES = new byte[0];

        private final Object[] objects;

        public RedisCmd(Object[] objects) {
                this.objects = objects;
        }

        /**
         * 检查参数个数
         *
         * @param argsNum
         * @return
         */
        public boolean checkArgNum(int argsNum) {
                return argsNum <= objects.length - 1;
        }

        public byte[] getName() {
                return getBytes(objects[0]);
        }

        private byte[] getBytes(Object object) {
                byte[] argument;
                if (object == null) {
                        argument = EMPTY_BYTES;
                } else if (object instanceof byte[]) {
                        argument = (byte[]) object;
                } else if (object instanceof ByteBuf) {
                        argument = ((ByteBuf) object).array();
                } else if (object instanceof String) {
                        argument = ((String) object).getBytes(Charsets.UTF_8);
                } else {
                        argument = object.toString().getBytes(Charsets.UTF_8);
                }
                return argument;
        }

        public void toArguments(Object[] arguments, Class<?>[] types) {
                int position = 0;
                for (Class<?> type : types) {
                        if (position <= 0 && type == String.class) {
                                arguments[position] = new String((byte[]) objects[1 + position], Charset.forName("UTF-8"));
                        } else if (type == int.class) {
                                arguments[position] = Integer.parseInt(new String((byte[]) objects[1 + position], Charset.forName("UTF-8")));
                        } else if (type == long.class) {
                                arguments[position] = Long.parseLong(new String((byte[]) objects[1 + position], Charset.forName("UTF-8")));
                        } else if (type == double.class) {
                                arguments[position] = Double.parseDouble(new String((byte[]) objects[1 + position], Charset.forName("UTF-8")));
                        } else if (type == byte[].class) {
                                arguments[position] = objects[1 + position];
                        } else {
                                int left = objects.length - position - 1;
                                byte[][] lastArgument = new byte[left][];
                                for (int i = 0; i < left; i++) {
                                        lastArgument[i] = (byte[]) objects[i + position + 1];
                                }
                                arguments[position] = lastArgument;
                        }
                        position++;
                }
        }

}
