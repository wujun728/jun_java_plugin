package com.jun.plugin.util4j.net.nettyImpl.handler.protobuf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage.ExtendableMessage;
import com.jun.plugin.util4j.net.JConnectionIdleListener;
import com.jun.plugin.util4j.net.nettyImpl.handler.listenerHandler.DefaultIdleListenerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * protobuf协议handler
 * T建议为可扩展类型的
 */
public class InitalizerProtobufHandler<T extends ExtendableMessage<T>> extends ChannelInitializer<SocketChannel> {
	private JConnectionIdleListener<T> listener;
	private T prototype;
	private ExtensionRegistry extensionRegistry;

	public InitalizerProtobufHandler(JConnectionIdleListener<T> listener,T prototype,ExtensionRegistry extensionRegistry) {
		this.listener = listener;
		this.prototype=prototype;
		this.extensionRegistry=extensionRegistry;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ProtobufVarint32FrameDecoder());
		p.addLast(new ProtobufDecoder(prototype,extensionRegistry));
		p.addLast(new ProtobufVarint32LengthFieldPrepender());
		p.addLast(new ProtobufEncoder());
		p.addLast(new DefaultIdleListenerHandler<T>(listener));
	}
}
