package com.jun.plugin.dbutils.dbutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
//import org.junit.Test;

import com.jun.plugin.datasource.DataSourceUtil;


public class DBUtil {

	private static final Logger log = Logger.getLogger(DBUtil.class);

	public static QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
	public static Connection conn = DataSourceUtil.getConn();
	static {
		run = new QueryRunner(DataSourceUtil.getDataSource());
		conn = DataSourceUtil.getConn();
	}

	public DBUtil() {

	}

	public static QueryRunner getRunner() {
		return new QueryRunner();
	}

	public List<Map<String, Object>> queryForJdbc(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> mm = new HashMap<String, Object>();
				for (int i = 0; i < cols; i++) {
					String colName = rsmd.getColumnName(i + 1);
					Object val = rs.getObject(i + 1);
					mm.put(colName, val);
				}
				list.add(mm);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 */
	public int insert(String sql, Object[] params) {
		int affectedRows = 0;
		try {
			if (params == null) {
				affectedRows = run.update(sql);
			} else {
				affectedRows = run.update(sql, params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("insert.test" + sql, e);
		}
		return affectedRows;
	}

	/**
	 * 
	 * @param sql
	 */
	public int update(String sql) {
		return update(sql, null);
	}

	/**
	 * @param sql
	 * @param param
	 */
	public int update(String sql, Object param) {
		return update(sql, new Object[] { param });
	}

	/**
	 * @param sql
	 * @param params
	 */
	public int update(String sql, Object[] params) {
		int affectedRows = 0;
		try {
			if (params == null) {
				affectedRows = run.update(sql);
			} else {
				affectedRows = run.update(sql, params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("update.1111" + sql, e);
		}
		return affectedRows;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 */
	public int[] batchUpdate(String sql, Object[][] params) {
		int[] affectedRows = new int[0];
		try {
			affectedRows = run.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("update.1111" + sql, e);
		}
		return affectedRows;
	}

	 
	public List<Map<String, Object>> find(String sql) {
		return find(sql, null);
	}

	 
	public List<Map<String, Object>> find(String sql, Object param) {
		return find(sql, new Object[] { param });
	}

	 
	 
	public List<Map<String, Object>> findPage(String sql, int page, int count, Object... params) {
		sql = sql + " LIMIT ?,?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (params == null) {
				list = (List<Map<String, Object>>) run.query(sql, new MapListHandler(), new Integer[] { page, count });
			} else {
				// list = (List<Map<String, Object>>) run.query(sql, new MapListHandler(),
				// ArrayUtils.addAll(params, new Integer[] { page, count }));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("map 11111", e);
		}
		return list;
	}
 
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> find(String sql, Object[] params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (params == null) {
				list = (List<Map<String, Object>>) run.query(sql, new MapListHandler());
			} else {
				list = (List<Map<String, Object>>) run.query(sql, new MapListHandler(), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("map 111", e);
		}
		return list;
	}
 
	public <T> List<T> find(Class<T> entityClass, String sql) {
		return find(entityClass, sql, null);
	}

	 
	public <T> List<T> find(Class<T> entityClass, String sql, Object param) {
		return find(entityClass, sql, new Object[] { param });
	}
 
	@SuppressWarnings("unchecked")
	public <T> List<T> find(Class<T> entityClass, String sql, Object[] params) {
		List<T> list = new ArrayList<T>();
		try {
			if (params == null) {
				list = (List<T>) run.query(sql, new BeanListHandler(entityClass));
			} else {
				list = (List<T>) run.query(sql, new BeanListHandler(entityClass), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Error occured while attempting to query data", e);
		}
		return list;
	}

	public <T> T findFirst(Class<T> entityClass, String sql) {
		return findFirst(entityClass, sql, null);
	}

	 
	public <T> T findFirst(Class<T> entityClass, String sql, Object param) {
		return findFirst(entityClass, sql, new Object[] { param });
	}

	 
	@SuppressWarnings("unchecked")
	public <T> T findFirst(Class<T> entityClass, String sql, Object[] params) {
		Object object = null;
		try {
			if (params == null) {
				object = run.query(sql, new BeanHandler(entityClass));
			} else {
				object = run.query(sql, new BeanHandler(entityClass), params);
			}
		} catch (SQLException e) {
			log.error("1111  findFirst" + e.getMessage());
			e.printStackTrace();
		}
		return (T) object;
	}

	 
	public Map<String, Object> findFirst(String sql) {
		return findFirst(sql, null);
	}

	 
	public Map<String, Object> findFirst(String sql, Object param) {
		return findFirst(sql, new Object[] { param });
	}
 
	@SuppressWarnings("unchecked")
	public Map<String, Object> findFirst(String sql, Object[] params) {
		Map<String, Object> map = null;
		try {
			if (params == null) {
				map = (Map<String, Object>) run.query(sql, new MapHandler());
			} else {
				map = (Map<String, Object>) run.query(sql, new MapHandler(), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("findFirst.11111" + sql, e);
		}
		return map;
	}

	 
	public Object findBy(String sql, String params) {
		return findBy(sql, params, null);
	}

	 
	public Object findBy(String sql, String columnName, Object param) {
		return findBy(sql, columnName, new Object[] { param });
	}

	 
	public Object findBy(String sql, String columnName, Object[] params) {
		Object object = null;
		try {
			if (params == null) {
				object = run.query(sql, new ScalarHandler(columnName));
			} else {
				object = run.query(sql, new ScalarHandler(columnName), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("findBy1111" + sql, e);
		}
		return object;
	}
 
	public Object findBy(String sql, int columnIndex) {
		return findBy(sql, columnIndex, null);
	}

	 
	public Object findBy(String sql, int columnIndex, Object param) {
		return findBy(sql, columnIndex, new Object[] { param });
	}

	public Object findBy(String sql, int columnIndex, Object[] params) {
		Object object = null;
		try {
			if (params == null) {
				object = run.query(sql, new ScalarHandler(columnIndex));
			} else {
				object = run.query(sql, new ScalarHandler(columnIndex), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("findBy.123" + sql, e);
		}
		return object;
	}


	@SuppressWarnings({ "unchecked", "deprecation" })
	public int[] batch(String sql, Object[][] params) throws Exception {
		int[] rows = null;

		try {
			rows = run.batch(conn, sql, params);
		} finally {

		}

		return rows;
	}

	public int executeUpdate(String sql) throws Exception {
		int rows = 0;

		try {
			rows = run.update(conn, sql);
		} finally {

		}
		return rows;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public int executeUpdate(String sql, Object param) throws Exception {
		int rows = 0;

		try {
			rows = run.update(conn, sql, param);
		} finally {

		}

		return rows;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public int executeUpdate(String sql, Object[] params) throws Exception {
		int rows = 0;

		try {

			rows = run.update(conn, sql, params);

		} finally {

		}

		return rows;
	}

	public Object queryToBean(Class type, String sql) throws Exception {

		Object result = null;

		ResultSetHandler h = new BeanHandler(type);
		try {

			result = run.query(conn, sql, h);

		} finally {

		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Object queryToBean(Class type, String sql, Object param) throws Exception {

		Object result = null;

		ResultSetHandler h = new BeanHandler(type);
		try {

			result = run.query(conn, sql, param, h);

		} finally {

		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Object queryToBean(Class type, String sql, Object[] params) throws Exception {

		Object result = null;

		ResultSetHandler h = new BeanHandler(type);
		try {
			result = run.query(conn, sql, params, h);
		} finally {

		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList queryToBeanList(Class type, String sql) throws Exception {

		ArrayList result = null;

		ResultSetHandler h = new BeanListHandler(type);
		try {
			result = (ArrayList) run.query(conn, sql, h);
		} finally {

		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList queryToBeanList(Class type, String sql, Object param) throws Exception {

		ArrayList result = null;

		ResultSetHandler h = new BeanListHandler(type);
		try {

			result = (ArrayList) run.query(conn, sql, param, h);

		} finally {

		}

		return result;
	}

	public ArrayList queryToBeanList(Class type, String sql, Object[] params) throws Exception {

		ArrayList result = null;
		ResultSetHandler h = new BeanListHandler(type);
		try {

			result = (ArrayList) run.query(conn, sql, params, h);

		} finally {

		}

		return result;
	}
	

}
