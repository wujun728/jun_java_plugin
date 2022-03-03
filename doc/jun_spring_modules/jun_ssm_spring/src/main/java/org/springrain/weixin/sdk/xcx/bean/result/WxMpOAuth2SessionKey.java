package org.springrain.weixin.sdk.xcx.bean.result;

import java.io.Serializable;

import org.springrain.weixin.sdk.xcx.util.json.WxXcxGsonBuilder;

public class WxMpOAuth2SessionKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String openId;

	private String sessionKey;

	private String unionId;

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public static WxMpOAuth2SessionKey fromJson(String json) {
		return WxXcxGsonBuilder.create().fromJson(json, WxMpOAuth2SessionKey.class);
	}

	@Override
	public String toString() {
		return "WxMpOAuth2AccessToken{" +

				" openId='" + this.openId + '\'' + ", sessionKey='" + this.sessionKey + '\'' + ", unionId='"
				+ this.unionId + '\'' + '}';
	}
}
