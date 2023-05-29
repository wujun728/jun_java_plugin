package com.jun.plugin.util4j.net.nettyImpl.handler.websocket;

import java.net.URI;

import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.internal.logging.InternalLogger;

/**
 * websocket客户端handler适配器
 * 通道注册时配置解码器
 * 通道激活时再配置监听器
 * @author Administrator
 */
@Sharable
public abstract class WebSocketClientInitializer extends ChannelInitializer<Channel>{
	
	protected final InternalLogger log = NetLogFactory.getLogger(getClass());
	protected final String url;
	
	public WebSocketClientInitializer(String url) {
		this.url=url;
	}
	
	/**
	 * 通道注册的时候配置websocket解码handler
	 */
	@Override
	protected final void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline=ch.pipeline();
		pipeline.addLast(new HttpClientCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		pipeline.addLast(new WebSocketClientProtocolHandler(WebSocketClientHandshakerFactory.newHandshaker(new URI(url), WebSocketVersion.V13, null, false, new DefaultHttpHeaders())));
        pipeline.addLast(new WebSocketConnectedClientHandler());//连接成功监听handler
	}
	
	/**
	 * 当握手成功后调用该抽象方法
	 * 注意此方法加入的handler需要手动触发
	 * ctx.fireChannelActive()
	 * ctx.fireChannelRegistered()
	 * @param channel
	 * @throws Exception
	 */
	protected abstract void webSocketHandComplete(ChannelHandlerContext ctx);
	
	/**
	  * 用于监测WebSocketClientProtocolHandler的事件
	  * 如果发现握手成功则构建业务handler
	 * @author Administrator
	 */
	private class WebSocketConnectedClientHandler extends ChannelInboundHandlerAdapter
	{
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
				throws Exception {
			if (evt == WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE)
			{
				log.debug("excute webSocketHandComplete……");
				webSocketHandComplete(ctx);
				ctx.pipeline().remove(this);
				log.debug("excuted webSocketHandComplete:"+ctx.pipeline().toMap().toString());
			}else
			{
				super.userEventTriggered(ctx, evt);
			}
		}
	}
}
