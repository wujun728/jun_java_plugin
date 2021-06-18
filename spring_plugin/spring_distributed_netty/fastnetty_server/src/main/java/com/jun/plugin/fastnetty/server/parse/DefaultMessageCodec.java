package com.jun.plugin.fastnetty.server.parse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import com.jun.plugin.fastnetty.core.message.OutputMessage;

/**
 * @author Wujun
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
