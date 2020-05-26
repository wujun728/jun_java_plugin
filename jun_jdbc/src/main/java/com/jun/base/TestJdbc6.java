package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestJdbc6 {

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

      conn.setAutoCommit(false);///////
      stmt = conn.createStatement();
      String sql = "insert into dept" +
          " values(10, 'market', 'bj')";
      int n = stmt.executeUpdate(sql);
      
      conn.commit();///////

    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null)
          conn.rollback();///////
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      
    } finally {
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
