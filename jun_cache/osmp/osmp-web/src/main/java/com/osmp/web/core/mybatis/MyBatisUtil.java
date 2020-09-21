/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实体类反射工具类
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-27 下午02:44:44
 */
public class MyBatisUtil {

	/**
	 * 获取POJO对应的表名 需要POJO中的属性定义@Table(name)
	 * 
	 * @return
	 */
	public static String tablename(Object o) {
		Table table = o.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.name();
		} else {
			// TODO 待实现异常处理
			return "";
		}
	}

	/**
	 * 获取POJO中的主键字段名 需要定义@Id
	 * 
	 * @return
	 */
	public static String id(Object o) {
		for (Field field : o.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				return field.getName();
			}
		}
		throw new RuntimeException("没有定义 POJO @Id");
	}

	/**
	 * 用于存放INSERT的列信息
	 */
	private static Map<Class<?>, List<String>> columnMap = new HashMap<Class<?>, List<String>>();

	/**
	 * 用于新增时属性与字段映射缓存
	 */
	public static Map<Class<?>, Map<String, String>> insertColumn = new HashMap<Class<?>, Map<String, String>>();

	/**
	 * 用于存放UPDATE时的字段信息
	 */
	private static Map<Class<?>, List<String>> columnMapUpdate = new HashMap<Class<?>, List<String>>();

	private static boolean isNull(String fieldname, Object o) {
		try {
			Field field = o.getClass().getDeclaredField(fieldname);
			return isNull(field, o);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static boolean isNull(Field field, Object o) {
		try {
			field.setAccessible(true);
			return field.get(o) == null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 用于计算类定义 需要POJO中的属性定义@Column(name)
	 * 
	 * @param includeId
	 *            是否包含 ID在内
	 */
	public static void caculationColumnList(Object o, boolean includeId) {
		if (includeId) {
			if (columnMap.containsKey(o.getClass())) {
				return;
			}
		} else {
			if (columnMapUpdate.containsKey(o.getClass())) {
				return;
			}
		}

		Field[] fields = o.getClass().getDeclaredFields();
		List<String> columnList = new ArrayList<String>(fields.length);
		for (Field field : fields) {
			if (includeId) {
				if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class)) {
					if (field.isAnnotationPresent(Column.class)) {
						Column col = field.getAnnotation(Column.class);
						String colName = col.name();
						if (null != colName && colName.length() > 0) {
							columnList.add(colName);
						} else {
							columnList.add(field.getName());
						}
					} else {
						columnList.add(field.getName());
					}
				}
			} else {
				if (field.isAnnotationPresent(Column.class)) {
					Column col = field.getAnnotation(Column.class);
					String colName = col.name();
					if (null != colName && colName.length() > 0) {
						columnList.add(colName);
					} else {
						columnList.add(field.getName());
					}
				}
			}
		}
		if (includeId) {
			columnMap.put(o.getClass(), columnList);
		} else {
			columnMapUpdate.put(o.getClass(), columnList);
		}
	}

	/**
	 * 类属性和字段映射
	 * 
	 * @param o
	 */
	public static void createPropertyColmn(Object o) {
		Map<String, String> map = insertColumn.get(o.getClass());
		if (null != map) {
			return;
		}
		Map<String, String> tempMap = new HashMap<String, String>();
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class)) {
				String columnName = field.getName();
				if (field.isAnnotationPresent(Column.class)) {
					Column col = field.getAnnotation(Column.class);
					String colName = col.name();
					if (null != colName && colName.length() > 0) {
						columnName = colName;
					}
				}
				tempMap.put(columnName, field.getName());
			}
		}
		insertColumn.put(o.getClass(), tempMap);
	}

	/**
	 * 用于获取查询时返回的字段，根据实体类Colmn和Id注解而来
	 * 如果实体类注解Column时定义了数据库对应的字段columnDefinition则使用此作为数据库字段 否则以实体类属性名作为数据库字对应字段
	 * 
	 * @return
	 */
	public static String returnSelectColumnsName(Object o) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = o.getClass().getDeclaredFields();
		int i = 0;
		for (Field field : fields) {
			if (i++ != 0) {
				sb.append(',');
			}
			if (field.isAnnotationPresent(Id.class)) {
				sb.append(field.getName());
			} else if (field.isAnnotationPresent(Column.class)) {
				Column col = field.getAnnotation(Column.class);
				String columnName = col.name();
				if (null != columnName && columnName.length() > 0) {
					sb.append(columnName);
				} else {
					sb.append(field.getName());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 用于获取Insert的字段累加
	 * 
	 * @return
	 */
	public static String returnInsertColumnsName(Object o) {
		StringBuilder sb = new StringBuilder();
		List<String> list = columnMap.get(o.getClass());
		Map<String, String> map = insertColumn.get(o.getClass());
		int i = 0;
		for (String column : list) {
			String temp = map.get(column);
			if (null != temp && temp.length() > 0) {
				if (isNull(temp, o)) {
					continue;
				}
			}

			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(column);
		}
		return sb.toString();
	}

	/**
	 * 用于获取Insert的字段映射累加
	 * 
	 * @return
	 */
	public static String returnInsertColumnsDefine(Object o) {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = insertColumn.get(o.getClass());
		List<String> list = columnMap.get(o.getClass());
		int i = 0;
		for (String column : list) {
			String temp = map.get(column);
			if (null != temp && temp.length() > 0) {
				if (isNull(temp, o)) {
					continue;
				}
			}

			if (i++ != 0) {
				sb.append(',');
			}
			sb.append("#{").append(temp).append('}');
		}
		return sb.toString();
	}

	/**
	 * 用于获取Update Set的字段累加
	 * 
	 * @return
	 */
	public static String returnUpdateSet(Object o) {
		StringBuilder sb = new StringBuilder();
		List<String> list = columnMapUpdate.get(o.getClass());
		Map<String, String> map = insertColumn.get(o.getClass());
		int i = 0;
		for (String column : list) {
			String temp = map.get(column);
			if (null != temp && temp.length() > 0) {
				if (isNull(temp, o)) {
					continue;
				}
			}

			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(column).append("=#{").append(temp).append('}');
		}
		return sb.toString();
	}

	/**
	 * 获取用于WHERE的 有值字段表
	 * 
	 * @return
	 */
	public static List<WhereColumn> returnWhereColumnsName(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<WhereColumn> columnList = new ArrayList<WhereColumn>(fields.length);

		for (Field field : fields) {
			if ((field.isAnnotationPresent(Column.class) && !isNull(field, obj))
					|| (field.isAnnotationPresent(Id.class) && !isNull(field, obj))) {
				String columnName = field.getName();
				if (field.isAnnotationPresent(Column.class)) {
					Column col = field.getAnnotation(Column.class);
					String colName = col.name();
					if (null != colName && colName.length() > 0) {
						columnName = colName;
					}
				}
				columnList.add(new WhereColumn(columnName, field.getGenericType().equals(String.class)));
			}
		}
		return columnList;
	}

	/**
	 * Where条件信息
	 * 
	 */
	public static class WhereColumn {
		public String name;
		public boolean isString;

		public WhereColumn(String name, boolean isString) {
			this.name = name;
			this.isString = isString;
		}
	}

}
