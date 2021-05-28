package org.smartboot.service.api;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.smartboot.service.facade.result.ServiceResult;

/**
 * 调用rest api的响应结果存放类
 *
 * @author Wujun
 * @version RestApiResult.java, v 0.1 2016年2月10日 下午3:04:06 Seer Exp. 
 */
public class RestApiResult<T> extends ServiceResult<T> {
	/** */
	private static final long serialVersionUID = -2254621671109894906L;
	/** API返回码 */
	private int code;
	/** API版本号 */
	private String version;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
