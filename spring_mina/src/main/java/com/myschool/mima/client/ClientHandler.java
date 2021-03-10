/*
 * @(#)ClientHandler.java 2014-12-12 上午11:20:18
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.myschool.mima.bean.Command;
import com.myschool.mima.bean.Message;

/**
 * <p>File：ClientHandler.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-12 上午11:20:18</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class ClientHandler extends  IoHandlerAdapter
{

	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandler#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
	 */
	public void exceptionCaught(IoSession session, Throwable e)
			throws Exception
	{
		e.printStackTrace();
		super.exceptionCaught(session, e);
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandler#inputClosed(org.apache.mina.core.session.IoSession)
	 */
	public void inputClosed(IoSession arg0) throws Exception
	{
		super.inputClosed(arg0);
	}

	/**
	 * 处理从服务端或控制台输入的消息
	 */
	public void messageReceived(IoSession session, Object message) throws Exception
	{
		System.out.println("client:-->" + message);
		//session.get
		//1.处理心跳数据包
		
		//2.处理其他信息对象数据
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandler#messageSent(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	public void messageSent(IoSession arg0, Object arg1) throws Exception
	{
		super.messageSent(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandler#sessionClosed(org.apache.mina.core.session.IoSession)
	 */
	public void sessionClosed(IoSession arg0) throws Exception
	{
		super.sessionClosed(arg0);
	}

	//回话建立的时候调用
	public void sessionCreated(IoSession arg0) throws Exception
	{
		super.sessionCreated(arg0);
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandler#sessionIdle(org.apache.mina.core.session.IoSession, org.apache.mina.core.session.IdleStatus)
	 */
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception
	{
		super.sessionIdle(arg0, arg1);
	}

	
	
	//会话建立并打开时调用。第一次建立连接的时候
	public void sessionOpened(IoSession session) throws Exception
	{
		Message message = new Message();
		message.setId(session.getId());
		message.setUser("admin");
		message.setMessage("客户:"+message.getUser()+"已连接!");
		message.setCommed(Command.LOGIN);
		session.write(message);
	}

}
