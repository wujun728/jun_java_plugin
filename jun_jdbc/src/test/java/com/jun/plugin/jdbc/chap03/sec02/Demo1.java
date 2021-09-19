package com.jun.plugin.jdbc.chap03.sec02;

import java.sql.Connection;
import java.sql.Statement;

import com.jun.plugin.jdbc.util.DbUtil;

public class Demo1 {

	public static void main(String[] args) throws Exception{
		DbUtil dbUtil=new DbUtil();
		String sql="insert into t_book values(null,'javaţ��',888,'B��',1)";
		Connection con=dbUtil.getCon(); //��ȡ��������
		Statement stmt=con.createStatement(); // ��ȡStatement
		int result=stmt.executeUpdate(sql);
		System.out.println("�����Ľ����"+result+"����");
		stmt.close();  // �ر�statement
		con.close();   // �ر�����
	}
}
