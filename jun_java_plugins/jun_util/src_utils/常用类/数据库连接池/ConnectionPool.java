package org.zxjava.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

public class ConnectionPool {

	private String jdbcDriver = ""; // 数据库驱动

	private String dbUrl = ""; // 数据 URL

	private String dbUsername = ""; // 数据库用户名

	private String dbPassword = ""; // 数据库用户密码

	private String testTable = ""; // 测试连接是否可用的测试表名，默认没有测试表

	private int initialConnections = 10; // 连接池的初始大小

	private int incrementalConnections = 5;// 连接池自动增加的大小

	private int maxConnections = 50; // 连接池最大的大小

	private Vector connections = null; // 存放连接池中数据库连接的向量 , 初始时为 null

	// 它中存放的对象为 PooledConnection 型

	/**

	 * 构造函数

	 *

	 * @param jdbcDriver String JDBC 驱动类串

	 * @param dbUrl String 数据库 URL

	 * @param dbUsername String 连接数据库用户名

	 * @param dbPassword String 连接数据库用户的密码

	 *

	 */

	public ConnectionPool(String jdbcDriver, String dbUrl, String dbUsername,
			String dbPassword) {

		this.jdbcDriver = jdbcDriver;

		this.dbUrl = dbUrl;

		this.dbUsername = dbUsername;

		this.dbPassword = dbPassword;

	}

	/**

	 * 返回连接池的初始大小

	 *

	 * @return 初始连接池中可获得的连接数量

	 */

	public int getInitialConnections() {

		return this.initialConnections;

	}

	/**

	 * 设置连接池的初始大小

	 *

	 * @param 用于设置初始连接池中连接的数量

	 */

	public void setInitialConnections(int initialConnections) {

		this.initialConnections = initialConnections;

	}

	/**

	 * 返回连接池自动增加的大小 、

	 *

	 * @return 连接池自动增加的大小

	 */

	public int getIncrementalConnections() {

		return this.incrementalConnections;

	}

	/**

	 * 设置连接池自动增加的大小

	 * @param 连接池自动增加的大小

	 */

	public void setIncrementalConnections(int incrementalConnections) {

		this.incrementalConnections = incrementalConnections;

	}

	/**

	 * 返回连接池中最大的可用连接数量

	 * @return 连接池中最大的可用连接数量

	 */

	public int getMaxConnections() {

		return this.maxConnections;

	}

	/**

	 * 设置连接池中最大可用的连接数量

	 *

	 * @param 设置连接池中最大可用的连接数量值

	 */

	public void setMaxConnections(int maxConnections) {

		this.maxConnections = maxConnections;

	}

	/**

	 * 获取测试数据库表的名字

	 *

	 * @return 测试数据库表的名字

	 */

	public String getTestTable() {

		return this.testTable;

	}

	/**

	 * 设置测试表的名字

	 * @param testTable String 测试表的名字

	 */

	public void setTestTable(String testTable) {

		this.testTable = testTable;

	}

	/**

	 *

	 * 创建一个数据库连接池，连接池中的可用连接的数量采用类成员

	 * initialConnections 中设置的值

	 */

	public synchronized void createPool() throws Exception {

		// 确保连接池没有创建

		// 如果连接池己经创建了，保存连接的向量 connections 不会为空

		if (connections != null) {

			return; // 如果己经创建，则返回

		}

		// 实例化 JDBC Driver 中指定的驱动类实例

		Driver driver = (Driver) (Class.forName(this.jdbcDriver).newInstance());

		DriverManager.registerDriver(driver); // 注册 JDBC 驱动程序

		// 创建保存连接的向量 , 初始时有 0 个元素

		connections = new Vector();

		// 根据 initialConnections 中设置的值，创建连接。

		createConnections(this.initialConnections);

		System.out.println(" 数据库连接池创建成功！ ");

	}

	/**

	 * 创建由 numConnections 指定数目的数据库连接 , 并把这些连接

	 * 放入 connections 向量中

	 *

	 * @param numConnections 要创建的数据库连接的数目

	 */

	@SuppressWarnings("unchecked")
	private void createConnections(int numConnections) throws SQLException {

		// 循环创建指定数目的数据库连接

		for (int x = 0; x < numConnections; x++) {

			// 是否连接池中的数据库连接的数量己经达到最大？最大值由类成员 maxConnections

			// 指出，如果 maxConnections 为 0 或负数，表示连接数量没有限制。

			// 如果连接数己经达到最大，即退出。

			if (this.maxConnections > 0
					&& this.connections.size() >= this.maxConnections) {

				break;

			}

			//add a new PooledConnection object to connections vector

			// 增加一个连接到连接池中（向量 connections 中）

			try {

				connections.addElement(new PooledConnection(newConnection()));

			} catch (SQLException e) {

				System.out.println(" 创建数据库连接失败！ " + e.getMessage());

				throw new SQLException();

			}

			System.out.println(" 数据库连接己创建 ......");

		}

	}

