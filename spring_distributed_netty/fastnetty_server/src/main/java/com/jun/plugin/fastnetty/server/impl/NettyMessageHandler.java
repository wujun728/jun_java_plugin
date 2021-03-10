package com.jun.plugin.fastnetty.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.fastnetty.handler.MessageHandler;
import com.jun.plugin.fastnetty.handler.MessageSender;
import com.jun.plugin.fastnetty.handler.SimpleMessageSender;

import java.util.Set;

/**
 * @author Wujun
 */
@Sharable
class NettyMessageHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageHandler.class);

    private Set<MessageHandler> handlers;

    public void setHandlers(Set<MessageHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        if (null != this.handlers && !this.handlers.isEmpty()) {
            MessageSender sender = new SimpleMessageSender(ctx.channel());
            byte[] bytes = buffer.array();
            for (MessageHandler handler : this.handlers) {
                try {
                    handler.handler(bytes, sender);
                } catch (Exception e) {
                    LOG.warn("handler exception..", e);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("channel error..", cause);
        ctx.close();
    }
}
