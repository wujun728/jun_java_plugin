package com.jun.plugin.fastnetty.server.impl;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.fastnetty.handler.MessageHandler;
import com.jun.plugin.fastnetty.server.NettyServer;
import com.jun.plugin.fastnetty.server.parse.DefaultMessageCodec;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author peiyu
 */
public abstract class AbstractNettyServer implements NettyServer {

    private static final   Logger              LOG             = LoggerFactory.getLogger(AbstractNettyServer.class);
    protected              DefaultMessageCodec messageCodec    = new DefaultMessageCodec();
    protected static final LoggingHandler      LOGGING_HANDLER = new LoggingHandler();
    protected              int                 workThreadSize  = 4;

    protected InetSocketAddress   socketAddress;
    protected Set<MessageHandler> messageHandlers;
    protected AbstractBootstrap   serverBootstrap;

    public void setPort(int port) {
        this.socketAddress = new InetSocketAddress(port);
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return this.socketAddress;
    }

    @Override
    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public void setWorkThreadSize(int size) {
        this.workThreadSize = size;
    }

    public void setMessageHandlers(Set<MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    @Override
    public void startServer() throws Exception {
        if (null == this.socketAddress) {
            throw new NullPointerException("socketAddress is null");
        }
        startServer(this.socketAddress);
    }

    @Override
    public void startServer(int port) throws Exception {
        startServer(new InetSocketAddress(port));
    }

    @Override
    public void startServer(InetSocketAddress socketAddress) throws Exception {
        this.socketAddress = socketAddress;
        LOG.debug("start server,port:{}", socketAddress.getPort());
        this.serverBootstrap.bind(this.socketAddress.getPort()).sync();
    }
}
