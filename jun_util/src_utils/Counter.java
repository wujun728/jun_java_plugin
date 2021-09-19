package com.gootrip.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 * <p>Title: 计数器类</p>
 * <p>Description: 获取最大序列数</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author advance.wu
 * @version 1.0
 * 
 * sql:
 * 
 * CREATE TABLE `tblserial` (
  `tablename` varchar(100) NOT NULL default '',
  `serialno` int(11) NOT NULL default '0',
  PRIMARY KEY  (`tablename`)
  )
 */

public class Counter {
  private static Counter counter = new Counter();
  private Counter() {
  }
  public static Counter getInstance(){
    return counter;
  }
  /**
   * 获取最大序列号
   * @param strTable 表名
   * @param con 数据库链接
   * @return
   */
  public synchronized long nextSerial(String strTable,Connection con){
    String strSQL = null;
    long serialno = 0;
    Statement stmt = null;
    try {
      strSQL = "select serialno from tblserial where tablename='" + strTable + "'";
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(strSQL);
      if (rs.next())
        serialno = rs.getLong(1);
      strSQL = "update tblserial set serialno = serialno + 1 where tablename='" + strTable + "'";
      stmt.execute(strSQL);
      rs.close();
      rs = null;
      serialno ++;
    }
    catch (Exception ex) {

    }finally{
      if (stmt != null)
        try {
          stmt.close();
          stmt = null;
        }
        catch (Exception ex) {}
    }
    return serialno;
  }
}
