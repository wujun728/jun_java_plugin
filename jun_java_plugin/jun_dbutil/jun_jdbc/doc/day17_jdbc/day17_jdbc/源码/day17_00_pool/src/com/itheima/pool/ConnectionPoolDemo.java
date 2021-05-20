package com.itheima.pool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.itheima.util.JdbcUtil;

//模拟数据库连接池的原理
public class ConnectionPoolDemo {
	private static List<Connection> pool = new ArrayList<Connection>();
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
	//从池中取出一个链接
	public synchronized static Connection getConnection(){
		if(pool.size()>0){
			Connection conn = pool.remove(0);
			return conn;
		}else{
			throw new RuntimeException("服务器真忙");
		}
	}
	//把链接还回池中
	public static void release(Connection conn){
		pool.add(conn);
	}
	
}
