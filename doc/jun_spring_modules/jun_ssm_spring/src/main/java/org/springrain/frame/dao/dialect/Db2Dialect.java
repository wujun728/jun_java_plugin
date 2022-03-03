package org.springrain.frame.dao.dialect;

import org.springframework.stereotype.Component;

import org.springrain.frame.util.Page;

@Component("db2Dialect")
public class Db2Dialect implements IDialect {

	@Override
	public String getPageSql(String sql, String orderby, Page page) {
		// 设置分页参数
		// 设置分页参数
		int satrt = (page.getPageIndex() - 1) * page.getPageSize() + 1;
		int end = page.getPageIndex() * page.getPageSize();
		//去掉无用的空格
		sql=sql.trim();
		// 去掉select
		if (sql.toLowerCase().startsWith("select")) {
			sql = sql.substring(6);
		}
	
  StringBuilder sb=new StringBuilder();
	sb.append("SELECT * from (SELECT ROWNUMBER() OVER (");
	sb.append(orderby);
	sb.append(")  frame_page_sql_row_number,");
	sb.append(sql);
	sb.append("  ) frame_sql_temp_table WHERE frame_sql_temp_table.frame_page_sql_row_number >= ");
	sb.append(satrt).append(" and frame_sql_temp_table.frame_page_sql_row_number<=").append(end);
//	sb.append(" order by frame_page_sql_row_number ");
		return sb.toString();
	}

	@Override
	public String getDataDaseType() {
		return "db2";
	}

	@Override
	public boolean isRowNumber() {
		return true;
	}

}
