package com.diamond.wm.accesstoken.entity;

import com.diamond.wm.accesstoken.enums.AccessTokenStatus;
import com.diamond.wm.accesstoken.enums.AccessTokenType;

public class AccessToken {
	private String accessToken;
	private AccessTokenType type;
	private AccessTokenStatus status;
	private long expires;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getExpires() {
		return expires;
	}
	public void setExpires(long l) {
		this.expires = l;
	}
	public AccessTokenType getType() {
		return type;
	}
	public void setType(AccessTokenType type) {
		this.type = type;
	}
	public AccessTokenStatus getStatus() {
		return status;
	}
	public void setStatus(AccessTokenStatus status) {
		this.status = status;
	}
	
	
}
