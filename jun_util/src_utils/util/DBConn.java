package cn.ipanel.routing.model.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import cn.ipanel.routing.util.Config;

public class DBConn{
	private static BasicDataSource ds;

	
	static{
		try{
			ds = new BasicDataSource();
			ds.setDriverClassName(Config.DATABASE_DRIVER);//Java的JDBC驱动程序类的名字
			ds.setUrl(Config.DATABASE_URL);               //数据库URL
			ds.setUsername(Config.DATABASE_USERNAME);     
			ds.setPassword(Config.DATABASE_PASSWORD);
			ds.setValidationQuery("select count(*) from area");//  SQL查询，将用来验证呼叫者他们从该池的连接，然后返回。
			ds.setTestOnBorrow(true);// 指示的对象是否将被验证前池借用了
			ds.setPoolPreparedStatements(true);//设定是否池声明或没有。
			ds.setInitialSize(10);//在启动初期池中的连接时创建的。
			ds.setMaxActive(30);//在没有限制的数量最大负主动或连接，这可以从池的分配，同样的时间。
			ds.setRemoveAbandoned(true); //移除废弃的
			ds.setRemoveAbandonedTimeout(120);
			}catch (Exception e){
			e.printStackTrace();
			}
	}


	public static Connection getConnection(boolean autocommit){
		Connection conn=null;
		try {
			conn= ds.getConnection();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}