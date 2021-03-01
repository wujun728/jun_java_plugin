package book.database.pool;

import java.sql.Connection;

public class DatabasePoolTest {

	public static JDBCInfo getJDBCInfo(){
		JDBCInfo jdbc = new JDBCInfo();
		jdbc.setDriver("com.mysql.jdbc.Driver");
		jdbc.setName("MySQL");
		jdbc.setUrl("jdbc:mysql://127.0.0.1:3306/studentdb");
		jdbc.setUser("test");
		jdbc.setPassword("test");
		jdbc.setMaxconn(10);
		return jdbc;
	}
	
	public static void main(String[] args) {
		JDBCInfo jdbc = getJDBCInfo();
		String sql = "SELECT * FROM student_basic";
		DataConnectMgr mgr =  DataConnectMgr.getInstance(jdbc);
		//从数据库连接池中获取连接
		Connection con = mgr.getConnection(jdbc.getName());
        try {
        	System.out.println("正在使用刚刚获得的数据库连接");
            java.sql.Statement sm = con.createStatement();
            sm.executeQuery(sql);
            //do something
            sm.close();
        } catch (java.sql.SQLException e) {
        	System.err.println("连接数据库出错！");
        } finally {
        	//释放连接到数据库连接池
        	mgr.freeConnection(jdbc.getName(), con);
        }
        mgr.release();
	}
}
