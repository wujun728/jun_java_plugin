package book.database.pool;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 数据库连接管理器，能够管理不同种类数据库的连接池。
 */
public class DataConnectMgr {
	//缺省的数据库连接池配置文件
	public static final String DEFAULT_DB_PROPERTIES = "./db.properties";
	// 唯一实例
	static private DataConnectMgr instance; 
	// 当前连接到该数据连接管理器上的客户端数目
	static private int clients; 
	//存放驱动类，对于每种数据库，都有一个驱动类
	private Vector drivers = new Vector();
	//存放已经建立的连接池，每种数据库都有一个连接池
	private Hashtable pools = new Hashtable();

	/**
	 * 构造函数私有，以防止其它对象创建本类实例
	 */
	private DataConnectMgr() {
		init();
	}
	private DataConnectMgr(LinkedList drivers, Hashtable jdbcs) {
		init2(drivers, jdbcs);
	}

	/**
	 * 单例模式，返回唯一实例，如果是第一次调用此方法，则创建实例，
	 * 根据缺省的数据库连接池配置文件创建连接池
	 * @return DBConnectionManager 唯一实例
	 */
	public static synchronized DataConnectMgr getInstance() {
		if (instance != null) {
			clients++;
			return instance;
		} else {
			instance = new DataConnectMgr();
			return instance;
		}
	}
	/**
	 * 获取数据库连接管理器实例，如果不存在，便创建实例，并指定了创建连接池的参数
	 * @param jdbcInfo	连接数据库的参数
	 * @return
	 */
	public static synchronized DataConnectMgr getInstance(JDBCInfo jdbcInfo) {
		if (instance != null) {
			clients++;
			return instance;
		}
		LinkedList drivers = new LinkedList();
		drivers.add(jdbcInfo.getDriver());
		Hashtable jdbcs = new Hashtable();
		jdbcs.put(jdbcInfo.getName(), jdbcInfo);
		return getInstance(drivers, jdbcs);
	}

	/**
	 * 获取数据库连接管理器实例，如果不存在，便创建实例，可以一次创建多个连接池
	 * @param drivers	每个连接池的数据库驱动类
	 * @param jdbcs		每个连接池的连接参数
	 * @return
	 */
	public static synchronized DataConnectMgr getInstance(LinkedList drivers,
			Hashtable jdbcs) {
		if (instance == null) {
			instance = new DataConnectMgr(drivers, jdbcs);
		}
		clients++;
		return instance;
	}

	/**
	 * 读取缺省的数据库连接池配置文件，根据缺省值完成初始化，创建数据库连接池
	 */
	private void init() {
		InputStream is = null;
		Properties dbProps = new Properties();
		try {
			is = new FileInputStream(DEFAULT_DB_PROPERTIES);
			dbProps.load(is);
		} catch (Exception e) {
			System.err.println("不能够读取默认数据库连接池配置文件，请确认文件是否存在："
							+ DEFAULT_DB_PROPERTIES);
			return;
		}
		loadDrivers(dbProps);
		createPools(dbProps);
	}

	/**
	 * 创建多个数据库连接池
	 * @param drivers	每个连接池的数据库驱动类
	 * @param jdbcInfo	每个连接池的连接参数
	 */
	private void init2(LinkedList drivers, Hashtable jdbcInfo) {
		loadDrivers(drivers);
		createPools(jdbcInfo);
	}

	/**
	 * 动态加载数据库连接驱动类
	 * @param mdrivers
	 */
	private void loadDrivers(LinkedList mdrivers) {
		for (int i = 0; i < mdrivers.size(); i++) {
			try {
				//根据数据库连接驱动类，利用反射机制创建驱动类对象
				Driver driver = (Driver) Class
						.forName((String) mdrivers.get(i)).newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				System.out.println("成功加载数据库连接驱动:  "
						+ mdrivers.get(i));
			} catch (Exception e) {
				System.err.println("加载数据库连接驱动失败:  " 
						+ mdrivers.get(i) + ". 错误信息：" + e);
			}
		}
	}

