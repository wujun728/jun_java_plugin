package org.springrain.weixin.sdk.common.api;

public interface IWxConfig {
	String getId();// 业务Id

	void setId(String string);

	String getAppId();

	void setAppId(String string);

	String getToken();

	void setToken(String token);

	String getAesKey();

	void setAesKey(String aesKey);

	String getTmpDirFile();

	void setTmpDirFile(String string);

	String getHttpProxyHost();

	void setHttpProxyHost(String httpProxyHost);

	Integer getHttpProxyPort();

	void setHttpProxyPort(Integer httpProxyPort);

	String getHttpProxyUsername();

	void setHttpProxyUsername(String httpProxyUsername);

	String getHttpProxyPassword();

	void setHttpProxyPassword(String httpProxyPassword);

	String getAccessToken();

	void setAccessToken(String accessToken);

	void setAccessTokenExpiresTime(Long l);

	boolean isAccessTokenExpired();

	String getSecret();

	void setSecret(String string);

	String getJsApiTicket();

	void setJsApiTicket(String jsapiTicket);

	void setJsApiTicketExpiresTime(Long l);

	boolean isJsApiTicketExpired();

	String getCardApiTicket();

	void setCardApiTicket(String cardApiTicket);

	void setCardApiTicketExpiresTime(Long l);

	boolean isCardApiTicketExpired();

	String getCertificateFile();

	void setCertificateFile(String certificateFile);

	boolean autoRefreshToken();

}
