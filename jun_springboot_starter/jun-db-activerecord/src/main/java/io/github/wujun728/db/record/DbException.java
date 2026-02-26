package io.github.wujun728.db.record;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbException extends RuntimeException {

	private static final long serialVersionUID = 7287397280270029496L;

	private static final Log loger = LogFactory.getLog(DbException.class);

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbException(Throwable cause) {
		super(cause);
	}

	public DbException(String message) {
		super(message);
	}

	public DbException(Exception e, String sql) {
		super("数据库运行期异常, SQL: " + sql, e);
		if (loger.isErrorEnabled()) {
			loger.error("数据库运行期异常，相关sql语句为" + sql, e);
		}
	}

	public DbException(String message, String sql) {
		super(message + ", SQL: " + sql);
		if (loger.isErrorEnabled()) {
			loger.error(message + "，相关sql语句为" + sql);
		}
	}
}
