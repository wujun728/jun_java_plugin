package com.tarena;

import java.sql.*;
import java.io.*;
public class GetPic {

  /**
   * @param args
   */
  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = "select id, name, photo from stu_ning" +
        " where id = ?";
    try{
      conn = ConnectionUtils.openConnection();
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, 1);
      rs = stmt.executeQuery();
      if (rs.next()){
        FileOutputStream fos = 
        new FileOutputStream(new File("abc.jpg"));
        InputStream is = rs.getBinaryStream(3);
        byte[] buffer = new byte[4 * 1024];
        int length = 0;
        while((length = is.read(buffer)) != -1){
          fos.write(buffer, 0, length);
        }
        fos.flush();
        fos.close();
        is.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      ConnectionUtils.closeStatement(stmt);
      ConnectionUtils.closeConnection(conn);
    }

  }

}
