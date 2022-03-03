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
import java.util.Date;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNews;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNewsBatchGetResult;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpMaterialNewsBatchGetGsonItemAdapter implements JsonDeserializer<WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem> {

  @Override
  public WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem wxMaterialNewsBatchGetNewsItem = new WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem();
    JsonObject json = jsonElement.getAsJsonObject();
    if (json.get("media_id") != null && !json.get("media_id").isJsonNull()) {
      wxMaterialNewsBatchGetNewsItem.setMediaId(GsonHelper.getAsString(json.get("media_id")));
    }
    if (json.get("update_time") != null && !json.get("update_time").isJsonNull()) {
      wxMaterialNewsBatchGetNewsItem.setUpdateTime(new Date(1000 * GsonHelper.getAsLong(json.get("update_time"))));
    }
    if (json.get("content") != null && !json.get("content").isJsonNull()) {
      JsonObject newsItem = json.getAsJsonObject("content");
      wxMaterialNewsBatchGetNewsItem.setContent(WxMpGsonBuilder.create().fromJson(newsItem, WxMpMaterialNews.class));
    }
    return wxMaterialNewsBatchGetNewsItem;
  }
}
