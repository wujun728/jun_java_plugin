/*
 * @(#)KeepAliveServer.java 2014-12-16 上午9:13:14
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.keepAlive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * <p>File：KeepAliveServer.java</p>
 * <p>Title: 心跳机制</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-16 上午9:13:14</p>
 * <p>Company: 8637.com</p>
 * @author Wujun
 * @version 1.0
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory 
{

	//心跳包内容
    private static final String HEARTBEATREQUEST = "HEARTBEATREQUEST";
    private static final String HEARTBEATRESPONSE = "HEARTBEATRESPONSE";
    
	/**
     * @see 返回给客户端的心跳包数据 return 返回结果才是客户端收到的心跳包数据
     * @author Wujun
     */
    public Object getRequest(IoSession session) {
    	//此处发送给客户端的心跳数据包
        return HEARTBEATREQUEST;
    }

    /**
     * @see 接受到的客户端数据包
     * @author Wujun
     */
    public Object getResponse(IoSession session, Object request) {
        return request;
    }

    /**
     * @see 判断是否是客户端发送来的的心跳包此判断影响 KeepAliveRequestTimeoutHandler实现类判断是否心跳包发送超时
     * @author Wujun
     */
    public boolean isRequest(IoSession session, Object message) {
        if(message.equals(HEARTBEATRESPONSE)){
            System.out.println("接收到客户端心数据包引发心跳事件                 心跳数据包是》》" + message);
	        return true;
	    }
        return false;
    }

    /**
     * @see  判断发送信息是否是心跳数据包此判断影响 KeepAliveRequestTimeoutHandler实现类 判断是否心跳包发送超时
     * @author Wujun
     */
    public boolean isResponse(IoSession session, Object message) {
        if(message.equals(HEARTBEATREQUEST)){
            System.out.println("服务器发送数据包中引发心跳事件: " + message);
            return true;
        }
        return false;
    }
}
