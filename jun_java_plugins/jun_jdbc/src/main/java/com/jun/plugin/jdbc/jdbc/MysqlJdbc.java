package com.jun.plugin.jdbc.jdbc;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySql数据库JDBC操作类封装
 * 
 * @author Wujun
 *
 */
public abstract class MysqlJdbc implements Jdbc {
	private String USER;
	private String PASS;
	private String DB_URL;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	/**
	 * MySQL JDBC封装
	 * 
	 * @param host
	 *            链接地址
	 * @param dbName
	 *            连接的数据库名称
	 * @param user
	 *            数据库用户名
	 * @param password
	 *            数据库用户密码
	 * @throws ClassNotFoundException
	 */
	public MysqlJdbc(String host, String dbName, String user, String password) {
		this(host, null, dbName, user, password);
	}

	/**
	 * MySQL JDBC封装
	 * 
	 * @param host
	 *            链接地址
	 * @param port
	 *            端口
	 * @param dbName
	 *            连接的数据库名称
	 * @param user
	 *            数据库用户名
	 * @param password
	 *            数据库用户密码
	 * @throws ClassNotFoundException
	 */
	public MysqlJdbc(String host, Integer port, String dbName, String user, String password) {
		USER = user;
		PASS = password;
		if (port == null) {
			port = 3306;
		}
		DB_URL = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?characterEncoding=UTF-8&amp;useUnicode=true";

		// 1.加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载失败!");
		}
	}

	@Override
	public int excuteUpdate(String sql) throws SQLException {
		return this.excuteUpdate(sql, null);
	}

	@Override
	public int excuteUpdate(String sql, ParamsHandler params) throws SQLException {
		int rs = 0;
		try {
			// 加try，保证异常情况，也会释放资源
			stmt = getConnection().prepareStatement(sql);
			// 参数赋值
			if (params != null) {
				params.doHandle(stmt);
			}
			rs = stmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.close();
		}
		return rs;
	}

	@Override
	public <T> T query(String sql, ResultHandler<T> handle) throws SQLException {
		return this.query(sql, null, handle);
	}

	@Override
	public <T> T query(String sql, ParamsHandler params, ResultHandler<T> handle) throws SQLException {
		if (handle == null) {
			throw new SQLException("ResultHandle is null");
		}

		try {
			// 2.创建连接
			stmt = getConnection().prepareStatement(sql);
			// 参数处理
			if (params != null) {
				params.doHandle(stmt);
			}
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();

			DataRow data = null;

			while (rs.next()) {
				data =new DataRow();
				String label;
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					label = meta.getColumnLabel(i);
					data.put(label, rs.getObject(label));
				}
				break;
			}

			if (data == null) {
				return null;
			}
			// 结果的处理也是可变的
			return handle.doHandle(data);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.close();
		}
	}

	@Override
	public <T> List<T> queryForList(String sql, ParamsHandler params, ResultHandler<T> handle) throws SQLException {
		if (handle == null) {
			throw new SQLException("ResultHandle is null");
		}

		try {
			// 2.创建连接
			stmt = getConnection().prepareStatement(sql);

			// 参数处理
			if (params != null) {
				params.doHandle(stmt);
			}

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();

			List<T> list = new ArrayList<>();
			String label;

			while (rs.next()) {
				DataRow data = new DataRow();
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					label = meta.getColumnLabel(i);
					data.put(label, rs.getObject(label));
				}
				list.add(handle.doHandle(data));
			}

			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			this.close();
		}
	}

	@Override
	public <T> List<T> queryForList(String sql, ResultHandler<T> handle) throws SQLException {
		return this.queryForList(sql, null, handle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryUniqueResult(String sql, ParamsHandler params, Type clazz) throws SQLException {
		if (clazz == null) {
			throw new SQLException("Result type is null");
		}

		try {
			// 2.创建连接
			stmt = getConnection().prepareStatement(sql);

			// 参数处理
			if (params != null) {
				params.doHandle(stmt);
			}

			ResultSet rs = stmt.executeQuery();

			T result = null;
			while (rs.next()) {
				result = (T) rs.getObject(1);
				break;
			}
			return result;
		} catch (SQLException e) {
			throw e;
		} finally {
			this.close();
		}
	}

	@Override
	public <T> T queryUniqueResult(String sql, Type clazz) throws SQLException {
		return this.queryUniqueResult(sql, null, clazz);
	}

	// NOTE: This uses raw DriverManager.getConnection() without connection pooling.
	// For production use, consider using jun_datasource module which provides
	// connection pools (C3P0, Druid, HikariCP).
	@Override
	public Connection getConnection() throws SQLException {
		if (conn == null) {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		return conn;
	}

	/**
	 * 释放资源
	 */
	private void close() {
		try {
			// 4.清理环境，注意顺序
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			System.out.println("ResultSet关闭异常。" + e.getMessage());
		}
		
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			System.out.println("Statement关闭异常。" + e.getMessage());
		}
		
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			System.out.println("连接关闭异常。" + e.getMessage());
		}
	}
}
