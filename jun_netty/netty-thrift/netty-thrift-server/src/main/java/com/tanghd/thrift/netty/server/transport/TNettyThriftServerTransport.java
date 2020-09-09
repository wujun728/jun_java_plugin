package com.tanghd.thrift.netty.server.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.tanghd.thrift.netty.common.message.TNettyThriftDef;
import com.tanghd.thrift.netty.server.server.TNettyThriftServerInitializer;
import com.tanghd.thrift.netty.server.server.TNettyThriftServerDef;

public class TNettyThriftServerTransport extends TServerTransport {

    private int serverPort;

    private int clientTimeout;

    private ServerBootstrap bootstrap;

    private ChannelFuture channelFuture;

    private TNettyThriftServerDef serverDef;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public TNettyThriftServerTransport(int port) {
        this(port, 0);
    }

    public TNettyThriftServerTransport(int port, int timeout) {
        this.serverPort = port;
        this.clientTimeout = timeout;
    }

    @Override
    public void listen() throws TTransportException {
        try {
            if (null != serverDef.getEventHandler()) {
                serverDef.getEventHandler();
            }

            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TNettyThriftServerInitializer(serverDef));

            if (clientTimeout > 0) {
                bootstrap.childOption(ChannelOption.SO_TIMEOUT, clientTimeout);
            }

            channelFuture = bootstrap.bind(serverPort);
            channelFuture.sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException ie) {
            throw new TTransportException(ie);
        } finally {
            close();
        }

    }

    @Override
    public void close() {
        try {
            channelFuture.channel().close();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    protected TTransport acceptImpl() throws TTransportException {
        return null;
    }

    public TNettyThriftServerDef getServerDef() {
        return serverDef;
    }

    public void setServerDef(TNettyThriftServerDef serverDef) {
        this.serverDef = serverDef;
    }

}
