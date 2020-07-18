package cn.org.rapid_framework.generator.util.typemapping;


public class DatabaseTypeUtils {
	
	public static String getDatabaseTypeByJdbcDriver(String driver) {
		if(driver == null) {
			return null;
		}
		driver = driver.toLowerCase();
		if(driver.indexOf("mysql") >= 0) {
			return "mysql";
		}
		if(driver.indexOf("oracle") >= 0) {
			return "oracle";
		}
		if(driver.indexOf("com.microsoft.sqlserver.jdbc.sqlserverdriver") >= 0 ) {
			return "sqlserver2005";
		}
		if(driver.indexOf("microsoft") >= 0 || driver.indexOf("jtds") >= 0) {
			return "sqlserver";
		}
		if(driver.indexOf("postgresql") >= 0) {
			return "postgresql";
		}
		if(driver.indexOf("sybase") >= 0) {
			return "sybase";
		}
		if(driver.indexOf("db2") >= 0) {
			return "db2";
		}
		if(driver.indexOf("hsqldb") >= 0) {
			return "hsqldb";
		}
		if(driver.indexOf("derby") >= 0) {
			return "derby";
		}
		if(driver.indexOf("h2") >= 0) {
			return "h2";
		}
		return driver;
	}
	
}
