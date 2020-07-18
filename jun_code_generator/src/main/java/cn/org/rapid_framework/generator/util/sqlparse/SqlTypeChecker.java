package cn.org.rapid_framework.generator.util.sqlparse;

public class SqlTypeChecker {

	/**
	 * 当前的sourceSql是否是select语句
	 * @return
	 */
	public static boolean isSelectSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*select\\s.*\\sfrom\\s+.*");
	}
	/**
	 * 当前的sourceSql是否是update语句
	 * @return
	 */
	public static boolean isUpdateSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*update\\s+.*");
	}
	/**
	 * 当前的sourceSql是否是delete语句
	 * @return
	 */
	public static boolean isDeleteSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*delete\\s+from\\s.*");
	}
	/**
	 * 当前的sourceSql是否是insert语句
	 * @return
	 */
	public static boolean isInsertSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*insert\\s+into\\s+.*");
	}
	
}
