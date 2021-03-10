package com.chentongwei.common.exception;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 业务异常
 */
public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private IBaseEnum baseEnum;

	public BussinessException(IBaseEnum baseEnum) {
		super();
		this.baseEnum = baseEnum;
	}

	public IBaseEnum getBaseEnum() {
		return baseEnum;
	}

	public void setBaseEnum(IBaseEnum baseEnum) {
		this.baseEnum = baseEnum;
	}
}
