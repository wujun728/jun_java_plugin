package com.redis.proxy.net.codec;

import com.redis.proxy.net.command.RedisCmd;
import com.redis.proxy.net.exceptions.RedisException;
import com.redis.proxy.net.resps.BulkRdsResp;
import com.redis.proxy.net.resps.MultiBulkRdsResp;
import com.redis.proxy.net.resps.RdsResp;
import static com.redis.proxy.net.resps.RdsResp.CR;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Wujun
 */
public class RedisCmdDecoder extends ReplayingDecoder<Void> {

        private byte[][] bytes;
        private int arguments = 0;

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                List<Object> out) throws Exception {
                if (bytes != null) {
                        int numArgs = bytes.length;
                        for (int i = arguments; i < numArgs; i++) {
                                if (in.readByte() == BulkRdsResp.MARKER) {
                                        int len = readInt(in);
                                        bytes[i] = new byte[len];
                                        in.readBytes(bytes[i]);
                                        if (in.bytesBefore((byte) RdsResp.CR) != 0) {
                                                throw new RedisException("Argument doesn't end in CRLF");
                                        }
                                        in.skipBytes(2);
                                        arguments++;
                                        checkpoint();
                                } else {
                                        throw new IOException("Unexpected character");
                                }
                        }
                        try {
                                out.add(new RedisCmd(bytes));
                        } finally {
                                bytes = null;
                                arguments = 0;
                        }
                } else if (in.readByte() == MultiBulkRdsResp.MARKER) {
                        int numArgs = readInt(in);
                        if (numArgs < 0) {
                                throw new RedisException("Invalid size: " + numArgs);
                        }
                        bytes = new byte[numArgs][];
                        checkpoint();
                        decode(ctx, in, out);
                } else {
                        in.readerIndex(in.readerIndex() - 1);
                        byte[][] b = new byte[1][];
                        b[0] = in.readBytes(in.bytesBefore((byte) CR)).array();
                        in.skipBytes(2);
                        out.add(new RedisCmd(b));
                }
        }

        public int readInt(ByteBuf is) throws IOException {
                int size = 0;
                int sign = 1;
                int read = is.readByte();
                if (read == '-') {
                        read = is.readByte();
                        sign = -1;
                }
                do {
                        if (read == CR) {
                                if (is.readByte() == RdsResp.LF) {
                                        break;
                                }
                        }
                        int value = read - '0';
                        if (value >= 0 && value < 10) {
                                size *= 10;
                                size += value;
                        } else {
                                throw new IOException("Invalid character in integer");
                        }
                        read = is.readByte();
                } while (true);
                return size * sign;
        }

}
