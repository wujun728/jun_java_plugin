package com.jun.plugin.utils2.db.dialect.impl;

import com.jun.plugin.utils2.db.dialect.DialectName;
import com.jun.plugin.utils2.db.sql.Wrapper;


/**
 * Postgree方言
 * @author loolly
 *
 */
public class PostgresqlDialect extends AnsiSqlDialect{
	public PostgresqlDialect() {
		wrapper = new Wrapper('"');
	}

	@Override
	public DialectName dialectName() {
		return DialectName.POSTGREESQL;
	}
}
