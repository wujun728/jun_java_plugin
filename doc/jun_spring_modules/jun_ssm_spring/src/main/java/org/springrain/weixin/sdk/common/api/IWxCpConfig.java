package org.springrain.weixin.sdk.common.api;

public interface IWxCpConfig extends IWxConfig {

	String getCorpId();

	void setCorpId(String corpId);

	Integer getAgentId();

	void setAgentId(Integer agentId);

	String getCorpSecret();

	void setCorpSecret(String secret);

	String getOauth2redirectUri();

	void setOauth2redirectUri(String oauth2redirectUri);

}
