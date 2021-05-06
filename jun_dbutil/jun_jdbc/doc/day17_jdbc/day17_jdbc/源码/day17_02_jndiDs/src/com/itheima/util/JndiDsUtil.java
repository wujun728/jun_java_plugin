package com.itheima.util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JndiDsUtil {
	public static Connection getConnection() throws Exception {
		Context initContext = new InitialContext();
		DataSource ds = (DataSource) initContext
				.lookup("java:/comp/env/jdbc/test");
		Connection conn = ds.getConnection();
		return conn;
	}
}
