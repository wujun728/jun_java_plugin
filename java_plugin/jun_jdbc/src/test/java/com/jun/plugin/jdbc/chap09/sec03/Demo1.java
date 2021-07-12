package com.jun.plugin.jdbc.chap09.sec03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jun.plugin.jdbc.util.DbUtil;

public class Demo1 {
	
	private static DbUtil dbUtil=new DbUtil();

	/**
	 * ת��
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
	 * ת��
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
			con.setAutoCommit(false); // ȡ���Զ��ύ
			System.out.println("������ʼ������ת�ˣ�");
			int account=500;
			outCount(con, "����", account);
			inCount(con, "����", account);
			System.out.println("ת�˳ɹ���");
		} catch (Exception e) {
			try {
				con.rollback(); // �ع�
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.commit();  // �ύ����
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
