/*
 * @(#)ServerAcceptor.java 2014-12-16 上午11:22:44
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter.MdcKey;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.myschool.mima.keepAlive.KeepAliveMessageFactoryImpl;
import com.myschool.mima.keepAlive.KeepAliveRequestTimeoutHandlerImpl;

/**
 * <p>File：ServerAcceptor.java</p>
 * <p>Title: TCP通讯启动类</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-16 上午11:22:44</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class ServerAcceptor
{

	private static final Logger logger = Logger.getLogger(ServerAcceptor.class);
	
	/**
	 * 心跳时间
	 */
	private Integer heartbeat;
	
	/**
	 * 超时时间
	 */
	private Integer idleTime;
	
	/**
	 * 地址
	 */
	private List<InetSocketAddress> address;
	
	/**
	 * 设置读取数据的缓冲区大小
	 */
	private Integer readBufferSize;
	
	/**
	 * 把小数据包拼接起来填充每个报文
	 */
	private Boolean tcpNoDelay;
	
	/**
	 * 设置发送数据的缓冲区大小  
	 */
	private Integer sendBufferSize;
	
	private SocketAcceptor acceptor ;
	
	/**
	 * 开启通讯服务
	 * @throws IOException
	 */
	public void bind() {
		logger.info("通讯服务开启中....");
		acceptor = new NioSocketAcceptor();
		// 设置核心消息业务处理器
		acceptor.setHandler(new ServerHandler());
		acceptor.getFilterChain().setFilters(getFilter());
		setSeesionConfig(acceptor.getSessionConfig());
		try
		{
			acceptor.bind(address);
		}
		catch (IOException e)
		{
			logger.error("监听地址不正确", e);
			throw new RuntimeException("监听地址不正确");
		}
		logger.info("通讯服务启动完毕....");
	}
	
	/**
	 * 关闭通讯服务
	 */
	public void unbind(){
		logger.info("通讯服务关闭中....");
		acceptor.unbind();
		logger.info("通讯服务关闭成功....");
	}
	
	
	/**
	 * 设置sessionConfig
	 * @param config
	 */
	private void setSeesionConfig(SocketSessionConfig config){
		config.setReadBufferSize(readBufferSize);				//设置读取数据的缓冲区大小  
		config.setTcpNoDelay(tcpNoDelay);						//true : 把小数据包拼接起来填充每个报文
		config.setSendBufferSize(sendBufferSize);				//设置发送数据的缓冲区大小  
		//acceptor.getSessionConfig().setBothIdleTime(idleTime);
		config.setIdleTime(IdleStatus.BOTH_IDLE,idleTime);		//设置session配置，30秒内无操作进入空闲状态
	}
	
	/**
	 * 添加过滤器链
	 * @return
	 */
	private Map<String, IoFilter> getFilter(){
		/*	loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
		loggingFilter.setMessageSentLogLevel(LogLevel.INFO);*/
		//心跳
		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
		KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE, heartBeatHandler);
		heartBeat.setForwardEvent(false);			// 是否回发    false : 空闲时不再调用IoHandlerAdapter.sessionIdle
		heartBeat.setRequestInterval(heartbeat);		//心跳频率
		Map<String, IoFilter> filters = new LinkedHashMap<String, IoFilter>();
		
		//new MyDemuxingProtocolCodecFactory(true) 自定义解码器，请根据双方协议来进行使用
		filters.put("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));      //添加编码过滤器 处理乱码、编码问题
		filters.put("loger", new LoggingFilter());			//添加日志过滤器
		filters.put("heartbeat", heartBeat);				//添加心跳
		filters.put("mdcInjectionFilter", new MdcInjectionFilter(MdcKey.remoteAddress));
		filters.put("executor", new ExecutorFilter());
		return filters;
	}


	public void setHeartbeat(Integer heartbeat)
	{
		this.heartbeat = heartbeat;
	}


	public void setIdleTime(Integer idleTime)
	{
		this.idleTime = idleTime;
	}

	public void setAddress(List<InetSocketAddress> address)
	{
		this.address = address;
	}

	public void setReadBufferSize(Integer readBufferSize)
	{
		this.readBufferSize = readBufferSize;
	}

	public void setTcpNoDelay(Boolean tcpNoDelay)
	{
		this.tcpNoDelay = tcpNoDelay;
	}

	public void setSendBufferSize(Integer sendBufferSize)
	{
		this.sendBufferSize = sendBufferSize;
	}
	
}
