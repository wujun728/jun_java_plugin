package com.jun.plugin.core.utils.db.dialect.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jun.plugin.core.utils.db.DbRuntimeException;
import com.jun.plugin.core.utils.db.DbUtil;
import com.jun.plugin.core.utils.db.Page;
import com.jun.plugin.core.utils.db.dialect.DialectName;
import com.jun.plugin.core.utils.db.sql.LogicalOperator;
import com.jun.plugin.core.utils.db.sql.Order;
import com.jun.plugin.core.utils.db.sql.Query;
import com.jun.plugin.core.utils.db.sql.SqlBuilder;
import com.jun.plugin.core.utils.db.sql.Wrapper;
import com.jun.plugin.core.utils.util.StrUtil;

/**
 * MySQL方言
 * @author loolly
 *
 */
public class MysqlDialect extends AnsiSqlDialect{
	
	public MysqlDialect() {
		wrapper = new Wrapper('`');
	}

	@Override
	public PreparedStatement psForPage(Connection conn, Query query) throws SQLException {
		//验证
		if(query == null || StrUtil.hasBlank(query.getTableNames())) {
			throw new DbRuntimeException("Table name is null !");
		}
		
		final SqlBuilder find = SqlBuilder.create(wrapper)
				.select(query.getFields())
				.from(query.getTableNames())
				.where(LogicalOperator.AND, query.getWhere());
		
		
		final Page page = query.getPage();
		if(null != page){
			final Order[] orders = page.getOrders();
			if(null != orders){
				find.orderBy(orders);
			}
		}
		
		find.append(" LIMIT ").append(page.getStartPosition()).append(", ").append(page.getNumPerPage());
		
		final PreparedStatement ps = conn.prepareStatement(find.build());
		DbUtil.fillParams(ps, find.getParamValueArray());
		return ps;
	}
	
	@Override
	public DialectName dialectName() {
		return DialectName.MYSQL;
	}
}
