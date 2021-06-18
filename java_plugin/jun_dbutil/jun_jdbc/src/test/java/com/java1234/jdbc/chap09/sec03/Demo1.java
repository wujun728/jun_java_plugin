package com.java1234.jdbc.chap09.sec03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.java1234.jdbc.util.DbUtil;

public class Demo1 {
	
	private static DbUtil dbUtil=new DbUtil();

	/**
	 * 转出
	 * @param con
	 * @param accountName
	 * @param account
	 * @throws Exception
	 */
	private static void outCount(Connection con,String accountName,int account)throws Exception{
		String sql="update t_account set accountBalance=accountBalance-? where accountName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, account);
		pstmt.setString(2, accountName);
		pstmt.executeUpdate();
	}
	
	/**
	 * 转入
	 * @param con
	 * @param accountName
	 * @param account
	 * @throws Exception
	 */
	private static void inCount(Connection con,String accountName,int account)throws Exception{
		String sql="update t_account set account=accountBalance+? where accountName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, account);
		pstmt.setString(2, accountName);
		pstmt.executeUpdate();
	}
	
	
	public static void main(String[] args) {
		Connection con=null;
		try {
			con=dbUtil.getCon(); 
			con.setAutoCommit(false); // 取消自动提交
			System.out.println("张三开始向李四转账！");
			int account=500;
			outCount(con, "张三", account);
			inCount(con, "李四", account);
			System.out.println("转账成功！");
		} catch (Exception e) {
			try {
				con.rollback(); // 回滚
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.commit();  // 提交事务
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
