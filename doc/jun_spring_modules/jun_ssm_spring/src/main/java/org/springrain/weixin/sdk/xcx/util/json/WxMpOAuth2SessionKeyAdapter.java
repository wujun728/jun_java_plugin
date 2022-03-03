package org.springrain.weixin.sdk.xcx.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.xcx.bean.result.WxMpOAuth2SessionKey;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpOAuth2SessionKeyAdapter implements JsonDeserializer<WxMpOAuth2SessionKey> {

	@Override
	public WxMpOAuth2SessionKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		WxMpOAuth2SessionKey sessionKey = new WxMpOAuth2SessionKey();
		JsonObject accessTokenJsonObject = json.getAsJsonObject();
		if (accessTokenJsonObject.get("openid") != null && !accessTokenJsonObject.get("openid").isJsonNull()) {
			sessionKey.setOpenId(GsonHelper.getAsString(accessTokenJsonObject.get("openid")));
		}
		if (accessTokenJsonObject.get("session_key") != null
				&& !accessTokenJsonObject.get("session_key").isJsonNull()) {
			sessionKey.setSessionKey(GsonHelper.getAsString(accessTokenJsonObject.get("session_key")));
		}
		if (accessTokenJsonObject.get("unionid") != null && !accessTokenJsonObject.get("unionid").isJsonNull()) {
			sessionKey.setUnionId(GsonHelper.getAsString(accessTokenJsonObject.get("unionid")));
		}
		return sessionKey;
	}

}
