package cn.com.bluemoon.common.exception;

/**
 * 异常枚举
 *
 * @author Wujun
 * @Date 2018/06/28 下午10:33
 */
public enum WebExceptionEnum implements ServiceExceptionEnum{

	/**
	 * 其他
	 */
	WRITE_ERROR(false, 500, "渲染界面错误"),

	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(false, 400, "FILE_READING_ERROR!"),
	FILE_NOT_FOUND(false, 400, "FILE_NOT_FOUND!"),

	/**
	 * 错误的请求
	 */
	REQUEST_NULL(false, 400, "请求有错误"),
	REQUEST_LIMIT(false, 400, "请求已达上限"),
	SERVER_ERROR(false, 500, "服务器异常"),
	TOKEN_NOT_FUND(false, 401, "未授权"),
	TOKEN_ERROR(false, 700, "token验证失败");

	private WebExceptionEnum(Boolean isSuccess, Integer responseCode, String responseMsg) {
		this.isSuccess = isSuccess;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}

	private Boolean isSuccess;
    
    private Integer responseCode;

    private String responseMsg;

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

}
