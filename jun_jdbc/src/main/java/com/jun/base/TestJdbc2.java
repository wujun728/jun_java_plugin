package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestJdbc2 {

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
      // 3.���������
      stmt = conn.createStatement();
      String sql = "select count(*) emp_count, " +
          "avg(sal) avg_sal " +
          "from emp";
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        int count = rs.getInt("emp_count");
        double avg_sal = rs.getDouble("avg_sal");
        System.out.println("total:" + count);
        System.out.println("avg:" + avg_sal);
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
