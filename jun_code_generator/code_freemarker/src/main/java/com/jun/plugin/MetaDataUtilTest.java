package com.jun.plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
 
public class MetaDataUtilTest {
 
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    String user = "root";
    String password = "";
    String jdbcDriver = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://localhost:3306/test?useUnicode=yes&characterEncoding=utf-8&allowMultiQueries=true";
    Connection conn = null;
    Class.forName(jdbcDriver);
    conn = DriverManager.getConnection(jdbcUrl, user, password);
 
    PreparedStatement pst = null;
    try {
      pst = conn.prepareStatement("select * from users where 1=2");
      ResultSetMetaData rsd = pst.executeQuery().getMetaData();
      for(int i = 0; i < rsd.getColumnCount(); i++) {
        System.out.print("java类型："+rsd.getColumnClassName(i + 1));
        System.out.print("  数据库类型:"+rsd.getColumnTypeName(i + 1));
        System.out.print("  字段名称:"+rsd.getColumnName(i + 1));
//        System.out.print("  字段备注:"+rsd.get(i + 1));
        System.out.print("  字段长度:"+rsd.getColumnDisplaySize(i + 1));
        System.out.println();
      }
    } catch(SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        pst.close();
        pst = null;
      } catch(SQLException e) {
        throw new RuntimeException(e);
      }
    }
 
  }
 
}