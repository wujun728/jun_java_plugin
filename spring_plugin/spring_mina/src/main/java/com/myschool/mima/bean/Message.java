/*
 * @(#)Message.java 2014-12-12 上午11:28:42
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.bean;

import java.io.Serializable;

/**
 * <p>File：Message.java</p>
 * <p>Title: 消息体</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-12 上午11:28:42</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class Message implements Serializable
{

	//
	private static final long	serialVersionUID	= 939854786585849815L;
	
	private long id;
	private String message;
	private String user;
	private Command commed;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user = user;
	}
	public Command getCommed()
	{
		return commed;
	}
	public void setCommed(Command commed)
	{
		this.commed = commed;
	}
	@Override
	public String toString()
	{
		return "Message [id=" + id + ", message=" + message + ", user=" + user
				+ ", commed=" + commed + "]";
	}
	
	
	
}
