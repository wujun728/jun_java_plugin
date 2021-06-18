package book.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 连接各类数据库的方法
 */
public class DBConnector {
	/**
	 * 获得数据库连接
	 * @param driverClassName	连接数据库用到的驱动类的类名
	 * @param dbURL		数据库的URL
	 * @param userName	登陆数据库的用户名
	 * @param password	登陆数据库的密码
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String driverClassName,
			String dbURL, String userName, String password)
			throws ClassNotFoundException, SQLException {
		Connection con = null;

		// 加载连接数据库的驱动类
		Class.forName(driverClassName);
		// 用用户名、密码连接数据库
		con = DriverManager.getConnection(dbURL, userName, password);

		return con;
	}
	
	/**
	 * 获得Oracle数据库的连接
	 * @param dricerClassName	连接数据库用到的驱动类的类名
	 * @param serverHost	数据库所在服务器的IP或域名
	 * @param serverPort	数据库所在服务器的端口
	 * @param dbName		数据库名
	 * @param userName		登陆数据库的用户名
	 * @param password		登陆数据库的密码
	 * @return
	 * @throws ClassNotFoundException		数据库驱动类无法找到是抛出该异常
	 * @throws SQLException		创建连接时可能抛出该异常
	 */
	public static Connection getOracleConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// 如果没有提供这些连接参数，则用默认值
		if (dricerClassName == null) {
			dricerClassName = "oracle.jdbc.driver.OracleDriver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "1521";
		}
		// 构建访问Oracle数据库的URL
		String dbURL = "jdbc:oracle:thin:@" + serverHost + ":" + serverPort
				+ ":" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * 获得DB2数据库的连接
	 */
	public static Connection getDB2Connection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// 如果没有提供这些连接参数，则用默认值
		if (dricerClassName == null) {
			dricerClassName = "com.ibm.db2.jdbc.app.DB2Driver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "5000";
		}
		// 构建访问DB2数据库的URL
		String dbURL = "jdbc:db2://" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * 获得SQL Server数据库的连接
	 */
	public static Connection getSQLServerConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// 如果没有提供这些连接参数，则用默认值
		if (dricerClassName == null) {
			dricerClassName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "1433";
		}
		// 构建访问SQL Server数据库的URL
		String dbURL = "jdbc:microsoft:sqlserver://" + serverHost + ":" + serverPort
				+ "; DatabaseName=" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * 获得MySQL数据库的连接
	 */
	public static Connection getMySQLConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// 如果没有提供这些连接参数，则用默认值
		if (dricerClassName == null) {
			dricerClassName = "com.mysql.jdbc.Driver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "3306";
		}
		// 构建访问SQL Server数据库的URL
		String dbURL = "jdbc:mysql://" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * 获得Sybase数据库的连接
	 */
	public static Connection getSybaseConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// 如果没有提供这些连接参数，则用默认值
		if (dricerClassName == null) {
			dricerClassName = "com.sybase.jdbc3.jdbc.SybDriver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "5007";
		}
		// 构建访问SQL Server数据库的URL
		String dbURL = "jdbc:sybase:Tds:" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}
	
	/**
	 * 获得PostgreSQL数据库的连接
	 */
	public static Connection getPostgreSQLConnection(String dricerClassName,
			String serverHost, String serverPort, String dbName,
			String userName, String password) throws ClassNotFoundException,
			SQLException {
		// 如果没有提供这些连接参数，则用默认值
		if (dricerClassName == null) {
			dricerClassName = "org.postgresql.Driver";
		}
		if (serverHost == null) {
			serverHost = "127.0.0.1";
		}
		if (serverPort == null) {
			serverPort = "5432";
		}
		// 构建访问SQL Server数据库的URL
		String dbURL = "jdbc:postgresql://" + serverHost + ":" + serverPort
				+ "/" + dbName;
		return getConnection(dricerClassName, dbURL, userName, password);
	}

	public static void main(String[] args) throws ClassNotFoundException, 
			SQLException {
		// 获得本地MySQL的连接实例，使用MySQL需要去www.mysql.com下载最新的MySQL安装程序和Java驱动
		// MySQL有多个连接MySQL的驱动类，如org.gjt.mm.mysql.Driver。
		// 这里使用MySQL官方网站上提供的驱动类
		String mySQLDirver = "com.mysql.jdbc.Driver";
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		Connection con = DBConnector.getMySQLConnection(mySQLDirver,
				null, null, dbName, userName, password);
		System.out.println("连接MySQL数据库成功！");
		con.close();
		System.out.println("成功关闭与MySQL数据库的连接！");
		String url = "jdbc:mysql://127.0.0.1:3306/" +  dbName;
		con = DBConnector.getConnection(mySQLDirver, url, userName, password);
		System.out.println("连接MySQL数据库成功！");
		con.close();
		System.out.println("成功关闭与MySQL数据库的连接！");
	}
}
