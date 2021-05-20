package com.java1234.jdbc.chap03.sec02;

import java.sql.Connection;
import java.sql.Statement;

import com.java1234.jdbc.util.DbUtil;

public class Demo1 {

	public static void main(String[] args) throws Exception{
		DbUtil dbUtil=new DbUtil();
		String sql="insert into t_book values(null,'java牛逼',888,'B哥',1)";
		Connection con=dbUtil.getCon(); //获取数据连接
		Statement stmt=con.createStatement(); // 获取Statement
		int result=stmt.executeUpdate(sql);
		System.out.println("操作的结果："+result+"数据");
		stmt.close();  // 关闭statement
		con.close();   // 关闭连接
	}
}
