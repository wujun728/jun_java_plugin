package com.tanghd.thrift.netty.server.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import com.tanghd.thrift.netty.common.codec.TNettyThriftByteToMessageDecoder;
import com.tanghd.thrift.netty.common.codec.TNettyThriftMessageToByteEncoder;
import com.tanghd.thrift.netty.server.handler.TNettyThriftServerHandler;

public class TNettyThriftServerInitializer extends ChannelInitializer<SocketChannel> {

    private TNettyThriftServerDef serverDef;

    public TNettyThriftServerInitializer(TNettyThriftServerDef serverDef) {
        this.serverDef = serverDef;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // message && byte
        pipeline.addLast(TNettyThriftByteToMessageDecoder.getName(),
                new TNettyThriftByteToMessageDecoder(serverDef.getMaxReadBufferLength()));
        pipeline.addLast(TNettyThriftMessageToByteEncoder.getName(), new TNettyThriftMessageToByteEncoder());

        pipeline.addLast(TNettyThriftServerHandler.getName(), new TNettyThriftServerHandler(serverDef));

    }

}
