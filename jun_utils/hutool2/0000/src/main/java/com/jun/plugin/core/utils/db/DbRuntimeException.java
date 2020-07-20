package com.jun.plugin.core.utils.db;

import com.jun.plugin.core.utils.exceptions.ExceptionUtil;
import com.jun.plugin.core.utils.util.StrUtil;

/**
 * 数据库异常
 * @author xiaoleilu
 */
public class DbRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 3624487785708765623L;

	public DbRuntimeException(Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}
	
	public DbRuntimeException(String message) {
		super(message);
	}
	
	public DbRuntimeException(String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}
	
	public DbRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public DbRuntimeException(Throwable throwable, String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
