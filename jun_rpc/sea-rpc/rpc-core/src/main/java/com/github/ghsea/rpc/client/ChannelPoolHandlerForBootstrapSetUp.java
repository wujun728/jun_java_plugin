package com.github.ghsea.rpc.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.client.codec.RpcRequestMsgEncoder;
import com.github.ghsea.rpc.client.codec.RpcResponseMsgDecoder;
import com.github.ghsea.rpc.core.RpcResponseFuture;
import com.github.ghsea.rpc.core.message.RpcResponseMsg;

/**
 * https://github.com/netty/netty/issues/3770
 * FixedChannelPool不再使用Bootstrap的channel intializer
 * 
 * @author ghsea 2017-4-15下午7:21:52
 */
public class ChannelPoolHandlerForBootstrapSetUp implements io.netty.channel.pool.ChannelPoolHandler {

	private final NettyClient client;

	private Logger log = LoggerFactory.getLogger(ChannelPoolHandlerForBootstrapSetUp.class);

	private volatile AtomicBoolean inited = new AtomicBoolean(false);

	public ChannelPoolHandlerForBootstrapSetUp(NettyClient client) {
		this.client = client;
	}

	@Override
	public void channelReleased(Channel ch) throws Exception {
		log.debug("channelReleased");
	}

	@Override
	public void channelAcquired(Channel ch) throws Exception {
		log.debug("channelAcquired");
	}

	@Override
	public void channelCreated(Channel ch) throws Exception {
		if (inited.compareAndSet(false, true)) {
			setUpPipeline(ch);
		}
	}

	private void setUpPipeline(final Channel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LengthFieldPrepender(2)).addLast(new RpcRequestMsgEncoder());

		// TODO maxLenght=1024 从配置文件读取
		pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2))
				.addLast(new RpcResponseMsgDecoder()).addLast(new RpcResponseHandler());
	}

	private class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponseMsg> {

		private Logger log = LoggerFactory.getLogger(RpcResponseHandler.class);

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMsg response) throws Exception {
			log.debug("Client received RpcResponseMsg: {} ", response);

			String requestId = response.getRequestId();
			RpcResponseFuture future = client.getFuture(requestId);
			if (future == null) {
				// TODO do nothing??? future已经被清掉了，可能是由于timeout被Timer清掉了
				return;
			}

			future.operationComplete(response);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			log.error(cause.getMessage(), cause);
			super.exceptionCaught(ctx, cause);
		}
	}

}
