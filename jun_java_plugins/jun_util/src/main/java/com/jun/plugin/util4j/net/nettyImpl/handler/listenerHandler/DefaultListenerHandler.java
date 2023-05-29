package com.jun.plugin.util4j.net.nettyImpl.handler.listenerHandler;

import com.jun.plugin.util4j.net.JConnectionListener;

import io.netty.channel.ChannelHandler.Sharable;

/**
 * 链路监听适配器
 * @author Administrator
 * @param <M>
 */
@Sharable
public class DefaultListenerHandler<M> extends AbstractListenerHandler<M,JConnectionListener<M>>{

	public DefaultListenerHandler(JConnectionListener<M> listener) {
		super(listener);
	}
}
