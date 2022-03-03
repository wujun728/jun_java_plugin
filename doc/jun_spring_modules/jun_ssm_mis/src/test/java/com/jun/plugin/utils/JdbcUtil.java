package com.jun.plugin.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public final class JdbcUtil {

	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}

	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class);
		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private static String DBDRIVER = "";
	private static String DBURL = "";
	private static String DBUSER = "";
	private static String DBPASSWORD = "";
	private static String DBPOOLSIZE = "100";

	// private static String DBDRIVER = "org.gjt.mm.mysql.Driver";
	// private static String DBURL = "jdbc:mysql://localhost:3306/erp";
	// private static String DBUSER = "root";
	// private static String DBPASSWORD = "mysqladmin";
	// private static String DBPOOLSIZE = "100";

	private static DataSource dataSource = null;
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static Statement stmt = null;
	private static PreparedStatement prepareStmt = null;

	private String propertyName = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = null;

	// private static ThreadLocal<Connection> tl = new
	// ThreadLocal<Connection>();
	private static List<Connection> pool = new ArrayList<Connection>();
	private static Logger logger = Logger.getLogger(JdbcUtil.class);// 这种情况下默认使用logger打印，如是只打印到自定义的logger就获取自定义的logger的名称

	static {
		Properties props = new Properties();
		InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			props.load(is);
			DBDRIVER = props.getProperty("jdbc.driver");
			DBURL = props.getProperty("jdbc.url");
			DBUSER = props.getProperty("jdbc.username");
			DBPASSWORD = props.getProperty("jdbc.password");
			DBPOOLSIZE = props.getProperty("jdbc.poolSize");
			Class.forName(DBDRIVER);

			// dataSource = new ComboPooledDataSource();
			// dataSource = new ComboPooledDataSource("mysql");
			// dataSource = new DruidDataSource ();
			dataSource = DruidDataSourceFactory.createDataSource(props);

			if (dataSource != null) {
				conn = dataSource.getConnection();
			}
			// dataSource = new ComboPooledDataSource("jun");
			// Context ctx = new InitialContext();
			// dataSource = (DataSource)
			// ctx.lookup("java:/comp/env/jdbc/mysql");
			// DBURL, DBUSER, DBPASSWORD
			// System.err.println("DBURL=" + DBURL + ",DBUSER=" + DBUSER +
			// ",DBPASSWORD=" + DBPASSWORD + ",dataSource=" +
			// dataSource.getConnection());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 第二步：静态代码块中创建多个连接
	/*
	 * static { try { for (int i = 0; i < 3; i++) { final Connection con =
	 * DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); Object proxyedCon
	 * = Proxy.newProxyInstance(JdbcUtil.class.getClassLoader(), new Class[] {
	 * Connection.class }, new InvocationHandler() { public Object invoke(Object
	 * proxy, Method method, Object[] args) throws Throwable { if
	 * (method.getName().equals("close")) {
	 * System.err.println("有人想关闭连接，不能关，还连接"); //
	 * 将proxy再加到pool中,这个proxy就是proxyedCon synchronized (pool) {
	 * pool.add((Connection) proxy); pool.notify(); } return null; } else {
	 * System.err.println("放行" + method.getName()); return method.invoke(con,
	 * args); } } }); pool.add((Connection) proxyedCon); } } catch (Exception e)
	 * { throw new RuntimeException(e.getMessage(), e); } }
	 */

	/**
	 * 取一个数据库中所有表的信息
	 * 
	 * @throws SQLException
	 */
	public static void getDatabaseMetaDataInfo() throws SQLException {
		Connection conn = JdbcUtil.getConnection();
		logger.info("######  DatabaseMetaData关于数据库的整体综合信息====");
		java.sql.DatabaseMetaData dbmd = conn.getMetaData();
		logger.info("数据库产品名: " + dbmd.getDatabaseProductName());
		logger.info("数据库是否支持事务: " + dbmd.supportsTransactions());
		logger.info("数据库产品的版本号:" + dbmd.getDatabaseProductVersion());
		logger.info("数据库的默认事务隔离级别:" + dbmd.getDefaultTransactionIsolation());
		logger.info("支持批量更新:" + dbmd.supportsBatchUpdates());
		logger.info("DBMS 的 URL:" + dbmd.getURL());
		logger.info("数据库的已知的用户名称:" + dbmd.getUserName());
		logger.info("数据库是否处于只读模式:" + dbmd.isReadOnly());
		logger.info("数据库是否支持为列提供别名:" + dbmd.supportsColumnAliasing());
		logger.info("是否支持指定 LIKE 转义子句:" + dbmd.supportsLikeEscapeClause());
		logger.info("是否为外连接提供受限制的支持:" + dbmd.supportsLimitedOuterJoins());
		logger.info("是否允许一次打开多个事务:" + dbmd.supportsMultipleTransactions());
		logger.info("是否支持 EXISTS 表达式中的子查询:" + dbmd.supportsSubqueriesInExists());
		logger.info("是否支持 IN 表达式中的子查询:" + dbmd.supportsSubqueriesInIns());
		logger.info("是否支持给定事务隔离级别:" + dbmd.supportsTransactionIsolationLevel(1));
		logger.info("此数据库是否支持事务:" + dbmd.supportsTransactions());
		logger.info("此数据库是否支持 SQL UNION:" + dbmd.supportsUnion());
		logger.info("此数据库是否支持 SQL UNION ALL:" + dbmd.supportsUnionAll());
		logger.info("此数据库是否为每个表使用一个文件:" + dbmd.usesLocalFilePerTable());
		logger.info("此数据库是否将表存储在本地文件中:" + dbmd.usesLocalFiles());
		logger.info("底层数据库的主版本号:" + dbmd.getDatabaseMajorVersion());
		logger.info("底层数据库的次版本号:" + dbmd.getDatabaseMinorVersion());
		logger.info("JDBC 驱动程序的主版本号:" + dbmd.getJDBCMajorVersion());
		logger.info("JDBC 驱动程序的次版本号:" + dbmd.getJDBCMinorVersion());
		logger.info("JDBC 驱动程序的名称:" + dbmd.getDriverName());
		logger.info("JDBC 驱动程序的 String 形式的版本号:" + dbmd.getDriverVersion());
		logger.info("可以在不带引号的标识符名称中使用的所有“额外”字符:" + dbmd.getExtraNameCharacters());
		logger.info("用于引用 SQL 标识符的字符串:" + dbmd.getIdentifierQuoteString());
		logger.info("允许用于类别名称的最大字符数:" + dbmd.getMaxCatalogNameLength());
		logger.info("允许用于列名称的最大字符数:" + dbmd.getMaxColumnNameLength());
		logger.info("允许在 GROUP BY 子句中使用的最大列数:" + dbmd.getMaxColumnsInGroupBy());
		logger.info("允许在 SELECT 列表中使用的最大列数:" + dbmd.getMaxColumnsInSelect());
		logger.info("允许在表中使用的最大列数:" + dbmd.getMaxColumnsInTable());
		logger.info("数据库的并发连接的可能最大数:" + dbmd.getMaxConnections());
		logger.info("允许用于游标名称的最大字符数:" + dbmd.getMaxCursorNameLength());
		logger.info("在同一时间内可处于开放状态的最大活动语句数:" + dbmd.getMaxStatements());
		// 获取所有表 new String[]{"TABLE"}
		// String[] type = {"TABLE","VIEW"} null
		logger.info("###### 获取表的信息");
		ResultSet tSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE", "VIEW" });
		while (tSet.next()) {
			logger.info(tSet.getRow() + "_表类别:" + tSet.getString("TABLE_CAT") + "_表模式:" + tSet.getString("TABLE_SCHEM")
					+ "_表名称:" + tSet.getString("TABLE_NAME") + "_表类型:" + tSet.getString("TABLE_TYPE")
			// +"\n_表的解释性注释:"+tSet.getString("REMARKS")+"_类型的类别:"+tSet.getString("TYPE_CAT")
			// +"\n_类型模式:"+tSet.getString("TYPE_SCHEM")+"_类型名称:"+tSet.getString("TYPE_NAME")
			// +"\n_有类型表的指定'identifier'列的名称:"+tSet.getString("SELF_REFERENCING_COL_NAME")
			// +"\n_指定在 SELF_REFERENCING_COL_NAME
			// 中创建值的方式:"+tSet.getString("REF_GENERATION")
			);
			// 2_表类别:MANOR_表模式:PUBLIC_表名称:SYS_RESOURCE_表类型:TABLE
			String tableName = tSet.getString(3);
			String sql = "select * from " + tableName;
			ResultSet rsSet = conn.createStatement().executeQuery(sql);
			ResultSetMetaData rsData = rsSet.getMetaData();
			for (int i = 1; i < rsData.getColumnCount(); i++) {
				logger.info("==列的信息:获取SQL语句的列名:" + rsData.getColumnName(i) + "(" + rsData.getColumnLabel(i) + ","
						+ rsData.getColumnType(i) + "," + rsData.getColumnClassName(i) + ")" + " 列宽"
						+ rsData.getPrecision(i) + " 大小写敏感" + rsData.isCaseSensitive(i) + " isReadOnly:"
						+ rsData.isReadOnly(i));
				// ==列的信息:获取SQL语句的列名:LIMITLEVER(LIMITLEVER,5,java.lang.Short)
				// 列宽5 大小写敏感true isReadOnly:false
			}
		}
		tSet.close();
		logger.info("###### 获取当前数据库所支持的SQL数据类型");

		ResultSet tableType = dbmd.getTypeInfo();
		while (tableType.next()) {
			logger.info("数据类型名:" + tableType.getString(1) + ",短整型的数:" + tableType.getString(2) + ",整型的数:"
					+ tableType.getString(3) + ",最小精度:" + tableType.getString(14) + ",最大精度:" + tableType.getString(15));
			// 数据类型名:TIMESTAMP,短整型的数:93,整型的数:23,最小精度:0,最大精度:10
			// 数据类型名:VARCHAR,短整型的数:12,整型的数:2147483647,最小精度:0,最大精度:0
		}
		logger.info("###### 表的主键列信息");
		ResultSet primaryKey = dbmd.getPrimaryKeys("MANOR", "PUBLIC", "SYS_ROLE_RES");
		while (primaryKey.next()) {
			logger.info("表名:" + primaryKey.getString("TABLE_NAME") + ",列名:" + primaryKey.getString("COLUMN_NAME")
					+ " 主键名:" + primaryKey.getString("PK_NAME"));
			// 表名:SYS_ROLE_RES,列名:SYS_RES_ID 主键名:CONSTRAINT_9
			// 表名:SYS_ROLE_RES,列名:SYS_ROLE_ID 主键名:CONSTRAINT_9
		}
		logger.info("###### 表的外键列信息");
		ResultSet foreinKey = dbmd.getImportedKeys("MANOR", "PUBLIC", "SYS_ROLE_RES");
		while (foreinKey.next()) {
			logger.info("主键名:" + foreinKey.getString("PK_NAME") + ",外键名:" + foreinKey.getString("FKCOLUMN_NAME")
					+ ",主键表名:" + foreinKey.getString("PKTABLE_NAME") + ",外键表名:" + foreinKey.getString("FKTABLE_NAME")
					+ ",外键列名:" + foreinKey.getString("PKCOLUMN_NAME") + ",外键序号:" + foreinKey.getString("KEY_SEQ"));
			// 主键名:PRIMARY_KEY_95,外键名:SYS_RES_ID,主键表名:SYS_RESOURCE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
			// 主键名:PRIMARY_KEY_A,外键名:SYS_ROLE_ID,主键表名:SYS_ROLE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
		}
		logger.info("###### 获取数据库中允许存在的表类型");
		ResultSet tableTypes = dbmd.getTableTypes();
		while (tableTypes.next()) {
			logger.info("类型名:" + tableTypes.getString(1));
			/**
			 * H2 类型名:SYSTEM TABLE 类型名:TABLE 类型名:TABLE LINK 类型名:VIEW
			 */
		}
		// 此外还可以获取索引等的信息
		conn.close();
	}

	/**
	 * PreparedStatement 信息 ResultSetMetaData 信息
	 * 
	 * @throws SQLException
	 */
	public static void getDBParameterMetaData() throws SQLException {
		Connection conn = JdbcUtil.getConnection(); // id,name
		PreparedStatement pre = conn.prepareStatement("SELECT * FROM SYS_APPTYPE where id = ?");
		pre.setInt(1, 3);
		java.sql.ParameterMetaData pmd = pre.getParameterMetaData();
		logger.info("参数的个数:" + pmd.getParameterCount());
		logger.info("获取指定参数的 SQL 类型:" + pmd.getParameterType(1));
		logger.info("culomn的参数类型:" + pmd.getParameterTypeName(1));
		logger.info("Java 类的完全限定名称:" + pmd.getParameterClassName(1));
		logger.info("获取指定参数的模式:" + pmd.getParameterMode(1));
		logger.info("获取指定参数的指定列大小:" + pmd.getPrecision(1));
		logger.info("获取指定参数的小数点右边的位数:" + pmd.getScale(1));
		logger.info("是否允许在指定参数中使用 null 值:" + pmd.isNullable(1));
		logger.info("指定参数的值是否可以是带符号的数字:" + pmd.isSigned(1));
		// 获取结果集元数据
		ResultSet rs = pre.executeQuery();
		while (rs.next()) {
			logger.info(rs.getString(1) + "___" + rs.getString(2));
		}
		rs.close();
	}

	/**
	 * 获取所有Driver信息
	 */
	public static void getAllDriverMsg() {
		Enumeration drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver d = (Driver) drivers.nextElement();
			logger.info(d.getClass().getName() + "_" + d.getMajorVersion());
		}
	}

	/**
	 * 提供一个静态工厂方法返回一个连接
	 */
	public static Connection getCon() {
		return getConn();
	}

	/**
	 * @param filterName
	 *            比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 例如：userName:LIKE_S
	 * @param value
	 *            待比较的值.
	 * @return
	 */
	@SuppressWarnings("unused")
	public void JdbcUtil(String filterName, String value) {
		String matchTypeStr = "";// StringUtils.substringAfter(filterName, ":");
		String matchTypeCode = "";// StringUtils.substringBefore(matchTypeStr,
									// "_");
		String propertyTypeCode = "";// StringUtils.substringAfter(matchTypeStr,
										// "_");
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
		}
		propertyName = "";// StringUtils.substringBefore(filterName,
							// ":").trim();
		if (null == value) {
			this.propertyValue = "";
		} else {
			this.propertyValue = value.trim();
		}
	}

	public static Connection getConn() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	// 事务回滚
	public static void rollback() throws SQLException {
		conn.rollback();
	}

	// 事务提交
	public static void commit() throws SQLException {
		try {
			if (conn == null)
				return;
			if (conn.isClosed())
				return;
			if (!(conn.isClosed() || conn.getAutoCommit()))
				conn.commit(); // 事务提交
			conn.close();// 释放connection，是将其放回到连接池.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	@SuppressWarnings("rawtypes")
	public static List execute(String Sql) throws Exception {
		PreparedStatement psmt = null;
		if (Sql.toUpperCase().startsWith("SELECT")) {
			psmt = conn.prepareStatement(Sql);
			ResultSet res = psmt.executeQuery();
			if (res.next()) {
				String.valueOf(res.getObject(1));
			}
		} else if (Sql.toUpperCase().startsWith("INSERT")) {
			psmt = conn.prepareStatement(Sql, Statement.RETURN_GENERATED_KEYS);
			psmt.executeUpdate();
			ResultSet res = psmt.getGeneratedKeys();
			if (res.next()) {
				String.valueOf(res.getObject(1));
			}
		} else if (Sql.toUpperCase().startsWith("UPDATE")) {
			psmt = conn.prepareStatement(Sql, Statement.RETURN_GENERATED_KEYS);
			psmt.executeUpdate();
			ResultSet res = psmt.getGeneratedKeys();
			if (res.next()) {
				String.valueOf(res.getObject(1));
			}
		}
		return null;
	}

	public int executeUpdateDeleteInsert(String sql) {
		int i = 0;
		try {
			// Connection c = getConnection();
			Statement state = conn.createStatement();
			i = state.executeUpdate(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return i;
	}

	// public static Connection getConnection(Ds ds, Boolean autoCommit) throws
	// Exception {
	// if (ds == null) {
	// throw new Exception("数据源不能为空");
	// }
	// Connection conn = null;
	// String url = "";
	// switch (2) {
	// case Constants.MSSQLSERVER:
	// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	// url = "jdbc:sqlserver://" + ds.getDataSource_ip() + ":" +
	// ds.getDataSource_port() + ";DatabaseName=" + ds.getDataSource_dbname();
	// break;
	// case Constants.MYSQL:
	// Class.forName("com.mysql.jdbc.Driver");
	// url = "jdbc:mysql://" + ds.getDataSource_ip() + ":" +
	// ds.getDataSource_port() + "/" + ds.getDataSource_dbname() +
	// "?useUnicode=true&characterEncoding=UTF-8";
	// break;
	// case Constants.ORACLE:
	// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	// url = "jdbc:mysql://" + ds.getDataSource_ip() + ":" +
	// ds.getDataSource_port() + "/" + ds.getDataSource_dbname() +
	// "?useUnicode=true&characterEncoding=UTF-8";
	// }
	// try {
	// conn = DriverManager.getConnection(url, ds.getDataSource_username(),
	// ds.getDataSource_password());
	// conn.setAutoCommit(autoCommit);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new ETLConnectionException(e.getMessage());
	// }
	// return conn;
	// }

	/**
	 * 通过配置文件创建连接
	 */
	public void testpool11() throws Exception {
		BasicDataSource ds = new BasicDataSource();
		// 设置driver
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// 设置url
		ds.setUrl("jdbc:mysql:///test?characterEncoding=UTf8");
		ds.setUsername("root");
		ds.setPassword("mysqladmin");
		ds.setMaxTotal(500);// 设置最多有几个连接
		ds.setInitialSize(2);// 设置在开始时创建几个连接
		// ..................
		ds.setDefaultAutoCommit(true);// 设置所有连接是否自动提交
		ds.setMaxIdle(3000);// 设置每个连接最大的空闲时间
		Connection c1 = ds.getConnection();
		Connection c2 = ds.getConnection();
		Connection c3 = ds.getConnection();
		Connection c4 = ds.getConnection();
		Connection c5 = ds.getConnection();
		System.err.println("c1:" + c1.hashCode() + "," + c1.getClass());// com.mysql.jdbc.Jdbc4Connection@11111,
		System.err.println("c2:" + c2.hashCode());// cn.itcast.MyDataSource$MyConn@11111
		System.err.println("c3:" + c3.hashCode());
		System.err.println("c4:" + c4.hashCode());
		System.err.println("c5:" + c5.hashCode());
		c2.close();
		Connection c6 = ds.getConnection();
		System.err.println("c6:" + c6.hashCode());
	}

	/**
	 * 通过配置文件创建连接
	 */
	public static String repeatSql(String Table_name, Map<String, Object> repeatParams) {
		StringBuffer sql = new StringBuffer("select 1");
		sql.append(" from ").append(Table_name).append(" where 1 = 1");
		for (String key : repeatParams.keySet()) {
			if (repeatParams.get(key) instanceof CharSequence || repeatParams.get(key) instanceof Timestamp) {
				sql.append(" and ").append(key).append(" = '").append(repeatParams.get(key)).append("'");
			} else {
				sql.append(" and ").append(key).append(" = ").append(repeatParams.get(key));
			}
		}
		return sql.toString();
	}

	@SuppressWarnings("unused")
	private static String getUpdateSql(String Table_name, Map<String, Object> params,
			Map<String, Object> repeatParams) {
		StringBuffer mapping = new StringBuffer();
		StringBuffer conditions = new StringBuffer();
		for (Entry<String, Object> entry : params.entrySet()) {
			mapping.append(entry.getKey()).append(" = ");
			if (entry.getValue() instanceof CharSequence || entry.getValue() instanceof Timestamp) {
				mapping.append("'" + entry.getValue() + "' ,");
			} else {
				mapping.append(entry.getValue() + " ,");
			}
		}
		for (Entry<String, Object> entry : repeatParams.entrySet()) {
			conditions.append(" and ").append(entry.getKey()).append(" = ");
			if (entry.getValue() instanceof CharSequence || entry.getValue() instanceof Timestamp) {
				conditions.append("'" + entry.getValue() + "'");
			} else {
				conditions.append(entry.getValue());
			}
		}
		if (mapping.length() > 0) {
			mapping.deleteCharAt(mapping.length() - 1);
		}
		StringBuffer sql = new StringBuffer("update ");
		sql.append(Table_name);
		sql.append(" set ");
		sql.append(mapping);
		sql.append(" where 1 = 1");
		sql.append(conditions);
		return sql.toString();
	}

	@SuppressWarnings("unused")
	public static String getInsertSql(String Table_name, Map<String, Object> params) {
		if (params == null || params.size() <= 0) {
			return null;
		}
		StringBuffer keys = new StringBuffer();
		StringBuffer values = new StringBuffer();
		for (Entry<String, Object> entry : params.entrySet()) {
			keys.append(entry.getKey() + ",");
			if (entry.getValue() instanceof CharSequence || entry.getValue() instanceof Timestamp) {
				values.append("'" + entry.getValue() + "',");
			} else {
				values.append(entry.getValue() + ",");
			}
		}
		if (keys.length() > 0) {
			keys.deleteCharAt(keys.length() - 1);
		}
		if (values.length() > 0) {
			values.deleteCharAt(values.length() - 1);
		}
		StringBuffer sql = new StringBuffer("insert into ");
		sql.append(Table_name);
		sql.append(" (" + keys);
		sql.append(") values (");
		sql.append(values + ")");
		return sql.toString();
	}

	@SuppressWarnings("unused")
	public static String getQuerySql(String Table_name, Map<String, Object> params) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append("").append(" from ").append(Table_name).append(" where 1 = 1");
		for (String key : params.keySet()) {
			if (params.get(key) instanceof CharSequence || params.get(key) instanceof Timestamp) {
				sql.append(" and ").append(key).append(" = '").append(params.get(key)).append("'");
			} else {
				sql.append(" and ").append(key).append(" = ").append(params.get(key));
			}
		}
		return sql.toString();
	}

	public Connection getJdbcConnection() throws SQLException, IOException, Exception {
		Properties props = new Properties();
		// String fileName = "e:\Database.Property";
		// FileInputStream in = new FileInputStream(fileName);
		InputStream in = getClass().getResourceAsStream("jdbc.properties");
		props.load(in);
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null) {
			System.setProperty("jdbc.drives", drivers);
		}
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		Class.forName(drivers);
		return DriverManager.getConnection(url, username, password);
	}

	public static void release(ResultSet rs, Statement stmt, Connection conn) throws SQLException {
		try {
			if (rs != null)
				rs.close();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public static Connection getMySqlConn(String url, String user, String password) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static Connection getOracleConn(String url, String user, String password) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	private static Connection getSqlServerConn(String url, String user, String password) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	// ResultSet rs = null;
	// Statement stmt = null;
	// private String sqlwords = null;
	/**
	 * 获取字段查询语句
	 * 
	 * @param pf
	 * @return
	 */
	public static String toSqlStringByField(JdbcUtil pf) {
		// 如果过滤属性为空，直接返回空
		String filterStr = "";
		if ("".equals(pf.getPropertyValue()) || null == pf.getPropertyValue()) {
			return filterStr;
		}
		// 获取比较符
		String matchStr = "";
		if (JdbcUtil.MatchType.EQ.equals(pf.getMatchType())) {
			matchStr = "=";
		} else if (JdbcUtil.MatchType.LIKE.equals(pf.getMatchType())) {
			// 暂时只实现全匹配
			matchStr = "like";
			pf.setPropertyValue("%" + pf.getPropertyValue() + "%");
		} else if (JdbcUtil.MatchType.LE.equals(pf.getMatchType())) {
			matchStr = "<=";
		} else if (JdbcUtil.MatchType.LT.equals(pf.getMatchType())) {
			matchStr = "<";
		} else if (JdbcUtil.MatchType.GE.equals(pf.getMatchType())) {
			matchStr = ">=";
		} else if (JdbcUtil.MatchType.GT.equals(pf.getMatchType())) {
			matchStr = ">";
		}
		// 获取sql查询属性
		if (JdbcUtil.PropertyType.S.getValue() == pf.getPropertyType()
				|| JdbcUtil.PropertyType.D.getValue() == pf.getPropertyType()) {
			pf.setPropertyValue("'" + pf.getPropertyValue() + "'");
		}
		filterStr = " " + pf.getPropertyName() + " " + matchStr + " " + pf.getPropertyValue();
		return filterStr;
	}

	/**
	 * 获取多条件查询语句(暂时条件都是 and)
	 * 
	 * @param pfList
	 * @param flag
	 *            是否需要where
	 * @return
	 */
	public static String toSqlString(List<JdbcUtil> pfList, boolean flag) {
		StringBuffer fragment = new StringBuffer();
		// 属性组合
		for (JdbcUtil pf : pfList) {
			if (flag) {
				if (null != pf.getPropertyValue() && !"".equals(pf.getPropertyValue())) {
					fragment.append("  where  ");
					fragment.append(toSqlStringByField(pf));
					flag = false;
				}
			} else {
				if (null != pf.getPropertyValue() && !"".equals(pf.getPropertyValue())) {
					fragment.append("  and  ");
					fragment.append(toSqlStringByField(pf));
				}
			}
		}
		return fragment.toString();
	}

	/**
	 * 获取比较值.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * 设置propertyValue
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较方式类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * 获取属性名称.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List query(String strSql) throws SQLException {
		rs = stmt.executeQuery(strSql);
		List results = new LinkedList();
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)))
			;
		return results;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List queryByRow(ResultSet rs, int rowsExpected) throws SQLException {
		List results = ((List) (rowsExpected <= 0 ? ((List) (new LinkedList()))
				: ((List) (new ArrayList(rowsExpected)))));
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)))
			;
		return results;
	}

	public static List extractData(ResultSet rs) throws SQLException {
		List results = new LinkedList();
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)))
			;
		return results;
	}

	public static List extractData(ResultSet rs, int rowsExpected) throws SQLException {
		List results = ((List) (rowsExpected <= 0 ? ((List) (new LinkedList()))
				: ((List) (new ArrayList(rowsExpected)))));
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)))
			;
		return results;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = new HashMap(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			String key = rsmd.getColumnName(i);
			Object obj = getResultSetValue(rs, i);
			mapOfColValues.put(key, obj);
		}
		return mapOfColValues;
	}

	public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		if (obj instanceof Blob)
			obj = rs.getBytes(index);
		else if (obj instanceof Clob)
			obj = rs.getString(index);
		else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP"))
			obj = rs.getTimestamp(index);
		else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName))
				obj = rs.getTimestamp(index);
			else
				obj = rs.getDate(index);
		} else if (obj != null && (obj instanceof Date)
				&& "java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index)))
			obj = rs.getTimestamp(index);
		return obj;
	}

	// ResultSet rs = null;
	// 执行查询语句的方法
	public static ResultSet executeQuery(String sql) {
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); // 建立与数据库服务器的连接
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);// 执行指定的数据查询语句
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	// 执行增、删改语句的方法
	public int executeUpdate(String sql) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);// 执行指定的数据操作语句
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return result;
	}

	// ************************************************************************************
	// ************************************************************************************
	// ************************************************************************************
	public static Connection getConnectionByUrl() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName(DBDRIVER);
				conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// ************************************************************************************
	// ************************************************************************************
	// ************************************************************************************

	public static void startTransaction() {
		// TODO Auto-generated method stub

	}

	public static void close(ResultSet rs2) {
		try {
			if (rs2 != null) {
				rs2.close();
			}
		} catch (Exception e) {
			System.out.print("Clost Database Connect Failed!:" + e);
		}

	}

	public static void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			System.out.print("Clost Database Connect Failed!:" + e);
		}

	}

	public static void close(Connection conn2) {
		try {
			if (conn2 != null) {
				conn2.close();
			}
		} catch (Exception e) {
			System.out.print("Clost Database Connect Failed!:" + e);
		}

	}

	/**
	 * @param args
	 * @throws SQLException
	 */
	// @Test
	public void TestThreadConn() throws Exception {
		for (int i = 0; i < 5; i++) {
			final int a = i;
			new Thread() {
				public void run() {
					Connection con = JdbcUtil.getConnection();
					System.err.println(a + ":" + con);
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
	}

	// @Test
	public void Test() throws SQLException {
		// 1. 注册驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Mysql 的驱动
			// 2. 获取数据库的连接
			// java.sql.Connection conn =
			// java.sql.DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=GBK",
			// "root", null);
		java.sql.Connection conn = java.sql.DriverManager.getConnection(
				"jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=GBK", "root", "mysqladmin");
		// 3. 获取表达式
		java.sql.Statement stmt = conn.createStatement();
		// 执行插入数据的 SQL
		stmt.executeUpdate("insert into User(username, password) values('JDBC 中文测试', '密码')");
		// 4. 执行 SQL
		java.sql.ResultSet rs = stmt.executeQuery("select * from User");
		// 5. 显示结果集里面的数据
		while (rs.next()) {
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString("username"));
			System.out.println(rs.getString("password"));
			System.out.println();
		}
		// 6. 释放资源
		rs.close();
		stmt.close();
		conn.close();
	}

	// @Test
	public void Test2() throws SQLException {
		long begin = System.currentTimeMillis();
		for (int i = 1; i <= 5000; i++) {
			Connection conn = JdbcUtil.getConnection();
			if (conn != null) {
				System.out.println(i + ":");
			}
			JdbcUtil.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("" + (end - begin) / 1000 + "");
	}

	@SuppressWarnings("static-access")
	// @Test
	public void Test3() throws Exception {
		long begin = System.currentTimeMillis();
		InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("dbcp.properties");
		Properties props = new Properties();
		props.load(is);
		BasicDataSourceFactory factory = new BasicDataSourceFactory();
		DataSource ds = factory.createDataSource(props);
		for (int i = 1; i <= 50000; i++) {
			Connection conn = ds.getConnection();
			if (conn != null) {
				System.out.println(i + ":ȡ������");
			}
			conn.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("����" + (end - begin) / 1000 + "��");
	}

	// @Test
	public void Test4() throws SQLException {
		long begin = System.currentTimeMillis();
		// ����C3P0���ӳ�
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		for (int i = 1; i <= 100000; i++) {
			Connection conn = dataSource.getConnection();
			if (conn != null) {
				System.out.println(i + ":ȡ������");
				conn.close();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("����" + (end - begin) / 1000 + "��");
	}

	// @Test
	public void JDBCTest() throws Exception {
		// DriverManager.registerDriver(new Driver());
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "mysqladmin");
		String sql = "select * from user";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("id");
			// String name = rs.getString("name");
			// String gender = rs.getString("gender");
			// float salary = rs.getFloat("salary");
			System.out.println(id);
		}
		rs.close();
		stmt.close();
		conn.close();
	}

	public void create(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			System.out.println(i > 0 ? "sucess" : "faild");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close();
		}
	}

	// @Test
	public void read() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from test where username='abc'";
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				System.out.println(name + ":" + gender);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close();
		}
	}

	public void update(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			System.out.println(i > 0 ? "sucess" : "faild");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close();
		}
	}

	public void delete(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			System.out.println(i > 0 ? "sucess" : "faild");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close();
		}
	}

	@Test
	public void proc1() throws Exception {
		// JdbcUtil不提供调用存储过程的能力
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc1()}");
		// 执行
		boolean boo = cs.execute();// 如果返回true,指最后一句执行的是select语句
		if (boo) {
			ResultSet rs = cs.getResultSet();
			while (rs.next()) {
				System.err.println(rs.getString("name"));
			}
		}
		con.close();
	}

	@Test
	public void proc2() throws Exception {
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc2(?,?)}");
		cs.setString(1, "UAAA");
		cs.setString(2, "王健");
		boolean boo = cs.execute();
		System.err.println(boo);
		con.close();
	}

	@Test
	public void proc3() throws Exception {
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc5(?,?,?)}");
		cs.setString(1, "UBDDB");
		cs.setString(2, "张三");
		cs.registerOutParameter(3, Types.INTEGER);// --int,
		boolean boo = cs.execute();
		System.err.println(">>:" + boo);// true
		// 从call中获取返回的值
		int size = cs.getInt(3);
		System.err.println("行数:" + size);
		if (boo) {
			ResultSet rs = cs.getResultSet();
			rs.next();
			int ss = rs.getInt(1);
			System.err.println("sss:" + ss);
		}
		con.close();
	}

	@Test
	public void proc6() throws Exception {
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc6(?,?,?,?)}");
		cs.setString(1, "UBafadsB");
		cs.setString(2, "张三");
		cs.registerOutParameter(3, Types.INTEGER);// --int,
		cs.registerOutParameter(4, Types.INTEGER);
		boolean boo = cs.execute();
		System.err.println(">>:" + boo);// faluse
		// 从call中获取返回的值
		int size = cs.getInt(3);
		int _s = cs.getInt(4);
		System.err.println("行数:" + size + "," + _s);
		con.close();
	}

	/**
	 * @param args
	 */
	public static void main111(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.ע����
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2.ͨ����������������
			// ��ʽ:jdbc:oracle:thin:@<IP��ַ>:<�˿ں�,Ĭ����1521>:<sid>
			String url = "jdbc:oracle:thin:@192.168.0.26:1521:tarena";
			String dbUsername = "openlab";
			String dbPassword = "open123";
			conn = DriverManager.getConnection(url, dbUsername, dbPassword);
			// 3.���������
			stmt = conn.createStatement();
			// 4.����������,�����ؽ��.
			String sql = "select empno, ename, job, sal, " + "to_char(hiredate, 'yyyy/mm/dd') hiredate " + "from mystu";
			// System.out.println(sql);
			stmt.setMaxRows(5);
			rs = stmt.executeQuery(sql);// 130000
			// rs.setFetchSize(5);

			while (rs.next()) {
				// dname��loc��sql����е�����
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				double sal = rs.getDouble("sal");
				String hiredate = rs.getString("hiredate");
				System.out.println(empno + ", " + ename + ", " + job + ", " + sal + ", " + hiredate);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5.�ر���Դ
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

	/**
	 * @param args
	 */
	public static void main1123(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.ע����
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2.ͨ����������������
			// ��ʽ:jdbc:oracle:thin:@<IP��ַ>:<�˿ں�,Ĭ����1521>:<sid>
			String url = "jdbc:oracle:thin:@192.168.0.26:1521:tarena";
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
			String sql = "select e.ename, d.dname" + " from vemp e join vdept d" + " on e.deptno = d.deptno";
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsm = rs.getMetaData();
			int count = rsm.getColumnCount();
			for (int i = 1; i <= count; i++) {
				System.out.print(rsm.getColumnName(i) + "\t");
			}
			System.out.println();
			while (rs.next()) {
				for (int i = 1; i <= count; i++) {
					System.out.print(rs.getString(rsm.getColumnName(i)) + "\t");
				}
				System.out.println();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5.�ر���Դ
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

	public void init() {
		try {
			// 1.ע����
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2.ͨ����������������
			// ��ʽ:jdbc:oracle:thin:@<IP��ַ>:<�˿ں�,Ĭ����1521>:<sid>
			String url = "jdbc:oracle:thin:@192.168.0.26:1521:tarena";
			String dbUsername = "openlab";
			String dbPassword = "open123";
			conn = DriverManager.getConnection(url, dbUsername, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			if (rs != null)
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

	public void update() {
		init();
		try {
			String sql = "update mydepttemp " + "set loc = ? " + "where deptno = ?";
			prepareStmt = conn.prepareStatement(sql);
			prepareStmt.setString(1, "���");
			prepareStmt.setInt(2, 10);

			int n = prepareStmt.executeUpdate();
			System.out.println(n + "����¼���޸�");
			System.out.println((n > 0) ? "OK" : "error");

		} catch (SQLException e) {
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

	/**
	 * 释放资源的方法
	 */
	public static void close(Connection conn, Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	// ���Ӳ���
	// public String url = "jdbc:mysql://localhost:3306/jdbc_demo";

	/**
	 * �������Ӷ���
	 */

	/**
	 * 2. ����JdbcUtil���Ĺ��������
	 */
	/*public static QueryRunner getQueryRuner1() {
		// ����QueryRunner���󣬴������ӳض���
		// �ڴ���QueryRunner�����ʱ������������Դ����
		// ��ô��ʹ��QueryRunner���󷽷���ʱ�򣬾Ͳ���Ҫ�������Ӷ���
		// ���Զ������Դ�л�ȡ����(���ùر�����)
		return new QueryRunner(dataSource);
	}*/

	/**
	 * 2. ����JdbcUtil���Ĺ��������
	 */
	/*public static QueryRunner getQueryRuner() {
		// ����QueryRunner���󣬴������ӳض���
		// �ڴ���QueryRunner�����ʱ������������Դ����
		// ��ô��ʹ��QueryRunner���󷽷���ʱ�򣬾Ͳ���Ҫ�������Ӷ���
		// ���Զ������Դ�л�ȡ����(���ùر�����)
		return new QueryRunner(dataSource);
	}*/

	/**
	 * 得到连接对象
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * ����JdbcUtil���ù��������
	 */
	/*public static QueryRunner getQuerrRunner() {
		return new QueryRunner(dataSource);
	}*/

	// 获得与指定数据库的连接

	// 统一的释放资源方法
	public static void release1(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	// 通用的增删改方法
	public static boolean update(String sql, Object[] params) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);// 将事物默认提交设置为flase
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; params != null && i < params.length; i++)
				pstmt.setObject(i + 1, params[i]);// 通过setObject设置参数所对应的值。sql语句的参数用？占位
			int num = pstmt.executeUpdate();// executeUpdate会返回影响结果的条数
			conn.commit();
			conn.setAutoCommit(false);
			if (num > 0)
				return true;
			return false;
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					throw new Exception(e1);
				}
			}
			throw new Exception(e);
		} finally {
			try {
				release(rs, pstmt, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author
	 * @throws SQLException
	 * @since
	 * @throws @description
	 *             对数据进行批量添加，当数据量大于100时，则整除1000时提交一次，wbs里存放的是批量数值数组的集合
	 */
	public static boolean updateBatch(String sql, List wbs) throws Exception, SQLException {
		logger.info("批量添加开始");
		Calendar calendar = Calendar.getInstance();
		long startTime = calendar.getTimeInMillis();
		calendar = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		boolean flag = false;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for (Iterator it = wbs.iterator(); it.hasNext();) {
				Object[] params = (Object[]) it.next();
				count++;
				for (int i = 0; params != null && i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
				pstmt.addBatch();// 将一批参数添加到pstmt对象的批处理命令
				if (count % 100 == 0) {
					pstmt.executeBatch();
					conn.commit();// 当数量到100时提交
					flag = true;
				}
			}
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			flag = true;
			if (flag) {
				logger.info("批量添加结束");
				Calendar calendarOld = Calendar.getInstance();
				long endTime = calendarOld.getTimeInMillis();
				// logger.info("批量添加共耗时:"+(endTime-startTime)+"ms");
				logger.info("批量添加共耗时:" + (endTime - startTime) + "ms");
				return true;
			}
			logger.info("批量添加失败");
			return false;
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					throw new Exception(e);
				}
			}
			throw new Exception(e);
		} finally {
			release(rs, pstmt, conn);
		}
	}



	/**
	 * 此处返回的是插入数据后生成的主键
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String sql, Object[] params) throws Exception, SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; params != null && i < params.length; i++)
				pstmt.setObject(i + 1, params[i]);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();// 获取刚刚插入的主键rs
			rs.next();// 获取刚刚获取的主键
			int key = rs.getInt(1);
			if (key > 0) {
				return key;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			release(rs, pstmt, conn);
		}
	}

	public static PreparedStatement getPs(Connection conn2, String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}




	/*public static DataSource dataSource = null;
	static {
		Properties props = new Properties();
		InputStream inStream = JdbcUtil.class.getClassLoader()
				.getResourceAsStream("com\\ibeifeng\\dbcpconfig.properties");
		try {
			props.load(inStream);
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			throw new ExceptionInInitializerError();
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}*/

	public static void release11(ResultSet rs, Statement stmt, Connection conn) throws SQLException {
		try {
			if (rs != null)
				rs.close();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	// @Test
	public void testpool1() throws Exception {
		BasicDataSource ds = new BasicDataSource();
		// 设置driver
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// 设置url
		ds.setUrl("jdbc:mysql:///db909?characterEncoding=UTf8");
		ds.setPassword("1234");
		ds.setUsername("root");
		ds.setMaxTotal(100);// 设置最多有几个连接
		ds.setInitialSize(2);// 设置在开始时创建几个连接
		// ..................
		ds.setDefaultAutoCommit(true);// 设置所有连接是否自动提交
		ds.setMaxIdle(3000);// 设置每个连接最大的空闲时间

		Connection c1 = ds.getConnection();
		Connection c2 = ds.getConnection();
		Connection c3 = ds.getConnection();
		Connection c4 = ds.getConnection();
		Connection c5 = ds.getConnection();
		System.err.println("c1:" + c1.hashCode() + "," + c1.getClass());// com.mysql.jdbc.Jdbc4Connection@11111,
		System.err.println("c2:" + c2.hashCode());// cn.itcast.MyDataSource$MyConn@11111
		System.err.println("c3:" + c3.hashCode());
		System.err.println("c4:" + c4.hashCode());
		System.err.println("c5:" + c5.hashCode());
		c2.close();
		Connection c6 = ds.getConnection();
		System.err.println("c6:" + c6.hashCode());
	}

	/**
	 * 通过配置文件创建连接
	 */
	@Test
	public void testPool2() throws Exception {
		Properties p = new Properties();
		p.load(JdbcUtil.class.getResourceAsStream("jdbc.properties"));
		DataSource ds = new BasicDataSourceFactory().createDataSource(p);
		Connection c1 = ds.getConnection();
		Connection c2 = ds.getConnection();
		Connection c3 = ds.getConnection();

		System.err.println(c1.hashCode() + "," + c1.getClass());
		System.err.println("c2:" + c2.hashCode());
		System.err.println("c3:" + c3.hashCode());
		c3.close();
		Connection c4 = ds.getConnection();
		System.err.println("c4:" + c4.hashCode());
	}

	/**
	 * 用Statement批量保存5000条记录 mysql statement batch time=3047 oracle statement
	 * batch time=2453
	 */
	@Test
	public void testStatementBatch() {
		Connection conn = null;
		Statement stmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < 5000; i++) {
				String sql = "INSERT INTO b_user(id,NAME,PASSWORD,age) VALUES(1,'t','t',1)";
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			System.out.println("mysql statement batch time=" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, null);
		}
	}

	/**
	 * 用PreparedStatement批量保存5000条记录 mysql preparedstatement batch time=3094
	 * 5000 oracle preparedstatement batch time=265 oracle preparedstatement
	 * batch time=422 50000 oracle preparedstatement batch time=2187
	 */
	@Test
	public void testPreparedStatementBatch() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO b_user(id,NAME,PASSWORD,age) VALUES(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < 500000; i++) {
				pstmt.setInt(1, 1);
				pstmt.setString(2, "t");
				pstmt.setString(3, "t");
				pstmt.setInt(4, 1);
				pstmt.addBatch();
				if (i % 50000 == 0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
			pstmt.executeBatch();
			System.out.println("oracle preparedstatement batch time=" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
	}

	/**
	 * mysql Statement time2984 oracle Statement time2703
	 */
	@Test
	public void testStatement() {
		Connection conn = null;
		Statement stmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();

		try {
			stmt = conn.createStatement();
			for (int i = 0; i < 5000; i++) {
				String sql = "INSERT INTO b_user(id,NAME,PASSWORD,age) VALUES(1,'t','t',1)";
				stmt.executeUpdate(sql);
			}
			System.out.println("oracle Statement time" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, null);
		}

	}

	@Test
	public void testScroll() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();

		try {
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from rs_user";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println("name=" + rs.getString("name"));
			}

			if (rs.next()) {
				System.out.println("name=" + rs.getString("name"));
			}
			// 抛出异常
			if (rs.previous()) {
				System.out.println("name=" + rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}

	}

//	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
//	private static final String USER = "scott";
//	private static final String PASSWORD = "tiger";


	/**
	 * 得到连接对象
	 */
	public static Connection getConnection111() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭指定的资源
	 */
	public static void close5435(Connection conn, Statement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭指定的资源
	 */
	private static void close2(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 关闭指定的资源
	 * 
	 * 必须严重不行
	 */
	private static void close3(Connection conn, Statement stmt, ResultSet rs) {

		if (rs != null) {// rs=null
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (conn != null) {
								conn.close();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 循环保存5000条数据 mysql preparedStatement time=3062ms oracle preparedStatement
	 * time2328 oracle preparedStatement time2125 oracle preparedStatement
	 * time2344
	 */
	@Test
	public void testEff() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO vs_user(id,NAME,PASSWORD,age) VALUES(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < 5000; i++) {
				pstmt.setInt(1, 1);
				pstmt.setString(2, "t");
				pstmt.setString(3, "1");
				pstmt.setInt(4, 1);
				pstmt.executeUpdate();
			}
			System.out.println("oracle preparedStatement time" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
	}

	/**
	 * 循环保存5000条数据 msql Statement 2984ms oracle Statement time2500 oracle
	 * Statement time2500 oracle Statement time2438
	 * 
	 * mysql preparedStatement time=3062ms oracle preparedStatement time2328
	 * oracle preparedStatement time2125 oracle preparedStatement time2344
	 */
	@Test
	public void testEff111() {
		Connection conn = null;
		Statement stmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();

		try {
			stmt = conn.createStatement();
			for (int i = 0; i < 5000; i++) {
				String sql = "INSERT INTO vs_user(id,NAME,PASSWORD,age) VALUES(1,'t','1',1)";
				stmt.executeUpdate(sql);
			}
			System.out.println("oracle Statement time" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, null);
		}

	}

	/**
	 * 用Statement批量保存5000条记录 mysql statement batch time=3047
	 */
	@Test
	public void testStatementBatch1() {
		Connection conn = null;
		Statement stmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < 5000; i++) {
				String sql = "INSERT INTO b_user(id,NAME,PASSWORD,age) VALUES(1,'t','t',1)";
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			System.out.println("mysql statement batch time=" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, null);
		}
	}

	/**
	 * 用PreparedStatement批量保存5000条记录 mysql preparedstatement batch time=3094
	 */
	@Test
	public void testPreparedStatementBatch1() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO b_user(id,NAME,PASSWORD,age) VALUES(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < 5000; i++) {
				pstmt.setInt(1, 1);
				pstmt.setString(2, "t");
				pstmt.setString(3, "t");
				pstmt.setInt(4, 1);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("mysql preparedstatement batch time=" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
	}

	/**
	 * mysql Statement time2984
	 */
	@Test
	public void testStatement1() {
		Connection conn = null;
		Statement stmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();

		try {
			stmt = conn.createStatement();
			for (int i = 0; i < 5000; i++) {
				String sql = "INSERT INTO b_user(id,NAME,PASSWORD,age) VALUES(1,'t','t',1)";
				stmt.executeUpdate(sql);
			}
			System.out.println("mysql Statement time" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, null);
		}

	}

	/**
	 * 保存带图片的数据
	 */
	@Test
	public void saveImage() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO bt_user (NAME,headimage) VALUES(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "tom");
			InputStream inputStream = new FileInputStream(
					"D:\\work\\Workspaces\\day14_jdbc\\src\\cn\\itcast\\mysql\\bt\\mm.jpg");
			pstmt.setBinaryStream(2, inputStream, inputStream.available());
			// mysql实现了所有方法，但有些方法执行无法通过，没有真正的实现
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取数据库带图片的一条记录
	 */
	@Test
	public void getImage() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();
		try {
			stmt = conn.createStatement();
			String sql = "select * from bt_user where id=1";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Blob blob = rs.getBlob("headimage");
				InputStream is = blob.getBinaryStream();
				String path = "D:\\work\\Workspaces\\day14_jdbc\\src\\cn\\itcast\\mysql\\bt\\mm2.jpg";
				OutputStream os = new FileOutputStream(path);
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				// os.flush();
				os.close();// close中有flush
				is.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * 保存带文本的数据
	 */
	@Test
	public void saveText() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO bt_user (NAME,resume) VALUES(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "tom2");
			String path = "D:\\work\\Workspaces\\day14_jdbc\\src\\cn\\itcast\\mysql\\bt\\工作.txt";
			InputStream is = new FileInputStream(path);
			Reader reader = new InputStreamReader(is, "utf-8");
			pstmt.setCharacterStream(2, reader, is.available());// 只有字节点才能得到流数据中的字节数
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取数据库带文本的一条记录
	 */
	@Test
	public void getText() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();
		try {
			stmt = conn.createStatement();
			String sql = "select * from bt_user where id=2";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Reader reader = rs.getCharacterStream("resume");
				String path = "D:\\work\\Workspaces\\day14_jdbc\\src\\cn\\itcast\\mysql\\bt\\工作2.txt";
				BufferedReader br = new BufferedReader(reader);
				OutputStream os = new FileOutputStream(path);
				Writer writer = new OutputStreamWriter(os, "utf-8");
				String s = null;
				while ((s = br.readLine()) != null) {
					writer.write(s);
					writer.write("\n");
				}
				writer.close();
				os.close();
				br.close();
				reader.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * 编写测试方法 加入junit测试包
	 * 
	 * @Test 定义方法 public void methodName() outline中 run as Junit
	 */
	@Test
	public void testgetConnection() {
		try {
			// 创建mysql的Driver对象
			Driver driver = new com.mysql.jdbc.Driver();
			// jdbc url 定位到一个数据库
			String url = "jdbc:mysql://localhost:3306/dbjdbc"; // 定位到一个数据库
			// 用于存储用户名和密码的对象（Map）
			Properties info = new Properties();
			info.put("user", "root"); // key固定
			info.put("password", "root");
			// 通过Driver对象得到连接对象
			Connection connection = driver.connect(url, info);
			System.out.println(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DriverManager.registerDriver(driver)
	 */
	@Test
	public void testGetConnection2() {

		try {
			// 使用DriverManager注册驱动对象
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost:3306/dbjdbc";
			String user = "root";
			String password = "root";
			// 使用DriverManager得到连接对象
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 采用class.forName的方式
	 */
	@Test
	public void testGetConnection() {

		/*
		 * static { try { java.sql.DriverManager.registerDriver(new Driver()); }
		 * catch (SQLException E) { throw new RuntimeException(
		 * "Can't register driver!"); } }
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String url = "jdbc:mysql://localhost:3306/dbjdbc";
			String user = "root";
			String password = "root";
			// 使用DriverManager得到连接对象
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDatabaseMetaData() {
		Connection conn = null;

		conn = JdbcUtil.getConnection();
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			String url = metaData.getURL();
			String productName = metaData.getDatabaseProductName();
			String productVersion = metaData.getDatabaseProductVersion();
			String driverName = metaData.getDriverName();
			String driverVersion = metaData.getDriverVersion();
			System.out.println("url=" + url + " productName=" + productName + " productVersion=" + productVersion);
			System.out.println("driverName=" + driverName + " driverVersion=" + driverVersion);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testResultSetMetaData() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id,name,password from m_user");
			/*
			 * while (rs.next()) { System.out.println("id=" + rs.getInt(1) +
			 * " name=" + rs.getString("name") + " password=" +
			 * rs.getString("password")); }
			 */
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnName(i + 1);
					Object value = rs.getObject(columnName);
					System.out.print(" " + columnName + "=" + value);
				}
				System.out.println();
			}

			System.out.println("rs中的字段数" + metaData.getColumnCount());
			String columnName = metaData.getColumnName(2);
			System.out.println("第２个字段名称为" + columnName);

			String columnType = metaData.getColumnClassName(1);
			System.out.println("第3个字段ClassName为" + columnType);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}

	}

	@Test
	public void testSaveToXml() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String tableName = "student";

		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from " + tableName;
			rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			Document document = DocumentHelper.createDocument();
			Element rootElement = document.addElement(tableName + "s");

			while (rs.next()) {
				/*
				 * <user> <id>1</id> <name>Tom</Tom> <password>123<password>
				 * <user>
				 */
				Element tableElement = rootElement.addElement(tableName);
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object value = rs.getObject(i);
					Element element = tableElement.addElement(columnName);
					element.addText(value.toString());
				}
			}

			// 写到指定的目录
			String filePath = "d:/data/" + tableName + "s.xml";

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), format);
			writer.write(document);
			writer.flush();
			writer.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}

	}

	@Test
	public void testSaveUpdateDelete() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 准备数据
		String name = "tt";
		String password = "123";
		int age = 12;
		conn = JdbcUtil.getConnection();
		// sql语句中不定值用?代替
		String sql = "INSERT INTO p_user(NAME,PASSWORD,age) VALUES(?,?,?)";
		// 更新
		sql = "UPDATE p_user SET NAME=?,PASSWORD=?,age=? WHERE id=?";
		// 删除
		sql = "DELETE FROM p_user WHERE id=?";
		try {
			// 调用prepareStatement(sql)得到PreparedStatement的实现类对象
			// 预编译
			pstmt = conn.prepareStatement(sql);

			/*
			 * pstmt.setString(1, name); pstmt.setString(2, password);
			 * pstmt.setInt(3, age);
			 */

			/*
			 * pstmt.setString(1, name+"2"); pstmt.setString(2, password+"2");
			 * pstmt.setInt(3, age+2); pstmt.setInt(4, 1);
			 */

			// 在执行sql之前必须把数据设置进去
			pstmt.setInt(1, 1);

			int result = pstmt.executeUpdate();
			System.out.println("udpate result=" + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDQL1() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from p_user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("name=" + rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
	}

	@Test
	public void testScroll1() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();

		try {
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from rs_user";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println("name=" + rs.getString("name"));
			}

			if (rs.next()) {
				System.out.println("name=" + rs.getString("name"));
			}

			if (rs.previous()) {
				System.out.println("name=" + rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}

	}

	@Test
	public void testResultSetMethod() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();

		try {
			// stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
			// ResultSet.CONCUR_READ_ONLY);
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from rs_user";
			rs = stmt.executeQuery(sql);

			rs.next();
			System.out.println("next=" + rs.getString("id"));

			rs.next();
			System.out.println("next=" + rs.getString("id"));

			rs.previous();
			System.out.println("previous=" + rs.getString("id"));

			rs.first();
			System.out.println("first=" + rs.getString("id"));

			rs.last();
			System.out.println("last=" + rs.getString("id"));

			// 第几条数据
			rs.absolute(2);
			System.out.println("absolute=" + rs.getString("id"));
			// 保光标移动多少位(正代表forword 负代表回滚)
			rs.relative(2);
			System.out.println("relative=" + rs.getString("id"));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}

	}

	@Test
	public void testCreateTable() {
		Connection conn = null;
		Statement stmt = null;

		/*
		 * 1 注册驱动 在com.mysql.jdbc.Driver这个类里有静态代码块 static { try {
		 * java.sql.DriverManager.registerDriver(new Driver()); } catch
		 * (SQLException E) { throw new RuntimeException(
		 * "Can't register driver!"); } }
		 */

		try {
			// 加载类字节码，使静态代码块被执行
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * 2 得到连接对象
		 */
		try {
			/*
			 * jdbc url = "jdbc:mysql://localhost:3306/dbjdbc" 1 jdbc
			 * 代表总协议是jdbc协议 不变 2 mysql 代表当前的实现协议是mysql,不同的数据库服务器不一样 3
			 * localhost:3306/dbjdbc * localhost 主机/IP * 3306 代表数据库服务器监听的端口号 *
			 * dbjdbc: 要连接到的数据库名称
			 */
			String url = "jdbc:mysql://localhost:3306/dbjdbc";
			// 登陆数据库的用户名
			String user = "root";
			// 登陆数据库的密码
			String password = "root";
			// 通过DriverManager得到连接对象
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * 3 得到能执行sql语句的Statement对象
		 */
		try {
			// 通过Connection对象得到能执行sql语句的Statement对象
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * 4 执行sql语句
		 */
		int result = -1;
		try {
			String sql = "CREATE TABLE s_user(id INT PRIMARY KEY AUTO_INCREMENT,NAME VARCHAR(20),PASSWORD VARCHAR(15))";
			// 通过statement对象执行sql语句（DDL）
			// executeUpdate: 如果执行的DDL语句返回的结果为0
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * 5 处理返回的结果
		 */
		System.out.println("create table result=" + result);
		/*
		 * 6 关闭资源 后开先关
		 */
		try {
			if (stmt != null) {// 关闭statement对象所占用的资源
				stmt.close();
			}
			if (conn != null) {// 关闭connection对象所占用的资源
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试使用Statement执行DML操作
	 */
	@Test
	public void testDML() {
		Connection conn = null;
		Statement stmt = null;

		/*
		 * 1 注册驱动 在com.mysql.jdbc.Driver这个类里有静态代码块 static { try {
		 * java.sql.DriverManager.registerDriver(new Driver()); } catch
		 * (SQLException E) { throw new RuntimeException(
		 * "Can't register driver!"); } }
		 */

		try {
			// 加载类字节码，使静态代码块被执行
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * 2 得到连接对象
		 */
		try {
			/*
			 * jdbc url = "jdbc:mysql://localhost:3306/dbjdbc" 1 jdbc
			 * 代表总协议是jdbc协议 不变 2 mysql 代表当前的实现协议是mysql,不同的数据库服务器不一样 3
			 * localhost:3306/dbjdbc * localhost 主机/IP * 3306 代表数据库服务器监听的端口号 *
			 * dbjdbc: 要连接到的数据库名称
			 */
			String url = "jdbc:mysql://localhost:3306/dbjdbc";
			// 登陆数据库的用户名
			String user = "root";
			// 登陆数据库的密码
			String password = "root";
			// 通过DriverManager得到连接对象
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * 3 得到能执行sql语句的Statement对象
		 */
		try {
			// 通过Connection对象得到能执行sql语句的Statement对象
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * 4 执行sql语句
		 */
		int result = -1;
		try {
			// String sql = "CREATE TABLE s_user(id INT PRIMARY KEY
			// AUTO_INCREMENT,NAME VARCHAR(20),PASSWORD VARCHAR(15))";
			// 插入数据
			String sql = "INSERT INTO s_user(NAME,PASSWORD) VALUES('小强','9527');";
			// 更新数据
			sql = "UPDATE s_user SET NAME='黄宏强',password='250' WHERE NAME='大黄'";
			// 删除数据
			sql = "delete from s_user";
			// 通过statement对象执行sql语句（DML）
			// executeUpdate: 如果执行的DML语句返回的结果为更新的记录数
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * 5 处理返回的结果
		 */
		System.out.println("create table result=" + result);
		/*
		 * 6 关闭资源 后开先关
		 */
		try {
			if (stmt != null) {// 关闭statement对象所占用的资源
				stmt.close();
			}
			if (conn != null) {// 关闭connection对象所占用的资源
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDQL() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/dbjdbc";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "SELECT id,NAME,PASSWORD FROM s_user";
			// sql = "SELECT id,NAME as username,PASSWORD FROM s_user";
			// executeQuery得到包含所有匹配的数据对象 ResultSet实现类对象
			// 跟Iterator类似
			rs = stmt.executeQuery(sql);

			// 使用ResultSet
			// next(): 移到光标到下一位，在移动之前确定其返回值，看右边有没有数据，有就返回true,没有就返回false
			while (rs.next()) {
				// rs.getXXX()取光标左边的数据
				// XXX跟字段类型一定要一致
				// 通过下标取数据
				// columnIndex - 第一个列是 1，第二个列是 2，……
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String pwd = rs.getString(3);
				System.out.println("id=" + id + " name=" + name + " pwd=" + pwd);

				// 通过字段名取数据
				// 大小写是否影响？没有影响，数据库看到的都会是大写，如果小写会自动转
				// 如果使用了别名，必须用别名去取
				id = rs.getInt("Id");
				name = rs.getString("name");
				// name = rs.getString("username");
				pwd = rs.getString("password");
				System.out.println("--id=" + id + " name=" + name + " pwd=" + pwd);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testIterator() {
		Set<String> set = new HashSet<String>();
		set.add("b");
		set.add("a");
		set.add("c");
		set.add("d");

		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String s = iterator.next();
			System.out.println(s);
		}
	}

	@Test
	public void testSaveMoney() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		// 准备数据
		String accountId = "123456789";
		double money = 100;

		try {
			conn = JdbcUtil.getConnection();

			/*****************************************************/
			// 设置事务为手动提交
			System.out.println("默认事务为" + conn.getAutoCommit());// true代表自动提交,false手动提交
			conn.setAutoCommit(false);
			System.out.println("事务为" + conn.getAutoCommit());
			/*****************************************************/

			// 向inaccount表中插入一条记录
			// sql: INSERT INTO inaccount(accountid,inbalance)
			// VALUES('123456789',100)
			String sql = "INSERT INTO inaccount(accountid,inbalance) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, accountId);
			pstmt.setDouble(2, money);
			pstmt.executeUpdate();// 数据马上同步到数据库

			// 更新account表，将指定账号的余额加上100
			// UPDATE account SET balance=balance+100 WHERE
			// accountid='123456789'
			sql = "UPDATE account SET balance=balance+? WHERE accountid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, money);
			pstmt.setString(2, accountId);

			// 模拟因网络或不明原因出异常
			boolean flag = true;
			if (flag) {
				throw new SQLException("因网络或不明原因出异常!");
			}
			pstmt.executeUpdate();

			/*****************************************************/
			// 提交事务
			conn.commit();
			/*****************************************************/

		} catch (SQLException e) {

			/*****************************************************/
			// 回滚事务
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/*****************************************************/

			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
	}

	/**
	 * PreparedStatement能解决sql注入问题
	 */
	@Test
	public void testSqlInject() {
		// 准备数据
		String name = "a' or 1=1 or 1='";
		String password = "123";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();
		String sql = "SELECT NAME,PASSWORD FROM p_user WHERE NAME=? AND PASSWORD=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("登陆成功啦！");
				System.out.println("用户名=" + rs.getString("name") + " 密码=" + rs.getString("password"));
			} else {
				System.out.println("用户名或密码不匹配！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
	}

	/**
	 * 循环保存5000条数据 mysql preparedStatement time=3062ms
	 */
	@Test
	public void testEff1() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO p_user(NAME,PASSWORD,age) VALUES(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < 5000; i++) {
				pstmt.setString(1, "t");
				pstmt.setString(2, "1");
				pstmt.setInt(3, 1);
				pstmt.executeUpdate();
			}
			System.out.println(System.currentTimeMillis() - time);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
	}

	/**
	 * 测试sql注入怎么产生
	 * 
	 * 模拟登陆
	 */
	@Test
	public void testSqlInject1() {
		// 准备数据
		String name = "a' or 1=1 or 1='";
		String password = "123";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = JdbcUtil.getConnection();
		try {
			stmt = conn.createStatement();
			String sql = "SELECT NAME,PASSWORD FROM p_user WHERE NAME='" + name + "' AND PASSWORD=" + password;
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("登陆成功啦！");
				System.out.println("用户名=" + rs.getString("name") + " 密码=" + rs.getString("password"));
			} else {
				System.out.println("用户名或密码不匹配！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * 循环保存5000条数据 msql Statement 2984ms
	 */
	@Test
	public void testEff11() {
		Connection conn = null;
		Statement stmt = null;
		long time = System.currentTimeMillis();
		conn = JdbcUtil.getConnection();

		try {
			stmt = conn.createStatement();
			for (int i = 0; i < 5000; i++) {
				String sql = "INSERT INTO p_user(NAME,PASSWORD,age) VALUES('t','1',1)";
				stmt.executeUpdate(sql);
			}
			System.out.println(System.currentTimeMillis() - time);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, stmt, null);
		}

	}

	private static Properties props = new Properties();

	static {
		InputStream is = null;

		is = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 获得连接
	public static Connection createConn() {
		Connection conn = null;
		try {
			Class.forName((String) props.get("jdbc.driver"));
			// ip地址 + 数据库名称
			conn = DriverManager.getConnection((String) props.get("jdbc.url"), (String) props.get("jdbc.username"),
					(String) props.get("jdbc.password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 编译执行
	public static PreparedStatement getPs1(Connection conn, String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public static void close1(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close1(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close1(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void dbm() throws Exception {
		Connection con = JdbcUtil.getConn();
		DatabaseMetaData dm = con.getMetaData();
		// ResultSet rs= dm.getCatalogs();//获取所有数据库名称
		// while(rs.next()){
		// String name = rs.getString("TABLE_CAT");
		// System.err.println(name);
		// }
		// System.err.println("======================");
		String dbName = dm.getDatabaseProductName();// 数据库名称
		System.err.println(dbName);
		System.err.println("数据库中有多少表：");
		ResultSet rs2 = dm.getTables("db909", "db909", null, new String[] { "TABLE" });
		while (rs2.next()) {
			String tableName = rs2.getString("TABLE_NAME");
			System.err.println(tableName);
		}
	}

	@Test
	public void rs2() throws Exception {
		Connection con = JdbcUtil.getConn();
		// 转到exam数据库中去
		Statement st = con.createStatement();
		st.execute("use exam");
		// 查询
		String sql = "select * from dept";
		ResultSet rs = st.executeQuery(sql);
		// 对rs结果集进行分析
		ResultSetMetaData rsmd = rs.getMetaData();
		// 获取有几个列
		int cols = rsmd.getColumnCount();
		System.err.println(cols);
		// 获取每一个字段名
		List<String> colNames = new ArrayList<String>();// 保存所有的字段
		for (int i = 0; i < cols; i++) {
			String colName = rsmd.getColumnName(i + 1);
			System.err.print(colName + "\t\t");
			colNames.add(colName);
		}
		System.err.println();
		// 获取数据
		while (rs.next()) {
			for (String nm : colNames) {// 遍历一行中的所列
				String val = rs.getString(nm);
				System.err.print(val + "\t\t");
			}
			System.err.println();
		}

		con.close();
	}

	// 连接数据库的URL
	private String url = "jdbc:mysql://localhost:3306/day17";
	// jdbc协议:数据库子协议:主机:端口/连接的数据库 //

	private String user = "root";// 用户名
	private String password = "root";// 密码

	/**
	 * 第一种方法
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 1.创建驱动程序类对象
		Driver driver = new com.mysql.jdbc.Driver(); // 新版本
		// Driver driver = new org.gjt.mm.mysql.Driver(); //旧版本

		// 设置用户名和密码
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);

		// 2.连接数据库，返回连接对象
		Connection conn = driver.connect(url, props);

		System.out.println(conn);
	}

	/**
	 * 使用驱动管理器类连接数据库(注册了两次，没必要)
	 * 
	 * @throws Exception
	 */
	@Test
	public void test21() throws Exception {
		Driver driver = new com.mysql.jdbc.Driver();
		// Driver driver2 = new com.oracle.jdbc.Driver();
		// 1.注册驱动程序(可以注册多个驱动程序)
		DriverManager.registerDriver(driver);
		// DriverManager.registerDriver(driver2);

		// 2.连接到具体的数据库
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);

	}

	/**
	 * （推荐使用这种方式连接数据库） 推荐使用加载驱动程序类 来 注册驱动程序
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// Driver driver = new com.mysql.jdbc.Driver();

		// 通过得到字节码对象的方式加载静态代码块，从而注册驱动程序
		Class.forName("com.mysql.jdbc.Driver");

		// Driver driver2 = new com.oracle.jdbc.Driver();
		// 1.注册驱动程序(可以注册多个驱动程序)
		// DriverManager.registerDriver(driver);
		// DriverManager.registerDriver(driver2);

		// 2.连接到具体的数据库
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);

	}

	/**
	 * 调用带有输入参数的存储过程 CALL pro_findById(4);
	 */
	@Test
	public void test11() {
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			// 获取连接
			conn = JdbcUtil.getConnection();

			// 准备sql
			String sql = "CALL pro_findById(?)"; // 可以执行预编译的sql

			// 预编译
			stmt = conn.prepareCall(sql);

			// 设置输入参数
			stmt.setInt(1, 6);

			// 发送参数
			rs = stmt.executeQuery(); // 注意：
										// 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！

			// 遍历结果
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				System.out.println(id + "," + name + "," + gender);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * 执行带有输出参数的存储过程 CALL pro_findById2(5,@NAME);
	 */
	@Test
	public void test2() {
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			// 获取连接
			conn = JdbcUtil.getConnection();
			// 准备sql
			String sql = "CALL pro_findById2(?,?)"; // 第一个？是输入参数，第二个？是输出参数

			// 预编译
			stmt = conn.prepareCall(sql);

			// 设置输入参数
			stmt.setInt(1, 6);
			// 设置输出参数(注册输出参数)
			/**
			 * 参数一： 参数位置 参数二： 存储过程中的输出参数的jdbc类型 VARCHAR(20)
			 */
			stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

			// 发送参数，执行
			stmt.executeQuery(); // 结果不是返回到结果集中，而是返回到输出参数中

			// 得到输出参数的值
			/**
			 * 索引值： 预编译sql中的输出参数的位置
			 */
			String result = stmt.getString(2); // getXX方法专门用于获取存储过程中的输出参数

			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, stmt, rs);
		}
	}

//	private static Connection conn = null; // 数据库连接对象
	private static CallableStatement cs = null;// 存储过程执行对象
//	private static ResultSet rs = null;// 结果集对象
	// 静态处理块
//	static {
//		try {
//			// 利用java反射技术获取对应驱动类
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			// 获取连接对象
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@0.0.0.0:1521:orcl", "scott", "tiger");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 
	 * @Discription 执行有参数，无返回值的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --有参数无返回值 create or replace procedure updateName(byNo in
	 * number,useName in varchar2) as begin update emp e set e.ename = useName
	 * where e.empno = byNo; end;
	 */
	public void callProcedureY(Connection conn) throws Exception {
		// 指定调用的存储过程
		cs = conn.prepareCall("{call updateName(?,?)}");
		cs.setInt(1, 7499);// 设置存储过程对应的输入参数
		cs.setString(2, "www");// 对应下标从1 开始
		// 执行存储过程调用
		cs.execute();
	}

	/**
	 * 
	 * @Discription 执行无参数，无返回值的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --无参数 create or replace procedure insertLine as begin insert
	 * into emp
	 * values(7333,'ALLEN','SAL',7698,to_date('2011/11/11','yyyy-MM-dd'),1600,
	 * 300,30); end;
	 */
	public void callProcedure(Connection conn) throws Exception {
		// 指定调用的存储过程
		cs = conn.prepareCall("{call insertLine}");
		// 执行存储过程的调用
		cs.execute();
	}

	/**
	 * 
	 * @Discription 执行有参数，有返回值的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --有参数，有返回值 create or replace procedure deleteLine(byNo in
	 * number,getCount out number) as begin delete from emp e where e.empno =
	 * byNo; select count(*) into getCount from emp e; end;
	 */
	public void callProcedureYY(Connection conn) throws Exception {
		// 指定调用的存储过程
		cs = conn.prepareCall("{call deleteLine(?,?)}");
		// 设置参数
		cs.setInt(1, 7839);
		// 这里需要配置OUT的参数新型
//		cs.registerOutParameter(2, OracleTypes.NUMBER);
		// 执行调用
		cs.execute();
		// 输入返回值
		System.out.println(cs.getString(2));
	}

	/**
	 * 
	 * @Discription 执行有参数，返回集合的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --有参数返回一个列表，使用package create or replace package someUtils as
	 * type cur_ref is ref cursor; procedure selectRows(cur_ref out
	 * someUtils.cur_ref); end someUtils; create or replace package body
	 * someUtils as procedure selectRows(cur_ref out someUtils.cur_ref) as begin
	 * open cur_ref for select * from emp e; end selectRows; end someUtils;
	 */
	public void callProcedureYYL(Connection conn) throws Exception {
		// 执行调用的存储过程
		cs = conn.prepareCall("{call someUtils.selectRows(?)}");
		// 设置返回参数
//		cs.registerOutParameter(1, OracleTypes.CURSOR);
		// 执行调用
		cs.execute();
		// 获取结果集 结果集是一个Object类型，需要进行强制转换 rs = (ResultSet)
		rs = (ResultSet) cs.getObject(1);
		// 输出返回值
		while (rs.next()) {
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
		}
	}

	/**
	 * 
	 * @Discription 执行有参数的函数
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --创建函数，有参数 create or replace function useOther(byNo in number)
	 * return String as returnValue char(10); begin select count(*) into
	 * returnValue from emp e where e.empno > byNo; return returnValue; end;
	 */
	public void callProcedureFY(Connection conn) throws Exception {
		// 指定调用的函数
		cs = conn.prepareCall("{? = call useOther(?)}");
		// 配置OUT参数信息
//		cs.registerOutParameter(1, OracleTypes.CHAR);
		// 配置输入参数
		cs.setInt(2, 1111);
		// 执行过程调用
		cs.execute();
		// 输入返回值
		System.out.println(cs.getString(1));
	}

	/**
	 * 
	 * @Discription 执行相应关闭操作
	 * @return void
	 * @param conn
	 * @param rs
	 */
	public void closeConn(Connection conn, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @说明 执行一条SQL
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> excuteQuery(String sql) {
		Connection conn = null;
		PreparedStatement psta = null;
		ResultSet resultSet = null;
		List<Object[]> relist = new ArrayList<Object[]>(); // 总数据
		Object[] objects = null; // 每行数据
		try {
			conn = JdbcUtil.getConn(); // 得到链接
			if (null != conn) {
				psta = conn.prepareStatement(sql);
				resultSet = psta.executeQuery(); // 执行查询，返回结果接集合
				int count = resultSet.getMetaData().getColumnCount(); // 一共有多少列数据
				// 循环行
				while (resultSet.next()) {
					objects = new Object[count];
					// 数据集索引从 1 开始，而数组存放时是从 0 开始
					for (int i = 1; i <= count; i++) {
						objects[i - 1] = resultSet.getObject(i);
					}
					relist.add(objects);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			relist = null;
		} finally {
			try {
				if (null != resultSet)
					resultSet.close();
				if (null != psta)
					psta.close();
				if (null != conn)
					conn.close();
			} catch (Exception e2) {

			}
		}
		return relist;
	}

//	private static org.apache.commons.dbcp.BasicDataSource ds = null;
//	static {
//		ds = new BasicDataSource(); // 组建数据源对象
//		int initialSize = 1; // 连接池启动时的初始值
//		int maxActive = 10; // 连接池的最大值
//		int maxIdle = 1; // 最大空闲值
//		int minIdle = 1; // 最小空闲值
//		ds.setDriverClassName("com.mysql.jdbc.Driver");
//		ds.setUrl("jdbc:mysql://192.168.154.128:3306/t2?useUnicode=true&characterEncoding=gbk");
//		ds.setUsername("root");
//		ds.setPassword("123456");
//		ds.setInitialSize(initialSize);
//		ds.setMaxActive(maxActive);
//		ds.setMaxIdle(maxIdle);
//		ds.setMinIdle(minIdle);
//	}

	/**
	 * 从数据源中取得数据库连接
	 */
//	public static Connection getConn() {
//		try {
//			return ds.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static void main2323(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "root");) {
			DatabaseMetaData dmd = con.getMetaData();
			System.out.println("当前数据库是：" + dmd.getDatabaseProductName());
			System.out.println("当前数据库版本：" + dmd.getDatabaseProductVersion());
			System.out.println("当前数据库驱动：" + dmd.getDriverVersion());
			System.out.println("当前数据库URL：" + dmd.getURL());
			System.out.println("当前数据库是否是只读模式？：" + dmd.isReadOnly());
			System.out.println("当前数据库是否支持批量更新？：" + dmd.supportsBatchUpdates());
			System.out.println("当前数据库是否支持结果集的双向移动（数据库数据变动不在ResultSet体现）？："
					+ dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
			System.out.println("当前数据库是否支持结果集的双向移动（数据库数据变动会影响到ResultSet的内容）？："
					+ dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
			System.out.println("========================================");
			ResultSet rs = dmd.getTables(null, null, "%", null);
			System.out.println("表名" + "," + "表类型");
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_NAME") + "," + rs.getString("TABLE_TYPE"));
			}
			System.out.println("========================================");
			rs = dmd.getPrimaryKeys(null, null, "t_student");
			while (rs.next()) {
				System.out.println(rs.getString(3) + "表的主键是：" + rs.getString(4));
			}
			System.out.println("========================================");
			rs = dmd.getColumns(null, null, "t_student", "%");
			System.out.println("t_student表包含的字段:");
			while (rs.next()) {
				System.out.println(rs.getString(4) + " " + rs.getString(6) + "(" + rs.getString(7) + ");");
			}
			System.out.println("========================================");
		} catch (SQLException e) {
			System.out.println("数据库操作出现异常");
		}
	}

	public static void main(String[] args) {
		try {
			// 加载驱动
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			System.out.println("Load the embedded driver");
			// 创建和连接数据库
			Connection conn = DriverManager.getConnection("jdbc:derby:myDB;create = true");
			System.out.println("create and connect to myDB");
			// 创建表
			Statement s = conn.createStatement();
			s.execute("create table employee(no varchar(4),name varchar(8),sex varchar(2),salary Float)");
			System.out.println("Created table");
			s.executeUpdate("insert into employee values(1001,'张强','男',675.20)");
			ResultSet rs = s.executeQuery("SELECT no,name,sex,salary FROM employee where sex='男'");

			System.out.println("no\tname\tsex\tsalary");
			while (rs.next()) {
				StringBuilder builder = new StringBuilder(rs.getString(1));
				builder.append("\t");
				builder.append(rs.getString(2));
				builder.append("\t");
				builder.append(rs.getString(3));
				builder.append("\t");
				builder.append(rs.getDouble(4));
				System.out.println(builder.toString());

			}
			s.execute("drop table employee");
			System.out.println("Dropped table ");
			rs.close();
			s.close();
			System.out.println("Closed result set and statement");
			conn.close();
			System.out.println("Closed connection");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 类型名称 显示长度 数据库类型 JAVA类型 JDBC类型索引(int) 描述
	//
	// VARCHAR L+N VARCHAR java.lang.String 12
	// CHAR N CHAR java.lang.String 1
	// BLOB L+N BLOB -4
	// TEXT 65535 VARCHAR java.lang.String -1
	//
	// INTEGER 4 INTEGER UNSIGNED java.lang.Long 4
	// TINYINT 3 TINYINT UNSIGNED java.lang.Boolean -6
	// SMALLINT 5 SMALLINT UNSIGNED java.lang.Integer 5
	// MEDIUMINT 8 MEDIUMINT UNSIGNED java.lang.Integer 4
	// BIT 1 BIT java.lang.Boolean -7
	// BIGINT 20 BIGINT UNSIGNED java.math.BigInteger -5
	// FLOAT 4+8 FLOAT java.lang.Float 7
	// DOUBLE 22 DOUBLE java.lang.Double 8
	// DECIMAL 11 DECIMAL java.math.BigDecimal 3
	// BOOLEAN 1 同TINYINT
	//
	// ID 11 PK (INTEGER UNSIGNED) java.lang.Long 4
	//
	// DATE 10 DATE java.sql.Date 91
	// TIME 8 TIME java.sql.Time 92
	// DATETIME 19 DATETIME java.sql.Timestamp 93
	// TIMESTAMP 19 TIMESTAMP java.sql.Timestamp 93
	// YEAR 4 YEAR java.sql.Date 91

	/**
	 * mysql非批量插入10万条记录 第1次：17437 ms 第2次：17422 ms 第3次：17046 ms
	 */
	public static void test_mysql() {
		String url = "jdbc:mysql://192.168.10.139:3306/test";
		String userName = "root";
		String password = "1234";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);
			String sql = "insert into t_user(id,uname) values(?,?)";
			PreparedStatement prest = conn.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for (int x = 0; x < 100000; x++) {
				prest.setInt(1, x);
				prest.setString(2, "张三");
				prest.execute();
			}
			conn.commit();
			long b = System.currentTimeMillis();
			System.out.println("MySql非批量插入10万条记录用时" + (b - a) + " ms");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * mysql批量插入10万条记录 第1次：17437 ms 第2次：17562 ms 第3次：17140 ms
	 */
	public static void test_mysql_batch() {
		String url = "jdbc:mysql://192.168.10.139:3306/test";
		String userName = "root";
		String password = "1234";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);
			String sql = "insert into t_user(id,uname) values(?,?)";
			PreparedStatement prest = conn.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for (int x = 0; x < 100000; x++) {
				prest.setInt(1, x);
				prest.setString(2, "张三");
				prest.addBatch();
			}
			prest.executeBatch();
			conn.commit();
			long b = System.currentTimeMillis();
			System.out.println("MySql批量插入10万条记录用时" + (b - a) + " ms");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * oracle非批量插入10万条记录 第1次：22391 ms 第2次：22297 ms 第3次：22703 ms
	 */
	public static void test_oracle() {
		String url = "jdbc:oracle:thin:@192.168.10.139:1521:orcl";
		String userName = "scott";
		String password = "tiger";
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);
			String sql = "insert into t_user(id,uname) values(?,?)";
			PreparedStatement prest = conn.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for (int x = 0; x < 100000; x++) {
				prest.setInt(1, x);
				prest.setString(2, "张三");
				prest.execute();
			}
			conn.commit();
			long b = System.currentTimeMillis();
			System.out.println("Oracle非批量插入10万记录用时" + (b - a) + " ms");
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * oracle批量插入10万条记录 第1次：360 ms 第2次：328 ms 第3次：359 ms
	 */
	public static void test_oracle_batch() {
		String url = "jdbc:oracle:thin:@192.168.10.139:1521:orcl";
		String userName = "scott";
		String password = "tiger";
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);
			String sql = "insert into t_user(id,uname) values(?,?)";
			PreparedStatement prest = conn.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for (int x = 0; x < 100000; x++) {
				prest.setInt(1, x);
				prest.setString(2, "张三");
				prest.addBatch();
			}
			prest.executeBatch();
			conn.commit();
			long b = System.currentTimeMillis();
			System.out.println("Oracle批量插入10万记录用时" + (b - a) + " ms");
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void proc21() throws Exception {
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc2(?,?)}");
		cs.setString(1, "UAAA");
		cs.setString(2, "王健");
		boolean boo = cs.execute();
		System.err.println(boo);
		con.close();
	}

	@Test
	public void proc31() throws Exception {
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc5(?,?,?)}");
		cs.setString(1, "UBDDB");
		cs.setString(2, "张三");
		cs.registerOutParameter(3, Types.INTEGER);// --int,
		boolean boo = cs.execute();
		System.err.println(">>:" + boo);// true
		// 从call中获取返回的值
		int size = cs.getInt(3);
		System.err.println("行数:" + size);
		if (boo) {
			ResultSet rs = cs.getResultSet();
			rs.next();
			int ss = rs.getInt(1);
			System.err.println("sss:" + ss);
		}
		con.close();
	}

	@Test
	public void proc61() throws Exception {
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc6(?,?,?,?)}");
		cs.setString(1, "UBafadsB");
		cs.setString(2, "张三");
		cs.registerOutParameter(3, Types.INTEGER);// --int,
		cs.registerOutParameter(4, Types.INTEGER);
		boolean boo = cs.execute();
		System.err.println(">>:" + boo);// faluse
		// 从call中获取返回的值
		int size = cs.getInt(3);
		int _s = cs.getInt(4);
		System.err.println("行数:" + size + "," + _s);
		con.close();
	}

	@Test
	public void proc11() throws Exception {
		// JdbcUtil不提供调用存储过程的能力
		Connection con = JdbcUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc1()}");
		// 执行

	}

	public static void main11(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "root");
				PreparedStatement pstmt = con.prepareStatement("select id_ as 主键,name_ as 姓名,sex as 性别 from t_student",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = pstmt.executeQuery();) {
			ResultSetMetaData rsm = rs.getMetaData();
			System.out.println("t_student表有几个字段？" + rsm.getColumnCount());
			System.out.println("第一个字段所在表？" + rsm.getTableName(1));
			System.out.println("========================");
			// 遍历一个不知道结构的表
			System.out.println("通用型遍历结果集：");
			System.out.println("1.获得所有的列名");
			int colNum = rsm.getColumnCount();
			String[] colName = new String[colNum]; // 字段名
			String[] colLabel = new String[colNum]; // 别名
			for (int i = 1; i < colNum; i++) {
				{
					colName[i - 1] = rsm.getColumnName(i);
					colLabel[i - 1] = rsm.getColumnLabel(i);
				}
				System.out.println(Arrays.asList(colName));
				System.out.println(Arrays.asList(colLabel));
				System.out.println("------------------------");
				System.out.println("2.遍历并封装");
				// 把结果集封装成List>
				List dbData = new ArrayList<>();
				while (rs.next()) {
					Map one = new HashMap();
					for (int i1 = 1; i1 < colNum; i1++) {
						{
							one.put(colLabel[i1 - 1], rs.getString(i1));
						}
						dbData.add(one);
					}
					// System.out.println(dbData);
					/*
					 * for(Map one1 : dbData) { { System.out.println(one1); }
					 * }catch(SQLException e) { System.out.println("数据库操作出现异常");
					 * }
					 */
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void main55(String[] args) {
		try {
			List<Object[]> list = (List<Object[]>) JdbcUtil.executeQuery("select * from t");
			for (int i = 0; i < list.size(); i++) {
				Object[] os = list.get(i);
				for (Object o : os) {
					if (o instanceof String) {
						String s = (String) o;
						String newStr = new String(s.getBytes("ISO-8859-1"), "GBK");
						System.out.print("字符串：" + newStr + "\t\t");
					} else if (o instanceof Long) {
						Long s = (Long) o;
						System.out.print("浮点值：" + s + "\t\t");
					} else if (o instanceof Integer) {
						Integer s = (Integer) o;
						System.out.print("整形值：" + s + "\t\t");
					} else {
						System.out.print("未知型：" + o + "\t\t");
					}
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main3(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test2.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people;");
		stat.executeUpdate("create table people (name, occupation);");
		PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");
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

	// 调用函数
	@Test
	public void executeOracleFunction() throws SQLException {
		java.sql.Connection conn = JdbcUtil.getConn();
		java.sql.CallableStatement cs = conn.prepareCall("{?=call sum_sal(?,?)}");
		cs.registerOutParameter(1, Types.NUMERIC);
		cs.registerOutParameter(3, Types.NUMERIC);
		cs.setInt(2, 80);
		cs.execute();
		System.out.println(cs.getDouble(1));
		System.out.println(cs.getDouble(3));
	}
	@Test
	public void executeOracleProduce() throws SQLException {
		java.sql.Connection conn = JdbcUtil.getConn();
		java.sql.CallableStatement cs = conn.prepareCall("{call add_sal_procedure(?,?)}");
		cs.registerOutParameter(2, Types.NUMERIC);
		cs.setInt(1, 80);
		cs.execute();
		System.out.println(cs.getDouble(2));
	}
	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/



	public static List extractData1(ResultSet rs)
		throws SQLException
	{
		List results = new LinkedList();
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)));
		return results;
	}

	public static List extractData1(ResultSet rs, int rowsExpected)
		throws SQLException
	{
		List results = ((List) (rowsExpected <= 0 ? ((List) (new LinkedList())) : ((List) (new ArrayList(rowsExpected)))));
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)));
		return results;
	}

	public static Object mapRow1(ResultSet rs, int rowNum)
		throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = new HashMap(columnCount);
		for (int i = 1; i <= columnCount; i++)
		{
			String key = getColumnKey(rsmd.getColumnName(i));
			Object obj = getColumnValue(rs, i);
			mapOfColValues.put(key, obj);
		}

		return mapOfColValues;
	}

	private static String getColumnKey(String columnName)
	{
		return columnName;
	}

	private static Object getColumnValue(ResultSet rs, int index)
		throws SQLException
	{
		return getResultSetValue(rs, index);
	}

	private static Object getResultSetValue1(ResultSet rs, int index)
		throws SQLException
	{
		Object obj = rs.getObject(index);
		if (obj instanceof Blob)
			obj = rs.getBytes(index);
		else
		if (obj instanceof Clob)
			obj = rs.getString(index);
		else
		if (obj != null && obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP"))
			obj = rs.getTimestamp(index);
		else
		if (obj != null && obj.getClass().getName().startsWith("oracle.sql.DATE"))
		{
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName))
				obj = rs.getTimestamp(index);
			else
				obj = rs.getDate(index);
		} else
		if (obj != null && (obj instanceof Date) && "java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index)))
			obj = rs.getTimestamp(index);
		return obj;
	}}
	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/

