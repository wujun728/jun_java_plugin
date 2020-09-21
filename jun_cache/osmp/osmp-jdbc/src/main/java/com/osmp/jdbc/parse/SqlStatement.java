/*   
 * Project: OSMP
 * FileName: SqlStatement.java
 * version: V1.0
 */
package com.osmp.jdbc.parse;

import java.util.List;
import java.util.Map;

import com.osmp.jdbc.parse.sqlblock.SQLBlock;

public class SqlStatement {
	private String id;
	private List<SQLBlock> sqlWrapper;

	public String getId() {
		return id;
	}

	public SqlStatement(String id, List<SQLBlock> sqlWrapper) {
		this.id = id;
		this.sqlWrapper = sqlWrapper;
	}

	public String generateSql(Map<String, Object> parasMap) {
		if (sqlWrapper == null || sqlWrapper.isEmpty())
			return "";
		StringBuffer sb = new StringBuffer();
		for (SQLBlock sqlBlock : sqlWrapper) {
			sb.append(sqlBlock.sql(parasMap));
		}
		String sql = sb.toString();
		if (null != parasMap && parasMap.size() > 0) {
			for (String i : parasMap.keySet()) {
				sql = sql.replaceAll("#" + i.trim() + "#", String.valueOf(parasMap.get(i)));
			}
		}
		return sql;
	}
}
