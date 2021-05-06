package com.tarena;
import java.sql.*;
import java.io.*;
public class SavePic {

  /**
   * @param args
   */
  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement stmt = null;
    String sql = "insert into stu_ning" +
        "(id, name, photo) " +
        "values (stu_ning_seq.nextval, ?, ?)";
    try {
      conn = ConnectionUtils.openConnection();
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, "peter");
      File f = new File("src\\kfc.jpg");
      //src/kfc.jpg
      FileInputStream fis =
        new FileInputStream(f);
      stmt.setBinaryStream(2, fis, (int)f.length());
      int n = stmt.executeUpdate();
      System.out.println(n + "条记录被插入");
    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      ConnectionUtils.closeStatement(stmt);
      ConnectionUtils.closeConnection(conn);
    }

  }

}
