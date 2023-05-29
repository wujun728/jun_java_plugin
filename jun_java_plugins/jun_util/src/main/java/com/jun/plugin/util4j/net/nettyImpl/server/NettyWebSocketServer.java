package com.jun.plugin.util4j.net.nettyImpl.server;

import java.net.InetSocketAddress;

import com.jun.plugin.util4j.net.nettyImpl.handler.websocket.binary.WebSocketServerBinaryAdapterHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 将WebSocket的Binary流转换为正常socket链路的服务器
 * @author Administrator
 */
public class NettyWebSocketServer extends NettyServer{
	protected final String uri;
	
	public NettyWebSocketServer(String host,int port,String uri,ChannelInboundHandlerAdapter handler) {
		super(new InetSocketAddress(host, port), handler);
		if(uri==null || uri.isEmpty())
		{
			this.uri="/";
		}else
		{
			this.uri=uri;
		}
	}
	public NettyWebSocketServer(NettyServerConfig config,String host,int port,String uri,ChannelInboundHandlerAdapter handler) {
		super(config,new InetSocketAddress(host, port), handler);
		if(uri==null || uri.isEmpty())
		{
			this.uri="/";
		}else
		{
			this.uri=uri;
		}
	}

	/**
	 * 使用websocket字节流的服务器
	 */
	@Override
	protected ChannelInboundHandlerAdapter fixHandlerBeforeDoBooterBind(ChannelHandler handler) {
		return new WebSocketServerBinaryAdapterHandler(uri, handler);
	}
}
