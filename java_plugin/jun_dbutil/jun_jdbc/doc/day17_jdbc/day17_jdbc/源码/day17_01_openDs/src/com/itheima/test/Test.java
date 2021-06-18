package com.itheima.test;

import java.sql.Connection;

import com.itheima.util.C3P0Util;
import com.itheima.util.DBCPUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		Connection conn = C3P0Util.getConnection();
		System.out.println(conn.getClass().getName());
		conn.close();//肯定是还回池中
	}

}
