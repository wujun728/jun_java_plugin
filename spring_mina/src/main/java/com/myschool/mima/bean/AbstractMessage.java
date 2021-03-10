/*
 * @(#)AbstractMessage.java 2014-12-17 上午9:25:25
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.bean;

/**
 * <p>File：AbstractMessage.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-17 上午9:25:25</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class AbstractMessage
{

	private int dataType;
	
	private Object data;

	public int getDataType()
	{
		return dataType;
	}

	public void setDataType(int dataType)
	{
		this.dataType = dataType;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
	
	
	
}
