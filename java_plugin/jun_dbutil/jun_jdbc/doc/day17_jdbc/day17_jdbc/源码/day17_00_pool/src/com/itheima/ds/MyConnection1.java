package com.itheima.ds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
这也是包装：对ConnectionAdapter进行包装。

包装类即是被包装类的包装，又是他的子类。

1、编写一个类，继承已经是包装类的类。
2、定义一个变量，引用被包装类的实例。
3、定义构造方法，传入被包装类的实例。
4、覆盖掉需要改写的方法
 */
public class MyConnection1 extends ConnectionAdapter {
	private Connection conn;
	private List<Connection> pool;
	public MyConnection1(Connection conn,List<Connection> pool){
		super(conn);
		this.conn = conn;
		this.pool = pool;
	}
	public void close() throws SQLException {
		pool.add(conn);
	}
	
}
