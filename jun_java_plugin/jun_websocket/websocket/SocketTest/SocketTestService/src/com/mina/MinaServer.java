package com.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SocketAcceptor acceptor = new NioSocketAcceptor();
		 acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
		acceptor.setHandler(new ServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);  
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		int bindPoit = 9800;
		try {
			acceptor.bind(new InetSocketAddress(bindPoit));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
