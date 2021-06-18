package com.roncoo.jui.common.util.base;

/**
 * 异常处理类
 * 
 * @author Wujun
 */
public class RoncooException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 异常码 */
	protected int expCode;

	/** 异常信息 */
	protected String expMsg;

	public RoncooException(int expCode, String expMsg) {
		this.expCode = expCode;
		this.expMsg = expMsg;
	}

	public int getExpCode() {
		return expCode;
	}

	public void setExpCode(int expCode) {
		this.expCode = expCode;
	}

	public String getExpMsg() {
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}

	@Override
	public String toString() {
		return "BizException [expCode=" + expCode + ", expMsg=" + expMsg + "]";
	}

}
