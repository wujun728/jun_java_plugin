package com.jun.plugin.util4j.net.nettyImpl.handler.websocket.text;

import com.jun.plugin.util4j.net.nettyImpl.handler.websocket.WebSocketClientInitializer;
import com.jun.plugin.util4j.net.nettyImpl.handler.websocket.text.codec.WebSocketTextFrameByteBufAdapter;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * websocket客户端handler适配器
 * @author Administrator
 */
public  class WebSocketClientTextAdapterHandler extends WebSocketClientInitializer{
	
	private ChannelHandler handler;
	public WebSocketClientTextAdapterHandler(String uri,ChannelHandler handler) {
		super(uri);
		this.handler=handler;
	}

	@Override
	protected void webSocketHandComplete(ChannelHandlerContext ctx) {
		ctx.channel().pipeline().addLast(new WebSocketTextFrameByteBufAdapter());//适配器
		ctx.channel().pipeline().addLast(handler);
		//为新加的handler手动触发必要事件
		ctx.fireChannelRegistered();
		ctx.fireChannelActive();
	}
}
