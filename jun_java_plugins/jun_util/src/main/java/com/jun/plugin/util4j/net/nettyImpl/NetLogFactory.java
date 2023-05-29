package com.jun.plugin.util4j.net.nettyImpl;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class NetLogFactory {

	public static InternalLogger getLogger(Class<?> c)
	{
		return InternalLoggerFactory.getInstance(c);
	}
}
