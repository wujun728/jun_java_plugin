package com.dcf.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.eos.common.connection.ConnectionHelper;
import com.eos.foundation.data.DataObjectUtil;
import com.eos.system.utility.StringUtil;
import commonj.sdo.DataObject;

public class TableManager {
	private static final String CATALOG = null;
	
	/**
	 * 获取当前数据库所有表名
	 * 
	 * @param
	 * @date:2015-12-31
	 */
	public static String[] getTableNameByCon() throws Exception {
		List<String> tables = new ArrayList<String>();
		Connection conn = DcfUtil.getConnection();
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			String schema = metadata.getUserName();
			ResultSet resultset = metadata.getTables(null, schema, null, new String[] { "TABLE" });
			while (resultset.next()) {
				String tableName = resultset.getString("TABLE_NAME");
				resultset.getString("REMARKS");
				tables.add(tableName);
			}

			String[] results = tables.toArray(new String[tables.size()]);
			Arrays.sort(results);
			return results;
		} finally {
			DcfUtil.closeSession(null, conn);
		}
	}
	
	/**
	 * 获取当前数据库所有表名
	 * 
	 * @param
	 * @date:2015-12-31
	 */
	public static String[] getTableNameByConByDataSource(DataSource ds) throws Exception {
		List<String> tables = new ArrayList<String>();
		Connection conn = ds.getConnection();
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			String schema = metadata.getUserName();
			ResultSet resultset = metadata.getTables(null, schema, null, new String[] { "TABLE" });
			while (resultset.next()) {
				String tableName = resultset.getString("TABLE_NAME");
				resultset.getString("REMARKS");
				tables.add(tableName);
			}

			String[] results = tables.toArray(new String[tables.size()]);
			Arrays.sort(results);
			return results;
		} finally {
			DcfUtil.closeSession(null, conn);
		}
	}
	
	/**
	 * 获取当前数据库所有表名
	 * 
	 * @param
	 * @date:2015-12-31
	 */
	public static String[] getTableNameByConByDataSource(String dsName) throws Exception {
		List<String> tables = new ArrayList<String>();
		Connection conn = ConnectionHelper.getConnection(dsName);
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			String schema = metadata.getUserName();
			ResultSet resultset = metadata.getTables(null, schema, null, new String[] { "TABLE" });
			while (resultset.next()) {
				String tableName = resultset.getString("TABLE_NAME");
				resultset.getString("REMARKS");
				tables.add(tableName);
			}

			String[] results = tables.toArray(new String[tables.size()]);
			Arrays.sort(results);
			return results;
		} finally {
			DcfUtil.closeSession(null, conn);
		}
	}
	
	/**
	 * 根据表名获取表的字段信息
	 * @param tableName 表名
	 */
	public static DataObject[] getColumns(String tableName) throws Exception {
		List<DataObject> columns = new ArrayList<DataObject>();
		List<String> primaryKeys = new ArrayList<String>();

		Connection conn = DcfUtil.getConnection();
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			String schema = metadata.getUserName();
			ResultSet pkResultSet = metadata.getPrimaryKeys(CATALOG, schema, tableName);

			while (pkResultSet.next()) {
				primaryKeys.add(pkResultSet.getString("COLUMN_NAME"));
			}
			
			// Oracle字段注释特殊处理
			Map<String, String> oracleRemarks = null;
			if (metadata.getDatabaseProductName().indexOf("Oracle") != -1) {
				oracleRemarks = new HashMap<String, String>();
				String sql = "select * from user_col_comments where comments is not null and table_name='" + tableName + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String columnName = rs.getString("COLUMN_NAME");
					String comment = rs.getString("COMMENTS");
					oracleRemarks.put(columnName, comment);
				}
			}

			ResultSet columnResultSet = metadata.getColumns(CATALOG, schema, tableName, null);
			int columnNo = 1;
			while (columnResultSet.next()) {
				String columnName = columnResultSet.getString("COLUMN_NAME");
				String columnType = columnResultSet.getString("TYPE_NAME");
				
				String columnDesc = columnResultSet.getString("REMARKS");
				if(StringUtil.isNullOrBlank(columnDesc) && oracleRemarks!=null){
					columnDesc = oracleRemarks.get(columnName);
				}

				int columnLength = columnResultSet.getInt("COLUMN_SIZE");
				int precision = columnResultSet.getInt("DECIMAL_DIGITS");

				String nullAble = columnResultSet.getInt("NULLABLE") == 1 ? "Y" : "N";

				DataObject propertyInfo = DataObjectUtil.createDataObject("com.dcf.system.entity.entitydataset.SysField");
				propertyInfo.set("fieldSort", columnNo);
				propertyInfo.set("fieldTablename", columnName);
				propertyInfo.set("fieldChinaName", columnDesc);
				propertyInfo.set("fieldType", columnType);
				propertyInfo.set("fieldLen", columnLength);
				propertyInfo.set("fieldDict", precision);
				propertyInfo.set("fieldIsNull", nullAble);
				propertyInfo.set("fieldDataType", JdbcTypeMapping.getShowType(columnType));

				if (primaryKeys.contains(columnName)) {
					propertyInfo.set("fieldIsKey", "Y");// 主键
					propertyInfo.set("fieldIsNull", "N");// 不为空
				} else {
					propertyInfo.set("fieldIsKey", "N");// 主键
				}
				columns.add(propertyInfo);
				columnNo++;
			}
			return columns.toArray(new DataObject[columns.size()]);
		} finally {
			DcfUtil.closeSession(null, conn);
		}
	}
	
	public static DataObject[] getColumnsByDS(String tableName,String ds) throws Exception {
		List<DataObject> columns = new ArrayList<DataObject>();
		List<String> primaryKeys = new ArrayList<String>();
		Connection conn = ConnectionHelper.getConnection(ds);
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			String schema = metadata.getUserName();
			ResultSet pkResultSet = metadata.getPrimaryKeys(CATALOG, schema, tableName);

			while (pkResultSet.next()) {
				primaryKeys.add(pkResultSet.getString("COLUMN_NAME"));
			}
			
			// Oracle字段注释特殊处理
			Map<String, String> oracleRemarks = null;
			if (metadata.getDatabaseProductName().indexOf("Oracle") != -1) {
				oracleRemarks = new HashMap<String, String>();
				String sql = "select * from user_col_comments where comments is not null and table_name='" + tableName + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String columnName = rs.getString("COLUMN_NAME");
					String comment = rs.getString("COMMENTS");
					oracleRemarks.put(columnName, comment);
				}
			}

			ResultSet columnResultSet = metadata.getColumns(CATALOG, schema, tableName, null);
			int columnNo = 1;
			while (columnResultSet.next()) {
				String columnName = columnResultSet.getString("COLUMN_NAME");
				String columnType = columnResultSet.getString("TYPE_NAME");
				
				String columnDesc = columnResultSet.getString("REMARKS");
				if(StringUtil.isNullOrBlank(columnDesc) && oracleRemarks!=null){
					columnDesc = oracleRemarks.get(columnName);
				}

				int columnLength = columnResultSet.getInt("COLUMN_SIZE");
				int precision = columnResultSet.getInt("DECIMAL_DIGITS");

				String nullAble = columnResultSet.getInt("NULLABLE") == 1 ? "Y" : "N";

				DataObject propertyInfo = DataObjectUtil.createDataObject("com.dcf.system.entity.entitydataset.SysField");
				propertyInfo.set("fieldSort", columnNo);
				propertyInfo.set("fieldTablename", columnName);
				propertyInfo.set("fieldChinaName", columnDesc);
				propertyInfo.set("fieldType", columnType);
				propertyInfo.set("fieldLen", columnLength);
				propertyInfo.set("fieldDict", precision);
				propertyInfo.set("fieldIsNull", nullAble);
				propertyInfo.set("fieldDataType", JdbcTypeMapping.getShowType(columnType));

				if (primaryKeys.contains(columnName)) {
					propertyInfo.set("fieldIsKey", "Y");// 主键
					propertyInfo.set("fieldIsNull", "N");// 不为空
				} else {
					propertyInfo.set("fieldIsKey", "N");// 主键
				}
				columns.add(propertyInfo);
				columnNo++;
			}
			return columns.toArray(new DataObject[columns.size()]);
		} finally {
			DcfUtil.closeSession(null, conn);
		}
	}
}
