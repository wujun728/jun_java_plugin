package com.jun.plugin.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.jun.plugin.bean.ColumnInfo;
import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.PropertyInfo;
import com.jun.plugin.bean.TableInfo;

/**
 * 支持单主键的表，建议主键设置在
 *
 * @author Wujun
 */
public class DbUtils {
	private static DbUtils dbUtils = new DbUtils();

	private DbUtils() {

	}

	public static DbUtils getInstance() {
		return dbUtils;
	}

//	private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

	/**
	 * 返回一个与特定数据库的连接
	 *
	 * @throws ClassNotFoundException
	 */
	private Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		try {
			// 加载属性文件，读取数据库连接配置信息
			Properties pro = new Properties();
			try {
				pro.load(DbUtils.class.getClassLoader().getResourceAsStream("config.properties"));
			} catch (IOException e) {
//				LOGGER.error("未找到数据源配置文件");
				return null;
			}
			String driverClass = pro.getProperty("jdbc_driverClassName");
			String url = pro.getProperty("jdbc_url");
			String user = pro.getProperty("jdbc_username");
			String password = pro.getProperty("jdbc_password");
			Class.forName(driverClass);
			// connection = DriverManager.getConnection(url, user, password);
			Properties props = new Properties();
			props.setProperty("user", user);
			props.setProperty("password", password);
			props.setProperty("remarks", "true"); // 设置可以获取remarks信息
			props.setProperty("useInformationSchema", "true");// 设置可以获取tables
																// remarks信息
			connection = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
//			LOGGER.error("获取数据源连接错误");
			return null;
		}
		return connection;
	}

	/**
	 * 只做单主键代码的生成
	 *
	 * @param metaData
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private String primaryKeyColumnName(DatabaseMetaData metaData, String tableName) throws SQLException {
		String primaryKeyColumnName = null;
		ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, null, tableName);
		while (primaryKeyResultSet.next()) {
			primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
			break;
		}
		if (primaryKeyColumnName == null) {
			primaryKeyColumnName = "id";
		}
		return primaryKeyColumnName;
	}

	/**
	 * 获取需要生成代码的表信息
	 *
	 * @param metaData
	 * @param tableNames
	 * @param entitySuffix
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<TableInfo> getAllTables(Config conf) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		DatabaseMetaData metaData = getMetaData(connection);
		List<String> tableNames = Arrays.asList(conf.getTables().split(","));
		String entitySuffix = conf.getEntitySuffix();
		boolean underline2Camel = conf.isUnderline2Camel();
		List<TableInfo> tables = new ArrayList<TableInfo>();
		ResultSet tableRet = getTableResultSet(metaData);

		boolean isAllTable = "all".equals(conf.getTables());
		while (tableRet.next()) {
			TableInfo tableInfo = new TableInfo();
			String tableName = tableRet.getString("TABLE_NAME");// 表明
			String tableDesc = tableRet.getString("REMARKS");// 表注释
//			String tableDesc = tableRet.getString("TABLE_COMMENT");// 表注释

			for (String _tableName : tableNames) {
				if ((!isAllTable) && !StringUtils.equalsIgnoreCase(tableName.trim(), _tableName.trim())) {
//					LOGGER.debug("Ignore  tableName:{}, tableDesc:{}", tableName, tableDesc);
					continue;
				}
//				LOGGER.debug("Handle tableName:{}, tableDesc:{}", tableName, tableDesc);
				// 字段处理
				List<ColumnInfo> columns = getAllColumns(metaData, tableName);// 表的所有字段

				columns2Properties(columns, underline2Camel, tableInfo);

				// 主键处理(主键唯一)
				String primaryKey = primaryKeyColumnName(metaData, tableName);

				String primaryKeyProperty = primaryKey;
				if (underline2Camel) {
					primaryKeyProperty = Underline2CamelUtils.underline2Camel(primaryKey);
				}
				Map<String, String> primaryKeyMap = new HashMap<String, String>();
				primaryKeyMap.put(primaryKey, primaryKeyProperty);

				// beanClass
				String beanName = getClassName(tableName, underline2Camel);
				if (StringUtils.isNoneBlank(entitySuffix)) {
					beanName = beanName + entitySuffix;
				}

				tableInfo.setTableName(tableName);
				tableInfo.setPrefix("");
				if (conf.isPrefix()) {
					tableInfo.setPrefix(getPrefixName(tableName));
				}
				tableInfo.setTableDesc(tableDesc);
				tableInfo.setColumns(columns);
				tableInfo.setBeanName(beanName);

				tableInfo.setPrimaryKey(primaryKeyMap);

				tables.add(tableInfo);
			}
		}
		return tables;
	}

	/**
	 * 表字段转换为属性字段
	 *
	 * @param columns
	 * @return
	 */
	private void columns2Properties(List<ColumnInfo> columns, boolean underline2Camel, TableInfo tableInfo) {
		Map<String, String> properties = new LinkedHashMap<String, String>();
		Map<String, PropertyInfo> propInfoMap = new LinkedHashMap<String, PropertyInfo>();
		List<PropertyInfo> allPropInfo = new ArrayList<>();
		Map<String, String> propertiesAnColumns = new LinkedHashMap<String, String>();
		Map<String, String> insertPropertiesAnColumns = new LinkedHashMap<String, String>();
		Set<String> propTypePackages = new HashSet<String>();

		for (ColumnInfo entry : columns) {
			String columnName = entry.getColumnName();// 字段名
			String columnType = entry.getColumnType();// 字段类型
			String columnRemarks = entry.getColumnRemarks();// 字段类型
			String propertyName = columnName;
			if (underline2Camel) {
				propertyName = Underline2CamelUtils.underline2Camel(columnName);
			}
			String propertyType = getFieldType(columnType, propTypePackages);
			properties.put(propertyName, propertyType);
			PropertyInfo beanInfo = new PropertyInfo();
			beanInfo.setPropertyName(propertyName);
			beanInfo.setPropertyType(propertyType);
			beanInfo.setPropertyDesc(columnRemarks);
			allPropInfo.add(beanInfo);
			propInfoMap.put(propertyName, beanInfo);
			propertiesAnColumns.put(propertyName, columnName);
			if (!excludeInsertProperties(propertyName)) {
				insertPropertiesAnColumns.put(propertyName, columnName);
			}
		}
		tableInfo.setProperties(properties);
		tableInfo.setPropInfoMap(propInfoMap);
		tableInfo.setAllPropInfo(allPropInfo);
		tableInfo.setPropertiesAnColumns(propertiesAnColumns);
		tableInfo.setInsertPropertiesAnColumns(insertPropertiesAnColumns);
		tableInfo.setPropTypePackages(propTypePackages);

	}

	private boolean excludeInsertProperties(String propertyName) {
		// return "id".equals(propertyName) || "createTime".equals(propertyName)
		// || "updateTime".equals(propertyName)
		// || "delFlag".equals(propertyName);
		return "id".equals(propertyName);
	}

	/**
	 * 获取表所有字段
	 *
	 * @param metaData
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private List<ColumnInfo> getAllColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
		String columnName;
		String columnType;
		String remarks;
		ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
		List<ColumnInfo> columns = new ArrayList<>();
		while (colRet.next()) {
			columnName = colRet.getString("COLUMN_NAME");
			columnType = colRet.getString("TYPE_NAME");
			int datasize = colRet.getInt("COLUMN_SIZE");
			int digits = colRet.getInt("DECIMAL_DIGITS");
			int nullable = colRet.getInt("NULLABLE");
			remarks = colRet.getString("remarks");
			ColumnInfo info = new ColumnInfo();
			info.setColumnName(columnName);
			info.setColumnType(columnType);
			info.setColumnRemarks(remarks);
			columns.add(info);
//			LOGGER.debug(
//					remarks + "-" + columnName + "-" + columnType + "-" + datasize + "-" + digits + "-" + nullable);
		}
		return columns;
	}

	/**
	 * 获取TableResultSet
	 *
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getTableResultSet(DatabaseMetaData metaData) throws SQLException {
		// DatabaseMetaData metaData = connection.getMetaData();
		// ResultSet tableRet = metaData.getTables(null, "%", "%", new String[]
		// { "TABLE" });
		String tableName = "%";
		return getTableResultSet(metaData, tableName);

	}

	/**
	 * 获取TableResultSet
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getTableResultSet(DatabaseMetaData metaData, String tableName) throws SQLException {
		ResultSet tableRet = metaData.getTables(null, "%", tableName, new String[] { "TABLE" });
		return tableRet;
	}

	/**
	 * 获取DatabaseMetaData
	 *
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private DatabaseMetaData getMetaData(Connection connection) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		return metaData;
	}

	/**
	 * 如果table名是t_开头，则去掉t_,其他_变驼峰，第一个字母大写。
	 *
	 * @param tableName
	 * @return
	 */
	private static String getClassName(String tableName, boolean underline2Camel) {
		String res = tableName;
		// 去t_
		if (StringUtils.startsWithIgnoreCase(tableName, Constants.IGNORE_T)) {
			res = StringUtils.substringAfter(tableName, Constants.IGNORE_T);
		}

		if (underline2Camel) {
			// 变驼峰
			res = Underline2CamelUtils.underline2Camel(res);
		}
		// 首字符大写
		res = StringUtils.capitalize(res);
		return res;
	}

	/**
	 * 如果table名是t_开头，则去掉t_,其他_变驼峰，第一个字母大写。
	 *
	 * @param tableName
	 * @return
	 */
	private static String getPrefixName(String tableName) {
		String res = tableName;
		// 去t_
		if (res.startsWith("t_")) {
			res = res.substring(2);
		}
		int index = StringUtils.indexOf(res, "_");
		if (index > 0) {
			return StringUtils.substringBefore(res, "_").toLowerCase();
		}
		return "";
	}

	/**
	 * 设置字段类型 MySql数据类型
	 *
	 * @param columnType
	 *            列类型字符串
	 * @param packages
	 *            封装包信息
	 * @return
	 */
	private static String getFieldType(String columnType, Set<String> packages) {

		columnType = columnType.toLowerCase();
		if (columnType.equals("varchar") || columnType.equals("nvarchar") || columnType.equals("char")
				|| columnType.equals("text") || columnType.equals("mediumtext")) // ||
		// columnType.equals("tinytext")||columnType.equals("mediumtext")||columnType.equals("longtext")
		{
			return "String";
		} else if (columnType.equals("tinyblob") || columnType.equals("blob") || columnType.equals("mediumblob")
				|| columnType.equals("longblob")) {
			return "byte[]";
		} else if (columnType.equals("datetime") || columnType.equals("date") || columnType.equals("timestamp")
				|| columnType.equals("time") || columnType.equals("year")) {
			packages.add("import java.util.Date;");
			return "Date";
		} else if (columnType.equals("bit") || columnType.equals("int") || columnType.equals("tinyint")
				|| columnType.equals("smallint")) // ||columnType.equals("bool")||columnType.equals("mediumint")
		{
			return "Integer";
		} else if (columnType.equals("int unsigned") || columnType.equals("tinyint unsigned")) {
			return "Integer";
		} else if (columnType.equals("bigint unsigned") || columnType.equals("bigint")) {
			return "Long";
		} else if (columnType.equals("float") || columnType.equals("float unsigned")) {
			packages.add("import java.math.BigDecimal;");
			return "BigDecimal";
		} else if (columnType.equals("double") || columnType.equals("double unsigned")) {
			packages.add("import java.math.BigDecimal;");
			return "BigDecimal";
		} else if (columnType.equals("decimal") || columnType.equals("decimal unsigned")) {
			packages.add("import java.math.BigDecimal;");
			return "BigDecimal";
		}
//		LOGGER.debug("error type is :" + columnType);
		return "ErrorType";
	}

	/**
	 * 设置类标题注释
	 *
	 * @param packages
	 * @param className
	 */
//	private static void getTitle(StringBuilder packages, String className) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
//		packages.append("\r\n/**\r\n");
//		packages.append("*\r\n");
//		packages.append("* 标题: " + className + "<br/>\r\n");
//		packages.append("* 说明: <br/>\r\n");
//		packages.append("*\r\n");
//		packages.append("* 作成信息: DATE: " + format.format(new Date()) + " NAME: author\r\n");
//		packages.append("*\r\n");
//		packages.append("* 修改信息<br/>\r\n");
//		packages.append("* 修改日期 修改者 修改ID 修改内容<br/>\r\n");
//		packages.append("*\r\n");
//		packages.append("*/\r\n");
//	}

}
