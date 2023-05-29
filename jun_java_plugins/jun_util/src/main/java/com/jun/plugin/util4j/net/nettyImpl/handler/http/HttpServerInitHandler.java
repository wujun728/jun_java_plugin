package com.jun.plugin.util4j.net.nettyImpl.handler.http;

import com.jun.plugin.util4j.net.JConnectionListener;
import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;
import com.jun.plugin.util4j.net.nettyImpl.NettyConnection;
import com.jun.plugin.util4j.net.nettyImpl.handler.listenerHandler.DefaultListenerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.internal.logging.InternalLogger;

public class HttpServerInitHandler extends ChannelInitializer<SocketChannel> {
	protected InternalLogger log=NetLogFactory.getLogger(NettyConnection.class);
	private JConnectionListener<HttpRequest> listener;
	private SslContext sslCtx;

	public HttpServerInitHandler(JConnectionListener<HttpRequest> listener) {
		this.listener = listener;
	}
	
	/**
	 * SslContextBuilder
	 * @param listener
	 * @param sslCtx
	 */
	public HttpServerInitHandler(JConnectionListener<HttpRequest> listener,SslContext sslCtx) {
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
		p.addLast(new HttpResponseEncoder());//必须放在最前面,如果decoder途中需要回复消息,则decoder前面需要encoder
		p.addLast(new HttpRequestDecoder());
		p.addLast(new HttpObjectAggregator(65536));//限制contentLength
		//大文件传输处理
//		p.addLast(new ChunkedWriteHandler());
//		p.addLast(new HttpContentCompressor());
		//跨域配置
		CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build();
		p.addLast(new CorsHandler(corsConfig));
		p.addLast(new DefaultListenerHandler<HttpRequest>(listener));
	}
}
