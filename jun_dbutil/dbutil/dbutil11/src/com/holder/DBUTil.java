package com.holder;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.holder.dbhelper.InstanceSqlHelper;
import com.holder.dbhelper.MySelect;
import com.holder.dbhelper.ParameterBinder;
import com.holder.dbhelper.PreparedStatementHelpor;
import com.holder.dbhelper.TableTypeHolder;
import com.holder.exception.NeedRollbackException;
import com.holder.log.ConsoleLog;
import com.holder.sqlhelper.PlaceHolderSqlHelper;
import com.holder.typehelper.ClassTypeHolder;
import com.holder.typehelper.MethodHelper;

public class DBUTil {

	private static ThreadLocal<Map<String, Object>> paramHolder = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	/**
	 * 將变量存储到上下文中
	 * 
	 * @param key
	 * @param value
	 */
	public static void setAttribute(String key, Object value) {
		key = key.toUpperCase();
		paramHolder.get().put(key, value);
	}

	/**
	 * 上下文中获取相应的变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object getAttribute(String key) {
		key = key.toUpperCase();
		return paramHolder.get().get(key);
	}

	/**
	 * 向数据库中新增一条记录的方法
	 * 
	 * @param sql
	 */
	public static void update(String sql) {
		PlaceHolderSqlHelper helper = PlaceHolderSqlHelper.findInstance(sql);
		String preparedSql = helper.getPreparedSql();
		final Map<Integer, String> placeWithParamName = helper.getParamBindPlace();
		int result = new PreparedStatementHelpor(preparedSql, null).updateSql(new ParameterBinder() {
			public void bindPreparedStatement(PreparedStatement pstmt) {
				for (Entry<Integer, String> entry : placeWithParamName.entrySet()) {
					String replacekey = entry.getValue();
					Integer type = findTypeByReplaceName(replacekey);
					Object value = findValueByReplaceName(replacekey);
					// Assert.notNull(value, replacekey);
					try {
						invokePstmtWithParams(pstmt, type, entry.getKey(), value);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new NeedRollbackException();
					} catch (Exception e) {
						ConsoleLog.debug("| invokePstmtWithParams | type :" + type + " , " + entry.getKey() + " : "
								+ value);
						throw new NeedRollbackException(e);
					}
				}
			}
		});
		ConsoleLog.debug("execute the sql : " + sql + " , with return " + result);
	}

	/**
	 * 根据传入的类型返回相应的查询列表的方法
	 * 
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getResult(String sql, Class<T> cla) {
		PlaceHolderSqlHelper helper = PlaceHolderSqlHelper.findInstance(sql);
		String preparedSql = helper.getPreparedSql();
		final Map<Integer, String> placeWithParamName = helper.getParamBindPlace();
		List<T> result;
		result = (List<T>) new PreparedStatementHelpor(preparedSql, cla).selectWithType(new ParameterBinder() {
			public void bindPreparedStatement(PreparedStatement pstmt) {
				for (Entry<Integer, String> entry : placeWithParamName.entrySet()) {
					String replacekey = entry.getValue();
					Integer type = findTypeByReplaceName(replacekey);
					Object value = findValueByReplaceName(replacekey);
					// Assert.notNull(value);
					try {
						invokePstmtWithParams(pstmt, type, entry.getKey(), value);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new NeedRollbackException();
					}
				}
			}
		});
		return result;
	}

	public static List<Map<String, Object>> getResult(String sql) {
		PlaceHolderSqlHelper helper = PlaceHolderSqlHelper.findInstance(sql);
		String preparedSql = helper.getPreparedSql();
		final Map<Integer, String> placeWithParamName = helper.getParamBindPlace();
		List<Map<String, Object>> result;
		result = new PreparedStatementHelpor(preparedSql, null).select(new ParameterBinder() {
			public void bindPreparedStatement(PreparedStatement pstmt) {
				for (Entry<Integer, String> entry : placeWithParamName.entrySet()) {
					String replacekey = entry.getValue();
					Integer type = findTypeByReplaceName(replacekey);
					Object value = findValueByReplaceName(replacekey);
					// Assert.notNull(value);
					try {
						invokePstmtWithParams(pstmt, type, entry.getKey(), value);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return result;
	}

	private static Integer findTypeByReplaceName(String key) {
		String classKey = key.substring(0, key.indexOf("."));
		String fieldkey = key.substring(key.indexOf(".") + 1);
		Object instance = getAttribute(classKey);
		TableTypeHolder holder = TableTypeHolder.findTableType(instance.getClass().getSimpleName());
		return holder.getColumnType(fieldkey);
	}

	private static Object findValueByReplaceName(String key) {
		String paramKey = key.substring(0, key.indexOf("."));
		String fieldkey = key.substring(key.indexOf(".") + 1).toUpperCase();
		Object instance = getAttribute(paramKey);
		Method method = ClassTypeHolder.getAllReader(instance.getClass()).get(fieldkey);
		return MethodHelper.invoke(method, instance, null);
	}

	private static void invokePstmtWithParams(PreparedStatement pstmt, Integer type, Integer place, Object value)
			throws NumberFormatException, SQLException {
		if (value == null) {
			pstmt.setObject(place, null);
			return;
		}
		switch (type) {
		case Types.INTEGER:
		case Types.BIGINT:
		case Types.SMALLINT:
			pstmt.setInt(place, Integer.valueOf(String.valueOf(value)));
			break;
		case Types.DATE:
		case Types.TIME:
			if (value instanceof java.sql.Date) {
				pstmt.setDate(place, (Date) value);
			}
			if (value instanceof java.util.Date) {
				pstmt.setDate(place, new Date(((java.util.Date) value).getTime()));
			}
			break;
		case Types.DOUBLE:
			pstmt.setDouble(place, Double.valueOf(value.toString()));
			break;
		case Types.FLOAT:
			pstmt.setFloat(place, Float.valueOf(value.toString()));
			break;
		case Types.NUMERIC:
			pstmt.setLong(place, Long.valueOf(value.toString()));
			break;
		case Types.TIMESTAMP:
			pstmt.setTimestamp(place.intValue(), new Timestamp(((java.util.Date) value).getTime()));
			break;
		case Types.BIT:
			if (value.equals("")) {
				pstmt.setObject(place, null);
				return;
			}
			pstmt.setByte(place, String.valueOf(value).getBytes()[0]);
			break;
		default:
			pstmt.setString(place, value.toString());
		}
	}

	/**
	 * 持久化某个实例的方法
	 * 
	 * @param o
	 */
	public static void save(Object o) {
		setAttribute(InstanceSqlHelper.INSTANCE_NAME, o);
		String sql = InstanceSqlHelper.getInsertSql(o.getClass());
		DBUTil.update(sql);
	}

