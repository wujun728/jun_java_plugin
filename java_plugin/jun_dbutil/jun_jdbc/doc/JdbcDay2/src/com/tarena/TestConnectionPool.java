package com.tarena;
import java.sql.*;

import org.apache.commons.dbcp.BasicDataSource;
public class TestConnectionPool {

  /**
   * @param args
   */
  public static void main(String[] args) {
    execute();
  }
  
  public static void execute(){
    String url = "jdbc:oracle:thin:@192.169.0.26:1521:tarena";
    String driver = "oracle.jdbc.driver.OracleDriver";
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername("openlab");
    ds.setPassword("open123");
    try {
      Connection conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = 
        stmt.executeQuery(
            "select count(*) from mystu");
      if (rs.next()){
        System.out.println(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }finally {
      try {
        ds.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    
  }

}
