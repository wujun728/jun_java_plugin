package com.jun.plugin.jdbc.chap03.sec04;

import java.sql.Connection;
import java.sql.Statement;

import com.jun.plugin.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * ɾ��ͼ��
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static int deleteBook(int id)throws Exception{
		Connection con = dbUtil.getCon(); // ��ȡ����
		String sql ="delete from t_book where id="+id;
		Statement stmt = con.createStatement(); // ����Statement
		int result = stmt.executeUpdate(sql);
		dbUtil.close(stmt, con); // �ر�Statement������
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		int result=deleteBook(3);
		if(result==1){
			System.out.println("ɾ���ɹ���");
		}else{
			System.out.println("ɾ��ʧ�ܣ�");
		}
	}
}
