package org.zxjava.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionPoolTest {

	public static void main(String args[]) {
		
		//初始化数据库连接池类
		ConnectionPool connPool = new ConnectionPool("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test", "root", "admin");

		try {
			//创建数据库连接池
			connPool.createPool();
			
			//连接池的相应信息
			System.out.println("*************************************************");
			System.out.println("链接池自动增加的大小：" + connPool.getIncrementalConnections());
			System.out.println("链接池初始大小：" + connPool.getInitialConnections());
			System.out.println("链接池最大连接数量：" + connPool.getMaxConnections());
			System.out.println("*************************************************");
			
			//通过连接池获得一个数据库连接
			Connection conn = connPool.getConnection();
			
			String sql = "select * from user";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				
				System.out.println("username is :" + rs.getString("username"));
				System.out.println("password is :" + rs.getString("password"));
			}
			rs.close();
			pstmt.close();
			connPool.returnConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
