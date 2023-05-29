package com.jun.plugin.util4j.net.nettyImpl.handler.listenerHandler;

import java.util.concurrent.TimeUnit;

import com.jun.plugin.util4j.net.JConnection;
import com.jun.plugin.util4j.net.JConnectionIdleListener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 具有心跳机制的chanle 监听适配器
 * 该handler必须放在编码解码器handler后面才能起作用
 * @author Administrator
 * @param <M>
 */
@Sharable
public class DefaultIdleListenerHandler<M> extends AbstractListenerHandler<M,JConnectionIdleListener<M>>{

	public DefaultIdleListenerHandler(JConnectionIdleListener<M> listener) {
		super(listener);
	}
	
	@Override
	public final void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) 
		{
			Channel channel=ctx.channel();
			JConnection connection = findConnection(channel);
			if (connection != null) 
			{
				IdleStateEvent event = (IdleStateEvent) evt;
				switch (event.state()) {
				case ALL_IDLE:
					listener.event_AllIdleTimeOut(connection);
					break;
				case READER_IDLE:
					listener.event_ReadIdleTimeOut(connection);
					break;
				case WRITER_IDLE:
					listener.event_WriteIdleTimeOut(connection);
					break;
				default:
					break;
				}
			} else {
				log.error(ctx.channel() + ":not found NettyConnection Created.");
			}
		}
		super.userEventTriggered(ctx, evt);
	}

	/**
	 * 和监听器相关的读写超时监测handler 不使用IdleStateHandler以防影响其它扩展
	 * 
	 * @author Administrator
	 */
	class ListenerIdleHandler extends IdleStateHandler {
		public ListenerIdleHandler(int readerIdleTimeSeconds,
				int writerIdleTimeSeconds, int allIdleTimeSeconds) {
			super(readerIdleTimeSeconds, writerIdleTimeSeconds,
					allIdleTimeSeconds);
		}

		public ListenerIdleHandler(long readerIdleTime, long writerIdleTime,
				long allIdleTime, TimeUnit unit) {
			super(readerIdleTime, writerIdleTime, allIdleTime, unit);
		}
	}

	protected String getIdleHandlerName(ChannelHandlerContext ctx)
	{
		return "ListenerIdleHandle("+ctx.channel().hashCode()+")";
	}
	
	private ListenerIdleHandler handler;
	
	@Override
	public final void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		@SuppressWarnings("unchecked")
		ListenerIdleHandler oldHandler=ctx.pipeline().get(ListenerIdleHandler.class);
		if(oldHandler!=null)
		{
			log.error("old Handler:"+oldHandler);
		}
		handler=new ListenerIdleHandler(listener.getReaderIdleTimeMills(), listener.getWriterIdleTimeMills(),listener.getAllIdleTimeMills(),TimeUnit.MILLISECONDS);
		// 当前ctx名字就是当前handler加入pipe的名字
		ctx.pipeline().addBefore(ctx.name(),getIdleHandlerName(ctx), handler);
		super.handlerAdded(ctx);
	}

	@Override
	public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
	}
}
