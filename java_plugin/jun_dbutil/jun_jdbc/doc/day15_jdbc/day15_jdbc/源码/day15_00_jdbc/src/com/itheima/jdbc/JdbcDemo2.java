package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//DriverManager详解
public class JdbcDemo2 {

	public static void main(String[] args) throws Exception {
		//方式一：不建议使用
//		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		//方式二：
		Class.forName("com.mysql.jdbc.Driver");
		
//---------------------------------------------------------------------------------------
//		2、获取与数据库的链接
		//方式一：
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day15", "root", "sorry");
		
		//方式二：
//		Properties props = new Properties();
//		props.setProperty("user", "root");//  参数名：参考数据库的文档
//		props.setProperty("password", "sorry");
//		props.setProperty("useUnicode", "true");//编码有关的参数
//		props.setProperty("characterEncoding", "utf8");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day15",props);
		
		//方式三
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day15?user=root&password=sorry");
		
		//System.out.println(conn.getClass().getName()); 要想知道具体类型，就这么办
//		3、创建代表SQL语句的对象
		Statement stmt = conn.createStatement();
//		4、执行SQL语句
		ResultSet rs = stmt.executeQuery("select id,name,password,email,birthday from users");
//		5、如果是查询语句，需要遍历结果集
		while(rs.next()){
			System.out.println("---------------------");
			System.out.println(rs.getObject("id"));
			System.out.println(rs.getObject("name"));
			System.out.println(rs.getObject("password"));
			System.out.println(rs.getObject("email"));
			System.out.println(rs.getObject("birthday"));
		}
//		6、释放占用的资源
		rs.close();
		stmt.close();
		conn.close();
	}

}
