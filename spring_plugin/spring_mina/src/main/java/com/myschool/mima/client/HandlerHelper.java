/*
 * @(#)HandlerHelper.java 2014-12-15 上午10:19:49
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.myschool.mima.bean.Message;

/**
 * <p>File：HandlerHelper.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-15 上午10:19:49</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class HandlerHelper
{
	/**
	 * 端口
	 */
	private String host;
	/**
	 * 监听的地址
	 */
	private Integer port;
	
	/**
	 * 超时时间
	 * 60秒
	 */
	private Integer timeout = 60;
	
	
	private IoConnector ioConnector;
	
	/**
	 * handler 处理器
	 */
	private IoHandler ioHandler;
	
	
	
	private IoSession ioSession;
	

	
	public HandlerHelper newIoSession(){
		if(ioConnector == null){
			ioConnector = new NioSocketConnector();
		}
		
		ioConnector.setConnectTimeoutMillis(timeout);
		DefaultIoFilterChainBuilder filterChain = ioConnector.getFilterChain();
		filterChain.addLast("logging", new LoggingFilter());
		filterChain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		filterChain.addLast("mdc", new MdcInjectionFilter());
		//filterChain.addLast("keepAlive", getKeepAlive());
	//	filterChain.setFilters(filters);
		ioConnector.setHandler(ioHandler);
		ConnectFuture future = ioConnector.connect(new InetSocketAddress(host, port));			//创建链接
		future.awaitUninterruptibly();// 等待连接创建完成
		this.ioSession = future.getSession();
        return this;
	}
	
	
	
	/**
	 * 释放客户端业务处理线程
	 */
	public void destroy(){
		
		 // 释放客户端业务处理线程
        if (null != ioSession){
        	ioSession.getCloseFuture().awaitUninterruptibly();// 等待连接断开
        }
        if (null != ioConnector){
        	ioConnector.dispose();
        }
	}
	
	
	
	/**
	 * 信息发送
	 */
	public HandlerHelper sendMsg(Message msg){
		ioSession.write(msg);
		return this;
	}



	public void setIoHandler(IoHandler ioHandler)
	{
		this.ioHandler = ioHandler;
	}


	public void setHost(String host)
	{
		this.host = host;
	}


	public void setPort(Integer port)
	{
		this.port = port;
	}


	public void setTimeout(Integer timeout)
	{
		this.timeout = timeout;
	}
	
	/*********************************************************************************************************************/
	/**
	 * 发送心跳包
	 * 客户端不发送心跳包
	 *//*
	public void heartBert(){
		
	}*/
	
	/**
	 * 添加心跳过滤器
	 *//*
	private KeepAliveFilter getKeepAlive(){
		KeepAliveMessageFactory Kafy = new KeepAliveMessageFactoryImpl();
		//说明：实例化一个  KeepAliveFilter  过滤器，传入 KeepAliveMessageFactory引用，IdleStatus参数为 BOTH_IDLE,及表明如果当前连接的读写通道都空闲的时候在指定的时间间隔getRequestInterval后发送出发Idle事件。
		KeepAliveFilter kaf = new KeepAliveFilter(Kafy, IdleStatus.BOTH_IDLE);	//
		
		//尤其 注意该句话，使用了 KeepAliveFilter之后，IoHandlerAdapter中的 sessionIdle方法默认是不会再被调用的！ 所以必须加入这句话 sessionIdle才会被调用
		kaf.setForwardEvent(true); //idle事件回发  当session进入idle状态的时候 依然调用handler中的idled方法
		
		////本服务器为被定型心跳  即需要每10秒接受一个心跳请求  否则该连接进入空闲状态 并且发出idled方法回调
		kaf.setRequestInterval(heartPeriod); 
		
		kaf.setRequestTimeout(timeout); //超时时间   如果当前发出一个心跳请求后需要反馈  若反馈超过此事件 默认则关闭连接
		return kaf;
	}*/
	
	
	
}
