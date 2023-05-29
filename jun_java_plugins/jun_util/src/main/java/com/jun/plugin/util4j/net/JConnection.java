package com.jun.plugin.util4j.net;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * 网络连接接口
 * @author Administrator
 */
public interface JConnection {

	public int getId();
	
	/**
	 * 是否活跃打开可写
	 * @return
	 */
	public boolean isActive();
	
	public void close();
	
	/**
	 * 是否有属性
	 * @param key
	 * @return
	 */
	public boolean hasAttribute(String key);
	/**
	 * 设置属性
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key,Object value);
	
	/**
	 * 获取属性key集合
	 * @return
	 */
	public Set<String> getAttributeNames();
	/**
	 * 获取属性
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key);
	
	/**
	 * 移除属性
	 * @param key
	 * @return
	 */
	public Object removeAttribute(String key);
	/**
	 * 清空属性
	 */
	public void clearAttributes();
	
	/**
	 * 获取附件
	 * @return
	 */
	public <T> T getAttachment();
	
	/**
	 * 设置附件
	 * @param attachment
	 */
	public <T> void setAttachment(T attachment);
	
	/**
	 * 获取连接远程地址
	 * @return
	 */
	public InetSocketAddress getRemoteAddress();
	/**
	 * 获取连接本地地址
	 * @return
	 */
	public InetSocketAddress getLocalAddress();
	
	public void write(Object obj);
	public void write(byte[] bytes);
	public void writeAndFlush(Object obj);
	public void writeAndFlush(byte[] bytes);
	public void flush();
}
