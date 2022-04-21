package com.jun.plugin.jdbc;


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
import java.io.Writer;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.jun.plugin.datasource.DataSourceUtil;

public final class JdbcUtilTest { 
	

	@Test
	public void proc2() throws Exception {
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
	public void proc1() throws Exception {
		// JdbcUtil不提供调用存储过程的能力
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
		}

	}

 
  

	 

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
			InputStream inputStream = new FileInputStream("D:\\work\\Workspaces\\day14_jdbc\\src\\cn\\itcast\\mysql\\bt\\mm.jpg");
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
	public void proc21() throws Exception {
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
		Connection con = DataSourceUtil.getDataSource().getConnection();
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
		Connection con = DataSourceUtil.getDataSource().getConnection();
		// 获取调用过程的对象
		CallableStatement cs = con.prepareCall("{call proc1()}");
		// 执行

	}

	// 调用函数
	@Test
	public void executeOracleFunction() throws SQLException {
		java.sql.Connection conn = DataSourceUtil.getConn();
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
		java.sql.Connection conn = DataSourceUtil.getConn();
		java.sql.CallableStatement cs = conn.prepareCall("{call add_sal_procedure(?,?)}");
		cs.registerOutParameter(2, Types.NUMERIC);
		cs.setInt(1, 80);
		cs.execute();
		System.out.println(cs.getDouble(2));
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
			
		}

	}
	
	

	@Test
	public void dbm() throws Exception {
		Connection con = DataSourceUtil.getConn();
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
		Connection con = DataSourceUtil.getConn();
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
		

		public static void test_mysql() throws Exception {
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/db_test", "root", "mysqladmin");) {
				DatabaseMetaData dmd = con.getMetaData();
				System.out.println("当前数据库是：" + dmd.getDatabaseProductName());
				System.out.println("当前数据库版本：" + dmd.getDatabaseProductVersion());
				System.out.println("当前数据库驱动：" + dmd.getDriverVersion());
				System.out.println("当前数据库URL：" + dmd.getURL());
				System.out.println("当前数据库是否是只读模式？：" + dmd.isReadOnly());
				System.out.println("当前数据库是否支持批量更新？：" + dmd.supportsBatchUpdates());
				System.out.println("当前数据库是否支持结果集的双向移动（数据库数据变动不在ResultSet体现）？：" + dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
				System.out.println("当前数据库是否支持结果集的双向移动（数据库数据变动会影响到ResultSet的内容）？：" + dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
				System.out.println("========================================");
				ResultSet rs = dmd.getTables(null, null, "%", null);
				System.out.println("表名" + "," + "表类型");
				while (rs.next()) {
					System.out.println(rs.getString("TABLE_NAME") + "," + rs.getString("TABLE_TYPE"));
				}
				System.out.println("========================================");
				rs = dmd.getPrimaryKeys(null, null, "sys_log_content");
				while (rs.next()) {
					System.out.println(rs.getString(3) + "表的主键是：" + rs.getString(4));
				}
				System.out.println("========================================");
				rs = dmd.getColumns(null, null, "sys_log_content", "%");
				System.out.println("t_student表包含的字段:");
				while (rs.next()) {
					System.out.println(rs.getString(4) + " " + rs.getString(6) + "(" + rs.getString(7) + ");");
				}
				System.out.println("========================================");
			} catch (SQLException e) {
				System.out.println("数据库操作出现异常");
			}
		}

		/**
		 * @param args
		 */
		public static void oracleDemo(String[] args) {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String url = "jdbc:oracle:thin:@localhost:1521:tarena";
				String dbUsername = "scott";
				String dbPassword = "tiger";
				conn = DriverManager.getConnection(url, dbUsername, dbPassword);
				stmt = conn.createStatement();
				String sql = "select empno, ename, job, sal, " + "to_char(hiredate, 'yyyy/mm/dd') hiredate " + "from mystu";
				// System.out.println(sql);
				stmt.setMaxRows(5);
				rs = stmt.executeQuery(sql);// 130000
				// rs.setFetchSize(5);
				while (rs.next()) {
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

		public static void derby() {
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


		public static void main11(String[] args) throws Exception {
			Class.forName("com.mysql.jdbc.Driver");
			try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "root");
					PreparedStatement pstmt = con.prepareStatement("select id_ as 主键,name_ as 姓名,sex as 性别 from t_student", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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


		/**
		 * mysql非批量插入10万条记录 第1次：17437 ms 第2次：17422 ms 第3次：17046 ms
		 */
		public static void test_mysql_nobatch() {
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

}
