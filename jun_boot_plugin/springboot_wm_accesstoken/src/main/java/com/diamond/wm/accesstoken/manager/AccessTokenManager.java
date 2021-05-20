package com.diamond.wm.accesstoken.manager;

import java.util.Calendar;

import org.json.JSONObject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.diamond.wm.accesstoken.entity.AccessToken;
import com.diamond.wm.accesstoken.enums.AccessTokenStatus;
import com.diamond.wm.accesstoken.enums.AccessTokenType;
import com.diamond.wm.accesstoken.exception.WechatRequestException;
import com.diamond.wm.accesstoken.utils.HttpRequest;

/**
 * 单例使用
 * @author Wujun
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccessTokenManager extends AbstractTokenManager{

	private String getAccessTokenUrl(String appid,String secret){
		return String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appid,secret);
	}
	
	@Override
	protected AccessToken getAccessToken(String appid, String secret) throws WechatRequestException {
		HttpRequest httpRequest = new HttpRequest(getAccessTokenUrl(appid, secret),HttpRequest.METHOD_GET);
		String body = httpRequest.body();
		JSONObject result = new JSONObject(body);
		if(result.has("access_token")){
			AccessToken token = new AccessToken();
			token.setAccessToken(result.getString("access_token"));
			token.setStatus(AccessTokenStatus.NEW);
			token.setType(AccessTokenType.WECHAT_ACCESS_TOKEN);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, result.getInt("expires_in"));
			token.setExpires(cal.getTime().getTime());
			return token;
		}else{
			throw new WechatRequestException(result);
		}
	}
	
	

}
