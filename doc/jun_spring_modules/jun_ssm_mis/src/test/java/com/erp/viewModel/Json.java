package com.erp.viewModel;

public class Json  implements java.io.Serializable {

	private boolean success = false;// 是否成功
	private String msg = "";// 提示信息
	private Object obj = null;// 其他信息
	//***********************************************************
	private String title="提示";
	private String message;
	private boolean status=false;
	
	public String getMessage() {
		return message;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
