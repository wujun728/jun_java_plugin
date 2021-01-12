package com.tarena;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionUtils {

	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	static {
		Properties props = new Properties();
		try {
			// 从属性文件中读取数据库配置信息
			props.load(ConnectionUtils.class.getClassLoader()
					.getResourceAsStream(
							"com/tarena/db_oracle.properties"));
		} catch (IOException e) {
      e.printStackTrace();
		}
		if (props != null) {
			url = props.getProperty("url");
			driver = props.getProperty("driver");
			username = props.getProperty("username");
			password = props.getProperty("password");

			// 装载并注册数据库驱动
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
			}
		}
	}

	public static Connection openConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
		}
	}

}