	/**

	 * 创建一个新的数据库连接并返回它

	 *

	 * @return 返回一个新创建的数据库连接

	 */

	private Connection newConnection() throws SQLException {

		// 创建一个数据库连接

		Connection conn = DriverManager.getConnection(dbUrl, dbUsername,
				dbPassword);

		// 如果这是第一次创建数据库连接，即检查数据库，获得此数据库允许支持的

		// 最大客户连接数目

		//connections.size()==0 表示目前没有连接己被创建

		if (connections.size() == 0) {

			DatabaseMetaData metaData = conn.getMetaData();

			int driverMaxConnections = metaData.getMaxConnections();

			// 数据库返回的 driverMaxConnections 若为 0 ，表示此数据库没有最大

			// 连接限制，或数据库的最大连接限制不知道

			//driverMaxConnections 为返回的一个整数，表示此数据库允许客户连接的数目

			// 如果连接池中设置的最大连接数量大于数据库允许的连接数目 , 则置连接池的最大

			// 连接数目为数据库允许的最大数目

			if (driverMaxConnections > 0
					&& this.maxConnections > driverMaxConnections) {

				this.maxConnections = driverMaxConnections;

			}

		}

		return conn; // 返回创建的新的数据库连接

	}

	/**

	 * 通过调用 getFreeConnection() 函数返回一个可用的数据库连接 ,

	 * 如果当前没有可用的数据库连接，并且更多的数据库连接不能创

	 * 建（如连接池大小的限制），此函数等待一会再尝试获取。

	 *

	 * @return 返回一个可用的数据库连接对象

	 */

	public synchronized Connection getConnection() throws SQLException {

		// 确保连接池己被创建

		if (connections == null) {

			return null; // 连接池还没创建，则返回 null

		}

		Connection conn = getFreeConnection(); // 获得一个可用的数据库连接

		// 如果目前没有可以使用的连接，即所有的连接都在使用中

		while (conn == null) {

			// 等一会再试

			wait(250);

			conn = getFreeConnection(); // 重新再试，直到获得可用的连接，如果

			//getFreeConnection() 返回的为 null

			// 则表明创建一批连接后也不可获得可用连接

		}

		return conn;// 返回获得的可用的连接

	}

	/**

	 * 本函数从连接池向量 connections 中返回一个可用的的数据库连接，如果

	 * 当前没有可用的数据库连接，本函数则根据 incrementalConnections 设置

	 * 的值创建几个数据库连接，并放入连接池中。

	 * 如果创建后，所有的连接仍都在使用中，则返回 null

	 * @return 返回一个可用的数据库连接

	 */

	private Connection getFreeConnection() throws SQLException {

		// 从连接池中获得一个可用的数据库连接

		Connection conn = findFreeConnection();

		if (conn == null) {

			// 如果目前连接池中没有可用的连接

			// 创建一些连接

			createConnections(incrementalConnections);

			// 重新从池中查找是否有可用连接

			conn = findFreeConnection();

			if (conn == null) {

				// 如果创建连接后仍获得不到可用的连接，则返回 null

				return null;

			}

		}

		return conn;

	}

	/**

	 * 查找连接池中所有的连接，查找一个可用的数据库连接，

	 * 如果没有可用的连接，返回 null

	 *

	 * @return 返回一个可用的数据库连接

	 */

	private Connection findFreeConnection() throws SQLException {

		Connection conn = null;

		PooledConnection pConn = null;

		// 获得连接池向量中所有的对象

		Enumeration enumerate = connections.elements();

		// 遍历所有的对象，看是否有可用的连接

		while (enumerate.hasMoreElements()) {

			pConn = (PooledConnection) enumerate.nextElement();

			if (!pConn.isBusy()) {

				// 如果此对象不忙，则获得它的数据库连接并把它设为忙

				conn = pConn.getConnection();

				pConn.setBusy(true);

				// 测试此连接是否可用

				if (!testConnection(conn)) {

					// 如果此连接不可再用了，则创建一个新的连接，

					// 并替换此不可用的连接对象，如果创建失败，返回 null

					try {

						conn = newConnection();

					} catch (SQLException e) {

						System.out.println(" 创建数据库连接失败！ " + e.getMessage());

						return null;

					}

					pConn.setConnection(conn);

				}

				break; // 己经找到一个可用的连接，退出

			}

		}

		return conn;// 返回找到到的可用连接

	}

	/**

	 * 测试一个连接是否可用，如果不可用，关掉它并返回 false

	 * 否则可用返回 true

	 *

	 * @param conn 需要测试的数据库连接

	 * @return 返回 true 表示此连接可用， false 表示不可用

	 */

