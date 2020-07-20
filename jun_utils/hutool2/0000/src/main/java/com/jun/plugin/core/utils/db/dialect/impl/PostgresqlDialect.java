package com.jun.plugin.core.utils.db.dialect.impl;

import com.jun.plugin.core.utils.db.dialect.DialectName;
import com.jun.plugin.core.utils.db.sql.Wrapper;


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
