package com.holder.dbhelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.holder.Assert;
import com.holder.DBContextHolder;
import com.holder.log.ConsoleLog;

/**
 * 用于存储一个表的方法
 * 
 * @author dongwenbin
 * 
 */
public class TableTypeHolder {

	private TableTypeHolder(String tableName) {
		Assert.notNull(tableName);
		this.tableName = tableName.toUpperCase(Locale.getDefault());
		this.table_select = getTableSelect();
	}

	private static Map<String, TableTypeHolder> container = new HashMap<String, TableTypeHolder>();

	public static TableTypeHolder findTableType(String tableName) {
		tableName = tableName.toUpperCase();
		if (container.containsKey(tableName)) {
			return container.get(tableName);
		}
		TableTypeHolder holder = new TableTypeHolder(tableName);
		container.put(tableName, holder);
		return holder;
	}

	/**
	 * 通过类型进行匹配的方法
	 * 
	 * @param cla
	 */
	@SuppressWarnings("unchecked")
	public TableTypeHolder(Class cla) {
		Assert.notNull(cla);
		this.tableName = cla.getSimpleName().toUpperCase(Locale.getDefault());
		this.table_select = getTableSelect();
	}

	private String tableName;

	private static final String TABLE_SELECT_PREFIX = "SELECT * FROM ";

	private static final String TABLE_SELEC_SUFFIX = " where 1=2 ";

	private String table_select = null;

	private Map<String, Integer> columnTypeHolder = new HashMap<String, Integer>();

	/**
	 * 用于取得返回一个没有数据的结果集的方法
	 * 
	 * @return
	 */
	private String getTableSelect() {
		if (this.table_select == null) {
			table_select = TABLE_SELECT_PREFIX + this.tableName
					+ TABLE_SELEC_SUFFIX;
		}
		return table_select;
	}

	public Integer getColumnType(String columnName) {
		columnName = columnName.toUpperCase(Locale.getDefault());
		return getColumnTypeWithUpperCase(columnName);
	}

	/**
	 * 使用一个大写的字段名获取相应的字段类型的方法
	 * 
	 * @param columnName
	 * @return
	 */
	private Integer getColumnTypeWithUpperCase(String columnName) {
		if (columnTypeHolder.isEmpty()) {
			initColumnTypeHolder();
		}
		if (!columnTypeHolder.containsKey(columnName)) {
			ConsoleLog.debug("there is none column named " + columnName
					+ " in table " + this.tableName);
		}
		return columnTypeHolder.get(columnName);
	}

	/**
	 * 初始化相应的列表的方法
	 */
	private void initColumnTypeHolder() {
		clearHolder();
		try {
			ConsoleLog
					.debug("now start to init the columnTypeHolder with sql : "
							+ getTableSelect());
			ResultSetMetaData metaData = getMetaDataFormSql(this.table_select);
			int length = metaData.getColumnCount();
			for (int i = 1; i <= length; i++) {
				columnTypeHolder.put(metaData.getColumnName(i).toUpperCase(
						Locale.getDefault()), metaData.getColumnType(i));
			}
			ConsoleLog.debug("init the columnTypeHolder finished! result:"
					+ columnTypeHolder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取相应的元数据的方法
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private ResultSetMetaData getMetaDataFormSql(String sql)
			throws SQLException {
		Connection conn = DBContextHolder.getContextConnection();
		ResultSet rs = conn.createStatement().executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		return metaData;
	}

	/**
	 * 清空缓存
	 */
	public void clearHolder() {
		ConsoleLog.debug("now start to clear the columnTypeHolder!");
		columnTypeHolder.clear();
	}
}
