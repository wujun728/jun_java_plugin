package com.java1234.jdbc.chap03.sec04;

import java.sql.Connection;
import java.sql.Statement;

import com.java1234.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * 删除图书
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static int deleteBook(int id)throws Exception{
		Connection con = dbUtil.getCon(); // 获取连接
		String sql ="delete from t_book where id="+id;
		Statement stmt = con.createStatement(); // 创建Statement
		int result = stmt.executeUpdate(sql);
		dbUtil.close(stmt, con); // 关闭Statement和连接
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		int result=deleteBook(3);
		if(result==1){
			System.out.println("删除成功！");
		}else{
			System.out.println("删除失败！");
		}
	}
}
