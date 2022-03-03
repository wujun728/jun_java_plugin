package org.springrain.weixin.sdk.mp.util.json;

import java.lang.reflect.Type;

import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.mp.bean.WxMpCard;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Created by springrain on 2017/1/8.
 *
 * @author springrain
 * @version 2017/1/8
 */
public class WxMpCardGsonAdapter implements JsonDeserializer<WxMpCard> {

  @Override
  public WxMpCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext
      jsonDeserializationContext) throws JsonParseException {
    WxMpCard card = new WxMpCard();
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    card.setCardId(GsonHelper.getString(jsonObject, "card_id"));
    card.setBeginTime(GsonHelper.getLong(jsonObject, "begin_time"));
    card.setEndTime(GsonHelper.getLong(jsonObject, "end_time"));

    return card;
  }

}
