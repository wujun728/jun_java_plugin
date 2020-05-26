package com.bing.excel.exception;

/**
 * 当用户定义实体缺少CellConfig注解时候抛出
 * @author shizhongtao
 *
 */
public class MissingCellConfigException extends IllegalEntityException {



	public MissingCellConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingCellConfigException(String message) {
		super(message);
	}

	public MissingCellConfigException(Throwable cause) {
		super(cause);
	}

}
