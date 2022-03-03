package org.springrain.weixin.sdk.common.api;

public interface IWxXcxConfig extends IWxConfig {

	String getSessionKey();

	void setSessionKey(String sessionKey);

	String getPartnerId();

	void setPartnerId(String partnerId);

	String getPartnerKey();

	void setPartnerKey(String partnerKey);

	// 开启oauth2.0认证,是否能够获取openId,0是关闭,1是开启
	Integer getOauth2();

	void setOauth2(Integer oauth2);
	
	//用户模板id
	String getPlanId();

	void setPlanId(String planId);
	
	// 用户请求序列号
	Integer getRequestSerial();

	void setRequestSerial(Integer requestSerial);

}
