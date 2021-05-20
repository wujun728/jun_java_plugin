package com.xiruibin.db.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import com.xiruibin.DBDriverAutoLoad;
import com.xiruibin.DBInfo;
import com.xiruibin.Log;
import com.xiruibin.Parameters;

public final class DBUtils {

	private Parameters parameters = null;

	private Connection conn = null;

	private Statement stm = null;

	private ResultSet rs = null;
	
	private Map<String, String> tableinfo = new LinkedHashMap<String, String>();

	public DBUtils(Parameters parameters) {
		this.parameters = parameters;
		DBDriverAutoLoad.load();
	}

	public Map<String, LinkedHashMap<String, LinkedHashMap<String, String>>> getDatabaseInfo()
			throws Exception {
		Map<String, LinkedHashMap<String, LinkedHashMap<String, String>>> info = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>();
		conn = DriverManager.getConnection(
				DBInfo.getCurrentDriverUrl(parameters.getHost(),
						parameters.getPort(), parameters.getDatabase()),
				parameters.getUser(), parameters.getPassword());
		DatabaseMetaData dmd = conn.getMetaData();
		String schema = null;
		if (parameters.getSchema() != null) {
			schema = parameters.getSchema().toUpperCase();
		}
		ResultSet dbrs = dmd.getTables(null, schema, null, new String[] { "TABLE" });
		int n = 0;
		while (dbrs.next()) {
			String table_name = dbrs.getString("TABLE_NAME");
			if (!parameters.getTables().isEmpty()) {
				if (!parameters.getTables().contains(table_name)) {
					continue;
				}
			}
//			 if (n>27)
//				 break;
			
			LinkedHashMap<String, LinkedHashMap<String, String>> tablesMap = info
					.get(table_name);
			if (tablesMap == null) {
				tablesMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
			}

			// tablesMap.put(column_name, columnInfo);

			Log.info("==========================="
					+ dbrs.getString("TABLE_NAME")
					+ "===========================");
			ResultSetMetaData rsmd = dbrs.getMetaData();
			String remark = dbrs.getString("REMARKS");
			if (remark != null && !"null".equals(remark)) {
				tableinfo.put(dbrs.getString("TABLE_NAME"), remark);
			} else {
				tableinfo.put(dbrs.getString("TABLE_NAME"), "");
			}
			
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				rsmd.getColumnName(i);
				Log.info(rsmd.getColumnName(i) + ":" + dbrs.getString(i));
			}

			Log.info("----------------------------------------------------------");
			ResultSet rsc = getColumns(table_name);
			ResultSetMetaData rscmd = rsc.getMetaData();
			while (rsc.next()) {
				String column_name = rsc.getString("COLUMN_NAME");
				LinkedHashMap<String, String> columnInfo = tablesMap
						.get(column_name);
				if (columnInfo == null)
					columnInfo = new LinkedHashMap<String, String>();
				try {
					columnInfo.put("column_comment", rsc.getString("REMARKS"));
				} catch (SQLException e) {
					columnInfo.put("column_comment", "");
				}

				String typeName = rsc.getString("TYPE_NAME");
				if ("CHAR".equals(typeName) || "VARCHAR".equals(typeName)) {
					columnInfo
							.put("column_type",
									typeName + "("
											+ rsc.getString("COLUMN_SIZE")
											+ ")");
				} else {
					columnInfo.put("column_type", typeName);
				}

				String def = String.valueOf(rsc.getObject("COLUMN_DEF"));
				if ("null".equals(def)) {
					columnInfo.put("column_default", "");
				} else {
					columnInfo.put("column_default", def);
				}

				String key = rsc.getString("IS_AUTOINCREMENT");
				if ("YES".equals(key)) {
					columnInfo.put("column_si", "√");
				} else {
					columnInfo.put("column_si", "");
				}
				if ("YES".equals(key)) {
					columnInfo.put("column_key", "√");
				} else {
					columnInfo.put("column_key", "");
				}

				if ("YES".equals(rsc.getString("IS_NULLABLE"))) {
					columnInfo.put("is_nullable", "");
				} else {
					columnInfo.put("is_nullable", "Ｘ");
				}

				StringBuilder sb = new StringBuilder();
				for (int j = 1; j <= rscmd.getColumnCount(); j++) {
					sb.append(rscmd.getColumnName(j)).append("[")
							.append(rsc.getObject(j)).append("]").append(" ");
				}
				Log.info(sb.toString());
				tablesMap.put(column_name, columnInfo);
			}
			info.put(table_name, tablesMap);
			Log.info("");
			n++;
		}

		stm = conn.createStatement();

		releaseDBResource();
		return info;
	}
	
	public Map<String, String> getTableInfo() {
		return tableinfo;
	}

	public ResultSet getColumns(String tableName) {
		try {
			DatabaseMetaData db2dmd = conn.getMetaData();
			String schema = null;
			if (parameters.getSchema() != null) {
				schema = parameters.getSchema().toUpperCase();
			}
			return db2dmd.getColumns(null, schema, tableName, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 释放资源
	 * 
	 * @throws SQLException
	 */
	public void releaseDBResource() throws SQLException {
		if (null != rs) {
			rs.close();
		}

		if (null != stm) {
			stm.close();
		}

		if (null != conn) {
			conn.close();
		}
	}

}