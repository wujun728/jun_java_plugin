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
import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNewsBatchGetResult;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpMaterialNewsBatchGetGsonAdapter implements JsonDeserializer<WxMpMaterialNewsBatchGetResult> {

  @Override
  public WxMpMaterialNewsBatchGetResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = new WxMpMaterialNewsBatchGetResult();
    JsonObject json = jsonElement.getAsJsonObject();
    if (json.get("total_count") != null && !json.get("total_count").isJsonNull()) {
      wxMpMaterialNewsBatchGetResult.setTotalCount(GsonHelper.getAsInteger(json.get("total_count")));
    }
    if (json.get("item_count") != null && !json.get("item_count").isJsonNull()) {
      wxMpMaterialNewsBatchGetResult.setItemCount(GsonHelper.getAsInteger(json.get("item_count")));
    }
    if (json.get("item") != null && !json.get("item").isJsonNull()) {
      JsonArray item = json.getAsJsonArray("item");
      List<WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem> items = new ArrayList<>();
      for (JsonElement anItem : item) {
        JsonObject articleInfo = anItem.getAsJsonObject();
        items.add(WxMpGsonBuilder.create().fromJson(articleInfo, WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem.class));
      }
      wxMpMaterialNewsBatchGetResult.setItems(items);
    }
    return wxMpMaterialNewsBatchGetResult;
  }
}
