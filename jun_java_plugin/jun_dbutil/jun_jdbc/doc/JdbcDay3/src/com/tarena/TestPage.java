package com.tarena;

import java.sql.*;
import java.util.Scanner;

public class TestPage {

  /**
   * @param args
   */
  public static void main(String[] args) {
    //getResult(10, 20);//[10,20)
    //getPage(10, 5);
    //getPageSimple(10, 5);
    Scanner sc = new Scanner(System.in);
    int page = sc.nextInt();
    getPageSimple(10, page);//每页10条,第page页
  }
  //获得数据表一共多少条记录
  public static int getTotalNumber(){
    int n = -1;
    String sql = "select count(*) num from mytemp";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ConnectionUtils.openConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      rs.next();
      n = rs.getInt("num");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return n;
  }
  
  
  public static void getPageSimple(int pageSize, int page){
    //totalPage:总页数; totalNumber:总记录条数
    int totalPage = 0;
    int totalNumber = getTotalNumber();
    if (totalNumber % pageSize == 0){
      totalPage = totalNumber/pageSize;
    }else{
      totalPage = totalNumber/pageSize + 1;
    }
    if (page > totalPage)
        page = totalPage;
    if (page < 1){
        page = 1; 
    }
    int from = (page - 1) * pageSize + 1;
    int to = from + pageSize;
    getResult(from, to);//[from, to)
  }
  
  
  
  public static void getPage(int pageSize, int page){
    int from = (page - 1) * pageSize + 1;
    int to = from + pageSize;
    String sql = "select id, user_id, " +
    "login_time, logout_time, rn " +
    "from " +
    "(select id, user_id, login_time, " +
    "logout_time, rownum rn " +
    "from mytemp where rownum < ? ) " +
    "where rn >= ?";
    Connection conn = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    try {
      conn = ConnectionUtils.openConnection();
      pStmt = conn.prepareStatement(sql);
      pStmt.setInt(1, to);
      pStmt.setInt(2, from);
      rs = pStmt.executeQuery();
      while(rs.next()){
        String line = rs.getString("id") 
          + "-" + rs.getString("user_id") 
          + "-" + rs.getString("rn"); 
        System.out.println(line);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally{
      ConnectionUtils.closeResultSet(rs);
      ConnectionUtils.closeStatement(pStmt);
      ConnectionUtils.closeConnection(conn);
    }
    
    
  }

  public static void getResult(int m, int n){
    String sql = "select id, user_id, " +
        "login_time, logout_time, rn " +
        "from " +
        "(select id, user_id, login_time, " +
        "logout_time, rownum rn " +
        "from mytemp where rownum < ? ) " +
        "where rn >= ?";
    Connection conn = null;
    PreparedStatement pStmt = null;
    try {
      conn = ConnectionUtils.openConnection();
      pStmt = conn.prepareStatement(sql);
      pStmt.setInt(1, n);
      pStmt.setInt(2, m);
      ResultSet rs = pStmt.executeQuery();
      while(rs.next()){
        String line = rs.getString("id") 
          + "-" + rs.getString("user_id") 
          + "-" + rs.getString("rn"); 
        System.out.println(line);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }
}
