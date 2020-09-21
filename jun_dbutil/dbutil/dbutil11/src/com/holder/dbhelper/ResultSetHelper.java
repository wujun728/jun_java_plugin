package com.holder.dbhelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.holder.log.ConsoleLog;
import com.holder.typehelper.ClassTypeHolder;
import com.holder.typehelper.InstanceCreator;

public abstract class ResultSetHelper {

	public static List<Map<String, Object>> bindResultToMap(ResultSet rs, SelectTypeHolder holder) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			Map<String, Integer> types = holder.getColumnTypes();
			Map<String, Object> _tmp = new HashMap<String, Object>();
			for (String column : types.keySet()) {
				_tmp.put(column, rs.getObject(column));
			}
			result.add(_tmp);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static List<Object> bindResultSetToTarget(ResultSet rs, Class cla, SelectTypeHolder holder)
			throws SQLException {
		List<Object> result = new ArrayList<Object>();
		Map<String, Method> writer = ClassTypeHolder.getAllWriter(cla);
		while (rs.next()) {
			Map<String, Integer> types = holder.getColumnTypes();
			Object instance = InstanceCreator.createInstance(cla);
			for (Entry<String, Integer> entry : types.entrySet()) {
				if (writer.containsKey(entry.getKey().toUpperCase())) {
					Object valueToBind = getResultSetValue(rs, entry.getKey(), entry.getValue());
					writeToInstance(valueToBind, instance, writer.get(entry.getKey().toUpperCase()));
				}
			}
			result.add(instance);
		}
		return result;
	}

	private static void writeToInstance(Object o, Object instance, Method writer) {
		try {
			Object[] params = new Object[1];
			params[0] = o;
			writer.invoke(instance, params);
		} catch (IllegalArgumentException e) {
			ConsoleLog.debug("now invoke [" + instance.getClass().getName() + "." + writer.getName()
					+ "] with parameter [" + o.getClass().getName() + " | " + o + "]");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static Object getResultSetValue(ResultSet rs, String columnName, Integer type) throws SQLException {
		switch (type) {
		case Types.INTEGER:
		case Types.BIGINT:
		case Types.SMALLINT:
			return rs.getInt(columnName);
		case Types.DATE:
			return rs.getDate(columnName);
		case Types.TIME:
			return rs.getTime(columnName);
		case Types.TIMESTAMP:
			return rs.getTimestamp(columnName);
		case Types.DOUBLE:
		case Types.REAL:
			return rs.getDouble(columnName);
		case Types.FLOAT:
		case Types.DECIMAL:
		case Types.NUMERIC:
			return rs.getDouble(columnName);
		default:
			return rs.getString(columnName);
		}
	}
}
