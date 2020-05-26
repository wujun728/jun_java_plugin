package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestJdbc5 {
  Connection conn = null;
  PreparedStatement prepareStmt = null;
  ResultSet rs = null;
  
  public void init(){
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
      String dbUsername = "openlab";
      String dbPassword = "open123";
      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public void close(){
    try {
      if(rs != null)
        rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (prepareStmt != null)
        prepareStmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (conn != null)
        conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    // new TestJdbc5().insert();
    //new TestJdbc5().update();
    new TestJdbc5().delete();
  }
  public void delete() {
    init();
    try {
      String sql = "delete mydepttemp " +
          " where deptno = ?";
      prepareStmt = conn.prepareStatement(sql);
      prepareStmt.setInt(1, 10);

      int n = prepareStmt.executeUpdate();
      System.out.println(n+"����¼��ɾ��");
    }  catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close();
    }
  }


  public void update() {
    init();
    try {
      String sql = "update mydepttemp " +
          "set loc = ? " + "where deptno = ?";
      prepareStmt = conn.prepareStatement(sql);
      prepareStmt.setString(1, "");
      prepareStmt.setInt(2, 10);

      int n = prepareStmt.executeUpdate();
      System.out.println(n+"");
      System.out.println((n > 0) ? "OK" : "error");

    }  catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close();
    }
  }

  public void insert() {
    init();
    try {
      
      String sql = "insert into dept " + "values(?, ?, ?)";
      prepareStmt = conn.prepareStatement(sql);
      prepareStmt.setInt(1, 10);
      prepareStmt.setString(2, "market");
      prepareStmt.setString(3, "beijing");

      int n = prepareStmt.executeUpdate();

      System.out.println((n == 1) ? "OK" : "error");

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close();
    }
  }

  public  void select() {
    init();
    try {
      String sql = "select * from vemp " + " where deptno = ?";
      prepareStmt = conn.prepareStatement(sql);
      prepareStmt.setInt(1, 20);
      rs = prepareStmt.executeQuery();
      while (rs.next()) {
        System.out.println(rs.getString("ename") + ", "
            + rs.getString("deptno"));
      }
    }catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close();
    }

  }

}
