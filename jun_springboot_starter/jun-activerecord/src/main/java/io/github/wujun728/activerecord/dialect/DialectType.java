package io.github.wujun728.activerecord.dialect;

import com.jfinal.plugin.activerecord.dialect.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据库方言
 *
 * 
 */
@Getter
@RequiredArgsConstructor
public enum DialectType {

	/**
	 * 方言
	 */
	Mysql,
	Oracle,
	Sqlite3,
	Postgre,
	SqlServer,
	Ansi,
	Druid;

	public Dialect getDialect(String name) {
//		return switch (name) {
//			case "Mysql" -> new MysqlDialect();
//			case "Oracle" -> new OracleDialect();
//			case "Sqlite3" -> new Sqlite3Dialect();
//			case "Postgre" -> new PostgreSqlDialect();
//			case "SqlServer" -> new SqlServerDialect();
//			case "Ansi" -> new AnsiSqlDialect();
//			default -> new DruidSqlDialect();
//		};
		return new AnsiSqlDialect();
	}

}
