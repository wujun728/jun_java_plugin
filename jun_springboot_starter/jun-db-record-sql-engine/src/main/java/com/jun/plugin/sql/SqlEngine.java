package com.jun.plugin.sql;

//import com.alibaba.fastjson2.JSONObject;
import cn.hutool.json.JSONObject;
import cn.hutool.log.StaticLog;
import com.jun.plugin.sql.engine.DynamicSqlEngine;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SqlEngine {
	
	static DynamicSqlEngine engine = new DynamicSqlEngine();

	public static DynamicSqlEngine getEngine() {
		return engine;
	}



	/**
	 * 1、查询返回List<JSONObject>
	 * 2、新增、修改、删除返回int，成功条数。
	 * 3、连接没有关闭，需要在调用方关闭
	 * @param connection
	 * @param sql
	 * @param jdbcParamValues
	 * @return
	 */
	public static Object executeSql(Connection connection, String sql, Map<String, Object> sqlParam,Boolean closeConn) throws SQLException {
		Object obj = executeSql(connection,sql,sqlParam);
		if(closeConn){
			connection.close();
		}
		return obj;
	}
	public static Object executeSql(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
		SqlMeta sqlMeta = SqlEngine.getEngine().parse(sql, sqlParam);
		return SqlEngine.executeSql(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
	}
	public static Object executeSql(Connection connection, String sql, List<Object> jdbcParamValues)
			throws SQLException {
//		log.debug(JSON.toJSONString(jdbcParamValues));
		PreparedStatement statement = connection.prepareStatement(sql);
		// 参数注入
		for (int i = 1; i <= jdbcParamValues.size(); i++) {
			statement.setObject(i, jdbcParamValues.get(i - 1));
		}
		boolean hasResultSet = statement.execute();

		if (hasResultSet) {
			ResultSet rs = statement.getResultSet();
			int columnCount = rs.getMetaData().getColumnCount();

			List<String> columns = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rs.getMetaData().getColumnLabel(i);
				columns.add(columnName);
			}
			List<JSONObject> list = new ArrayList<>();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				columns.stream().forEach(t -> {
					try {
						Object value = rs.getObject(t);
						jo.put(t, value);
						if(value==null) {
							jo.put(t, "");
						}
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
				});
				list.add(jo);
			}
			return list;
		} else {
			int updateCount = statement.getUpdateCount();
			System.out.println("["+updateCount + "] rows affected");
			return updateCount;
		}

	}


}