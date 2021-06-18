package com.jun.plugin.fastnetty.server.impl;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * udp server
 *
 * @author peiyu
 */
public class NettyUDPServer extends AbstractNettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyUDPServer.class);

    private EventLoopGroup workGroup;

    public NettyUDPServer() {
        createServerBootstrap();
    }

    @Override
    public ServerType getServerType() {
        return ServerType.UDP;
    }

    AbstractBootstrap createServerBootstrap() {
        this.serverBootstrap = new Bootstrap();
        this.workGroup = new NioEventLoopGroup(this.workThreadSize);
        this.serverBootstrap.group(this.workGroup);
        this.serverBootstrap.channel(NioDatagramChannel.class);
        this.serverBootstrap.handler(new ChannelInitializer<DatagramChannel>() {
            @Override
            protected void initChannel(DatagramChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                NettyMessageHandler messageHandler = new NettyMessageHandler();
                messageHandler.setHandlers(messageHandlers);
                pipeline.addLast(LOGGING_HANDLER, messageHandler, messageCodec);
            }
        });
        return this.serverBootstrap;
    }

    @Override
    public void stopServer() throws Exception {
        LOG.debug("stop the udp server:{}", getClass().getName());
        this.workGroup.shutdownGracefully();
    }
}
