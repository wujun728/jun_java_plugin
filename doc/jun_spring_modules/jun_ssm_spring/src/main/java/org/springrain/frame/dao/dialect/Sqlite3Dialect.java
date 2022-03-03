package org.springrain.frame.dao.dialect;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import org.springrain.frame.util.Page;

@Component("sqlite3Dialect")
public class Sqlite3Dialect implements IDialect {

	@Override
	public String getPageSql(String sql, String orderby, Page page) {
		// 设置分页参数
		int pageSize = page.getPageSize();
		int pageNo = page.getPageIndex();
		StringBuilder sb = new StringBuilder(sql);
		if (StringUtils.isNotBlank(orderby)) {
			sb.append(" ").append(orderby);
		}
		sb.append(" limit ").append(pageSize * (pageNo - 1)).append(",")
				.append(pageSize);
		return sb.toString();
	}

	@Override
	public String getDataDaseType() {
		return "sqlite3";
	}

	@Override
	public boolean isRowNumber() {
		return false;
	}

}
