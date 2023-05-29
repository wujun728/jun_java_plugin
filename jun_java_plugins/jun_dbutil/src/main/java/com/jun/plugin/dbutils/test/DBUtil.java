package com.jun.plugin.dbutils.test;

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
//import org.apache.commons.lang.ArrayUtils;
//import org.junit.Test;
import org.apache.log4j.Logger;

import com.jun.plugin.datasource.DataSourceUtil;

public class DBUtil {

	private static final Logger log = Logger.getLogger(DBUtil.class);

	public static QueryRunner run = null;
	private static Connection conn = null;
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
		// 灏佽鏁版嵁鐢�
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 澹版槑杩斿洖鐨勫璞�
		try {
			// 鎵ц鏌ヨ
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			// 鍒嗘瀽缁撴灉闆�
			ResultSetMetaData rsmd = rs.getMetaData();
			// 鑾峰彇鍒楁暟
			int cols = rsmd.getColumnCount();
			// 閬嶅巻鏁版嵁
			while (rs.next()) {
				// 涓�琛屾暟鎹�
				Map<String, Object> mm = new HashMap<String, Object>();
				// 閬嶅巻鍒�
				for (int i = 0; i < cols; i++) {
					// 鑾峰彇鍒楀悕
					String colName = rsmd.getColumnName(i + 1);
					// 鑾峰彇鏁版嵁
					Object val = rs.getObject(i + 1);
					// 灏佽鍒癿ap
					mm.put(colName, val);
				}
				// 灏嗚繖涓猰ap鏀惧埌list
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
	 *            鎻掑叆sql璇彞
	 * @param params
	 *            鎻掑叆鍙傛暟
	 * @return 杩斿洖褰卞搷琛屾暟
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
			log.error("insert.鎻掑叆璁板綍閿欒锛�" + sql, e);
		}
		return affectedRows;
	}

	/**
	 * 鎵цsql璇彞
	 * 
	 * @param sql
	 *            sql璇彞
	 * @return 鍙楀奖鍝嶇殑琛屾暟
	 */
	public int update(String sql) {
		return update(sql, null);
	}

	/**
	 * 鍗曟潯淇敼璁板綍
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param param
	 *            鍙傛暟
	 * @return 鍙楀奖鍝嶇殑琛屾暟
	 */
	public int update(String sql, Object param) {
		return update(sql, new Object[] { param });
	}

	/**
	 * 鍗曟潯淇敼璁板綍
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 鍙楀奖鍝嶇殑琛屾暟
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
			log.error("update.鍗曟潯淇敼璁板綍閿欒锛�" + sql, e);
		}
		return affectedRows;
	}

	/**
	 * 鎵归噺淇敼璁板綍
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            浜岀淮鍙傛暟鏁扮粍
	 * @return 鍙楀奖鍝嶇殑琛屾暟鐨勬暟缁�
	 */
	public int[] batchUpdate(String sql, Object[][] params) {
		int[] affectedRows = new int[0];
		try {
			affectedRows = run.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("update.鎵归噺淇敼璁板綍閿欒锛�" + sql, e);
		}
		return affectedRows;
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌涓�涓狹ap瀵硅薄涓紝鐒跺悗灏嗘墍鏈塎ap瀵硅薄淇濆瓨鍒癓ist涓�
	 * 
	 * @param sql
	 *            sql璇彞
	 * @return 鏌ヨ缁撴灉
	 */
	public List<Map<String, Object>> find(String sql) {
		return find(sql, null);
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌涓�涓狹ap瀵硅薄涓紝鐒跺悗灏嗘墍鏈塎ap瀵硅薄淇濆瓨鍒癓ist涓�
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param param
	 *            鍙傛暟
	 * @return 鏌ヨ缁撴灉
	 */
	public List<Map<String, Object>> find(String sql, Object param) {
		return find(sql, new Object[] { param });
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌涓�涓狹ap瀵硅薄涓紝鐒跺悗灏嗘墍鏈塎ap瀵硅薄淇濆瓨鍒癓ist涓�
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 鏌ヨ缁撴灉
	 */
	@SuppressWarnings("unchecked")
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
			log.error("map 鏁版嵁鍒嗛〉鏌ヨ閿欒", e);
		}
		return list;
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌涓�涓狹ap瀵硅薄涓紝鐒跺悗灏嗘墍鏈塎ap瀵硅薄淇濆瓨鍒癓ist涓�
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 鏌ヨ缁撴灉
	 */
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
			log.error("map 鏁版嵁鏌ヨ閿欒", e);
		}
		return list;
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌Bean涓紝鐒跺悗灏嗘墍鏈塀ean淇濆瓨鍒癓ist涓�
	 * 
	 * @param entityClass
	 *            绫诲悕
	 * @param sql
	 *            sql璇彞
	 * @return 鏌ヨ缁撴灉
	 */
	public <T> List<T> find(Class<T> entityClass, String sql) {
		return find(entityClass, sql, null);
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌Bean涓紝鐒跺悗灏嗘墍鏈塀ean淇濆瓨鍒癓ist涓�
	 * 
	 * @param entityClass
	 *            绫诲悕
	 * @param sql
	 *            sql璇彞
	 * @param param
	 *            鍙傛暟
	 * @return 鏌ヨ缁撴灉
	 */
	public <T> List<T> find(Class<T> entityClass, String sql, Object param) {
		return find(entityClass, sql, new Object[] { param });
	}

	/**
	 * 鎵ц鏌ヨ锛屽皢姣忚鐨勭粨鏋滀繚瀛樺埌Bean涓紝鐒跺悗灏嗘墍鏈塀ean淇濆瓨鍒癓ist涓�
	 * 
	 * @param entityClass
	 *            绫诲悕
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 鏌ヨ缁撴灉
	 */
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

	/**
	 * 鏌ヨ鍑虹粨鏋滈泦涓殑绗竴鏉¤褰曪紝骞跺皝瑁呮垚瀵硅薄
	 * 
	 * @param entityClass
	 *            绫诲悕
	 * @param sql
	 *            sql璇彞
	 * @return 瀵硅薄
	 */
	public <T> T findFirst(Class<T> entityClass, String sql) {
		return findFirst(entityClass, sql, null);
	}

	/**
	 * 鏌ヨ鍑虹粨鏋滈泦涓殑绗竴鏉¤褰曪紝骞跺皝瑁呮垚瀵硅薄
	 * 
	 * @param entityClass
	 *            绫诲悕
	 * @param sql
	 *            sql璇彞
	 * @param param
	 *            鍙傛暟
	 * @return 瀵硅薄
	 */
	public <T> T findFirst(Class<T> entityClass, String sql, Object param) {
		return findFirst(entityClass, sql, new Object[] { param });
	}

	/**
	 * 鏌ヨ鍑虹粨鏋滈泦涓殑绗竴鏉¤褰曪紝骞跺皝瑁呮垚瀵硅薄
	 * 
	 * @param entityClass
	 *            绫诲悕
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 瀵硅薄
	 */
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
			log.error("杩斿洖涓�鏉¤褰曢敊璇細findFirst" + e.getMessage());
			e.printStackTrace();
		}
		return (T) object;
	}

