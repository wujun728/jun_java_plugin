package com.jun.plugin.jdbc.chap08.sec01;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import com.jun.plugin.jdbc.util.DbUtil;

public class Demo1 {

	public static void main(String[] args)throws Exception {
		DbUtil dbUtil=new DbUtil();
		Connection con=dbUtil.getCon();
		DatabaseMetaData dmd=con.getMetaData(); // ��ȡԪ����
		System.out.println("���ݿ����ƣ�"+dmd.getDatabaseProductName());
		System.out.println("���ݿ�汾��"+dmd.getDriverMajorVersion()+"."+dmd.getDriverMinorVersion());
		
	}
}
