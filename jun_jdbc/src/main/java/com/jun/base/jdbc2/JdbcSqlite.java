package com.jun.base.jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Wujun
 */
public class JdbcSqlite {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// 0 连接SQLite的JDBC
			String sql = "jdbc:sqlite://e:/tim.db";
			Class.forName("org.sqlite.JDBC");
			// 1 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager.getConnection(sql);
			Statement stat = conn.createStatement();
			// 2 创建一个表tbl1，录入数据
			stat.executeUpdate("drop table if exists tbl1;");
			stat.executeUpdate("create table if not exists tbl1(name varchar(20), salary int);");// 创建一个表，两列
			stat.executeUpdate("insert into tbl1 values('ZhangSan',8000);"); // 插入数据
			stat.executeUpdate("insert into tbl1 values('LiSi',7800);");
			stat.executeUpdate("insert into tbl1 values('WangWu',5800);");
			stat.executeUpdate("insert into tbl1 values('ZhaoLiu',9100);");
			ResultSet rs = stat.executeQuery("select * from tbl1;"); // 查询数据
			System.out.println("创建表结构录入数据操作演示：");
			while (rs.next()) { // 将查询到的数据打印出来
				System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
				System.out.println("salary = " + rs.getString("salary")); // 列属性二
			}
			rs.close();
			// 3 修改表结构，添加字段 address varchar(20) default 'changsha';
			stat.executeUpdate("alter table tbl1 add column address varchar(20) not null default 'changsha'; ");// 创建一个表，两列
			stat.executeUpdate("insert into tbl1 values('HongQi',9000,'tianjing');"); // 插入数据
			stat.executeUpdate("insert into tbl1(name,salary) values('HongQi',9000);"); // 插入数据
			rs = stat.executeQuery("select * from tbl1;"); // 查询数据
			System.out.println("表结构变更操作演示：");
			while (rs.next()) { // 将查询到的数据打印出来
				System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
				System.out.print("name = " + rs.getString("name") + ", "); // 列属性二
				System.out.println("address = " + rs.getString("address")); // 列属性三
			}
			rs.close();
			conn.close(); // 结束数据库的连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	   
	public static void main2(String[] args) throws Exception {   
	          Class.forName("org.sqlite.JDBC");   
	          Connection conn = DriverManager.getConnection("jdbc:sqlite:test2.db");   
	          Statement stat = conn.createStatement();   
	          stat.executeUpdate("drop table if exists people;");   
	          stat.executeUpdate("create table people (name, occupation);");   
	          PreparedStatement prep = conn.prepareStatement(   
	"insert into people values (?, ?);");   
	          prep.setString(1, "Gandhi");   
	          prep.setString(2, "politics");   
	          prep.addBatch();   
	          prep.setString(1, "Turing");   
	          prep.setString(2, "computers");   
	          prep.addBatch();   
	          prep.setString(1, "Wittgenstein");   
	          prep.setString(2, "smartypants");   
	          prep.addBatch();   
	          conn.setAutoCommit(false);   
	          prep.executeBatch();   
	          conn.setAutoCommit(true);   
	          ResultSet rs = stat.executeQuery("select * from people;");   
	while (rs.next()) {   
	              System.out.println("name = " + rs.getString("name"));   
	              System.out.println("job = " + rs.getString("occupation"));   
	          }   
	          rs.close();   
	          conn.close();   
	      }  
	
	
	

    public static void main4(String[] args) {
        try {
            // The SQLite (3.3.8) Database File
            // This database has one table (pmp_countries) with 3 columns (country_id, country_code, country_name)
            // It has like 237 records of all the countries I could think of.
            String fileName = "c:/pmp.db";
            // Driver to Use
            // http://www.zentus.com/sqlitejdbc/index.html
            Class.forName("org.sqlite.JDBC");
            // Create Connection Object to SQLite Database
            // If you want to only create a database in memory, exclude the +fileName
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            // Create a Statement object for the database connection, dunno what this stuff does though.
            Statement stmt = conn.createStatement();
            // Create a result set object for the statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM pmp_countries ORDER BY country_name ASC");
            // Iterate the result set, printing each column
            // if the column was an int, we could do rs.getInt(column name here) as well, etc.
            while (rs.next()) {
                String id   = rs.getString("country_id");   // Column 1
                String code = rs.getString("country_code"); // Column 2
                String name = rs.getString("country_name"); // Column 3
                System.out.println("ID: "+id+" Code: "+code+" Name: "+name);
            }
            // Close the connection
            conn.close();
        }
        catch (Exception e) {
            // Print some generic debug info
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
    }
}