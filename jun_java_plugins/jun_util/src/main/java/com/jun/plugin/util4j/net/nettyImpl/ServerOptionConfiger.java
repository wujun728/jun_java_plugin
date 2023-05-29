package com.jun.plugin.util4j.net.nettyImpl;

import io.netty.channel.ChannelOption;

public interface ServerOptionConfiger extends OptionConfiger{

	public <T> ServerOptionConfiger option(ChannelOption<T> option, T value);
	
	public <T> ServerOptionConfiger childOption(ChannelOption<T> option, T value);
}
