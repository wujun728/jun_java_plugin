package com.tanghd.thrift.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.tanghd.thrift.netty.common.message.TNettyThriftMessage;
import com.tanghd.thrift.netty.common.transport.TNettyThriftTransport;
import com.tanghd.thrift.netty.server.processor.TNettyThriftServerProcessor;
import com.tanghd.thrift.netty.server.server.TNettyThriftServerDef;

public class TNettyThriftServerHandler extends SimpleChannelInboundHandler<TNettyThriftMessage> {

    private static final String name = "MESSAGE_HANDLER";

    private TNettyThriftServerDef serverDef;

    public TNettyThriftServerHandler(TNettyThriftServerDef serverDef) {
        this.serverDef = serverDef;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TNettyThriftMessage msg) throws Exception {
        TNettyThriftTransport transport = new TNettyThriftTransport(msg);
        TNettyThriftServerProcessor processor = new TNettyThriftServerProcessor(serverDef, transport);
        processor.invoke();
        ctx.writeAndFlush(msg);
    }

    public static String getName() {
        return name;
    }

}