package com.holder.dbhelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class InstanceSqlHelper {
	@SuppressWarnings("unchecked")
	private static Map<Class, String> selectSqlContainer = new HashMap<Class, String>();

	@SuppressWarnings("unchecked")
	private static Map<Class, String> insertSqlContainer = new HashMap<Class, String>();

	@SuppressWarnings("unchecked")
	private static Map<Class, String> updateSqlContainer = new HashMap<Class, String>();

	public static final String INSTANCE_NAME = "thisObj";

	private static final String INSERT_SUFFIX = "insert into ";

	private static final String INSERT_SECOND = "(";

	private static final String INSERT_MIDDLE = ") values(";

	private static final String INSERT_PREFFIX = ");";

	/**
	 * 取得获取语句的方法
	 * 
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getSelectSql(Class cla) {
		String result = selectSqlContainer.get(cla);
		if (result == null || result.isEmpty()) {
			result = "select * from " + cla.getSimpleName();
			selectSqlContainer.put(cla, result);
		}
		return result;
	}

	/**
	 * 获取insert语句的方法
	 * 
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getInsertSql(Class cla) {
		String result = insertSqlContainer.get(cla);
		if (result == null || result.isEmpty()) {
			result = createInsertSql(cla);
			insertSqlContainer.put(cla, result);
		}
		return result;
	}

	/**
	 * 初始化一个insert语句的方法
	 * 
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String createInsertSql(Class cla) {
		Field[] fields = cla.getDeclaredFields();
		StringBuilder field_str = new StringBuilder();
		StringBuilder field_value = new StringBuilder();
		for (int i = 0; i < fields.length - 1; i++) {
			field_str.append(fields[i].getName());
			field_str.append(",");
			field_value.append("${");
			field_value.append(INSTANCE_NAME);
			field_value.append(".");
			field_value.append(fields[i].getName());
			field_value.append("},");
		}
		field_str.append(fields[fields.length - 1].getName());
		field_value.append("${");
		field_value.append(INSTANCE_NAME);
		field_value.append(".");
		field_value.append(fields[fields.length - 1].getName());
		field_value.append("}");
		return INSERT_SUFFIX + cla.getSimpleName() + INSERT_SECOND
				+ field_str.toString() + INSERT_MIDDLE + field_value.toString()
				+ INSERT_PREFFIX;
	}

	/**
	 * 
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getUpdateSql(Class cla) {
		String result = updateSqlContainer.get(cla);
		if (result == null || result.isEmpty()) {
			result = createUpdateSql(cla);
			updateSqlContainer.put(cla, result);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static String createUpdateSql(Class cla) {
		Field[] fields = cla.getDeclaredFields();
		StringBuilder tmpstr = new StringBuilder();
		for (int i = 0; i < fields.length - 1; i++) {
			tmpstr.append(fields[i].getName());
			tmpstr.append("=");
			tmpstr.append("${thisObj.");
			tmpstr.append(fields[i].getName());
			tmpstr.append("},");
		}
		tmpstr.append(fields[fields.length - 1].getName());
		tmpstr.append("=");
		tmpstr.append("${thisObj.");
		tmpstr.append(fields[fields.length - 1].getName());
		tmpstr.append("}");
		return "update " + cla.getSimpleName() + " set " + tmpstr.toString()
				+ " where id = ${thisObj.ID}";
	}

}
