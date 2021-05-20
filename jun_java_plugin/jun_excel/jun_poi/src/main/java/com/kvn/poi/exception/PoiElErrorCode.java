package com.kvn.poi.exception;

import java.text.MessageFormat;

/**
 * 异常码区段：[1001-1999]
* @author wzy
* @date 2017年6月20日 上午11:59:32
*/
public enum PoiElErrorCode implements IErrors {
	TAG_NOT_FOUND(1001, "[{0}]中找不到tag:[{1}]"),
	TEMPLATE_FILE_NOT_FOUND(1002, "找不到模板[{0}]"),
	EXCEL_FILE_NOT_FOUND(1003, "找不到被导入的excel[{0}]"),
	/**-----------------COMMON ERROR--------------------*/
	SYSTEM_ERROR(1998, "系统错误"),
	ILLEGAL_PARAM(1999, "参数异常:{0}");
	
	
	private int code;
	private String msg;
	
	private PoiElErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	@Override
	public PoiElException exp() {
		return new PoiElException(code, msg);
	}

	@Override
	public PoiElException exp(Object... args) {
		return new PoiElException(code, MessageFormat.format(msg, args));
	}

	@Override
	public PoiElException exp(Throwable cause, Object... args) {
		return new PoiElException(code, MessageFormat.format(msg, args), cause);
	}

	@Override
	public PoiElException expMsg(String message, Object... args) {
		return new PoiElException(code, MessageFormat.format(message, args));
	}

	@Override
	public PoiElException expMsg(String message, Throwable cause, Object... args) {
		return new PoiElException(code, MessageFormat.format(message, args), cause);
	}
	
}
