/*   
 * Project: OSMP
 * FileName: TransactionWrapper.java
 * version: V1.0
 */
package com.osmp.jdbc.define.tool;

import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import com.osmp.jdbc.define.Column;
import com.osmp.jdbc.utils.ReflectUtil;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 上午11:35:50
 */

public class ToolsRowMapper<T extends Object> implements RowMapper<T> {

	private final Class<T> clazz;

	public ToolsRowMapper(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (clazz.isAssignableFrom(Map.class)) {
			return this.mapParse(rs, rowNum);
		}
		if (clazz.isAssignableFrom(String.class)) {
			String val = rs.getString(1);
			return val == null ? null : (T) val;
		}

		if (isNormalType(clazz)) {
			String val = rs.getString(1);
			if (val == null)
				return null;
			try {
				return (T) string2Primitive(clazz, val);
			} catch (NumberFormatException e) {
			}
			return null;
		}

		T obj = null;
		Field[] fields = this.clazz.getDeclaredFields();
		if (null != fields) {
			try {
				obj = this.clazz.newInstance();
			} catch (InstantiationException e) {
				Assert.notNull(e, e.getMessage());
			} catch (IllegalAccessException e) {
				Assert.notNull(e, e.getMessage());
			}

			for (Field f : fields) {
				Column column = f.getAnnotation(Column.class);
				if (null != column) {
					String fieldName = column.name();
					String mapName = column.mapField();
					if (null == mapName || "".equals(mapName)) {
						mapName = f.getName();
					}
					if (null == fieldName || "".equals(fieldName)) {
						fieldName = f.getName();
					}
					Class<?> clz = f.getType();
					Object o = null;
					if (clz == String.class) {
						o = rs.getString(fieldName);
					} else if (clz.isPrimitive() || isNormalType(clz)) {
						try {
							o = string2Primitive(clz, rs.getString(fieldName));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							continue;
						}
					} else if (clz == Date.class) {
						o = rs.getTimestamp(fieldName);
					} else {
						o = rs.getObject(fieldName);
					}

					ReflectUtil.setFieldValue(obj, mapName, o);
				}
			}
		}

		return obj;
	}

	private boolean isNormalType(Class cls) {
		return cls == Byte.class || cls == Short.class || cls == Integer.class || cls == Long.class
				|| cls == Float.class || cls == Double.class || cls == Character.class || cls == Boolean.class;
	}

	private Object string2Primitive(Class cls, String value) {
		if (cls == Byte.class || cls == byte.class) {
			return Byte.valueOf(value);
		} else if (cls == Character.class || cls == char.class) {
			return value.toCharArray()[0];
		} else if (cls == Short.class || cls == short.class) {
			return Short.valueOf(value);
		} else if (cls == Integer.class || cls == int.class) {
			return Integer.valueOf(value);
		} else if (cls == Long.class || cls == long.class) {
			return Long.valueOf(value);
		} else if (cls == Float.class || cls == float.class) {
			return Float.valueOf(value);
		} else if (cls == Double.class || cls == double.class) {
			return Double.valueOf(value);
		} else if (cls == Boolean.class || cls == boolean.class) {
			return Boolean.valueOf(value);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private T mapParse(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsm = rs.getMetaData();
		int colNum = rsm.getColumnCount();
		String[] colName = new String[colNum]; // 字段名
		String[] colLabel = new String[colNum]; // 别名
		for (int i = 1; i <= colNum; i++) {
			colName[i - 1] = rsm.getColumnName(i);
			colLabel[i - 1] = rsm.getColumnLabel(i);
		}
		Map<String, Object> one = new HashMap<String, Object>();
		for (int i = 1; i <= colNum; i++) {
			Object dt = rs.getObject(i);
			if (dt instanceof Clob) {
				Clob clob = (Clob) dt;
				one.put(colLabel[i - 1], clob.getSubString(1, (int) clob.length()));
			} else {
				one.put(colLabel[i - 1], dt);
			}
		}
		return (T) one;
	}

}
