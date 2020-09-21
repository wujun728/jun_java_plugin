package com.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.config.PropertiesConfig;
import com.config.PropertiesService;
import com.dto.UserDto;

public class DbBuilder {
	private static DataSource dataSource;
	private static Logger logger = Logger.getLogger(DbBuilder.class);

	private DbBuilder() {
	}

	public static DataSource getDataSource() {
		if (DbBuilder.dataSource == null) {
			try {
				PropertiesConfig config = PropertiesService.getApplicationConfig();
				BasicDataSource dbcpDataSource = new BasicDataSource();
				dbcpDataSource.setUrl(config.getProperty("jdbc.url"));
				dbcpDataSource.setDriverClassName(config.getProperty("jdbc.driver"));
				dbcpDataSource.setUsername(config.getProperty("jdbc.username"));
				dbcpDataSource.setPassword(config.getProperty("jdbc.password"));
				dbcpDataSource.setDefaultAutoCommit(Boolean.parseBoolean(config.getProperty("dbcp.defaultAutoCommit")));
				dbcpDataSource.setMaxActive(Integer.parseInt(config.getProperty("dbcp.maxActive")));
				dbcpDataSource.setMaxIdle(Integer.parseInt(config.getProperty("dbcp.maxIdle")));
				dbcpDataSource.setMaxWait(Integer.parseInt(config.getProperty("dbcp.maxActive")));
				DbBuilder.dataSource = (DataSource) dbcpDataSource;
				logger.info("dbcp数据源初始化成功!");
			} catch (Exception ex) {
				logger.info("dbcp数据源初始化失败:" + ex.getMessage());
			}
		}
		return DbBuilder.dataSource;
	}

