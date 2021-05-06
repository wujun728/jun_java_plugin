package com.tarena;

import java.sql.*;

public class TestBatch {

  /**
   * @param args
   */
  public static void main(String[] args) {
    batch();
  }

  public static void batch() {
    Connection conn = null;
    Statement stmt = null;
    String sql1 = "insert into mystu" 
      + " values(200, 'rose', 19, 'F')";
    String sql2 = "insert into mystu" 
      + " values(210, 'martha', 25, 'F')";
    try {
      conn = ConnectionUtils.openConnection();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.addBatch(sql1);
      stmt.addBatch(sql2);
      int [] result = stmt.executeBatch();
      conn.commit();
      for (int i : result){
        System.out.println(i);
      }
      

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      ConnectionUtils.closeStatement(stmt);
      ConnectionUtils.closeConnection(conn);
    }
  }
}
