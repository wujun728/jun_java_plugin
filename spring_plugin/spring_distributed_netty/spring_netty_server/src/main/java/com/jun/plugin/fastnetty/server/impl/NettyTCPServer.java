package com.jun.plugin.fastnetty.server.impl;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * tcp server
 *
 * @author Wujun
 */
public class NettyTCPServer extends AbstractNettyServer {

    private static final Logger         LOG            = LoggerFactory.getLogger(NettyTCPServer.class);
    /**
     * 用于分配处理业务线程的线程组个数
     */
    private static final int            BIZ_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;    //默认
    private final        EventLoopGroup BOSS_GROUP     = new NioEventLoopGroup(BIZ_GROUP_SIZE);
    private EventLoopGroup workGroup;

    public NettyTCPServer() {
        createServerBootstrap();
    }

    @Override
    public ServerType getServerType() {
        return ServerType.TCP;
    }

    AbstractBootstrap createServerBootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        this.workGroup = new NioEventLoopGroup(this.workThreadSize);
        bootstrap.group(BOSS_GROUP, this.workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                NettyMessageHandler messageHandler = new NettyMessageHandler();
                messageHandler.setHandlers(messageHandlers);
                pipeline.addLast(LOGGING_HANDLER, messageHandler, messageCodec);
            }
        });
        this.serverBootstrap = bootstrap;
        return this.serverBootstrap;
    }

    @Override
    public void stopServer() throws Exception {
        LOG.debug("stop the tcp server:{}", getClass().getName());
        BOSS_GROUP.shutdownGracefully();
        this.workGroup.shutdownGracefully();
    }
}
