package com.jun.plugin.util4j.net.nettyImpl;

import io.netty.channel.ChannelOption;

public interface OptionConfiger {

	public <T> OptionConfiger option(ChannelOption<T> option, T value);
}
