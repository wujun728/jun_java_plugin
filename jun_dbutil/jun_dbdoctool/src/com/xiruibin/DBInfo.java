package com.xiruibin;

import java.util.HashMap;
import java.util.Map;

public class DBInfo {

	public static String currentdbtype = null;

	public final static Map<String, String> DB_DRIVER_MAP = new HashMap<String, String>();

	public final static Map<String, String> DB_URL_MAP = new HashMap<String, String>();

	static {
		DB_DRIVER_MAP.put("mysql", "com.mysql.jdbc.Driver");
		DB_DRIVER_MAP.put("sqlserver2000",
				"com.microsoft.jdbc.sqlserver.SQLServerDriver");
		DB_DRIVER_MAP.put("sqlserver2005",
				"com.microsoft.sqlserver.jdbc.SQLServerDriver");
		DB_DRIVER_MAP.put("postgresql", "org.postgresql.Driver");
		DB_DRIVER_MAP.put("db2", "com.ibm.db2.jcc.DB2Driver");
		DB_DRIVER_MAP.put("oracle", "oracle.jdbc.driver.OracleDriver");

		
		DB_URL_MAP.put("mysql", "jdbc:mysql://{ip}:{port}/{database}");
		DB_URL_MAP
				.put("sqlserver2000",
						"jdbc:microsoft:sqlserver://{ip}:{port};DatabaseName={database}");
		DB_URL_MAP.put("sqlserver2005",
				"jdbc:sqlserver://{ip}:{port};DatabaseName={database}");
		DB_URL_MAP
				.put("postgresql", "jdbc:postgresql://{ip}:{port}/{database}");
		DB_URL_MAP.put("db2", "jdbc:db2://{ip}:{port}/{database}");
		DB_URL_MAP.put("oracle", "jdbc:oracle:thin:@{ip}:{port}:{database}");
	}
	
	public static String getDriverUrl(String dbtype, String ip, String port, String dbname) {
		String dburl = DB_URL_MAP.get(dbtype);
		if (dburl != null) {
			return dburl.replace("{ip}", ip).replace("{port}", port).replace("{database}", dbname);
		}
		return null;
	}
	
	public static String getCurrentDriverUrl(String ip, String port, String dbname) {
		if (currentdbtype != null) {
			return getDriverUrl(currentdbtype, ip, port, dbname);
		}
		return null;
	}

}