	/**
	 * 装载和注册数据库连接配置文件中所有的JDBC驱动程序
	 * @param props 属性
	 */
	private void loadDrivers(Properties props) {
		String driverClasses = null;
		driverClasses = props.getProperty("drivers");

		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				// 新建驱动类
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance();
				// 注册驱动
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				System.out.println("成功加载数据库连接驱动: "
						+ driverClassName);
			} catch (Exception e) {
				System.err.println("加载数据库连接驱动失败: "
						+ driverClassName + ". 错误信息: " + e);
			}
		}
	}

	/**
	 * 根据指定的数据库连接池配置文件创建连接池实例.
	 * @param props 连接池属性
	 */
	private void createPools(Properties props) {
		Enumeration propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			// 获得连接数据库的各种属性
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");

				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					System.err.println("最大连接数限制数错误: "
							+ maxconn + " .连接池名: " + poolName);
					max = 0;
				}
				System.out.println("准备创建数据库连接池：" + poolName);
				DBConnectionPool pool = new DBConnectionPool(poolName, url,
						user, password, max);
				pools.put(poolName, pool);
				System.out.println("创建数据库连接池: "
						+ poolName + "成功");
			}
		}
	}

	/**
	 * 根据数据库连接池配置信息创建连接池
	 * @param jdbcInfos
	 */
	private void createPools(Hashtable jdbcInfos) {

		Iterator it = jdbcInfos.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry en = (Map.Entry) it.next();
			JDBCInfo info = (JDBCInfo) en.getValue();
			if (info.getUrl() == null) {
				continue;
			}
			System.out.println("准备创建数据库连接池: " + (String) en.getKey());
			DBConnectionPool pool = new DBConnectionPool((String) en.getKey(),
					info.getUrl(), info.getUser(), info.getPassword(), info
							.getMaxconn());
			pools.put(en.getKey(), pool);
		}
	}

	/**
	 * 获得一个可用的(空闲的)连接.如果没有可用连接,且已有连接数小于最大连接数
	 * 限制,则创建并返回新连接
	 * @param name 在属性文件中定义的连接池名字
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			System.out.println("从数据库连接池：" + pool.getName() + "获取一个连接！");
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制,
	 * 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.
	 *
	 * @param name 连接池名字
	 * @param time 以毫秒计的等待时间
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name, long time) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * 将连接对象返回给由名字指定的连接池
	 * @param name 在属性文件中定义的连接池名字，即数据源信息中的name字段
	 * @param con 连接对象
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			System.out.println("释放了一个数据库连接到连接池：" + pool.getName());
			pool.freeConnection(con);
		}
	}

	/**
	 * 关闭所有连接,撤销驱动程序的注册 ，
	 * 只有当连接到该数据库连接管理器的客户端数目为0时，才能够完成撤销。
	 */
	public synchronized void release() {
		// 等待直到最后一个客户程序调用
		if (--clients != 0) {
			return;
		}
		//释放连接池
		Enumeration allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			System.out.println("准备关闭数据库连接池: " + pool.getName());
			pool.release();
			System.out.println("数据库连接池: " + pool.getName() + "已经被关闭！");
		}
		//反注册已经注册的数据库连接驱动类
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				System.out.println("数据库连接驱动：" + driver.getClass().getName() + "已经被注销了！");
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 此内部类定义了一个连接池.它能够根据要求创建新连接,直到预定的最
	 * 大连接数为止.在返回连接给客户程序之前,它能够验证连接的有效性.
	 */
	class DBConnectionPool {
		private int checkedOut = 0;//当前已经被取走的数据库连接数，也就是正在被使用的连接数
		private Vector freeConnections = new Vector();//该连接池中可用的数据库连接
		private int maxConn;//该连接池允许的最大数据库连接数
		private String name;//数据库连接池的名字
		private String user;//连接数据库的用户名
		private String password;//连接数据库的密码
		private String URL;//数据库的URL

		//默认构造函数
		public DBConnectionPool() {
			this.maxConn = 0;
			this.password = "";
			this.URL = "";
		}
		/**创建新的连接池
		 * @param name 连接池名字
		 * @param URL 数据库的JDBC URL
		 * @param user 数据库帐号,或 null
		 * @param password 密码,或 null
		 * @param maxConn 此连接池允许建立的最大连接数
		 */
		public DBConnectionPool(String name, String URL, String user,
				String password, int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
			this.initConnection();
		}
		/**
		 * 创建新的连接
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL.trim());
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				System.out.println("连接池" + this.name + "创建一个新的数据库连接, 目前共有"
						+ this.checkedOut + "个连接在使用！");
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return null;
			}
			return con;
		}
		/**
		 * 预先打开一个连接
		 */
		private void initConnection() {
			Connection con = getConnection();
			freeConnections.addElement(con);
		}
		/**
		 * 从连接池获得一个可用连接.如没有空闲的连接且当前使用的连接数小于最大连接
		 * 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之,
		 * 然后递归调用自己以尝试新的可用连接.
		 */
		public synchronized Connection getConnection() {
			Connection con = null;
			if (freeConnections.size() > 0) {
				// 获取向量中第一个可用连接
				con = (Connection) freeConnections.firstElement();
				freeConnections.removeElementAt(0);
				try {
					//如果存放的这个连接已经过期或者不可用，则继续获取
					if ((con == null) || (con.isClosed())) {
						con = getConnection();
					}
				} catch (SQLException e) {
					con = getConnection();
				}
			} else if (maxConn == 0 || checkedOut < maxConn) {
				//创建新的连接
				System.out.println("数据库连接池: " + this.name + "准备创建一个新的连接");
				con = newConnection();
			} else {
				System.out.println("数据库连接池" + this.name + "没有可用的连接！");
			}
			if (con != null){
				this.checkedOut++;
			}
			return con;
		}

		/**
		 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间
		 * 参见前一个getConnection()方法.
		 *
		 * @param timeout 以毫秒计的等待时间限制
		 */
		public synchronized Connection getConnection(long timeout) {
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null) {
				try {
					//等待一段时间，期待其他客户端释放连接。
					wait(timeout);
				} catch (InterruptedException e) {
				}
				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()返回的原因是超时，表示没有得到可用的连接，返回null
					return null;
				}
			}
			return con;
		}
		/**
		 * 将不再使用的连接返回给连接池
		 * @param con 客户程序释放的连接
		 */
		public synchronized void freeConnection(Connection con) {
			// 将指定连接加入到向量末尾
			freeConnections.addElement(con);
			checkedOut--;
			notifyAll();
		}
		/**
		 * 关闭所有连接
		 */
		public synchronized void release() {
			Enumeration allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection con = (Connection) allConnections.nextElement();
				try {
					con.close();
					System.out.println("关闭了数据库连接池：" + this.name + "中的一个数据库连接!");
				} catch (SQLException e) {
					System.err.println("无法关闭连接池" + this.name + "中的连接"
							+ e.getMessage());
				}
			}
			freeConnections.removeAllElements();
		}
		/**
		 * 返回数据库连接池的名字
		 */
		public String getName(){
			return this.name;
		}
	}
}