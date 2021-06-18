/*
 * @(#)SpringClient.java 2014-12-15 下午5:03:59
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.tcp;

import com.myschool.mima.bean.Command;
import com.myschool.mima.bean.Message;
import com.myschool.mima.client.ClientHandler;
import com.myschool.mima.client.HandlerHelper;

/**
 * <p>File：SpringClient.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-15 下午5:03:59</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class TCPClient
{
	public static void main(String[] args)
	{
		
		HandlerHelper hh = new HandlerHelper();
		hh.setHost("127.0.0.1");
		hh.setIoHandler(new ClientHandler());
		hh.setPort(8989);
		hh.setTimeout(60000);
		Message msg = new Message();
		msg.setCommed(Command.LOGIN);
		msg.setId(32);
		msg.setMessage("备案及回复");
		hh.newIoSession().sendMsg(msg).destroy();
	}
}
