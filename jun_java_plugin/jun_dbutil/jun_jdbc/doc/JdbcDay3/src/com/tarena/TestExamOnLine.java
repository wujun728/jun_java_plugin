package com.tarena;
import java.sql.*;
import java.util.*;
public class TestExamOnLine {

  /**
   * @param args
   */
  public static void main(String[] args) {
    Connection conn = null;
    Statement stmt = null;
    try {
      String sql = "select * from mytemp";//t_loginfo
      conn = ConnectionUtils.openConnection();
      stmt = conn.createStatement
      (ResultSet.TYPE_SCROLL_INSENSITIVE, 
          ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = stmt.executeQuery(sql);
      
      Set<Integer> nums = new HashSet<Integer>();
      Random r = new Random();
      do { 
        int n = r.nextInt(99);
        //if (n != 0)
          nums.add(n + 1);
      }while (nums.size() < 5);
      //System.out.println(nums);
      for (Integer num : nums){
        rs.absolute(num);
        System.out.println(rs.getString("user_id"));
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    } finally{
      ConnectionUtils.closeStatement(stmt);
      ConnectionUtils.closeConnection(conn);
    }

  }

}
