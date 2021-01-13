package com.gs.mina;

import com.gs.Constants;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Wang Genshen on 2017-04-18.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        IoSessionConfig sessionConfig = acceptor.getSessionConfig();
        sessionConfig.setReadBufferSize(1024);
        sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 10);
        DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
        filterChain.addLast("logger", new LoggingFilter());
        filterChain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(Constants.CHARSET))));
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(Constants.PORT));
    }
}
