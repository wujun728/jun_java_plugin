package com.github.sd4324530.fastnetty.server.parse;

import com.github.sd4324530.fastnetty.core.message.OutputMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @author peiyu
 */
public class DefaultMessageCodec extends ByteToMessageCodec<OutputMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, OutputMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.toBytes());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in);
    }
}