	public static void save(final List<Object> list) {
		DBContextHolder.beginTransaction();
		Object o = list.get(0);
		String sql = InstanceSqlHelper.getUpdateSql(o.getClass());
		PlaceHolderSqlHelper helper = PlaceHolderSqlHelper.findInstance(sql);
		String preparedSql = helper.getPreparedSql();
		final Map<Integer, String> placeWithParamName = helper.getParamBindPlace();
		new PreparedStatementHelpor(preparedSql, null).updateBatch(new ParameterBinder() {
			public void bindPreparedStatement(PreparedStatement pstmt) {
				for (Object o : list) {
					DBUTil.setAttribute("thisObj", o);
					Iterator<Entry<Integer, String>> localIterator2 = placeWithParamName.entrySet().iterator();
					while (localIterator2.hasNext()) {
						Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) localIterator2.next();
						String replacekey = (String) entry.getValue();
						Integer type = findTypeByReplaceName(replacekey);
						Object value = findValueByReplaceName(replacekey);
						try {
							invokePstmtWithParams(pstmt, type, (Integer) entry.getKey(), value);
							pstmt.addBatch();
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
							throw new NeedRollbackException();
						}
					}
				}
			}
		});
		DBContextHolder.commit();
	}

	/**
	 * 根据id更新某个实例的方法
	 * 
	 * @param o
	 */
	public static void update(Object o) {
		setAttribute(InstanceSqlHelper.INSTANCE_NAME, o);
		String sql = InstanceSqlHelper.getUpdateSql(o.getClass());
		DBUTil.update(sql);
	}

	/**
	 * 删除某条记录的方法
	 * 
	 * @param o
	 */
	public static void delete(Object o) {
		setAttribute(InstanceSqlHelper.INSTANCE_NAME, o);
		String sql = "delete from " + o.getClass().getSimpleName() + " where id = ${thisObj.ID}";
		DBUTil.update(sql);
	}

	/**
	 * 查询出该表中的所有数据的方法
	 * 
	 * @param <T>
	 * @param cla
	 * @return
	 */
	public static <T> List<T> select(Class<T> cla) {
		String sql = InstanceSqlHelper.getSelectSql(cla);
		return getResult(sql, cla);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection conn = DriverManager.getConnection("jdbc:odbc:wenbin", "", "");
		DBContextHolder.setContextConnection(conn);
		MySelect my = new MySelect();
		my.setName("董温彬");
		my.setAge(25);
		DBUTil.setAttribute("myselect", my);
		DBUTil.update("insert into myselect(name,age) values(${myselect.name},${myselect.age})");
		System.out.println(DBUTil
				.getResult("select * from myselect where name = ${myselect.name} and age = ${myselect.age}"));
		DBUTil.update("delete from myselect where age = ${myselect.age}");
		DBContextHolder.commit();
	}

}
