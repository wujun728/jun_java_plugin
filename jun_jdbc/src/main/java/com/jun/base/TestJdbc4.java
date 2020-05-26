package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestJdbc4 {

  /**
   * @param args
   */
  public static void main(String[] args) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
      String dbUsername = "openlab";
      String dbPassword = "open123";
      conn = DriverManager.getConnection(url, dbUsername, dbPassword);

      
      stmt = conn.createStatement();
      String sql = "select comm from vemp";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        System.out.println(
            rs.getDouble("comm") + ", " + 
            rs.wasNull());
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  }

}
