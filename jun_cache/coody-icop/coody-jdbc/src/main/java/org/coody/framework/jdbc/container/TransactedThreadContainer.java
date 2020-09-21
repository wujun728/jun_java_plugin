package org.coody.framework.jdbc.container;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.coody.framework.core.util.StringUtil;


@SuppressWarnings({  "unchecked" })
public class TransactedThreadContainer {

	
	
	public static ThreadLocal<Map<String, Object>> THREAD_TRANSACTED_CONTAINER = new ThreadLocal<Map<String, Object>>();
	
	/**
	 * 是否需要事物
	 */
	private static final String NEED_TRANSACTED="NEED_TRANSACTED";
	
	/**
	 * connection容器
	 */
	private static final String CONNECTION_CONTAINER="CONNECTION_CONTAINER";
	
	
	
	public static void clear(){
		THREAD_TRANSACTED_CONTAINER.remove();
	}
	/**
	 * 判断是否存在事物控制
	 * @return
	 */
	public static boolean hasTransacted(){
		
		Map<String, Object> threadContainer=THREAD_TRANSACTED_CONTAINER.get();
		if(StringUtil.isNullOrEmpty(threadContainer)){
			return false;
		}
		Boolean needTransacteder=(Boolean) threadContainer.get(NEED_TRANSACTED);
		if(needTransacteder==null){
			return false;
		}
		return needTransacteder;
	}
	
	public static void writeHasTransacted(){
		Map<String, Object> threadContainer=THREAD_TRANSACTED_CONTAINER.get();
		if(StringUtil.isNullOrEmpty(threadContainer)){
			threadContainer=new HashMap<String, Object>();
		}
		threadContainer.put(NEED_TRANSACTED, true);
		THREAD_TRANSACTED_CONTAINER.set(threadContainer);
	}
	
	public static void writeDataSource(DataSource source,Connection connection){
		Map<String, Object> threadContainer=THREAD_TRANSACTED_CONTAINER.get();
		if(StringUtil.isNullOrEmpty(threadContainer)){
			threadContainer=new HashMap<String, Object>();
		}
		Map<DataSource, Connection> connectionMap=(Map<DataSource, Connection>) threadContainer.get(CONNECTION_CONTAINER);
		if(StringUtil.isNullOrEmpty(connectionMap)){
			connectionMap=new HashMap<DataSource, Connection>();
		}
		connectionMap.put(source, connection);
		threadContainer.put(CONNECTION_CONTAINER, connectionMap);
		THREAD_TRANSACTED_CONTAINER.set(threadContainer);
	}
	
	/**
	 * 从线程容器获取连接
	 * @param source
	 * @return
	 */
	public static Connection getConnection(DataSource source){
		Map<String, Object> threadContainer=THREAD_TRANSACTED_CONTAINER.get();
		if(StringUtil.isNullOrEmpty(threadContainer)){
			return null;
		}
		Map<DataSource, Connection> connectionMap=(Map<DataSource, Connection>) threadContainer.get(CONNECTION_CONTAINER);
		if(StringUtil.isNullOrEmpty(connectionMap)){
			connectionMap=new HashMap<DataSource, Connection>();
		}
		return connectionMap.get(source);
	}
	
	/**
	 * 从线程容器获取所有连接
	 * @param source
	 * @return
	 */
	public static List<Connection> getConnections(){
		Map<String, Object> threadContainer=THREAD_TRANSACTED_CONTAINER.get();
		if(StringUtil.isNullOrEmpty(threadContainer)){
			return null;
		}
		Map<DataSource, Connection> connectionMap=(Map<DataSource, Connection>) threadContainer.get(CONNECTION_CONTAINER);
		if(StringUtil.isNullOrEmpty(connectionMap)){
			connectionMap=new HashMap<DataSource, Connection>();
		}
		return new ArrayList<Connection>(connectionMap.values());
	}
}
