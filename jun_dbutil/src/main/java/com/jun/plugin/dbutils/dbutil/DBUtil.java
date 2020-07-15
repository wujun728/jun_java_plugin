package com.jun.plugin.dbutils.dbutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
//import org.junit.Test;

import com.jun.base.datasource.DataSourceUtil;

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

	//////////////////////////////demo beggin  //////////////////////////////
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
	
	
	
	
	public void TestDBTemplate() {
		Object[] params = { "22" };
		List<Map<String, Object>> list = new DBUtil().find(" select name,content,id from test where name= ? ", params);
		System.out.println(list.size());
	}
	

	
	public void query1() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "select * from users";
		Map<Object, Map<String, Object>> mm = (Map<Object, Map<String, Object>>) run.query(sql, new KeyedHandler("id"));
		System.err.println(mm);
		Iterator it = mm.keySet().iterator();
		while (it.hasNext()) {
			Map m1 = mm.get(it.next());
			System.err.println(m1.get("id") + "," + m1.get("name") + "," + m1.get("pwd"));
		}

	}

	// 返回Map一行
	
	public void query5() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid where u.id='U001'";
		System.err.println(sql);
		Map<String, Object> mm = run.query(sql, new MapHandler());
		System.err.println(mm);
	}

	
	public void query6() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid";
		System.err.println(sql);
		List<Map<String, Object>> mm = run.query(sql, new MapListHandler());
		System.err.println(mm);
	}

	
	public void query7() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "select count(*) from contacts";
		Object o = run.query(sql, new ScalarHandler());
		Integer ss = Integer.valueOf(o.toString());
		System.err.println(ss);
	}


	
	public void insert1() throws Exception {
		String sql = "insert into users values('U002','李四','888')";
		run.update(sql);
	}

	
	public void insert2() throws Exception {
		String sql = "insert into users values(?,?,?)";
		run.update(sql, "U003", "王五", "7777");
	}

	
	public void del1() throws Exception {
		String sql = "delete from users where name=?";
		int len = run.update(sql, "李四");
		System.err.println(len);
	}

	
	public void udpate1() throws Exception {
		String sql = "update users set name=? where id=?";
		run.update(sql, "赵'七", "U001");
	}

	

	public static void main(String[] args) throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "insert into users values (?,?,?)";
		for (int i = 1; i <= 1043; i++) {
			String uuid = "U";
			String num = "00000" + i;// 0001
			num = num.substring(num.length() - 6);
			uuid = uuid + "-" + num;
			run.update(sql, uuid, "Jack" + i, "AA");
		}
	}

 

 

	
	public void tx1() throws Exception {
		Connection con = DataSourceUtil.getConn();
		try {
			String sql = "insert into users values('U008','AA','AA')";
			// 设置事务的开始标记
			con.setAutoCommit(false);
			run.update(con, sql);
			String sql2 = "insert into users values('U009,'AA','AA')";
			run.update(con, sql2);
			// 提交
			con.commit();
		} catch (Exception e) {
			System.err.println("出错了");
			con.rollback();
		} finally {
			con.close();
		}
	}

	
	public void login() throws Exception {
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		String pwd = sc.nextLine();
		// DBUtil run = new DBUtil();
		String sql = "select * from users where name='" + name + "' and pwd='" + pwd + "'";
		System.err.println(sql);
		List list = run.query(sql, new MapListHandler());
		if (list.size() > 0) {
			System.err.println("OK");
		} else {
			System.err.println("errorlll");
		}
	}

	
	public void query11188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "select * from users";
		Map<Object, Map<String, Object>> mm = (Map<Object, Map<String, Object>>) run.query(sql, new KeyedHandler("id"));
		System.err.println(mm);
		Iterator it = mm.keySet().iterator();
		while (it.hasNext()) {
			Map m1 = mm.get(it.next());
			System.err.println(m1.get("id") + "," + m1.get("name") + "," + m1.get("pwd"));
		}

	}

	// 返回Map一行
	
	public void query5188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid where u.id='U001'";
		System.err.println(sql);
		Map<String, Object> mm = run.query(sql, new MapHandler());
		System.err.println(mm);
	}

	
	public void query688() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid";
		System.err.println(sql);
		List<Map<String, Object>> mm = run.query(sql, new MapListHandler());
		System.err.println(mm);
	}

	
	public void query788() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "select count(*) from contacts";
		Object o = run.query(sql, new ScalarHandler());
		Integer ss = Integer.valueOf(o.toString());
		System.err.println(ss);
	}
	 

	
	public void insert11() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values('U002','李四','888')";
		run.update(sql);
	}

	
	public void insert21() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values(?,?,?)";
		run.update(sql, "U003", "王五", "7777");
	}

	
	public void del11() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "delete from users where name=?";
		int len = run.update(sql, "李四");
		System.err.println(len);
	}

	
	public void udpate11() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "update users set name=? where id=?";
		run.update(sql, "赵'七", "U001");
	}
 

	// 不确定条件的查询
	/*
	 *  public void query1() throws Exception{ QueryRunner run = new
	 * QueryRunner(); Contact c = new Contact(); //c.setId("C001");
	 * c.setName("王'"); c.setSex("1"); c.setTel("123"); c.setAddr("中国");
	 * c.setAge(55); String sql = "select * from contacts where 1=1";
	 * List<Object> params = new ArrayList<Object>(); if(c.getId()!=null){
	 * sql+=" and id=?"; params.add(c.getId()); } if(c.getSex()!=null){ sql =
	 * sql+" and sex=?"; params.add(c.getSex()); } if(c.getName()!=null){
	 * sql+=" and name like ?"; params.add("%"+c.getName()+"%"); }
	 * if(c.getAddr()!=null){ sql+= " and addr like ?";
	 * params.add("%"+c.getAddr()+"%"); } if(c.getTel()!=null){
	 * sql+=" and tel like ?"; params.add("%"+c.getTel()+"%"); }
	 * if(c.getAge()!=null){ sql+=" and age=?" ; params.add(c.getAge()); }
	 * System.err.println(">>>>>>:"+sql); System.err.println(params);
	 * List<Contact> cs = run.query(sql, new
	 * BeanListHandler<Contact>(Contact.class), params.toArray()); for(Contact
	 * cc:cs){ System.err.println(cc); } }
	 */

	
	public void tx188() throws Exception {
		QueryRunner run = new QueryRunner();
		Connection con = conn;
		try {
			String sql = "insert into users values('U008','AA','AA')";
			// 设置事务的开始标记
			con.setAutoCommit(false);
			run.update(con, sql);
			String sql2 = "insert into users values('U009,'AA','AA')";
			run.update(con, sql2);
			// 提交
			con.commit();
		} catch (Exception e) {
			System.err.println("出错了");
			con.rollback();
		} finally {
			con.close();
		}
	}

	
	public void login88() throws Exception {
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		String pwd = sc.nextLine();
		QueryRunner run = new QueryRunner();
		String sql = "select * from users where name='" + name + "' and pwd='" + pwd + "'";
		System.err.println(sql);
		List list = run.query(sql, new MapListHandler());
		if (list.size() > 0) {
			System.err.println("OK");
		} else {
			System.err.println("errorlll");
		}
	}

	public static void main1188(String[] args) throws Exception {
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

	
	public void query111() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "select * from users";
		Map<Object, Map<String, Object>> mm = (Map<Object, Map<String, Object>>) run.query(sql, new KeyedHandler("id"));
		System.err.println(mm);
		Iterator it = mm.keySet().iterator();
		while (it.hasNext()) {
			Map m1 = mm.get(it.next());
			System.err.println(m1.get("id") + "," + m1.get("name") + "," + m1.get("pwd"));
		}

	}

	// 返回Map一行
	
	public void query51() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid where u.id='U001'";
		System.err.println(sql);
		Map<String, Object> mm = run.query(sql, new MapHandler());
		System.err.println(mm);
	}

	
	public void query61() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid";
		System.err.println(sql);
		List<Map<String, Object>> mm = run.query(sql, new MapListHandler());
		System.err.println(mm);
	}

	 
	/**
	 * 查询返回List< Bean>
	 * 
	 * @throws Exception
	 */
	/*
	 *  public void query3() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from users" ; List<User> us =
	 * run.query(sql,new BeanListHandler<User>(User.class)); for(User u:us){
	 * System.err.println(u); }
	 * 
	 * }
	 */

	
	public void insert188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values('U002','李四','888')";
		run.update(sql);
	}

	
	public void insert288() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values(?,?,?)";
		run.update(sql, "U003", "王五", "7777");
	}

	
	public void del188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "delete from users where name=?";
		int len = run.update(sql, "李四");
		System.err.println(len);
	}

	
	public void udpate881() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "update users set name=? where id=?";
		run.update(sql, "赵'七", "U001");
	}

	

}
