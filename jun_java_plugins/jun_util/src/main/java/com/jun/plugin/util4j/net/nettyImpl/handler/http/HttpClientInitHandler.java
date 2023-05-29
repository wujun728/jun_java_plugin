package com.jun.plugin.util4j.net.nettyImpl.handler.http;

import com.jun.plugin.util4j.net.JConnectionListener;
import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;
import com.jun.plugin.util4j.net.nettyImpl.NettyConnection;
import com.jun.plugin.util4j.net.nettyImpl.handler.listenerHandler.DefaultListenerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.internal.logging.InternalLogger;

public class HttpClientInitHandler extends ChannelInitializer<SocketChannel> {
	protected InternalLogger log=NetLogFactory.getLogger(NettyConnection.class);
	private JConnectionListener<HttpResponse> listener;
	private SslContext sslCtx;

	public HttpClientInitHandler(JConnectionListener<HttpResponse> listener) {
		this.listener = listener;
	}
	
	/**
	 * SslContextBuilder
	 * @param listener
	 * @param sslCtx
	 */
	public HttpClientInitHandler(JConnectionListener<HttpResponse> listener,SslContext sslCtx) {
		this.listener=listener;
		this.sslCtx=sslCtx;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if(sslCtx!=null)
		{
			p.addLast(new SslHandler(sslCtx.newEngine(ch.alloc())));
		}
		p.addLast(new HttpResponseDecoder());
		//限制contentLength
		p.addLast(new HttpObjectAggregator(65536));
		p.addLast(new HttpRequestEncoder());
		//大文件传输处理
//		p.addLast(new ChunkedWriteHandler());
		p.addLast(new DefaultListenerHandler<HttpResponse>(listener));
	}
}
