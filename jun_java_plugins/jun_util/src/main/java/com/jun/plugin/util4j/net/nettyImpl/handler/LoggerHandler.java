package com.jun.plugin.util4j.net.nettyImpl.handler;

import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class LoggerHandler extends LoggingHandler{
	
	public LoggerHandler() {
		super();
	}

	public LoggerHandler(Class<?> clazz, LogLevel level) {
		super(clazz, level);
	}

	public LoggerHandler(Class<?> clazz) {
		super(clazz);
	}

	public LoggerHandler(LogLevel level) {
		super(level);
	}

	public LoggerHandler(String name, LogLevel level) {
		super(name, level);
	}

	public LoggerHandler(String name) {
		super(name);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof IOException)
		{
			if (logger.isEnabled(internalLevel)) 
			{
				logger.log(internalLevel,cause.getClass().getName()+":"+cause.getLocalizedMessage());
		    }
			return;
		}
		if (logger.isEnabled(internalLevel)) 
		{
			logger.log(internalLevel, format(ctx, "EXCEPTION", cause),cause);
	    }
		ctx.fireExceptionCaught(cause);
	}
}
