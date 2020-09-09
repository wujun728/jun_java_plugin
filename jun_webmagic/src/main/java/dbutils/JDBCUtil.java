package dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
 
import com.alibaba.druid.pool.DruidDataSource;
 
/**
 * 通用数据库工具类,基于Druid连接池实现
 * 包含以下功能
 * 1.获取连接
 * 2.关闭资源
 * 3.执行通用的更新操作
 * 4.执行通用的查询列表操作
 * 5.执行通用的查询单条记录操作
 * @author Luo
 *
 */
 
public class JDBCUtil {
	
	
	//声明druid连接池对象
	private static DruidDataSource pool;
	
	/**数据库 链接URL地址**/
	private static String url;
	/**账号**/
	private static String username;
	/**密码**/
	private static String password;
	/**初始连接数**/
	private static int initialSize;
	/**最大活动连接数**/
	private static int maxActive;
	/**最小闲置连接数**/
	private static int minIdle;
	/**连接耗尽时最大等待获取连接时间**/
	private static long maxWait;
	
	private static String fileName = "/jdbc.properties";
 
	static {
		init();
	}
	
	/**
	 * 加载属性文件并读取属性文件中的内容将其设置给连接信息
	 * @param propName
	 */
	private static void loadProp(String propName) {
		fileName = propName;
		try {
			//属性文件位于src根目录时,加"/"则不要使用ClassLoader,如果使用ClassLoader则无需"/"
			InputStream is = JDBCUtil.class.getResourceAsStream(fileName);
			Properties p = new Properties();
			p.load(is);
			
			
			url = p.getProperty("jdbc.url");
			username = p.getProperty("jdbc.username");
			password = p.getProperty("jdbc.password");
			
			initialSize = Integer.parseInt(p.getProperty("initialSize"));
			maxActive = Integer.parseInt(p.getProperty("maxActive"));
			maxWait = Integer.parseInt(p.getProperty("maxWait"));
			minIdle = Integer.parseInt(p.getProperty("minIdle"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void init() {
		pool = new DruidDataSource();
		//加载属性文件,初始化配置
		loadProp(fileName);
		pool.setUrl(url);
		pool.setUsername(username);
		pool.setPassword(password);
		
		//设置连接池中初始连接数
		pool.setInitialSize(initialSize);
		//设置最大连接数
		pool.setMaxActive(maxActive);
		//设置最小的闲置链接数
		pool.setMinIdle(minIdle);
		//设置最大的等待时间(等待获取链接的时间)
		pool.setMaxWait(maxWait);
	}
	
	/**
	 * 链接获取
	 * @return
	 */
	public static Connection getConn() {
		try {
			//如果连接池为空或者被异常关闭,则重新初始化一个
			if(pool == null || pool.isClosed()) {
				init();
			}
			return pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
 
	/**
	 * 资源关闭
	 * 
	 * @param stmt
	 * @param conn
	 */
	public static void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * 封装通用的更新操作，对所有更新(INSERT,UPDATE，DELETE)有关的操作都能通过该方法实现
	 * 
	 * @param sql
	 * @return
	 * 
	 */
	public static boolean exeUpdate(Connection conn,String sql, Object... obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, null);
		}
		return false;
	}
 
	/**
	 * 技术参数: 泛型，集合框架，反射，JDBC 封装通用查询多条及操作
	 * 
	 * @param t
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryList(Class<T> t, String sql, Object... params) {
		List<T> list = new ArrayList<>();
		T obj = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			// 获取插叙结果集中的元数据(获取列类型，数量以及长度等信息)
			ResultSetMetaData rsmd = rs.getMetaData();
			// 声明一个map集合，用于临时存储查询到的一条数据（key：列名；value：列值）
			Map<String, Object> map = new HashMap<>();
			// 遍历结果集
			while (rs.next()) {
				// 防止缓存上一条数据
				map.clear();
				// 遍历所有的列
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					// 获取列名
					String cname = rsmd.getColumnLabel(i + 1);
					//获取列类型的int表示形式，以及列类型名称
//					System.out.println("列类型:"+rsmd.getColumnType(i + 1)+"----"+rsmd.getColumnTypeName(i+1));
					// 获取列值
					Object value = rs.getObject(cname);
					// 将列明与列值存储到map中
					map.put(cname, value);
				}
				// 利用反射将map中的数据注入到Java对象中，并将对象存入集合
				if (!map.isEmpty()) {
					// 获取map集合键集(列名集合)
					Set<String> columnNames = map.keySet();
					// 创建对象
					obj = t.newInstance();//new Student() //java.lang.Object
					for (String column : columnNames) {
						// 根据键获取值
						Object value = map.get(column);
						//当数据对象不为空时，才注入数据到属性中
						if(Objects.nonNull(value)){	
							// 获取属性对象
							Field f = t.getDeclaredField(column);
							// 设置属性为可访问状态
							f.setAccessible(true);
							// 为属性设置
							f.set(obj, value);
						}
					}
					list.add(obj);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return list;
	}
 
	/**
	 * 封装查询单个对象的方法
	 * 
	 * @param t
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryOne(Class<T> t, String sql, Object... params) {
		T obj = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			//ORM操作（对象关系映射）
			if (rs.next()) {
				// 创建一个指定类型的实例对象(必须包含默认构造器)
				obj = t.newInstance();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					//获取指定列的列名称
					String cname = rsmd.getColumnLabel(i + 1);
					//获取列值
					Object value = rs.getObject(cname);
					if(Objects.nonNull(value)){						
						//根据列名称获取Java类的属性名(要求表中的列名称必须与类中的属性名保持一致)
						Field field = t.getDeclaredField(cname);
						//将字段设置为可访问状态
						field.setAccessible(true);
						//为字段设置属性值
						field.set(obj, value);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
 