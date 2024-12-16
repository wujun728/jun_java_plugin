package io.github.wujun728.db.record.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SqlException extends RuntimeException {

	private static final long serialVersionUID = 7287397280270029496L;

	private static final Log loger = LogFactory.getLog(SqlException.class);

	public SqlException(String message) {
		super(message);
	}

	public SqlException(Exception e, String sql) {
		super("数据库运行期异常");
		e.printStackTrace();
		if (loger.isErrorEnabled()) {
			loger.error("数据库运行期异常，相关sql语句为" + sql);
			loger.error(e);
		}
	}

	public SqlException(String message, String sql) {
		super(message);
		if (loger.isErrorEnabled()) {
			loger.error(message + "，相关sql语句为" + sql);
		}
	}
}