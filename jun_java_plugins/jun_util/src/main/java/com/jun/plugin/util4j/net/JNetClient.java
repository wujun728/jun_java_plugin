package com.jun.plugin.util4j.net;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 网络客户端
 * @author Administrator
 */
public interface JNetClient {

	public void start();
	
	public void stop();
	
	public InetSocketAddress getTarget();
	
	public boolean isConnected();

	/**
	 * 开启断线重连
	 * @param executor 断线重连调度服务器
	 * @param timeMills 间隔
	 */
	public void enableReconnect(ScheduledExecutorService executor,long timeMills);
	
	/**
	 * 禁用断线重连
	 * @param reconnect
	 */
	public void disableReconnect();
	
	public boolean isReconnect();
	
	public long getReconnectTimeMills();
	
	public String getName();
	
	public void setName(String name);
	
	public void sendData(byte[] data);
	
	public void sendObject(Object obj);
	
	/**
	 * 刷新发送缓冲区
	 */
	public void flush();
}
