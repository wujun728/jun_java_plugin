package cn.com.bluemoon.common.response;

/**
 * 通用响应model
 * @author Wujun
 * @version 1.0
 */
public class BaseResponse{

	//请求是否成功
	private Boolean isSuccess = true;
	//请求响应码，成功时为0
	private int responseCode = 0;
	//请求响应码对应描述
	private String responseMsg = "请求成功";
	
	public BaseResponse(){}
	
	public BaseResponse(Boolean isSuccess, int responseCode,
			String responseMsg) {
		this.isSuccess = isSuccess;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
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
}
