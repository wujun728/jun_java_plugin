

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
//import org.junit.Test;
import org.junit.Test;

import com.jun.plugin.datasource.DataSourceUtil;
import com.jun.plugin.dbutils.dbutil.DBUtil;
import com.jun.plugin.dbutils.test.JdbcUtil;

public class DBUtilTest6 { 
	
	private DataSource ds = null;
	// private static QueryRunner run =null;
	public static QueryRunner run = null;
	private static Connection conn = null;
	static {
		run = new QueryRunner(DataSourceUtil.getDataSource());
		conn = DataSourceUtil.getConn();
		run = run;
	}
	
	@Test
	public void TestDBTemplate() {
		Object[] params = { "22" };
		List<Map<String, Object>> list = new DBUtil().find(" select name,content,id from test where name= ? ", params);
		System.out.println(list.size());
	}
	

	@Test
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
	@Test
	public void query5() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid where u.id='U001'";
		System.err.println(sql);
		Map<String, Object> mm = run.query(sql, new MapHandler());
		System.err.println(mm);
	}

	@Test
	public void query6() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid";
		System.err.println(sql);
		List<Map<String, Object>> mm = run.query(sql, new MapListHandler());
		System.err.println(mm);
	}

	@Test
	public void query7() throws Exception {
		// DBUtil run = new DBUtil();
		String sql = "select count(*) from contacts";
		Object o = run.query(sql, new ScalarHandler());
		Integer ss = Integer.valueOf(o.toString());
		System.err.println(ss);
	}



	// 封装成唯一的一个bean
	// @Test
	// public void query2() throws Exception {
	// DBUtil run = new DBUtil();
	// String sql = "select * from users where id=?";
	// User user = run.query(sql, new BeanHandler<User>(User.class), "U003");
	// System.err.println(user);
	// }

	/**
	 * 查询返回List< Bean>
	 * 
	 * @throws Exception
	 */
	// @Test
	// public void query3() throws Exception {
	// DBUtil run = new DBUtil();
	// String sql = "select * from users";
	// List<User> us = run.query(sql, new BeanListHandler<User>(User.class));
	// for (User u : us) {
	// System.err.println(u);
	// }
	//
	// }

	@Test
	public void insert1() throws Exception {
		String sql = "insert into users values('U002','李四','888')";
		run.update(sql);
	}

	// 接收?
	@Test
	public void insert2() throws Exception {
		String sql = "insert into users values(?,?,?)";
		run.update(sql, "U003", "王五", "7777");
	}

	// 删除
	@Test
	public void del1() throws Exception {
		String sql = "delete from users where name=?";
		int len = run.update(sql, "李四");
		System.err.println(len);
	}

	@Test
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

 

 

	@Test
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

	@Test
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

	@Test
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
	@Test
	public void query5188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid where u.id='U001'";
		System.err.println(sql);
		Map<String, Object> mm = run.query(sql, new MapHandler());
		System.err.println(mm);
	}

	@Test
	public void query688() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid";
		System.err.println(sql);
		List<Map<String, Object>> mm = run.query(sql, new MapListHandler());
		System.err.println(mm);
	}

	@Test
	public void query788() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "select count(*) from contacts";
		Object o = run.query(sql, new ScalarHandler());
		Integer ss = Integer.valueOf(o.toString());
		System.err.println(ss);
	}
	/*
	 * @Test public void query8() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql =
	 * "select id as cid,name as cname,tel as ctel,sex from contacts";
	 * List<Contact> cs = run.query(sql,new
	 * BeanListHandler<Contact>(Contact.class)); System.err.println(cs); }
	 */

	/*
	 * @Test public void query9() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from contacts"; List<Contact> cs =
	 * run.query(sql, new ResultSetHandler<List<Contact>>(){
	 * 
	 * @Override public List<Contact> handle(ResultSet rs) throws SQLException {
	 * List<Contact> list = new ArrayList<Contact>(); while(rs.next()){ Contact
	 * c = new Contact(); c.setCid(rs.getString("name"));
	 * c.setCname(rs.getString("id")); c.setCtel(rs.getString("tel"));
	 * list.add(c); } return list; } }); System.err.println(">>>:"+cs); }
	 */

	// 封装成唯一的一个bean
	/*
	 * @Test public void query2() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from users where id=?"; User user =
	 * run.query(sql,new BeanHandler<User>(User.class),"U003");
	 * System.err.println(user); }
	 */
	/**
	 * 查询返回List< Bean>
	 * 
	 * @throws Exception
	 */
	/*
	 * @Test public void query3() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from users" ; List<User> us =
	 * run.query(sql,new BeanListHandler<User>(User.class)); for(User u:us){
	 * System.err.println(u); }
	 * 
	 * }
	 */

	@Test
	public void insert11() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values('U002','李四','888')";
		run.update(sql);
	}

	// 接收?
	@Test
	public void insert21() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values(?,?,?)";
		run.update(sql, "U003", "王五", "7777");
	}

	// 删除
	@Test
	public void del11() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "delete from users where name=?";
		int len = run.update(sql, "李四");
		System.err.println(len);
	}

	@Test
	public void udpate11() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "update users set name=? where id=?";
		run.update(sql, "赵'七", "U001");
	}

	// 接收一个ds
	// private DataSource ds;
	/*
	 * public QueryRunner() { } public QueryRunner(DataSource ds) { this.ds =
	 * ds; }
	 */
 
	// @Test
	/*
	 * public void myutils2(){ QueryRunner run = new QueryRunner(); String sql =
	 * "select * from contacts"; List<Contact> us =
	 * run.queryForBean(sql,Contact.class); for(Contact u:us){
	 * System.err.println(u); }
	 * 
	 * }
	 */
	/*
	 * @Test public void myutils3(){ QueryRunner run = new QueryRunner(); String
	 * sql = "select * from users" ; List<User> cs = run.query(sql,new
	 * MyBeanListHandler<User>(User.class)); for(User c:cs){
	 * System.err.println(">>:"+c); } }
	 */

	// 不确定条件的查询
	/*
	 * @Test public void query1() throws Exception{ QueryRunner run = new
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

	@Test
	public void tx188() throws Exception {
		QueryRunner run = new QueryRunner();
		Connection con = JdbcUtil.getConnection();
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

	@Test
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

	@Test
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
	@Test
	public void query51() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid where u.id='U001'";
		System.err.println(sql);
		Map<String, Object> mm = run.query(sql, new MapHandler());
		System.err.println(mm);
	}

	@Test
	public void query61() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "SELECT u.name as uname,c.name as cname" + " FROM users u INNER JOIN contacts c ON u.id=c.uid";
		System.err.println(sql);
		List<Map<String, Object>> mm = run.query(sql, new MapListHandler());
		System.err.println(mm);
	}

	/*
	 * @Test public void query7() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select count(*) from contacts"; Object o =
	 * run.query(sql,new ScalarHandler()); Integer ss =
	 * Integer.valueOf(o.toString()); System.err.println(ss); }
	 * 
	 * @Test public void query8() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql =
	 * "select id as cid,name as cname,tel as ctel,sex from contacts";
	 * List<Contact> cs = run.query(sql,new
	 * BeanListHandler<Contact>(Contact.class)); System.err.println(cs); }
	 */

	/*
	 * @Test public void query9() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from contacts"; List<Contact> cs =
	 * run.query(sql, new ResultSetHandler<List<Contact>>(){
	 * 
	 * @Override public List<Contact> handle(ResultSet rs) throws SQLException {
	 * List<Contact> list = new ArrayList<Contact>(); while(rs.next()){ Contact
	 * c = new Contact(); c.setCid(rs.getString("name"));
	 * c.setCname(rs.getString("id")); c.setCtel(rs.getString("tel"));
	 * list.add(c); } return list; } }); System.err.println(">>>:"+cs); }
	 */

	// 封装成唯一的一个bean
	/*
	 * @Test public void query2() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from users where id=?"; User user =
	 * run.query(sql,new BeanHandler<User>(User.class),"U003");
	 * System.err.println(user); }
	 */
	/**
	 * 查询返回List< Bean>
	 * 
	 * @throws Exception
	 */
	/*
	 * @Test public void query3() throws Exception{ QueryRunner run = new
	 * QueryRunner(); String sql = "select * from users" ; List<User> us =
	 * run.query(sql,new BeanListHandler<User>(User.class)); for(User u:us){
	 * System.err.println(u); }
	 * 
	 * }
	 */

	@Test
	public void insert188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values('U002','李四','888')";
		run.update(sql);
	}

	// 接收?
	@Test
	public void insert288() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "insert into users values(?,?,?)";
		run.update(sql, "U003", "王五", "7777");
	}

	// 删除
	@Test
	public void del188() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "delete from users where name=?";
		int len = run.update(sql, "李四");
		System.err.println(len);
	}

	@Test
	public void udpate881() throws Exception {
		QueryRunner run = new QueryRunner();
		String sql = "update users set name=? where id=?";
		run.update(sql, "赵'七", "U001");
	}

}
