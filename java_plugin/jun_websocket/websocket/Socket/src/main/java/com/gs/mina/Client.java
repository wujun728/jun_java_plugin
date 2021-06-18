package com.gs.mina;

import com.gs.Constants;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Wang Genshen on 2017-04-18.
 */
public class Client {

    public static void main(String[] args) {
        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
        filterChain.addLast("logger", new LoggingFilter());
        filterChain.addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(Constants.CHARSET))));
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new ClientHandler());
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(Constants.IP, Constants.PORT));
        connectFuture.awaitUninterruptibly();
        NioSession session = (NioSession) connectFuture.getSession();
        session.write("Hi Server!");
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