	/**
	 * 鏌ヨ鍑虹粨鏋滈泦涓殑绗竴鏉¤褰曪紝骞跺皝瑁呮垚Map瀵硅薄
	 * 
	 * @param sql
	 *            sql璇彞
	 * @return 灏佽涓篗ap鐨勫璞�
	 */
	public Map<String, Object> findFirst(String sql) {
		return findFirst(sql, null);
	}

	/**
	 * 鏌ヨ鍑虹粨鏋滈泦涓殑绗竴鏉¤褰曪紝骞跺皝瑁呮垚Map瀵硅薄
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param param
	 *            鍙傛暟
	 * @return 灏佽涓篗ap鐨勫璞�
	 */
	public Map<String, Object> findFirst(String sql, Object param) {
		return findFirst(sql, new Object[] { param });
	}

	/**
	 * 鏌ヨ鍑虹粨鏋滈泦涓殑绗竴鏉¤褰曪紝骞跺皝瑁呮垚Map瀵硅薄
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 灏佽涓篗ap鐨勫璞�
	 */
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
			log.error("findFirst.鏌ヨ涓�鏉¤褰曢敊璇�" + sql, e);
		}
		return map;
	}

	/**
	 * 鏌ヨ鏌愪竴鏉¤褰曪紝骞跺皢鎸囧畾鍒楃殑鏁版嵁杞崲涓篛bject
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param columnName
	 *            鍒楀悕
	 * @return 缁撴灉瀵硅薄
	 */
	public Object findBy(String sql, String params) {
		return findBy(sql, params, null);
	}

	/**
	 * 鏌ヨ鏌愪竴鏉¤褰曪紝骞跺皢鎸囧畾鍒楃殑鏁版嵁杞崲涓篛bject
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param columnName
	 *            鍒楀悕
	 * @param param
	 *            鍙傛暟
	 * @return 缁撴灉瀵硅薄
	 */
	public Object findBy(String sql, String columnName, Object param) {
		return findBy(sql, columnName, new Object[] { param });
	}

	/**
	 * 鏌ヨ鏌愪竴鏉¤褰曪紝骞跺皢鎸囧畾鍒楃殑鏁版嵁杞崲涓篛bject
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param columnName
	 *            鍒楀悕
	 * @param params
	 *            鍙傛暟鏁扮粍
	 * @return 缁撴灉瀵硅薄
	 */
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
			log.error("findBy銆傞敊璇�" + sql, e);
		}
		return object;
	}

	/**
	 * 鏌ヨ鏌愪竴鏉¤褰曪紝骞跺皢鎸囧畾鍒楃殑鏁版嵁杞崲涓篛bject
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param columnIndex
	 *            鍒楃储寮�
	 * @return 缁撴灉瀵硅薄
	 */
	public Object findBy(String sql, int columnIndex) {
		return findBy(sql, columnIndex, null);
	}

	/**
	 * 鏌ヨ鏌愪竴鏉¤褰曪紝骞跺皢鎸囧畾鍒楃殑鏁版嵁杞崲涓篛bject
	 * 
	 * @param sql
	 *            sql璇彞
	 * @param columnIndex
	 *            鍒楃储寮�
	 * @param param
	 *            鍙傛暟
	 * @return 缁撴灉瀵硅薄
	 */
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
			log.error("findBy.閿欒" + sql, e);
		}
		return object;
	}

	public static void main1(String[] args) throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values (?,?,?)";
		for (int i = 1; i <= 1043; i++) {
			String uuid = "U";
			String num = "00000" + i;// 0001
			num = num.substring(num.length() - 6);
			uuid = uuid + "-" + num;
			run.update(sql, uuid, "Jack" + i, "AA");
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public int[] batch99(String sql, Object[][] params) throws Exception {
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
