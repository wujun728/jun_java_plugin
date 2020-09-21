package com.baijob.commonTools.db;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL执行类
 * 
 * @author Luxiaolei
 * 
 */
public class SqlRunner {
	private static Logger logger = LoggerFactory.getLogger(SqlRunner.class);

	private DataSource ds;

	public SqlRunner(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * 查询
	 * 
	 * @param sql 查询语句
	 * @param rsh 结果集处理对象
	 * @param params 参数
	 * @return 结果对象
	 * @throws SQLException
	 */
	public <T> T query(String sql, RsHandler<T> rsh, Object... params) throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return this.query(conn, sql, rsh, params);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}
	}

	/**
	 * 查询<br/>
	 * 发查询语句包括 插入、更新、删除<br/>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param sql 查询语句
	 * @param rsh 结果集处理对象
	 * @param params 参数
	 * @return 结果对象
	 * @throws SQLException
	 */
	public <T> T query(Connection conn, String sql, RsHandler<T> rsh, Object... params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			this.fillParams(ps, params);
			rs = ps.executeQuery();
			return rsh.handle(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(rs, ps);
		}
	}

	/**
	 * 执行非查询语句<br/>
	 * 发查询语句包括 插入、更新、删除
	 * 
	 * @param sql SQL
	 * @param params 参数
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int update(String sql, Object... params) throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return this.update(conn, sql, params);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}
	}

	/**
	 * 执行非查询语句<br/>
	 * 发查询语句包括 插入、更新、删除<br/>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param sql SQL
	 * @param params 参数
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int update(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			this.fillParams(ps, params);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}

	/**
	 * 批量执行非查询语句
	 * 
	 * @param sql SQL
	 * @param paramsBatch 批量的参数
	 * @return 每个SQL执行影响的行数
	 * @throws SQLException
	 */
	public int[] updateBatch(String sql, Object[]... paramsBatch) throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return this.updateBatch(conn, sql, paramsBatch);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}
	}

	/**
	 * 批量执行非查询语句<br/>
	 * 发查询语句包括 插入、更新、删除<br/>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param sql SQL
	 * @param paramsBatch 批量的参数
	 * @return 每个SQL执行影响的行数
	 * @throws SQLException
	 */
	public int[] updateBatch(Connection conn, String sql, Object[]... paramsBatch) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (Object[] params : paramsBatch) {
				this.fillParams(ps, params);
				ps.addBatch();
			}
			return ps.executeBatch();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}

	/**
	 * 填充SQL的参数。
	 * 
	 * @param ps PreparedStatement
	 * @param params SQL参数
	 * @throws SQLException
	 */
	private void fillParams(PreparedStatement ps, Object... params) throws SQLException {
		if (params == null) {
			return;
		}
		ParameterMetaData pmd = ps.getParameterMetaData();
		for (int i = 0; i < params.length; i++) {
			int paramIndex = i + 1;
			if (params[i] != null) {
				ps.setObject(paramIndex, params[i]);
			} else {
				int sqlType = Types.VARCHAR;
				try {
					sqlType = pmd.getParameterType(paramIndex);
				} catch (SQLException e) {
					logger.warn("null参数出现无法识别的类型。");
				}
				ps.setNull(paramIndex, sqlType);
			}
		}
	}
}