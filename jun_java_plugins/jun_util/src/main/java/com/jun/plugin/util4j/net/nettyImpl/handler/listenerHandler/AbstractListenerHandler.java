package com.jun.plugin.util4j.net.nettyImpl.handler.listenerHandler;

import java.io.IOException;
import java.util.Objects;

import com.jun.plugin.util4j.net.JConnection;
import com.jun.plugin.util4j.net.JConnectionListener;
import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;
import com.jun.plugin.util4j.net.nettyImpl.NettyConnection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;

/**
 * 负责chanel与JConnectionListener的绑定
 * @author Administrator
 * @param <M>
 */
abstract class AbstractListenerHandler<M,L extends JConnectionListener<M>> extends ChannelInboundHandlerAdapter{

	protected final InternalLogger log = NetLogFactory.getLogger(getClass());
	
	protected final L listener;

	public AbstractListenerHandler(L listener) {
		Objects.requireNonNull(listener);
		this.listener=listener;
	}

	@Override
	public final void channelRegistered(ChannelHandlerContext ctx)throws Exception {
		super.channelRegistered(ctx);
	}
	
	@Override
	public final void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
	}

	/**
	 * 放于心跳handler和解码器后面
	 */
	@Override
	public final void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel=ctx.channel();
		JConnection connection=buildConnection(channel);
		listener.connectionOpened(connection);
		super.channelActive(ctx);
	}

	/**
	 * 到达业务线程后需要注意msg被释放的问题
	 */
	@Override
	public final void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception 
	{
		if (msg == null) 
		{
			return;
		}
		boolean release = false;
		try {
			@SuppressWarnings("unchecked")
			M imsg = (M) msg;
			Channel channel=ctx.channel();
			JConnection connection = findConnection(channel);
			if (connection != null) {
				listener.messageArrived(connection, imsg);
				release = true;
			} else {
				log.error(ctx.channel() + ":not found NettyConnection Created.");
				ctx.fireChannelRead(msg);// 下一个handler继续处理
				release = false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			if(!release)
			{//如果出错且还没有被释放
				ctx.fireChannelRead(msg);// 下一个handler继续处理
			}
		} finally {
			if (release) {
				ReferenceCountUtil.release(msg);
			}
		}
	}

	@Override
	public final void channelInactive(ChannelHandlerContext ctx)throws Exception 
	{
		Channel channel=ctx.channel();
		JConnection connection = findConnection(channel);
		if (connection != null) 
		{
			listener.connectionClosed(connection);
		} else 
		{
			log.error(ctx.channel() + ":not found NettyConnection Created.");
		}
		super.channelInactive(ctx);
	}
	
	@Override
	public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if(cause instanceof IOException)
		{
			return;
		}
		log.error(ctx.channel() + ":"+cause.toString());
		ctx.fireExceptionCaught(cause);
	}

	/**
	 * 创建一个链接实例
	 * @return
	 */
	protected JConnection buildConnection(Channel channel){
		JConnection connection=new NettyConnection(channel);
		return connection;
	}

	/**
	 * 查找链接
	 * @param channel
	 * @return
	 */
	protected JConnection findConnection(Channel channel)
	{
		return NettyConnection.findConnection(channel);
	}
}
