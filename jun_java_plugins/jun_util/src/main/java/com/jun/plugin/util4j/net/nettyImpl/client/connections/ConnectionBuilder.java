package com.jun.plugin.util4j.net.nettyImpl.client.connections;

import java.net.InetSocketAddress;

import com.jun.plugin.util4j.net.nettyImpl.client.NettyClientConfig;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class ConnectionBuilder extends NettyClientConfig{
	
	public ConnectionBuilder() {
		super();
	}

	public ConnectionBuilder(Class<? extends SocketChannel> channelClass, EventLoopGroup ioWorkers) {
		super(channelClass, ioWorkers);
	}

	public ConnectionBuilder(int ioThreads) {
		super(ioThreads);
	}

	public final ChannelFuture connect(InetSocketAddress address)
	{
		return doBooterConnect(address, null);
	}
	
	public final ChannelFuture connect(InetSocketAddress address,ChannelHandler handler)
	{
		return doBooterConnect(address,handler);
	}
	
	public static void main(String[] args) {
		ConnectionBuilder cb=new ConnectionBuilder();
		ChannelFuture cf=cb.connect(new InetSocketAddress("127.0.0.1", 4000));
		System.out.println(cf.channel());
	}
}
