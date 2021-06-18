package org.tangjl.multidatasource.util;

public class DBContextHolder {
	public static final String DATA_SOURCE_A = "a"; // 对应dataSource_a数据源key
	public static final String DATA_SOURCE_B = "b"; // 对应远程dataSource_b数据源key
	public static final String DATA_SOURCE_C = "c"; // 对应远程dataSource_c数据源key

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	/**
	 * 设置数据源
	 * 
	 * @param dbType
	 *            本类中两个静态变量的值
	 */
	public static void setDBType(String dbType) {
		contextHolder.set(dbType);
	}
	public static String getDBType() {
		return contextHolder.get();
	}
	public static void clearDBType() {
		contextHolder.remove();
	}
}