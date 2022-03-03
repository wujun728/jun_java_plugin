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

import org.springrain.weixin.sdk.mp.bean.template.WxMpTemplateData;
import org.springrain.weixin.sdk.mp.bean.template.WxMpTemplateMessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class WxMpTemplateMessageGsonAdapter implements JsonSerializer<WxMpTemplateMessage> {

  @Override
  public JsonElement serialize(WxMpTemplateMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    messageJson.addProperty("touser", message.getToUser());
    messageJson.addProperty("template_id", message.getTemplateId());
    if (message.getUrl() != null) {
      messageJson.addProperty("url", message.getUrl());
    }
    
    if(message.getFormId()!=null){
       messageJson.addProperty("form_id", message.getFormId());
    }
    if(message.getPage()!=null){
        messageJson.addProperty("page", message.getPage());
     }

    JsonObject data = new JsonObject();
    messageJson.add("data", data);

    for (WxMpTemplateData datum : message.getData()) {
      JsonObject dataJson = new JsonObject();
      dataJson.addProperty("value", datum.getValue());
      if (datum.getColor() != null) {
        dataJson.addProperty("color", datum.getColor());
      }
      data.add(datum.getName(), dataJson);
    }

    return messageJson;
  }

}
