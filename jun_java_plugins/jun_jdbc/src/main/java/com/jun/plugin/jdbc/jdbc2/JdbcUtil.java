package com.jun.plugin.jdbc.jdbc2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jun.plugin.datasource.DataSourceUtil;

public final class JdbcUtil {

	private static String DBDRIVER = "";
	private static String DBURL = "";
	private static String DBUSER = "";
	private static String DBPASSWORD = "";

	private static Connection conn = null;
	private static ResultSet rs = null;
	private static Statement stmt = null;
	private static PreparedStatement prepareStmt = null;

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
			Class.forName(DBDRIVER);
//			dataSource = DruidDataSourceFactory.createDataSource(props);
//			if (dataSource != null) {
//				conn = dataSource.getConnection();
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public static String getUpdateSql(String Table_name, Map<String, Object> params, Map<String, Object> repeatParams) {
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
		}
	}

	/**
	 * @author
	 * @throws SQLException
	 * @since
	 * @throws @description 对数据进行批量添加，当数据量大于100时，则整除1000时提交一次，wbs里存放的是批量数值数组的集合
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

	/**
	 * 
	 * @Discription 执行有参数，无返回值的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */

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
			conn = DataSourceUtil.getConn(); // 得到链接
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

	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/

	public static List extractData1(ResultSet rs) throws SQLException {
		List results = new LinkedList();
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)))
			;
		return results;
	}

	public static List extractData1(ResultSet rs, int rowsExpected) throws SQLException {
		List results = ((List) (rowsExpected <= 0 ? ((List) (new LinkedList()))
				: ((List) (new ArrayList(rowsExpected)))));
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)))
			;
		return results;
	}

	public static Object mapRow1(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = new HashMap(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			String key = getColumnKey(rsmd.getColumnName(i));
			Object obj = getColumnValue(rs, i);
			mapOfColValues.put(key, obj);
		}

		return mapOfColValues;
	}

	public static String getColumnKey(String columnName) {
		return columnName;
	}

	public static Object getColumnValue(ResultSet rs, int index) throws SQLException {
		return getResultSetValue(rs, index);
	}

	public static Object getResultSetValue1(ResultSet rs, int index) throws SQLException {
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

	public static int getRow(String sql) {
		int i = 0;
		conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT COUNT(*) FROM " + sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			i = 0;
		} finally {
			System.out.println("SELECT COUNT(*) FROM " + sql);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
			}
		}
		return i;
	}

	/**
	 * @Description: JDBC操作元数据示例-- DatabaseMetaData接口
	 * @version V1.0
	 */
	// ***************************************************************
	// 获得驱动
	private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	// 获得url
	private static String URL = "jdbc:oracle:thin:@localhost:test";
	// 获得连接数据库的用户名
	private static String USER = "root";
	// 获得连接数据库的密码
	private static String PASS = "root";
	static {
		try {
			// 初始化JDBC驱动并让驱动加载到jvm中
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static Connection getConnection(){ Connection conn = null; try {
	 * //连接数据库
	 * 
	 * 
	 * 设置可获取REMARK备注信息 Properties props =new Properties();
	 * props.put("remarksReporting","true"); props.put("user", USER);
	 * props.put("password", PASS); conn =DriverManager.getConnection(URL,props);
	 * 
	 * // conn = DriverManager.getConnection(URL,USER,PASS); conn =
	 * JdbcUtil.getConnection(); conn.setAutoCommit(true); } catch (SQLException e)
	 * { e.printStackTrace(); } return conn; }
	 */

	// 关闭连接
	public static void close(Object o) {
		if (o == null) {
			return;
		}
		if (o instanceof ResultSet) {
			try {
				((ResultSet) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Statement) {
			try {
				((Statement) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Connection) {
			Connection c = (Connection) o;
			try {
				if (!c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		close(rs);
		close(stmt);
		close(conn);
	}

	public static void close(ResultSet rs, Connection conn) {
		close(rs);
		close(conn);
	}

	/**
	 * @Description: 获取数据库相关信息 @author: chenzw @CreateTime: 2014-1-27
	 * 下午5:09:12 @throws
	 */
	public static void getDataBaseInfo() {
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println("数据库已知的用户: " + dbmd.getUserName());
			System.out.println("数据库的系统函数的逗号分隔列表: " + dbmd.getSystemFunctions());
			System.out.println("数据库的时间和日期函数的逗号分隔列表: " + dbmd.getTimeDateFunctions());
			System.out.println("数据库的字符串函数的逗号分隔列表: " + dbmd.getStringFunctions());
			System.out.println("数据库供应商用于 'schema' 的首选术语: " + dbmd.getSchemaTerm());
			System.out.println("数据库URL: " + dbmd.getURL());
			System.out.println("是否允许只读:" + dbmd.isReadOnly());
			System.out.println("数据库的产品名称:" + dbmd.getDatabaseProductName());
			System.out.println("数据库的版本:" + dbmd.getDatabaseProductVersion());
			System.out.println("驱动程序的名称:" + dbmd.getDriverName());
			System.out.println("驱动程序的版本:" + dbmd.getDriverVersion());

			System.out.println("数据库中使用的表类型");
			rs = dbmd.getTableTypes();
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_TYPE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description:获得数据库中所有Schemas(对应于oracle中的Tablespace) @author:
	 * chenzw @CreateTime: 2014-1-27 下午5:10:35 @throws
	 */
	public static void getSchemasInfo() {
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getSchemas();
			while (rs.next()) {
				String tableSchem = rs.getString("TABLE_SCHEM");
				System.out.println(tableSchem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description: 获取数据库中所有的表信息 @author: chenzw @CreateTime: 2014-1-27
	 * 下午5:08:28 @throws
	 */
	public static void getTablesList() {
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			/**
			 * 设置连接属性,使得可获取到表的REMARK(备注)
			 */
//	            ((OracleConnection)conn).setRemarksReporting(true);   
			DatabaseMetaData dbmd = conn.getMetaData();
			String[] types = { "TABLE" };
			rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME"); // 表名
				String tableType = rs.getString("TABLE_TYPE"); // 表类型
				String remarks = rs.getString("REMARKS"); // 表备注
				System.out.println(tableName + " - " + tableType + " - " + remarks);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description: 获取某表信息 @author: chenzw @CreateTime: 2014-1-27 下午3:26:30 @throws
	 */
	public static void getTablesInfo() {
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			/**
			 * 设置连接属性,使得可获取到表的REMARK(备注)
			 */
//	            ((OracleConnection)conn).setRemarksReporting(true);   
			DatabaseMetaData dbmd = conn.getMetaData();
			/**
			 * 获取给定类别中使用的表的描述。 方法原型:ResultSet getTables(String catalog,String
			 * schemaPattern,String tableNamePattern,String[] types); catalog -
			 * 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。 schema -
			 * 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列;
			 * 可包含单字符通配符("_"),或多字符通配符("%"); tableNamePattern -
			 * 表名称;可包含单字符通配符("_"),或多字符通配符("%"); types - 表类型数组; "TABLE"、"VIEW"、"SYSTEM
			 * TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和
			 * "SYNONYM";null表示包含所有的表类型;可包含单字符通配符("_"),或多字符通配符("%");
			 */
			rs = dbmd.getTables(null, null, "CUST_INTER_TF_SERVICE_REQ", new String[] { "TABLE", "VIEW" });

			while (rs.next()) {
				String tableCat = rs.getString("TABLE_CAT"); // 表类别(可为null)
				String tableSchemaName = rs.getString("TABLE_SCHEM");// 表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
				String tableName = rs.getString("TABLE_NAME"); // 表名
				String tableType = rs.getString("TABLE_TYPE"); // 表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL
																// TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
				String remarks = rs.getString("REMARKS"); // 表备注

				System.out.println(
						tableCat + " - " + tableSchemaName + " - " + tableName + " - " + tableType + " - " + remarks);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description: 获取表主键信息 @author: chenzw @CreateTime: 2014-1-27
	 * 下午5:12:53 @throws
	 */
	public static void getPrimaryKeysInfo() {
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			/**
			 * 获取对给定表的主键列的描述 方法原型:ResultSet getPrimaryKeys(String catalog,String
			 * schema,String table); catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。 schema
			 * - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列;
			 * 可包含单字符通配符("_"),或多字符通配符("%"); table - 表名称;可包含单字符通配符("_"),或多字符通配符("%");
			 */
//	            rs = dbmd.getPrimaryKeys(null, null, "CUST_INTER_TF_SERVICE_REQ");    
			rs = dbmd.getPrimaryKeys(null, null, "sys_fileupload");

			while (rs.next()) {
				String tableCat = rs.getString("TABLE_CAT"); // 表类别(可为null)
				String tableSchemaName = rs.getString("TABLE_SCHEM");// 表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
				String tableName = rs.getString("TABLE_NAME"); // 表名
				String columnName = rs.getString("COLUMN_NAME");// 列名
				short keySeq = rs.getShort("KEY_SEQ");// 序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
				String pkName = rs.getString("PK_NAME"); // 主键名称

				System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName + " - " + columnName + " - "
						+ keySeq + " - " + pkName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description: 获取表索引信息 @author: chenzw @CreateTime: 2014-1-27
	 * 下午5:12:04 @throws
	 */
	public static void getIndexInfo() {
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			/**
			 * 获取给定表的索引和统计信息的描述 方法原型:ResultSet getIndexInfo(String catalog,String
			 * schema,String table,boolean unique,boolean approximate) catalog -
			 * 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。 schema -
			 * 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列;
			 * 可包含单字符通配符("_"),或多字符通配符("%"); table - 表名称;可包含单字符通配符("_"),或多字符通配符("%"); unique
			 * - 该参数为 true时,仅返回唯一值的索引; 该参数为 false时,返回所有索引; approximate -
			 * 该参数为true时,允许结果是接近的数据值或这些数据值以外的值;该参数为 false时,要求结果是精确结果;
			 */
			rs = dbmd.getIndexInfo(null, null, "CUST_INTER_TF_SERVICE_REQ", false, true);
			while (rs.next()) {
				String tableCat = rs.getString("TABLE_CAT"); // 表类别(可为null)
				String tableSchemaName = rs.getString("TABLE_SCHEM");// 表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
				String tableName = rs.getString("TABLE_NAME"); // 表名
				boolean nonUnique = rs.getBoolean("NON_UNIQUE");// 索引值是否可以不唯一,TYPE为 tableIndexStatistic时索引值为 false;
				String indexQualifier = rs.getString("INDEX_QUALIFIER");// 索引类别（可能为空）,TYPE为 tableIndexStatistic 时索引类别为
																		// null;
				String indexName = rs.getString("INDEX_NAME");// 索引的名称 ;TYPE为 tableIndexStatistic 时索引名称为 null;
				/**
				 * 索引类型： tableIndexStatistic - 此标识与表的索引描述一起返回的表统计信息 tableIndexClustered - 此为集群索引
				 * tableIndexHashed - 此为散列索引 tableIndexOther - 此为某种其他样式的索引
				 */
				short type = rs.getShort("TYPE");// 索引类型;
				short ordinalPosition = rs.getShort("ORDINAL_POSITION");// 在索引列顺序号;TYPE为 tableIndexStatistic 时该序列号为零;
				String columnName = rs.getString("COLUMN_NAME");// 列名;TYPE为 tableIndexStatistic时列名称为 null;
				String ascOrDesc = rs.getString("ASC_OR_DESC");// 列排序顺序:升序还是降序[A:升序; B:降序];如果排序序列不受支持,可能为 null;TYPE为
																// tableIndexStatistic时排序序列为 null;
				int cardinality = rs.getInt("CARDINALITY"); // 基数;TYPE为 tableIndexStatistic 时,它是表中的行数;否则,它是索引中唯一值的数量。
				int pages = rs.getInt("PAGES"); // TYPE为 tableIndexStatisic时,它是用于表的页数,否则它是用于当前索引的页数。
				String filterCondition = rs.getString("FILTER_CONDITION"); // 过滤器条件,如果有的话(可能为 null)。

				System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName + " - " + nonUnique + " - "
						+ indexQualifier + " - " + indexName + " - " + type + " - " + ordinalPosition + " - "
						+ columnName + " - " + ascOrDesc + " - " + cardinality + " - " + pages + " - "
						+ filterCondition);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description: 获取表中列值信息 @author: chenzw @CreateTime: 2014-1-27
	 * 下午2:55:56 @throws
	 */
	public static void getColumnsInfo() {
		Connection conn = getConnection();
		ResultSet rs = null;

		try {
			/**
			 * 设置连接属性,使得可获取到列的REMARK(备注)
			 */
//	            ((OracleConnection)conn).setRemarksReporting(true);   
			DatabaseMetaData dbmd = conn.getMetaData();
			/**
			 * 获取可在指定类别中使用的表列的描述。 方法原型:ResultSet getColumns(String catalog,String
			 * schemaPattern,String tableNamePattern,String columnNamePattern) catalog -
			 * 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。 schema -
			 * 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列;
			 * 可包含单字符通配符("_"),或多字符通配符("%"); tableNamePattern -
			 * 表名称;可包含单字符通配符("_"),或多字符通配符("%"); columnNamePattern - 列名称;
			 * ""表示获取列名为""的列(当然获取不到);null表示获取所有的列;可包含单字符通配符("_"),或多字符通配符("%");
			 */
			rs = dbmd.getColumns(null, null, "sys_fileupload", null);

			while (rs.next()) {
				String tableCat = rs.getString("TABLE_CAT"); // 表类别（可能为空）
				String tableSchemaName = rs.getString("TABLE_SCHEM"); // 表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
				String tableName_ = rs.getString("TABLE_NAME"); // 表名
				String columnName = rs.getString("COLUMN_NAME"); // 列名
				int dataType = rs.getInt("DATA_TYPE"); // 对应的java.sql.Types的SQL类型(列类型ID)
				String dataTypeName = rs.getString("TYPE_NAME"); // java.sql.Types类型名称(列类型名称)
				int columnSize = rs.getInt("COLUMN_SIZE"); // 列大小
				int decimalDigits = rs.getInt("DECIMAL_DIGITS"); // 小数位数
				int numPrecRadix = rs.getInt("NUM_PREC_RADIX"); // 基数（通常是10或2） --未知
				/**
				 * 0 (columnNoNulls) - 该列不允许为空 1 (columnNullable) - 该列允许为空 2
				 * (columnNullableUnknown) - 不确定该列是否为空
				 */
				int nullAble = rs.getInt("NULLABLE"); // 是否允许为null
				String remarks = rs.getString("REMARKS"); // 列描述
				String columnDef = rs.getString("COLUMN_DEF"); // 默认值
				int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH"); // 对于 char 类型，该长度是列中的最大字节数
				int ordinalPosition = rs.getInt("ORDINAL_POSITION"); // 表中列的索引（从1开始）
				/**
				 * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ]) YES -- 该列可以有空值;
				 * NO -- 该列不能为空; 空字符串--- 不知道该列是否可为空
				 */
				String isNullAble = rs.getString("IS_NULLABLE");

				/**
				 * 指示此列是否是自动递增 YES -- 该列是自动递增的 NO -- 该列不是自动递增 空字串--- 不能确定该列是否自动递增
				 */
				// String isAutoincrement = rs.getString("IS_AUTOINCREMENT"); //该参数测试报错

				System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName_ + " - " + columnName + " - "
						+ dataType + " - " + dataTypeName + " - " + columnSize + " - " + decimalDigits + " - "
						+ numPrecRadix + " - " + nullAble + " - " + remarks + " - " + columnDef + " - "
						+ charOctetLength + " - " + ordinalPosition + " - " + isNullAble);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtil.close(rs, conn);
		}
	}

	/**
	 * @Description: TODO @author: chenzw @CreateTime: 2014-1-17 下午2:47:45 @param
	 * args @throws
	 */
	public static void main(String[] args) {
		getDataBaseInfo(); // 获取数据库信息
		getSchemasInfo(); // 获取数据库所有Schema
		getTablesList(); // 获取某用户下所有的表
		getTablesInfo(); // 获取表信息
		getPrimaryKeysInfo(); // 获取表主键信息
		getIndexInfo(); // 获取表索引信息
		getColumnsInfo(); // 获取表中列值信息
	}

}
