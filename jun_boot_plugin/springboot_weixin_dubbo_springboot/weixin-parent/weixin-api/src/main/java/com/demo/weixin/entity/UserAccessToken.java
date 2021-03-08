package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserAccessToken extends BaseWeixinResponse {

	private static final long serialVersionUID = 85546950659565551L;

	/**
	 * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 */
	@JSONField(name="access_token")
	private String accessToken;

	/**
	 * 用户刷新access_token
	 */
	@JSONField(name="refresh_token")
	private String refreshToken;

	/**
	 * access_token接口调用凭证超时时间，单位（秒）
	 */
	@JSONField(name="expires_in")
	private long expiresIn;

	/**
	 * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
	 */
	@JSONField(name = "openid")
	private String openId;

	/**
	 * 用户授权的作用域，使用逗号（,）分隔
	 */
	@JSONField(name="scope")
	private String scope;
	
	public UserAccessToken(){
		
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}
	
	

}
