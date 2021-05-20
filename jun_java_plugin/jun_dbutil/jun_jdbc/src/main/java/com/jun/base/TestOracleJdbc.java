package com.jun.base;

import java.sql.*;

import javax.sql.*;

public class TestOracleJdbc {
	
	Connection conn = null;
	  PreparedStatement prepareStmt = null;
	  ResultSet rs = null;
	  
	  public void init(){
	    try {
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
	      String dbUsername = "openlab";
	      String dbPassword = "open123";
	      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  }
	  public void close(){
	    try {
	      if(rs != null)
	        rs.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    try {
	      if (prepareStmt != null)
	        prepareStmt.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    try {
	      if (conn != null)
	        conn.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
	  /**
	   * @param args
	   */
	  public static void main(String[] args) {
	    // new TestJdbc5().insert();
	    //new TestJdbc5().update();
	    new TestOracleJdbc().delete();
	  }
	  public void delete() {
	    init();
	    try {
	      String sql = "delete mydepttemp " +
	          " where deptno = ?";
	      prepareStmt = conn.prepareStatement(sql);
	      prepareStmt.setInt(1, 10);

	      int n = prepareStmt.executeUpdate();
	      System.out.println(n+"����¼��ɾ��");
	    }  catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      close();
	    }
	  }


	  public void update() {
	    init();
	    try {
	      String sql = "update mydepttemp " +
	          "set loc = ? " + "where deptno = ?";
	      prepareStmt = conn.prepareStatement(sql);
	      prepareStmt.setString(1, "");
	      prepareStmt.setInt(2, 10);

	      int n = prepareStmt.executeUpdate();
	      System.out.println(n+"");
	      System.out.println((n > 0) ? "OK" : "error");

	    }  catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      close();
	    }
	  }

	  public void insert() {
	    init();
	    try {
	      
	      String sql = "insert into dept " + "values(?, ?, ?)";
	      prepareStmt = conn.prepareStatement(sql);
	      prepareStmt.setInt(1, 10);
	      prepareStmt.setString(2, "market");
	      prepareStmt.setString(3, "beijing");

	      int n = prepareStmt.executeUpdate();

	      System.out.println((n == 1) ? "OK" : "error");

	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      close();
	    }
	  }

	  public  void select() {
	    init();
	    try {
	      String sql = "select * from vemp " + " where deptno = ?";
	      prepareStmt = conn.prepareStatement(sql);
	      prepareStmt.setInt(1, 20);
	      rs = prepareStmt.executeQuery();
	      while (rs.next()) {
	        System.out.println(rs.getString("ename") + ", "
	            + rs.getString("deptno"));
	      }
	    }catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      close();
	    }

	  }

	  

	public static void TestSelect1() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
			String dbUsername = "openlab";
			String dbPassword = "open123";
			conn = DriverManager.getConnection(url, dbUsername, dbPassword);
			stmt = conn.createStatement();
			String sql = "select dname, loc from dept";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String dname = rs.getString("dname");
				String loc = rs.getString("loc");
				System.out.println(dname + ", " + loc);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
	
	 public static void TestSelect() {
		    Connection conn = null;
		    Statement stmt = null;
		    ResultSet rs = null;
		    try {
		      Class.forName("oracle.jdbc.driver.OracleDriver");
		      String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
		      String dbUsername = "openlab";
		      String dbPassword = "open123";
		      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
		      stmt = conn.createStatement();
		      String sql = "select empno, ename, job, sal, " +
		          "to_char(hiredate, 'yyyy/mm/dd') hiredate " +
		          "from mystu";
		      //System.out.println(sql);
		      stmt.setMaxRows(5);
		      rs = stmt.executeQuery(sql);//130000
		      //rs.setFetchSize(5);
		      
		      while (rs.next()) {
		        int empno = rs.getInt("empno");
		        String ename = rs.getString("ename");
		        String job = rs.getString("job");
		        double sal = rs.getDouble("sal");
		        String hiredate = rs.getString("hiredate");
		        System.out.println(empno + ", " 
		            + ename + ", " 
		            + job + ", " 
		            + sal + ", "
		            + hiredate);
		      }
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        rs.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		      try {
		        stmt.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		      try {
		        conn.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }

		    }

		  }
	 
	 
	 public static void TestCount() {
		    Connection conn = null;
		    Statement stmt = null;
		    ResultSet rs = null;
		    try {
		      Class.forName("oracle.jdbc.driver.OracleDriver");
		      String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
		      String dbUsername = "openlab";
		      String dbPassword = "open123";
		      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
		      // 3.���������
		      stmt = conn.createStatement();
		      String sql = "select count(*) emp_count, " +
		          "avg(sal) avg_sal " +
		          "from emp";
		      rs = stmt.executeQuery(sql);
		      if (rs.next()) {
		        int count = rs.getInt("emp_count");
		        double avg_sal = rs.getDouble("avg_sal");
		        System.out.println("total:" + count);
		        System.out.println("avg:" + avg_sal);
		      }
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        rs.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		      try {
		        stmt.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		      try {
		        conn.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }

		    }

		  }
	 
	 public static void TestSelect2() {
		    Connection conn = null;
		    Statement stmt = null;
		    ResultSet rs = null;
		    try {
		      Class.forName("oracle.jdbc.driver.OracleDriver");
		      String url = "jdbc:oracle:thin:@192.168.0.26:1521:test";
		      String dbUsername = "openlab";
		      String dbPassword = "open123";
		      conn = DriverManager.getConnection(url, dbUsername, dbPassword);

		      DatabaseMetaData dmd = conn.getMetaData();
		      System.out.println(dmd.getDatabaseProductName());
		      System.out.println(dmd.getDatabaseProductVersion());
		      System.out.println(dmd.getDriverName());
		      System.out.println(dmd.getURL());
		      System.out.println(dmd.getUserName());

		      stmt = conn.createStatement();
		      String sql = "select e.ename, d.dname" +
		          " from vemp e join vdept d" +
		          " on e.deptno = d.deptno";
		      rs = stmt.executeQuery(sql);
		      ResultSetMetaData rsm = rs.getMetaData();
		      int count = rsm.getColumnCount();
		      for (int i = 1; i <= count; i++) {
		        System.out.print(
		            rsm.getColumnName(i) + "\t");
		      }
		      System.out.println();
		      while (rs.next()) {
		        for (int i = 1; i <= count; i++) {
		          System.out.print(
		              rs.getString(rsm.getColumnName(i)) + "\t");
		        }
		        System.out.println();
		      }

		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        rs.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		      try {
		        stmt.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		      try {
		        conn.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }

		    }

		  }


}
