package com.jun.plugin.util4j.net.nettyImpl.handler.websocket;

import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.internal.logging.InternalLogger;

/**
 * websocket服务端handler适配器
 * 通道注册时配置解码器
 * 通道激活时再配置监听器
 * @author Administrator
 */
@Sharable
public abstract class WebSocketServerInitializer extends ChannelInitializer<Channel>{
	
	protected final InternalLogger log = NetLogFactory.getLogger(getClass());
	
	protected final String uri;
	
	public WebSocketServerInitializer(String uri) {
		this.uri=uri;
	}

	@Override
	protected final void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline=ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new WebSocketServerProtocolHandler(uri));
        pipeline.addLast(new WebSocketConnectedServerHandler());//连接成功监听handler
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
	private class WebSocketConnectedServerHandler extends ChannelInboundHandlerAdapter
	{
		@SuppressWarnings("deprecation")
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {
			if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE)
			{//旧版本
				log.debug("excute webSocketHandComplete……");
				webSocketHandComplete(ctx);
				ctx.pipeline().remove(this);
				log.debug("excuted webSocketHandComplete:"+ctx.pipeline().toMap().toString());
				return;
			}
			if(evt instanceof HandshakeComplete)
			{//新版本
				HandshakeComplete hc=(HandshakeComplete)evt;
				log.debug("excute webSocketHandComplete……,HandshakeComplete="+hc);
				webSocketHandComplete(ctx);
				ctx.pipeline().remove(this);
				log.debug("excuted webSocketHandComplete:"+ctx.pipeline().toMap().toString());
				return;
			}
			super.userEventTriggered(ctx, evt);
		}
	}
}
