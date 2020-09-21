package com.holder.sqlhelper;

import java.util.HashMap;
import java.util.Map;

import com.holder.Assert;
import com.holder.log.ConsoleLog;

/**
 * 用于将带有占位符的sql转化为?格式的sql的类
 * 
 * @author dongwenbin
 * 
 */
public class PlaceHolderSqlHelper {

	private PlaceHolderSqlHelper(String sql) {
		Assert.notNull(sql);
		SqlPaser paser = new SqlPaser(sql);
		this.preparedSql = paser.getPreparedSql();
		this.paramBinder = paser.getPlaceBinder();
	}

	private String preparedSql;

	private Map<Integer, String> paramBinder;

	private static Map<String, PlaceHolderSqlHelper> container = new HashMap<String, PlaceHolderSqlHelper>();

	public static PlaceHolderSqlHelper findInstance(String sql) {
		if (container.containsKey(sql)) {
			return container.get(sql);
		}
		PlaceHolderSqlHelper helper = new PlaceHolderSqlHelper(sql);
		container.put(sql, helper);
		return helper;
	}

	/**
	 * 获取可以用于preparedstatement的sql
	 * 
	 * @return
	 */
	public String getPreparedSql() {
		return this.preparedSql;
	}

	public Map<Integer, String> getParamBindPlace() {
		return this.paramBinder;
	}

	public static void main(String[] args) {
		System.out.println(new PlaceHolderSqlHelper("${dsfs} fdsf ${}")
				.getPreparedSql());
		System.out.println(new PlaceHolderSqlHelper("${dsfs} fdsf ${}")
				.getParamBindPlace());
	}
}

class SqlPaser {

	private static final String prefix = "${";

	private static final String suffix = "}";

	private static final String replace = "?";

	SqlPaser(String sql) {
		this.sql = sql;
		length = 1;
		initPlaceHolderParam(sql);
	}

	private String sql;

	private int length;

	private Map<Integer, String> paramBinder = new HashMap<Integer, String>();

	String getPreparedSql() {
		for (String str : this.paramBinder.values()) {
			ConsoleLog.debug("replace the " + getReplaceString(str));
			sql = sql.replace(getReplaceString(str), replace);
		}
		return sql;
	}

	Map<Integer, String> getPlaceBinder() {
		return paramBinder;
	}

	private void initPlaceHolderParam(String sql) {
		ConsoleLog.debug(" the param : " + sql);
		if (sql.indexOf(suffix) < 0) {
			return;
		}
		int begin = sql.indexOf(prefix) + prefix.length();
		int end = sql.indexOf(suffix);
		paramBinder.put(length++, sql.substring(begin, end));
		sql = sql.substring(sql.indexOf(suffix) + suffix.length());
		initPlaceHolderParam(sql);
	}

	private String getReplaceString(String str) {
		return prefix + str + suffix;
	}
}
