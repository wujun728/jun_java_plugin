package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestJdbc1 {

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
      String sql = "select empno, ename, job, sal, " +
          "to_char(hiredate, 'yyyy/mm/dd') hiredate " +
          "from mystu";
      //System.out.println(sql);
      stmt.setMaxRows(5);
      rs = stmt.executeQuery(sql);//130000
      //rs.setFetchSize(5);
      
      while (rs.next()) {
        int empno = rs.getInt("empno");
        String ename = rs.getString("ename");
        String job = rs.getString("job");
        double sal = rs.getDouble("sal");
        String hiredate = rs.getString("hiredate");
        System.out.println(empno + ", " 
            + ename + ", " 
            + job + ", " 
            + sal + ", "
            + hiredate);
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
