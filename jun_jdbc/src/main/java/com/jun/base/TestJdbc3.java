package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestJdbc3 {

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

      DatabaseMetaData dmd = conn.getMetaData();
      System.out.println(dmd.getDatabaseProductName());
      System.out.println(dmd.getDatabaseProductVersion());
      System.out.println(dmd.getDriverName());
      System.out.println(dmd.getURL());
      System.out.println(dmd.getUserName());

      stmt = conn.createStatement();
      String sql = "select e.ename, d.dname" +
          " from vemp e join vdept d" +
          " on e.deptno = d.deptno";
      rs = stmt.executeQuery(sql);
      ResultSetMetaData rsm = rs.getMetaData();
      int count = rsm.getColumnCount();
      for (int i = 1; i <= count; i++) {
        System.out.print(
            rsm.getColumnName(i) + "\t");
      }
      System.out.println();
      while (rs.next()) {
        for (int i = 1; i <= count; i++) {
          System.out.print(
              rs.getString(rsm.getColumnName(i)) + "\t");
        }
        System.out.println();
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
