package org.springrain.weixin.sdk.mp.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserBlacklistGetResult;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * @author springrain
 */
public class WxUserBlacklistGetResultGsonAdapter implements JsonDeserializer<WxMpUserBlacklistGetResult> {
  @Override
  public WxMpUserBlacklistGetResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    WxMpUserBlacklistGetResult wxMpUserBlacklistGetResult = new WxMpUserBlacklistGetResult();
    wxMpUserBlacklistGetResult.setTotal(GsonHelper.getInteger(o, "total"));
    wxMpUserBlacklistGetResult.setCount(GsonHelper.getInteger(o, "count"));
    wxMpUserBlacklistGetResult.setNextOpenid(GsonHelper.getString(o, "next_openid"));
    if (o.get("data") != null && !o.get("data").isJsonNull() && !o.get("data").getAsJsonObject().get("openid").isJsonNull()) {
      JsonArray data = o.get("data").getAsJsonObject().get("openid").getAsJsonArray();
      for (int i = 0; i < data.size(); i++) {
        wxMpUserBlacklistGetResult.getOpenidList().add(GsonHelper.getAsString(data.get(i)));
      }
    }
    return wxMpUserBlacklistGetResult;
  }
}