	public static Connection getConnection() {
		try {
			DataSource dataSource = DbBuilder.getDataSource();
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static QueryRunner getQueryRunner() {
		return new QueryRunner(DbBuilder.getDataSource());
	}
	
	/**
	 * 得到查询记录的条数
	 * 
	 * @param sql
	 *            必须为select count(*) from t_user的格式
	 * @return
	 */
	public static int getCount(String sql) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			Object value = runner.query(sql, new ScalarHandler<Object>());
			return CommonUtil.objectToInteger(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 得到查询记录的条数
	 * 
	 * @param sql
	 *            必须为select count(*) from t_user的格式
	 * @return
	 */
	public static int getCount(Connection connection, String sql) {
		try {
			QueryRunner runner = new QueryRunner();
			Object value = runner.query(connection, sql, new ScalarHandler<Object>());
			return CommonUtil.objectToInteger(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 得到查询记录的条数
	 * 
	 * @param sql
	 *            必须为select count(*) from t_user的格式
	 * @param params
	 * @return
	 */
	public static int getCount(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			Object value = runner.query(sql, new ScalarHandler<Object>(), params);
			return CommonUtil.objectToInteger(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 得到查询记录的条数
	 * 
	 * @param sql
	 *            必须为select count(*) from t_user的格式
	 * @param params
	 * @return
	 */
	public static int getCount(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			Object value = runner.query(connection, sql, new ScalarHandler<Object>(), params);
			return CommonUtil.objectToInteger(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据传入的sql，查询记录，以数组形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @return
	 */
	public static Object[] getFirstRowArray(String sql) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new ArrayHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以数组形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @return
	 */
	public static Object[] getFirstRowArray(Connection connection, String sql) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new ArrayHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以数组形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Object[] getFirstRowArray(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new ArrayHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以数组形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Object[] getFirstRowArray(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new ArrayHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询返回所有记录，以List数组形式返回
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Object[]> getListArray(String sql) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new ArrayListHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询返回所有记录，以List数组形式返回
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Object[]> getListArray(Connection connection, String sql) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new ArrayListHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询返回所有记录，以List数组形式返回
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Object[]> getListArray(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new ArrayListHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询返回所有记录，以List数组形式返回
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Object[]> getListArray(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new ArrayListHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以Map形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @return
	 */
	public static Map<String, Object> getFirstRowMap(String sql) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new MapHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以Map形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @return
	 */
	public static Map<String, Object> getFirstRowMap(Connection connection, String sql) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new MapHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以Map形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Map<String, Object> getFirstRowMap(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new MapHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql，查询记录，以Map形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Map<String, Object> getFirstRowMap(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new MapHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql查询所有记录，以List Map形式返回
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String sql) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new MapListHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql查询所有记录，以List Map形式返回
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(Connection connection, String sql) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new MapListHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql查询所有记录，以List Map形式返回
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new MapListHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的sql查询所有记录，以List Map形式返回
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new MapListHandler(), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql和对象，查询结果并以对象形式返回
	 * 
	 * @param sql
	 * @param type
	 * @return
	 */
	public static <T> T getBean(String sql, Class<T> type) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new BeanHandler<T>(type));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql和对象，查询结果并以对象形式返回
	 * 
	 * @param sql
	 * @param type
	 * @return
	 */
	public static <T> T getBean(Connection connection, String sql, Class<T> type) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new BeanHandler<T>(type));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql和对象，查询结果并以对象形式返回
	 * 
	 * @param sql
	 * @param type
	 * @param params
	 * @return
	 */
	public static <T> T getBean(String sql, Class<T> type, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new BeanHandler<T>(type), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql和对象，查询结果并以对象形式返回
	 * 
	 * @param sql
	 * @param type
	 * @param params
	 * @return
	 */
	public static <T> T getBean(Connection connection, String sql, Class<T> type, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new BeanHandler<T>(type), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询list对象
	 * 
	 * @param sql
	 * @param type
	 * @return
	 */
	public static <T> List<T> getListBean(String sql, Class<T> type) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new BeanListHandler<T>(type));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询list对象
	 * 
	 * @param sql
	 * @param type
	 * @return
	 */
	public static <T> List<T> getListBean(Connection connection, String sql, Class<T> type) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new BeanListHandler<T>(type));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询list对象
	 * 
	 * @param sql
	 * @param type
	 * @param params
	 * @return
	 */
	public static <T> List<T> getListBean(String sql, Class<T> type, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.query(sql, new BeanListHandler<T>(type), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据sql查询list对象
	 * 
	 * @param sql
	 * @param type
	 * @param params
	 * @return
	 */
	public static <T> List<T> getListBean(Connection connection, String sql, Class<T> type, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new BeanListHandler<T>(type), params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int save(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.update(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 保存操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int save(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.update(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 更新操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.update(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 更新操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.update(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 删除操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int delete(String sql, Object... params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.update(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 删除操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int delete(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.update(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 批量操作，包括批量保存、修改、删除
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int[] batch(String sql, Object[][] params) {
		try {
			QueryRunner runner = DbBuilder.getQueryRunner();
			return runner.batch(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 批量操作，包括批量保存、修改、删除
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int[] batch(Connection connection, String sql, Object[][] params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.batch(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 开启事务
	 */
	public static void beginTransaction(Connection conn) {
		try {
			// 开启事务
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交事务
	 */
	public static void commit(Connection conn) {
		try {
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(DbBuilder.getCount("select count(*) from T_USER"));

			// 返回ArrayHandler结果,第一行结果：Object[]
			System.out.println("返回ArrayHandler结果......");
			Object[] arrayResult = DbBuilder.getFirstRowArray("select * from T_USER where id=2");
			QueryRunner runner = DbBuilder.getQueryRunner();
			for (int i = 0; i < arrayResult.length; i++) {
				System.out.print(arrayResult[i] + "    ");
			}
			System.out.println();

			// 返回ArrayListHandler结果
			System.out.println("返回ArrayListHandler结果.........");
			List<Object[]> arrayListResult = DbBuilder.getListArray("select * from T_USER");
			for (int i = 0; i < arrayListResult.size(); i++) {
				for (int j = 0; j < arrayListResult.get(i).length; j++) {
					System.out.print(arrayListResult.get(i)[j] + "    ");
				}
				System.out.println();
			}
			System.out.println();

			// 返回bean
			UserDto user = DbBuilder.getBean("select * from T_USER where Id=?", UserDto.class, 1);
			System.out.println(user.getUsername());

			// 返回beanlist
			System.out.println("返回BeanList结果......");
			List<UserDto> beanListResult = DbBuilder.getListBean("select * from T_USER", UserDto.class);
			Iterator<UserDto> iter_beanList = beanListResult.iterator();
			while (iter_beanList.hasNext()) {
				System.out.println(iter_beanList.next().getUsername());
			}

			// 返回指定列
			System.out.println("返回ColumnList结果......");
			List<Object> columnResult = runner.query("select * from T_USER", new ColumnListHandler<Object>("username"));
			Iterator<Object> iter = columnResult.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}

			// 返回KeyedHandler结果：Map<Object,Map<String,Object>>：map的key为KeyedHandler指定
			System.out.println("返回KeyedHandler结果.........");
			Map<Object, Map<String, Object>> keyedResult = runner.query("select * from T_USER", new KeyedHandler<Object>(
					"username"));
			System.out.println(keyedResult.get("username"));

			// MapHandler
			System.out.println("返回MapHandler结果.........");
			Map<String, Object> mapResult = DbBuilder.getFirstRowMap("select * from T_USER");
			Iterator<String> iter_mapResult = mapResult.keySet().iterator();
			while (iter_mapResult.hasNext()) {
				System.out.print(mapResult.get(iter_mapResult.next()) + "   ");
			}
			System.out.println();

			// 返回MapListHandler结果
			System.out.println("返回MapListHandler结果.........");
			List<Map<String, Object>> mapListResult = DbBuilder.getListMap("select * from T_USER");
			for (int i = 0; i < mapListResult.size(); i++) {
				Iterator<String> values = mapListResult.get(i).keySet().iterator();
				while (values.hasNext()) {
					System.out.print(mapListResult.get(i).get(values.next()) + "   ");
				}
				System.out.println();
			}
			Connection conn = DbBuilder.getConnection();
			DbBuilder.beginTransaction(conn);
			System.out.println(DbBuilder.save(conn,"insert into t_user(username,password) values(?,?)", "demo", "demo"));
			DbBuilder.commit(conn);
			DbBuilder.close(conn);
			Object increaseId = runner.query("select last_insert_id()", new ScalarHandler<Object>());
			System.out.println(increaseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
