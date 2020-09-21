package com.yzm.vo;

import java.io.Serializable;

public class CheckVo implements Serializable {

	private String token;
	private String check;

	public CheckVo(String token, String check) {
		this.token = token;
		this.check = check;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

}
