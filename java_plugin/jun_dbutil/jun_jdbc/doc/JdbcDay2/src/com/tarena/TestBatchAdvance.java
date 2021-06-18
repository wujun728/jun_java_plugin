package com.tarena;

import java.sql.*;

public class TestBatchAdvance {

  /**
   * @param args
   */
  public static void main(String[] args) {
    batch();
  }
  public static void batch() {
    Connection conn = null;
    PreparedStatement stmt = null;
    String sql = "insert into mytemp "
      + "values(?, ?)";  
    try {
      conn = ConnectionUtils.openConnection();
      conn.setAutoCommit(false);
      stmt = conn.prepareStatement(sql);
      for (int i = 0; i < 100000; i++){
        stmt.setInt(1, i);
        stmt.setString(2, "value" + i);
        stmt.addBatch();
        if (i % 1000 == 0){
          stmt.executeBatch();
        }
        stmt.executeBatch();
      }
      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      ConnectionUtils.closeStatement(stmt);
      ConnectionUtils.closeConnection(conn);
    }
  }

}
