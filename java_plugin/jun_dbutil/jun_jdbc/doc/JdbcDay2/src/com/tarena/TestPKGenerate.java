package com.tarena;
import java.sql.*;
public class TestPKGenerate {

  /**
   * @param args
   */
  public static void main(String[] args) {
    insert();
  }
  
  public static void insert(){
    Connection conn = null;
    PreparedStatement stmt = null;
    //向mystu新增记录时,使用序列myseq生成主键值
    String sql = "insert into " +
        "mystu(id, name, age, sex) " +
        "values(myseq.nextval, ?, ?, ?)";
    try {
      conn = ConnectionUtils.openConnection();
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, "donna");
      stmt.setInt(2, 30);
      stmt.setString(3, "F");
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      ConnectionUtils.closeStatement(stmt);
      ConnectionUtils.closeConnection(conn);
    }
  }

}
