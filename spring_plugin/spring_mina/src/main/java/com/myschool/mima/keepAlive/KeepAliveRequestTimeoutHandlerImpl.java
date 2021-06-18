/*
 * @(#)KeepAliveRequestTimeoutHandler.java 2014-12-16 上午10:46:12
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.keepAlive;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**
 * <p>File：KeepAliveRequestTimeoutHandler.java</p>
 * <p>Title:当心跳超时时的处理，也可以用默认处理 </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-16 上午10:46:12</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler
{

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler#keepAliveRequestTimedOut(org.apache.mina.filter.keepalive.KeepAliveFilter, org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter,
			IoSession session) throws Exception
	{
		System.out.println("服务器端心跳包发送超时处理(即长时间没有发送（接受）心跳包)---关闭当前长连接");
		CloseFuture closeFuture = session.close(true);
		closeFuture.addListener(new IoFutureListener<IoFuture>()
		{
			public void operationComplete(IoFuture future)
			{
				if (future instanceof CloseFuture)
				{
					((CloseFuture) future).setClosed();
					System.out.println("sessionClosed CloseFuture setClosed-->"
							+ future.getSession().getId());
				}
			}
		});
	}

}
