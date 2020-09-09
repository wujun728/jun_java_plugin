package com.github.ghsea.rpc.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.server.codec.RpcRequestDecoder;
import com.github.ghsea.rpc.server.codec.RpcResponseEncoder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ResponseChannelInitializer extends ChannelInitializer<SocketChannel> {

	private Logger log = LoggerFactory.getLogger(ResponseChannelInitializer.class);

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		log.debug("Server initChannel start");
		ChannelPipeline pipeline = ch.pipeline();
		//Encoder
		pipeline.addLast(new LengthFieldPrepender(2)).addLast(new RpcResponseEncoder());

		//Decoder
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Rpc Bussiness Process Thread %d ")
				.build();
		final EventExecutorGroup bizThreadGroup = new DefaultEventExecutorGroup(16, threadFactory);
		pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2)).addLast(new RpcRequestDecoder())
				.addLast(bizThreadGroup, new RequestMsgHandle());
		log.debug("Server initChannel end");
		// TODO time out handler

		// ReadTimeoutHandler a = new ReadTimeoutHandler(500);
		// * ...
		// *
		// * {@link ChannelPipeline} pipeline = ch.pipeline();
		// *
		// pipeline.addLast("decoder", new MyProtocolDecoder());
		// pipeline.addLast("encoder", new MyProtocolEncoder());
		// *
		// * // Tell the pipeline to run MyBusinessLogicHandler's event handler
		// methods
		// * // in a different thread than an I/O thread so that the I/O thread
		// is not blocked by
		// * // a time-consuming task.
		// * // If your business logic is fully asynchronous or finished very
		// quickly, you don't
		// * // need to specify a group.
		// ch.pipeline().addLast(group, "handler", new ReadTimeoutHandler());
	}
}
