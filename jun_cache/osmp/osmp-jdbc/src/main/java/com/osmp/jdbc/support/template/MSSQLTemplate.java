/*   
 * Project: OSMP
 * FileName: MSSQLTemplate.java
 * version: V1.0
 */
package com.osmp.jdbc.support.template;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * Description:MSSQLTemplate
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:17:25上午10:51:30
 */
public class MSSQLTemplate extends BaseTemplate {

	protected String buildQueryPageSql(String querySql, Map<String, Object> queryParam, int startIndex, int pageSize) {
		Assert.isTrue(StringUtils.hasText(querySql), "page query sql string is must be not null or empty");
		Assert.isTrue(pageSize > 0, "page query page size must be > 0");

		 querySql = querySql.replaceFirst("(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)",
		 "select top " + (startIndex + pageSize)
		 + " temp_column=0,");
		startIndex = Math.max(0, startIndex);
		StringBuilder sqlBuilder = new StringBuilder("select * from (")
				.append("select row_number() over(order by temp_column) temp_rownumber,* from (").append(querySql)
				.append(")dt )tab where temp_rownumber > " + startIndex);

		return sqlBuilder.toString();
	}

	protected String buildQueryPageSql(String querySql, List<Object> queryParam, int startIndex, int pageSize) {
		Assert.isTrue(StringUtils.hasText(querySql), "page query sql string is must be not null or empty");
		Assert.isTrue(pageSize > 0, "page query page size must be > 0");

		 querySql = querySql.replaceFirst("(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)",
		 "select top " + (startIndex + pageSize)
		 + " temp_column=0,");
		startIndex = Math.max(0, startIndex);
		StringBuilder sqlBuilder = new StringBuilder("select * from (")
				.append("select row_number() over(order by temp_column) temp_rownumber,* from (").append(querySql)
				.append(")dt )tab where temp_rownumber > " + startIndex);

		return sqlBuilder.toString();
	}
}
