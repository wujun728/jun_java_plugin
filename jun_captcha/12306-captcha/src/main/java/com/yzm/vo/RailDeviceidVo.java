package com.yzm.vo;

public class RailDeviceidVo {
	private String token;
	private String result;

	
	public RailDeviceidVo(){}
	
	public RailDeviceidVo(String token,String result){
		this.token = token;
		this.result = result;
	
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}
