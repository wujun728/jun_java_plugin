package com.java1234.jdbc.chap07.sec02;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import com.java1234.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * 调用存储过程，通过id查询bookName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static String getBookNameById(int id)throws Exception{
		Connection con=dbUtil.getCon();  // 获取数据库连接
		String sql="{CALL pro_getBookNameById(?,?)}";
		CallableStatement cstmt=con.prepareCall(sql);
		cstmt.setInt(1, id); // 设置第一个参数
		cstmt.registerOutParameter(2, Types.VARCHAR);  // 设置返回类型
		cstmt.execute();
		String bookName=cstmt.getString("bN");  // 获取返回值
		dbUtil.close(cstmt, con);
		return bookName;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("图书名称是："+getBookNameById(11));
	}
}
