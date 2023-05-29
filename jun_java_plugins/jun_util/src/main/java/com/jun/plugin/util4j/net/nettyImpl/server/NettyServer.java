package com.jun.plugin.util4j.net.nettyImpl.server;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.jun.plugin.util4j.net.JConnection;
import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;
import com.jun.plugin.util4j.net.nettyImpl.NettyConnection;
import com.jun.plugin.util4j.net.nettyImpl.ServerOptionConfiger;
import com.jun.plugin.util4j.net.nettyImpl.handler.LoggerHandler;
import com.jun.plugin.util4j.net.nettyImpl.handler.ShareableChannelInboundHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.internal.logging.InternalLogger;

public class NettyServer extends AbstractNettyServer{

	protected final ChannelGroup channelGroup=new DefaultChannelGroup(getName()+"ChannelGroup",ImmediateEventExecutor.INSTANCE);
	protected final NettyServerConfig config;
	protected final ServerBootstrap booter=new ServerBootstrap();
	private static final InternalLogger log = NetLogFactory.getLogger(AbstractNettyServer.class); 
	
	protected final ChannelHandler handler;
	
	public NettyServer(String host,int port,ChannelHandler handler) {
		this(new InetSocketAddress(host, port),handler);
	}
	
	public NettyServer(InetSocketAddress local,ChannelHandler handler) {
		this(new NettyServerConfig(), local, handler);
	}
	
	public NettyServer(NettyServerConfig config,String host,int port,ChannelHandler handler) {
		this(config, new InetSocketAddress(host, port),handler);
	}
	
	public NettyServer(NettyServerConfig config,InetSocketAddress local,ChannelHandler handler) {
		super(local);
		this.config=config;
		this.handler=handler;
		initBooter();
	}

	private void initBooter()
	{
		booter.group(config.getBoss(), config.getIoworkers());
		booter.channel(config.getChannelClass());
	}
	
	protected void initServerOptions(ServerOptionConfiger configer){
		configer.option(ChannelOption.SO_BACKLOG, 1024);//设置连接等待最大队列
		configer.option(ChannelOption.TCP_NODELAY,true);
		configer.option(ChannelOption.SO_KEEPALIVE, true);//设置保持连接
	}
	
	public ServerOptionConfiger optionConfig()
	{
		return new ServerOptionConfiger() {
			@Override
			public <T> ServerOptionConfiger option(ChannelOption<T> option, T value) {
				booter.option(option, value);
		        return this;
			}

			@Override
			public <T> ServerOptionConfiger childOption(ChannelOption<T> option, T value) {
				booter.childOption(option, value);
				return this;
			}
		};
	}
	
	protected void manageChannel(Channel channel)
	{
		channelGroup.add(channel);
		channel.closeFuture().addListener(new ChannelFutureListener(){
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				channelGroup.remove(future.channel());
			}
		});
	}
	
	/**
	 * 初始化handler适配包装
	 * @param init
	 * @return
	 */
	protected ChannelHandler initLogHandlerAdapter(ChannelHandler init)
	{
		ChannelHandler handler=new  ShareableChannelInboundHandler() {
			@Override
			public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
				Channel ch=ctx.channel();
				manageChannel(ch);
				LogLevel level=config.getChannelLevel();
				if(level!=null)
				{//单个链路的日志记录器
					ch.pipeline().addLast(new LoggerHandler(level));
				}
				ch.pipeline().addLast(init);
				ctx.pipeline().remove(this);//移除当前handler
				ctx.fireChannelRegistered();//从当前handler往后抛出事件
			}
		};
//		ChannelHandler handler=new ChannelInitializer<Channel>() {
//			@Override
//			protected void initChannel(Channel ch) throws Exception {
//				channelGroup.add(ch);
//				LogLevel level=config.getLevel();
//				if(level!=null)
//				{
//					ch.pipeline().addLast(new LoggerHandler(config.getLevel()));
//				}
//				ch.pipeline().addLast(init);
//			}
//		};
		return handler;
	}
	
	/**
	 * 执行启动绑定之前修正handler,子类可进行二次修改
	 * @param handler
	 * @return
	 */
	protected ChannelHandler fixHandlerBeforeDoBooterBind(ChannelHandler handler)
	{
		return handler;
	}
	
	@Override
	protected final ChannelFuture doBind(InetSocketAddress local) {
		booter.localAddress(local);
		initServerOptions(optionConfig());
		ChannelHandler fixedHandler=fixHandlerBeforeDoBooterBind(handler);//修正handler
		return doBooterBind(local,fixedHandler);//启动端口绑定
	}
	
	protected ChannelFuture doBooterBind(InetSocketAddress local,final ChannelHandler fixedHandler) {
		ChannelFuture cf;
		synchronized (booter) {
			final CountDownLatch latch=new CountDownLatch(1);
			LoggerHandler loggerHandler=null;//server接收处理链路的日志记录器
			LogLevel level=config.getLevel();
			if(level!=null)
			{
				loggerHandler=new LoggerHandler(level);
			}
			ChannelHandler childHandler=initLogHandlerAdapter(fixedHandler);
			booter.handler(loggerHandler).childHandler(childHandler);
			cf=booter.bind(local);
			cf.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					latch.countDown();
				}
			});
			try {
				latch.await(3,TimeUnit.SECONDS);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return cf;
	}

	@Override
	public ServerBootstrap getBooter() {
		return this.booter;
	}

	@Override
	public EventLoopGroup getIoWorkers() {
		return this.config.boss;
	}

	@Override
	public EventLoopGroup getBossWorkers() {
		return this.config.ioWorkers;
	}
	
	public ChannelGroup getChannelGroup()
	{
		return channelGroup;
	}
	
	@Override
	public JConnection getConnection(long id) {
		if(channelGroup!=null)
		{
			Iterator<Channel> it=channelGroup.iterator();
			while(it.hasNext())
			{
				Channel channel=it.next();
				if(NettyConnection.getChannelId(channel)==id)
				{
					return NettyConnection.findConnection(channel);
				}
			}
		}
		return null;
	}

	@Override
	public Set<JConnection> getConnections() {
		Set<JConnection> connections=new HashSet<JConnection>();
		if(channelGroup!=null)
		{
			Iterator<Channel> it=channelGroup.iterator();
			while(it.hasNext())
			{
				Channel channel=it.next();
				NettyConnection conn=NettyConnection.findConnection(channel);
				if(conn!=null)
				{
					connections.add(conn);
				}
			}
		}
		return connections;
	}
	public final void broadCast(Object message)
	{
		channelGroup.writeAndFlush(message);
		log.debug("broadCast message total:"+channelGroup.size()+",type:"+message);
	}

	@Override
	public int getConnectionCount() {
		return channelGroup.size();
	}
}
