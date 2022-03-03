/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package org.springrain.weixin.sdk.common.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.bean.menu.WxMenu;
import org.springrain.weixin.sdk.common.bean.menu.WxMenuButton;
import org.springrain.weixin.sdk.common.bean.menu.WxMenuRule;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * @author springrain
 */
public class WxMenuGsonAdapter implements JsonSerializer<WxMenu>, JsonDeserializer<WxMenu> {

  @Override
  public JsonElement serialize(WxMenu menu, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();

    JsonArray buttonArray = new JsonArray();
    for (WxMenuButton button : menu.getButtons()) {
      JsonObject buttonJson = convertToJson(button);
      buttonArray.add(buttonJson);
    }
    json.add("button", buttonArray);

    if (menu.getMatchRule() != null) {
      json.add("matchrule", convertToJson(menu.getMatchRule()));
    }

    return json;
  }

  protected JsonObject convertToJson(WxMenuButton button) {
    JsonObject buttonJson = new JsonObject();
    buttonJson.addProperty("type", button.getType());
    buttonJson.addProperty("name", button.getName());
    buttonJson.addProperty("key", button.getKey());
    buttonJson.addProperty("url", button.getUrl());
    buttonJson.addProperty("media_id", button.getMediaId());
    if (button.getSubButtons() != null && button.getSubButtons().size() > 0) {
      JsonArray buttonArray = new JsonArray();
      for (WxMenuButton sub_button : button.getSubButtons()) {
        buttonArray.add(convertToJson(sub_button));
      }
      buttonJson.add("sub_button", buttonArray);
    }
    return buttonJson;
  }

  protected JsonObject convertToJson(WxMenuRule menuRule) {
    JsonObject matchRule = new JsonObject();
    matchRule.addProperty("tag_id", menuRule.getTagId());
    matchRule.addProperty("sex", menuRule.getSex());
    matchRule.addProperty("country", menuRule.getCountry());
    matchRule.addProperty("province", menuRule.getProvince());
    matchRule.addProperty("city", menuRule.getCity());
    matchRule.addProperty("client_platform_type", menuRule.getClientPlatformType());
    matchRule.addProperty("language", menuRule.getLanguage());
    return matchRule;
  }

  @Override
  public WxMenu deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    /*
     * 操蛋的微信
     * 创建菜单时是 { button : ... }
     * 查询菜单时是 { menu : { button : ... } }
     */
    WxMenu menu = new WxMenu();
    JsonObject menuJson = json.getAsJsonObject().get("menu").getAsJsonObject();
    JsonArray buttonsJson = menuJson.get("button").getAsJsonArray();
    for (int i = 0; i < buttonsJson.size(); i++) {
      JsonObject buttonJson = buttonsJson.get(i).getAsJsonObject();
      WxMenuButton button = convertFromJson(buttonJson);
      menu.getButtons().add(button);
      if (buttonJson.get("sub_button") == null || buttonJson.get("sub_button").isJsonNull()) {
        continue;
      }
      JsonArray sub_buttonsJson = buttonJson.get("sub_button").getAsJsonArray();
      for (int j = 0; j < sub_buttonsJson.size(); j++) {
        JsonObject sub_buttonJson = sub_buttonsJson.get(j).getAsJsonObject();
        button.getSubButtons().add(convertFromJson(sub_buttonJson));
      }
    }
    return menu;
  }

  protected WxMenuButton convertFromJson(JsonObject json) {
    WxMenuButton button = new WxMenuButton();
    button.setName(GsonHelper.getString(json, "name"));
    button.setKey(GsonHelper.getString(json, "key"));
    button.setUrl(GsonHelper.getString(json, "url"));
    button.setType(GsonHelper.getString(json, "type"));
    button.setMediaId(GsonHelper.getString(json, "media_id"));
    return button;
  }

}
