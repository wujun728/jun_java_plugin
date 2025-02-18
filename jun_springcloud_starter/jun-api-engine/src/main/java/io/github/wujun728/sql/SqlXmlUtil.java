//package io.github.wujun728.sql;
//
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.json.JSONObject;
//import io.github.wujun728.sql.engine.DynamicSqlEngine;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Deprecated
//public class SqlXmlUtil {
//
//	static DynamicSqlEngine engine = new DynamicSqlEngine();
//
//	public static DynamicSqlEngine getEngine() {
//		return engine;
//	}
//
//
//
//	/**
//	 * 1、查询返回List<JSONObject>
//	 * 2、新增、修改、删除返回int，成功条数。
//	 * 3、连接没有关闭，需要在调用方关闭
//	 * @param connection
//	 * @param sql
//	 * @return
//	 */
//	public static Object executeSql(Connection connection, String sql, Map<String, Object> sqlParam,Boolean closeConn) throws SQLException {
//		Object obj = executeSql(connection,sql,sqlParam);
//		if(closeConn){
//			connection.close();
//		}
//		return obj;
//	}
//	public static Object executeSql(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
//		SqlMeta sqlMeta = SqlXmlUtil.getEngine().parse(sql, sqlParam);
//		return SqlXmlUtil.executeSql(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
//	}
//	public static Object executeSql(Connection connection, String sql, List<Object> jdbcParamValues)
//			throws SQLException {
////		log.debug(JSON.toJSONString(jdbcParamValues));
//		try {
//			System.out.println("execute sql is "+sql);
//			PreparedStatement statement = connection.prepareStatement(sql);
//			// 参数注入
//			for (int i = 1; i <= jdbcParamValues.size(); i++) {
//				statement.setObject(i, jdbcParamValues.get(i - 1));
//			}
//			boolean hasResultSet = statement.execute();
//
//			if (hasResultSet) {
//				ResultSet rs = statement.getResultSet();
//				int columnCount = rs.getMetaData().getColumnCount();
//
//				List<String> columns = new ArrayList<>();
//				for (int i = 1; i <= columnCount; i++) {
//					String columnName = rs.getMetaData().getColumnLabel(i);
//					columns.add(columnName);
//				}
//				List<JSONObject> list = new ArrayList<>();
//				while (rs.next()) {
//					JSONObject jo = new JSONObject();
//					columns.stream().forEach(t -> {
//						try {
//							Object value = rs.getObject(t);
//							jo.put(t, value);
//							if(value==null) {
//								jo.put(t, "");
//							}
//						} catch (SQLException throwables) {
//							throwables.printStackTrace();
//						}
//					});
//					list.add(jo);
//				}
//				return list;
//			} else {
//				int updateCount = statement.getUpdateCount();
//				System.out.println("["+updateCount + "] rows affected");
//				return updateCount;
//			}
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			connection.close();
//		}
//	}
//
//	public static int update(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
//		SqlMeta sqlMeta = SqlXmlUtil.getEngine().parse(sql, sqlParam);
//		return SqlXmlUtil.update(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
//	}
//	public static int update(Connection connection, String sql, List<Object> jdbcParamValues) throws SQLException {
////		log.debug(JSON.toJSONString(jdbcParamValues));
//		try {
//			System.out.println("execute sql is "+sql);
//			PreparedStatement statement = connection.prepareStatement(sql);
//			// 参数注入
//			for (int i = 1; i <= jdbcParamValues.size(); i++) {
//				statement.setObject(i, jdbcParamValues.get(i - 1));
//			}
//			boolean hasResultSet = statement.execute();
//
//			if (hasResultSet) {
//				throw new RuntimeException("查询脚本调用query方法");
//			} else {
//				int updateCount = statement.getUpdateCount();
//				System.out.println("["+updateCount + "] rows affected");
//				return updateCount;
//			}
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} catch (RuntimeException e) {
//			throw new RuntimeException(e);
//		} finally {
//			connection.close();
//		}
//	}
//
//	public static Long count(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
//		SqlMeta sqlMeta = SqlXmlUtil.getEngine().parse(sql, sqlParam);
//		List<Map<String,Object>> datas =  SqlXmlUtil.query(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
//		if(datas.size()==1){
//			try {
//				Map<String,Object> data = datas.get(0);
//				for(String key: data.keySet()){
//					Object val = data.get(key);
//					return (Long) val;
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("count脚本执行返回有误");
//			}
//		}
//		if(datas.size()>1){
//			throw new RuntimeException("count脚本执行返回多条");
//		}
//		return 0L;
//	}
//	public static List<Map<String,Object>> query(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
//		SqlMeta sqlMeta = SqlXmlUtil.getEngine().parse(sql, sqlParam);
//		return SqlXmlUtil.query(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
//	}
//	public static List<Map<String,Object>> query(Connection connection, String sql, List<Object> jdbcParamValues)
//			throws SQLException {
////		log.debug(JSON.toJSONString(jdbcParamValues));
//		System.out.println("execute sql is "+sql);
//		PreparedStatement statement = connection.prepareStatement(sql);
//		// 参数注入
//		for (int i = 1; i <= jdbcParamValues.size(); i++) {
//			statement.setObject(i, jdbcParamValues.get(i - 1));
//		}
//		boolean hasResultSet = statement.execute();
//
//		if (hasResultSet) {
//			ResultSet rs = statement.getResultSet();
//			int columnCount = rs.getMetaData().getColumnCount();
//
//			List<String> columns = new ArrayList<>();
//			for (int i = 1; i <= columnCount; i++) {
//				String columnName = rs.getMetaData().getColumnLabel(i);
//				columns.add(columnName);
//			}
//			List<Map<String,Object>> list = new ArrayList<>();
//			while (rs.next()) {
//				//JSONObject jo = new JSONObject();
//				Map<String,Object> jo = new HashMap<>();
//				columns.stream().forEach(t -> {
//					try {
//						Object value = rs.getObject(t);
//						jo.put(t, value);
//						if(value==null) {
//							jo.put(t, null);
//						}
//					} catch (SQLException throwables) {
//						throwables.printStackTrace();
//					}
//				});
//				list.add(jo);
//			}
//			connection.close();
//			return list;
//		} else {
//			connection.close();
//			throw new RuntimeException("修改脚本调用update方法");
//		}
//	}
//
//
//
//
//}