package com.jun.base.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Types;

public class AccountDao {
	
	private static DbUtil dbUtil=new DbUtil();

	/**
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
		Savepoint sp=null;
		try {
			con=dbUtil.getCon(); 
			con.setAutoCommit(false); // ȡ���Զ��ύ
			System.out.println("������ʼ������ת�ˣ�");
			int account=500;
			outCount(con, "����", account);
			sp=con.setSavepoint(); // ����һ�������
			inCount(con, "����", account);
			System.out.println("ת�˳ɹ���");
		} catch (Exception e) {
			try {
				con.rollback(sp); // �ع���sp�����
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
	
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static String getBookNameById(int id)throws Exception{
		Connection con=dbUtil.getCon();  // ��ȡ���ݿ�����
		String sql="{CALL pro_getBookNameById(?,?)}";
		CallableStatement cstmt=con.prepareCall(sql);
		cstmt.setInt(1, id); // ���õ�һ������
		cstmt.registerOutParameter(2, Types.VARCHAR);  // ���÷�������
		cstmt.execute();
		String bookName=cstmt.getString("bN");  // ��ȡ����ֵ
		dbUtil.close(cstmt, con);
		return bookName;
	}
	
	public static void test3(String[] args) {
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
