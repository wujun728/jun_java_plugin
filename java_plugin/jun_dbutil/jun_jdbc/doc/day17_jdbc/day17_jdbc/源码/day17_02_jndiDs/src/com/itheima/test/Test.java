package com.itheima.test;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Test {

	public static void main(String[] args) throws Exception {
		Context initContext = new InitialContext();
		
		DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/test");
		Connection conn = ds.getConnection();
		System.out.println(conn);
		
	}

}
