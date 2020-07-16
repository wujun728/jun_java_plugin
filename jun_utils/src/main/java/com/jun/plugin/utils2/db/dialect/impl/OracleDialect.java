package com.jun.plugin.utils2.db.dialect.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jun.plugin.utils2.db.DbRuntimeException;
import com.jun.plugin.utils2.db.DbUtil;
import com.jun.plugin.utils2.db.Page;
import com.jun.plugin.utils2.db.dialect.DialectName;
import com.jun.plugin.utils2.db.sql.LogicalOperator;
import com.jun.plugin.utils2.db.sql.Order;
import com.jun.plugin.utils2.db.sql.Query;
import com.jun.plugin.utils2.db.sql.SqlBuilder;
import com.jun.plugin.utils2.db.sql.Wrapper;
import com.jun.plugin.utils2.util.StrUtil;

/**
 * Oracle 方言
 * @author loolly
 *
 */
public class OracleDialect extends AnsiSqlDialect{
	
	public OracleDialect() {
		wrapper = new Wrapper('"');	//Oracle所有字段名用双引号包围，防止字段名或表名与系统关键字冲突
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
		
		int[] startEnd = page.getStartEnd();
		final SqlBuilder sql = SqlBuilder.create(wrapper);
		sql.append("SELECT * FROM ( SELECT row_.*, rownum rownum_ from ( ")
			.append(find)
			.append(" ) row_ where rownum <= ").append(startEnd[1])
			.append(") table_alias")
			.append(" where table_alias.rownum_ >= ").append(startEnd[0]);
		
		final PreparedStatement ps = conn.prepareStatement(sql.build());
		DbUtil.fillParams(ps, find.getParamValues());
		return ps;
	}
	
	@Override
	public DialectName dialectName() {
		return DialectName.ORACLE;
	}
}