	private boolean testConnection(Connection conn) {

		try {

			// 判断测试表是否存在

			if (testTable.equals("")) {

				// 如果测试表为空，试着使用此连接的 setAutoCommit() 方法

				// 来判断连接否可用（此方法只在部分数据库可用，如果不可用 ,

				// 抛出异常）。注意：使用测试表的方法更可靠

				conn.setAutoCommit(true);

			} else {// 有测试表的时候使用测试表测试

				//check if this connection is valid

				Statement stmt = conn.createStatement();

				stmt.execute("select count(*) from " + testTable);

			}

		} catch (SQLException e) {

			// 上面抛出异常，此连接己不可用，关闭它，并返回 false;

			closeConnection(conn);

			return false;

		}

		// 连接可用，返回 true

		return true;

	}

	/**

	 * 此函数返回一个数据库连接到连接池中，并把此连接置为空闲。

	 * 所有使用连接池获得的数据库连接均应在不使用此连接时返回它。

	 *

	 * @param 需返回到连接池中的连接对象

	 */

	
	//********************************************** 用完此连接后释放连接 **********************************
	public void returnConnection(Connection conn) {

		// 确保连接池存在，如果连接没有创建（不存在），直接返回

		if (connections == null) {

			System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");

			return;

		}

		PooledConnection pConn = null;

		Enumeration enumerate = connections.elements();

		// 遍历连接池中的所有连接，找到这个要返回的连接对象

		while (enumerate.hasMoreElements()) {

			pConn = (PooledConnection) enumerate.nextElement();

			// 先找到连接池中的要返回的连接对象

			if (conn == pConn.getConnection()) {

				// 找到了 , 设置此连接为空闲状态

				pConn.setBusy(false);

				break;

			}

		}

	}

	/**

	 * 刷新连接池中所有的连接对象

	 *

	 */

	public synchronized void refreshConnections() throws SQLException {

		// 确保连接池己创新存在

		if (connections == null) {

			System.out.println(" 连接池不存在，无法刷新 !");

			return;

		}

		PooledConnection pConn = null;

		Enumeration enumerate = connections.elements();

		while (enumerate.hasMoreElements()) {

			// 获得一个连接对象

			pConn = (PooledConnection) enumerate.nextElement();

			// 如果对象忙则等 5 秒 ,5 秒后直接刷新

			if (pConn.isBusy()) {

				wait(5000); // 等 5 秒

			}

			// 关闭此连接，用一个新的连接代替它。

			closeConnection(pConn.getConnection());

			pConn.setConnection(newConnection());

			pConn.setBusy(false);

		}

	}

	/**

	 * 关闭连接池中所有的连接，并清空连接池。

	 */

	public synchronized void closeConnectionPool() throws SQLException {

		// 确保连接池存在，如果不存在，返回

		if (connections == null) {

			System.out.println(" 连接池不存在，无法关闭 !");

			return;

		}

		PooledConnection pConn = null;

		Enumeration enumerate = connections.elements();

		while (enumerate.hasMoreElements()) {

			pConn = (PooledConnection) enumerate.nextElement();

			// 如果忙，等 5 秒

			if (pConn.isBusy()) {

				wait(5000); // 等 5 秒

			}

			//5 秒后直接关闭它

			closeConnection(pConn.getConnection());

			// 从连接池向量中删除它

			connections.removeElement(pConn);

		}

		// 置连接池为空

		connections = null;

	}

	/**

	 * 关闭一个数据库连接

	 *

	 * @param 需要关闭的数据库连接

	 */

	private void closeConnection(Connection conn) {

		try {

			conn.close();

		} catch (SQLException e) {

			System.out.println(" 关闭数据库连接出错： " + e.getMessage());

		}

	}

	/**

	 * 使程序等待给定的毫秒数

	 *

	 * @param 给定的毫秒数

	 */

	private void wait(int mSeconds) {

		try {

			Thread.sleep(mSeconds);

		} catch (InterruptedException e) {

		}

	}

	/**

	 *

	 * 内部使用的用于保存连接池中连接对象的类

	 * 此类中有两个成员，一个是数据库的连接，另一个是指示此连接是否

	 * 正在使用的标志。

	 */

	class PooledConnection {

		Connection connection = null;// 数据库连接

		boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用

		// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象

		public PooledConnection(Connection connection) {

			this.connection = connection;

		}

		// 返回此对象中的连接

		public Connection getConnection() {

			return connection;

		}

		// 设置此对象的，连接

		public void setConnection(Connection connection) {

			this.connection = connection;

		}

		// 获得对象连接是否忙

		public boolean isBusy() {

			return busy;

		}

		// 设置对象的连接正在忙

		public void setBusy(boolean busy) {

			this.busy = busy;

		}

	}

}

/*

 这个例子是根据POSTGRESQL数据库写的，
 请用的时候根据实际的数据库调整。

 调用方法如下：

 ①　ConnectionPool connPool 
 = new ConnectionPool("org.postgresql.Driver"
 ,"jdbc:postgresql://dbURI:5432/DBName"
 ,"postgre"
 ,"postgre");

 ②　connPool .createPool();
 Connection conn = connPool .getConnection();

 */