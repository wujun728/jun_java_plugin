package com.holder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:wenbin",
					"", "");
			DBContextHolder.setContextConnection(conn);
			conn.createStatement().execute("delete from myselect");
			System.out.println(conn.createStatement().executeQuery(
					"select count(*) from myselect"));
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
