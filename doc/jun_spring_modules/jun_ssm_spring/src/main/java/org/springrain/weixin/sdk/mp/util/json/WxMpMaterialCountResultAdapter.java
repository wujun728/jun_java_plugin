/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package org.springrain.weixin.sdk.mp.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialCountResult;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * @author springrain
 */
public class WxMpMaterialCountResultAdapter implements JsonDeserializer<WxMpMaterialCountResult> {

  @Override
  public WxMpMaterialCountResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    WxMpMaterialCountResult countResult = new WxMpMaterialCountResult();
    JsonObject materialCountResultJsonObject = json.getAsJsonObject();

    if (materialCountResultJsonObject.get("voice_count") != null && !materialCountResultJsonObject.get("voice_count").isJsonNull()) {
      countResult.setVoiceCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("voice_count")));
    }
    if (materialCountResultJsonObject.get("video_count") != null && !materialCountResultJsonObject.get("video_count").isJsonNull()) {
      countResult.setVideoCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("video_count")));
    }
    if (materialCountResultJsonObject.get("image_count") != null && !materialCountResultJsonObject.get("image_count").isJsonNull()) {
      countResult.setImageCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("image_count")));
    }
    if (materialCountResultJsonObject.get("news_count") != null && !materialCountResultJsonObject.get("news_count").isJsonNull()) {
      countResult.setNewsCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("news_count")));
    }
    return countResult;
  }

}
