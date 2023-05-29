package com.jun.plugin.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class BizDao {

	public Boolean execute(String sql, Object[] param) {
		Boolean flag = false;
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		try {
			int returnval = runner.update(sql, param);
			System.err.println(returnval);
			flag = returnval > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	public List getRows(String sql, int page, int rows) {
		return BizDao.queryForList(sql, page, rows);
	}

	public Integer getTotal(String sql) {
		Integer total = BizDao.queryForInt(sql);
		return total;
	}

	/**
	 * 查询单行记录
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map queryForMap(String sql, Object[] param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			list = (List<Map<String, Object>>) run.query(sql, new MapListHandler(), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			map = list.get(0);
		} else {
			map = null;
		}
		return map;
	}

	/**
	 * 查询单行记录
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map queryForMap2(String sql, Object[] param) throws Exception {
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		Map<String, Object> map = null;
		try {
			map = (Map<String, Object>) runner.query(sql, new MapHandler(), param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 查询COUNT统计
	 * 
	 * @param sql
	 * @return
	 */
	public static Integer queryForInt(String sql) {
		Integer total = 0;
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			// total = ((Long)run.query(sql, new ScalarHandler(1))).intValue();
			Object result[] = (Object[]) run.query(sql, new ArrayHandler());
			total = ((Long) result[0]).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 查询COUNT统计
	 * 
	 * @param sql
	 * @return
	 */
	public int getCount(String sql) {
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			Long count = run.query(sql, new ScalarHandler<Long>());
			// Long sum = (Long) runner.query(sql,new ScalarHandler());
			return count.intValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询List集合 分页
	 * 
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	public static List queryForList(String sql, int page, int rows) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			if (rows > 0) {
				list = (List<Map<String, Object>>) run.query(sql, new MapListHandler(),
						new Integer[] { (page - 1) * rows, page * rows });
			} else {
				list = (List<Map<String, Object>>) run.query(sql, new MapListHandler());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询List集合
	 * 
	 * @param sql
	 * @return
	 */
	public static List queryForList(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			list = (List<Map<String, Object>>) run.query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询List集合
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public static List queryForList(String sql, Object[] param) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			list = (List<Map<String, Object>>) run.query(sql, new MapListHandler(), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public String queryForStr(String sql, Object[] param) {
		String val = new String();
		try {
			QueryRunner run = new QueryRunner(DataSourceUtil.getDataSource());
			val = (String) run.query(sql, new ScalarHandler(), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return val;
	}

	public void excuteTx(String sql, Object[] params) throws Exception {
		QueryRunner run = new QueryRunner();
		Connection conn = DataSourceUtil.getConn();
		try {
			conn.setAutoCommit(false);
			run.update(conn, sql, params);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void query788() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "select count(*) from contacts";
		Object o = run.query(sql, new ScalarHandler());
		Integer ss = Integer.valueOf(o.toString());
		System.err.println(ss);
	}

	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
	// ####################################################################################################################
//	@Test
//	public void testBeanHandler() throws SQLException {
//		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
//		String sql = "select * from user";
//		User user = (User) runner.query(sql, new BeanHandler(User.class));
//		System.out.println("�û���" + user.getUsername());
//	}
//
//	@Test
//	public void testBeanListHandler() throws SQLException {
//		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
//		String sql = "select * from user";
//		List<User> userList = (List<User>) runner.query(sql, new BeanListHandler(User.class));
//		for (User user : userList) {
//			System.out.println("�û���" + user.getUsername());
//			System.out.println("���룺" + user.getPassword());
//		}
//	}

//	@Test
	public void testArrayHandler() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "select * from user";
		Object[] array = (Object[]) runner.query(sql, new ArrayHandler());
		System.out.println("��� : " + array[0]);
		System.out.println("�û��� : " + array[1]);
	}

//	@Test
	public void testArrayListHandler() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "select * from user";
		List<Object[]> list = (List<Object[]>) runner.query(sql, new ArrayListHandler());
		for (Object[] array : list) {
			System.out.print("��� : " + array[0] + "\t");
			System.out.println("�û��� : " + array[1]);
		}
	}

	public void dropTable(String tableName) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "drop table if exists " + tableName;
		runner.update(sql);
	}

	// ������
	public void createTable(String tableName) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "create table if not exists " + tableName
				+ "(id varchar(40) primary key,curr_time timestamp not null)";
		runner.update(sql);
	}

	// ��ʼ�����
	public void init(String tableName, String id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "insert into " + tableName + "(id) values(?)";
		runner.update(sql, id);
	}

	// ���ID��ѯ��ѡ����Ϣ
	/*
	 * public Vote findVoteById(int id) throws SQLException{ Vote vote = null;
	 * QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource()); String sql =
	 * "select * from vote where id = ?"; vote = (Vote) runner.query(sql,id,new
	 * BeanHandler(Vote.class)); return vote; }
	 */
	// ��ѯ���еĺ�ѡ����Ϣ
	/*
	 * public List<Vote> findAllVote() throws SQLException{ List<Vote> voteList =
	 * new ArrayList<Vote>(); QueryRunner runner = new
	 * QueryRunner(DataSourceUtil.getDataSource()); String sql = "select * from vote";
	 * voteList = (List<Vote>) runner.query(sql,new BeanListHandler(Vote.class));
	 * return voteList; }
	 */

//	@Test
//	public void query111() throws Exception {
//		QueryRunner run = new QueryRunner();
//		String sql = "select * from users";
//		Map<Object, Map<String, Object>> mm = run.query(sql, new KeyedHandler("id"));
//		System.err.println(mm);
//		Iterator it = mm.keySet().iterator();
//		while (it.hasNext()) {
//			Map m1 = mm.get(it.next());
//			System.err.println(m1.get("id") + "," + m1.get("name") + "," + m1.get("pwd"));
//		}
//
//	}

}
