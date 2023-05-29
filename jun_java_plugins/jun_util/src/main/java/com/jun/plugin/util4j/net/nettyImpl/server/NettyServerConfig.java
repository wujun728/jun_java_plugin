package com.jun.plugin.util4j.net.nettyImpl.server;

import com.jun.plugin.util4j.thread.NamedThreadFactory;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;

/**
 * 默认后台线程
 * @author Administrator
 */
public class NettyServerConfig {

	protected final Class<? extends ServerChannel> channelClass;
	protected final EventLoopGroup boss;
	protected final EventLoopGroup ioWorkers;
	
	/**
	 * 设置日志记录的级别
	 * 当日志级别不为空时,则设置日志记录器
	 */
	protected LogLevel level;
	
	/**
	 * 链路日志级别,可打印字节信息
	 */
	protected LogLevel channelLevel;
	
	public NettyServerConfig() {
		this(0, 0);
	}
	
	public NettyServerConfig(int bossThreads,int ioThreads) {
		EventLoopGroup bossGroup;
		EventLoopGroup workerGroup;
		Class<? extends ServerChannel> channelClass;
		if (Epoll.isAvailable()) {
			channelClass = EpollServerSocketChannel.class;
			bossGroup = new EpollEventLoopGroup(bossThreads,new NamedThreadFactory("ServerConfig-boss",true));
			workerGroup = new EpollEventLoopGroup(ioThreads,new NamedThreadFactory("ServerConfig-ioWorkers",true));
		} else {
			channelClass = NioServerSocketChannel.class;
			bossGroup = new NioEventLoopGroup(bossThreads,new NamedThreadFactory("ServerConfig-boss",true));
			workerGroup = new NioEventLoopGroup(ioThreads,new NamedThreadFactory("ServerConfig-ioWorkers",true));
		}
		this.channelClass = channelClass;
		this.boss = bossGroup;
		this.ioWorkers = workerGroup;
	}
	
	public NettyServerConfig(Class<? extends ServerChannel> channelClass, EventLoopGroup boss,EventLoopGroup ioworkers) {
		this.channelClass = channelClass;
		this.boss = boss;
		this.ioWorkers = ioworkers;
	}
	
	public Class<? extends ServerChannel> getChannelClass() {
		return channelClass;
	}
	public EventLoopGroup getBoss() {
		return boss;
	}
	public EventLoopGroup getIoworkers() {
		return ioWorkers;
	}
	
	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public LogLevel getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(LogLevel channelLevel) {
		this.channelLevel = channelLevel;
	}

	public void destory()
	{
		if(boss!=null)
		{
			boss.shutdownGracefully();
		}
		if(ioWorkers!=null)
		{
			ioWorkers.shutdownGracefully();
		}
	}
}
