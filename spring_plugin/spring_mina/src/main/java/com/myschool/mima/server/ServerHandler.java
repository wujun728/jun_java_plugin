/*
 * @(#)ServerHandler.java 2014-12-12 上午11:55:10
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myschool.mima.bean.Command;
import com.myschool.mima.bean.Message;

/**
 * <p>File：ServerHandler.java</p>
 * <p>Title: </p>
 * <p>Description:
 * 	Iohandler的7个方法其实是根据session的4个状态值间变化来调用的：

 Connected：会话被创建并使用；

 Idle：会话在一段时间(可配置)内没有任何请求到达，进入空闲状态；

 Closing：会话将被关闭（剩余message将被强制flush）；

 Closed：会话被关闭；
 * 
 * 
 * </p>
 * <p>Copyright: Copyright (c) 2014 2014-12-12 上午11:55:10</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class ServerHandler extends IoHandlerAdapter
{
	
	private static final Logger logger = LoggerFactory
            .getLogger(ServerHandler.class);
	
	
	
	
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception
	{
		//KeepAliveMessageFactoryImpl
		//KeepAliveMessageFactoryImpl
	//	org.apache.mina.filter.keepalive.KeepAliveFilter
		cause.printStackTrace();
		super.exceptionCaught(session, cause);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#inputClosed(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void inputClosed(IoSession session) throws Exception
	{
		super.inputClosed(session);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		System.out.println("服务端接收到的信息：--》"  + message);
		
		Map<Long, IoSession> clients = session.getService().getManagedSessions();			//获取所有客户端。
		
		super.messageReceived(session, message);
	}
	

	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageSent(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception
	{
		//当一个消息被(IoSession#write)发送出去后调用
		super.messageSent(session, message);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionClosed(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		//当连接关闭时调用
		//客户端正在关闭网络连接
		super.sessionClosed(session);
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionCreated(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		//当一个新的连接建立时，由I/O processor thread调用；
		if(session != null ){
			SocketAddress so = session.getRemoteAddress();
			InetSocketAddress isa = (InetSocketAddress)so;
			String ip = isa.getAddress().getHostAddress();
			logger.info("客户端：" + ip + " 正在建立网络连接...");
		}
		super.sessionCreated(session);
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionIdle(org.apache.mina.core.session.IoSession, org.apache.mina.core.session.IdleStatus)
	 * 当连接进入空闲状态时调用  给客户端发送一个心跳包,确保客户端没有断开连接
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception
	{
		/*Message msg = new Message();
		msg.setCommed(Command.HEARTBEAT);
		msg.setId(session.getId());
		msg.setMessage("心跳包");
		session.write(msg);*/
		
		//当连接进入空闲状态时调用；
		System.out.println("好空啊" + session);
		super.sessionIdle(session, status);
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		//当连接打开是调用；
		super.sessionOpened(session);
	}
	
}
