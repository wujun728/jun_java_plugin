package org.springrain.weixin.sdk.common.api;

public interface IWxMpConfig extends IWxConfig {



	String getPartnerId();
	void setPartnerId(String partnerId);
	
	
	String getPartnerKey();
	void setPartnerKey(String partnerKey);
	
	//开启oauth2.0认证,是否能够获取openId,0是关闭,1是开启
	 Integer getOauth2();
	 void setOauth2(Integer oauth2);





}
