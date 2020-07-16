package com.jun.plugin.utils2.db.dialect.impl;

import com.jun.plugin.utils2.db.dialect.DialectName;
import com.jun.plugin.utils2.db.sql.Wrapper;

/**
 * SqlLite3方言
 * @author loolly
 *
 */
public class Sqlite3Dialect extends AnsiSqlDialect{
	public Sqlite3Dialect() {
		wrapper = new Wrapper('[', ']');
	}
	
	@Override
	public DialectName dialectName() {
		return DialectName.SQLITE3;
	}
}
