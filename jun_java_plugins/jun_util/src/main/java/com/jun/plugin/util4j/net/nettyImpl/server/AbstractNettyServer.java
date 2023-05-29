package com.jun.plugin.util4j.net.nettyImpl.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;

import java.net.InetSocketAddress;

import com.jun.plugin.util4j.net.JNetServer;
import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;

/**
 * 抽象服务端基类
 * 实现服务端生命周期和绑定监听
 * @author Administrator
 */
public abstract class AbstractNettyServer implements JNetServer{
	
	private static final InternalLogger log = NetLogFactory.getLogger(AbstractNettyServer.class); 
	protected final InetSocketAddress local;
	private String name="NettyServer";
	protected ServerSocketChannel serverCahnel;
	
	public AbstractNettyServer(InetSocketAddress local) {
		this.local=local;
	}
	
	/**
	 * 获取服务端使用的起动器
	 * @return
	 */
	public abstract ServerBootstrap getBooter();
	/**
	 * 获取服务端使用的IO线程池
	 * @return
	 */
	public abstract EventLoopGroup getIoWorkers();
	/**
	 * 获取服务端使用的选择线程池
	 * @return
	 */
	public abstract EventLoopGroup getBossWorkers();
	
	/**
	 * 启动端口绑定
	 * @param local
	 * @return
	 */
	protected final boolean bind(InetSocketAddress local)
	{
		boolean isBind=false;
		try {
			log.debug(getName()+"端口绑定中……"+local.toString());
			ChannelFuture cf=doBind(local);
			isBind=cf.channel()!=null && cf.channel().isActive();
			if(isBind)
			{
				log.debug(getName()+"端口绑定成功!"+cf.channel());
				serverCahnel=(ServerSocketChannel) cf.channel();
			}else
			{
				log.debug(getName()+"端口绑定失败!"+cf.channel());
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		return isBind;
	}

	/**
	 * 使用起动器进行端口绑定
	 * @param local
	 * @return
	 */
	protected abstract ChannelFuture doBind(InetSocketAddress local);
	
	@Override
	public final boolean start() {
		return bind(local);
	}

	@Override
	public final void stop() {
		if(serverCahnel!=null)
		{
			serverCahnel.close();
		}
	}

	@Override
	public boolean isActive() {
		return serverCahnel!=null && serverCahnel.isActive();
	}
	
	@Override
	public final String getName() {
		return name;
	}
	@Override
	public final void setName(String name) {
		if(name!=null)
		{
			this.name=name;
		}
	}
}
