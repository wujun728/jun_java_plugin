package cn.com.bluemoon.common.response;


/**
 * 返回对象的基础bean
 * @author Wujun
 *
 */
public class ResponseBean {
	
	/**
	 * 请求是否成功
	 */
	private boolean isSuccess;
	
	/**
	 * 请求响应码，成功时为0
	 */
	private int responseCode;
	
	/**
	 * 请求响应码对应描述
	 */
	private String responseMsg;
	
	/**
	 * 请求响应的数据对象
	 */
	private Object data;

	public ResponseBean(boolean isSuccess, int responseCode, String responseMsg, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
		this.data = data;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
