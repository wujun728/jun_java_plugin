package com.socket.server.container;


/**
 * server容器
 * @author luoweiyi
 *
 */
public interface Container {
	
	/**
	 * 启动server
	 */
	public void start();
	
	/**
	 * 关闭server
	 */
	public void stop();

}
