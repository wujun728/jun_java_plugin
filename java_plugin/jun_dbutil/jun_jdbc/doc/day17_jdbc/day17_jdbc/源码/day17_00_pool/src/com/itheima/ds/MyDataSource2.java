package com.itheima.ds;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.itheima.util.JdbcUtil;
//用动态代理编写的数据源
public class MyDataSource2 implements DataSource {
	
	private static List<Connection> pool = Collections.synchronizedList(new ArrayList<Connection>());
	static{
		try {
			for(int i=0;i<10;i++){
				Connection conn = JdbcUtil.getConnection();//创建的新连接
				pool.add(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		if(pool.size()>0){
			final Connection conn = pool.remove(0);//得到的是数据库驱动的实现
			Connection connProxy = (Connection)Proxy.newProxyInstance(conn.getClass().getClassLoader(), 
					conn.getClass().getInterfaces(), 
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method, Object[] args)
								throws Throwable {
							if("close".equals(method.getName())){
								//还回池中
								return pool.add(conn);
							}else{
								return method.invoke(conn, args);
							}
						}
					}
					);
			return connProxy;//返回30行的代理对象
		}else{
			throw new RuntimeException("服务器真忙");
		}
	}
	
	
	
	
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	public void setLoginTimeout(int seconds) throws SQLException {

	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	
	public Connection getConnection(String username, String password)
			throws SQLException {
		return null;
	}
	
	
}
