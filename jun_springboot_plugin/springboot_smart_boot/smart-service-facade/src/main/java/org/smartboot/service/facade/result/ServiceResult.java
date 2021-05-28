package org.smartboot.service.facade.result;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.smartboot.service.facade.enumeration.ResultCodeEnum;

/**
 * 调用接口的响应结果存放类
 *
 * @author Wujun
 * @version v 0.1 2015年11月5日 上午10:59:08 Seer Exp.
 */
public class ServiceResult<T> implements Serializable {
	/** */
	private static final long serialVersionUID = -7815723967101753647L;
	private ResultCodeEnum resultCode;
	private String message;
	private T data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return resultCode != null && resultCode == ResultCodeEnum.SUCCESS;
	}

	public void setSuccess(boolean success) {
		this.resultCode = success ? ResultCodeEnum.SUCCESS : ResultCodeEnum.FAIL;
	}

	/**
	 * Getter method for property <tt>resultCode</tt>.
	 *
	 * @return property value of resultCode
	 */
	public final ResultCodeEnum getResultCode() {
		return resultCode;
	}

	/**
	 * Setter method for property <tt>resultCode</tt>.
	 *
	 * @param resultCode value to be assigned to property resultCode
	 */
	public final void setResultCode(ResultCodeEnum resultCode) {
		this.resultCode = resultCode;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
