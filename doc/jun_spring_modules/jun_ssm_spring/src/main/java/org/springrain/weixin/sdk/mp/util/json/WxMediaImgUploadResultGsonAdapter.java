package org.springrain.weixin.sdk.mp.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.material.WxMediaImgUploadResult;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author springrain
 */
public class WxMediaImgUploadResultGsonAdapter implements JsonDeserializer<WxMediaImgUploadResult> {
  @Override
  public WxMediaImgUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    WxMediaImgUploadResult wxMediaImgUploadResult = new WxMediaImgUploadResult();
    JsonObject jsonObject = json.getAsJsonObject();
    if (null != jsonObject.get("url") && !jsonObject.get("url").isJsonNull()) {
      wxMediaImgUploadResult.setUrl(GsonHelper.getAsString(jsonObject.get("url")));
    }
    return wxMediaImgUploadResult;
  }
}
