/*
 * @(#)Command.java 2014-12-12 上午11:41:37
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.bean;

/**
 * <p>File：Command.java</p>
 * <p>Title:命令 </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-12 上午11:41:37</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public enum Command
{
	/**
	 * 登录
	 */
	LOGIN,
	/**
	 * 退出
	 */
	QUIT,
	/**
	 * 广播
	 */
	BROADCAST,
	/**
	 * 心跳
	 */
	HEARTBEAT
}
