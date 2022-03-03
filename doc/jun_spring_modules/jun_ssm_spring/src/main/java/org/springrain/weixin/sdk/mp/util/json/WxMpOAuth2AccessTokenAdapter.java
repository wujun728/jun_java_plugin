package org.springrain.weixin.sdk.mp.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.result.WxMpOAuth2AccessToken;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpOAuth2AccessTokenAdapter implements JsonDeserializer<WxMpOAuth2AccessToken> {

  @Override
  public WxMpOAuth2AccessToken deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
      JsonParseException {
    WxMpOAuth2AccessToken accessToken = new WxMpOAuth2AccessToken();
    JsonObject accessTokenJsonObject = json.getAsJsonObject();

    if (accessTokenJsonObject.get("access_token") != null && !accessTokenJsonObject.get("access_token").isJsonNull()) {
      accessToken.setAccessToken(GsonHelper.getAsString(accessTokenJsonObject.get("access_token")));
    }
    if (accessTokenJsonObject.get("expires_in") != null && !accessTokenJsonObject.get("expires_in").isJsonNull()) {
      accessToken.setExpiresIn(GsonHelper.getAsPrimitiveInt(accessTokenJsonObject.get("expires_in")));
    }
    if (accessTokenJsonObject.get("refresh_token") != null && !accessTokenJsonObject.get("refresh_token").isJsonNull()) {
      accessToken.setRefreshToken(GsonHelper.getAsString(accessTokenJsonObject.get("refresh_token")));
    }
    if (accessTokenJsonObject.get("openid") != null && !accessTokenJsonObject.get("openid").isJsonNull()) {
      accessToken.setOpenId(GsonHelper.getAsString(accessTokenJsonObject.get("openid")));
    }
    if (accessTokenJsonObject.get("scope") != null && !accessTokenJsonObject.get("scope").isJsonNull()) {
      accessToken.setScope(GsonHelper.getAsString(accessTokenJsonObject.get("scope")));
    }
    if (accessTokenJsonObject.get("unionid") != null && !accessTokenJsonObject.get("unionid").isJsonNull()) {
      accessToken.setUnionId(GsonHelper.getAsString(accessTokenJsonObject.get("unionid")));
    }
    return accessToken;
  }

}
