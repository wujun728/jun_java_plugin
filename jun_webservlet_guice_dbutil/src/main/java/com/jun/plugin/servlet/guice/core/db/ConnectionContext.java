package com.jun.plugin.servlet.guice.core.db;

import java.sql.Connection;

public class ConnectionContext {

	private ConnectionContext(){}
	
	private static ConnectionContext context=new ConnectionContext();
	
	public static ConnectionContext getInstance(){
		return context;
	}
	
	private ThreadLocal<Connection> connectionThreadLocal=new ThreadLocal<Connection>();
	
	/**
	 * 绑定连接
	 * @param con
	 */
	public void bind(Connection con){
		connectionThreadLocal.set(con);
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public Connection getCon(){
		return connectionThreadLocal.get();
	}
	
	
	/**
	 * 移除连接
	 */
	public void remove(){
		connectionThreadLocal.remove();
	}
	
}
