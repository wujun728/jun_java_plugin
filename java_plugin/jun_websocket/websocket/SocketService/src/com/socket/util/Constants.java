package com.socket.util;

/**
 * 
 * @author luoweiyi
 *
 */
public class Constants {
	
	//status
	public static final char SUCCESS = 's';
	public static final char FAILURE = 'f';
	public static final char TIMEOUT = 't';
	
	//server
	public static final String SERVER_PORT = "port";//服务器端口
	public static final String SERVER_CONNECTION = "connetion";//客户端最大连接数
	public static final String THREAD_POOL_MAX_SIZE = "maxThreads";//最大核心线程数
	public static final String TIME_OUT = "timeout";//接口调用超时时间
	public static final String SERVER_IP = "ip";//服务端绑定ip

}
