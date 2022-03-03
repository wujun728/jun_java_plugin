package org.springrain.weixin.sdk.mp.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.mp.bean.WxMpMassPreviewMessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author springrain
 */
public class WxMpMassPreviewMessageGsonAdapter implements JsonSerializer<WxMpMassPreviewMessage> {
  @Override
  public JsonElement serialize(WxMpMassPreviewMessage wxMpMassPreviewMessage, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("towxname", wxMpMassPreviewMessage.getToWxUsername());
    if (WxConsts.MASS_MSG_NEWS.equals(wxMpMassPreviewMessage.getMsgType())) {
      JsonObject news = new JsonObject();
      news.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
      jsonObject.add(WxConsts.MASS_MSG_NEWS, news);
    }
    if (WxConsts.MASS_MSG_TEXT.equals(wxMpMassPreviewMessage.getMsgType())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("content", wxMpMassPreviewMessage.getContent());
      jsonObject.add(WxConsts.MASS_MSG_TEXT, sub);
    }
    if (WxConsts.MASS_MSG_VOICE.equals(wxMpMassPreviewMessage.getMsgType())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
      jsonObject.add(WxConsts.MASS_MSG_VOICE, sub);
    }
    if (WxConsts.MASS_MSG_IMAGE.equals(wxMpMassPreviewMessage.getMsgType())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
      jsonObject.add(WxConsts.MASS_MSG_IMAGE, sub);
    }
    if (WxConsts.MASS_MSG_VIDEO.equals(wxMpMassPreviewMessage.getMsgType())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
      jsonObject.add(WxConsts.MASS_MSG_VIDEO, sub);
    }
    jsonObject.addProperty("msgtype", wxMpMassPreviewMessage.getMsgType());
    return jsonObject;
  }
}
