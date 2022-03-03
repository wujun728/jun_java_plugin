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
import org.springrain.weixin.sdk.mp.bean.result.WxMpQrCodeTicket;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * 
 * @author springrain
 *
 */
public class WxQrCodeTicketAdapter implements JsonDeserializer<WxMpQrCodeTicket> {

  @Override
  public WxMpQrCodeTicket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    WxMpQrCodeTicket ticket = new WxMpQrCodeTicket();
    JsonObject ticketJsonObject = json.getAsJsonObject();

    if (ticketJsonObject.get("ticket") != null && !ticketJsonObject.get("ticket").isJsonNull()) {
      ticket.setTicket(GsonHelper.getAsString(ticketJsonObject.get("ticket")));
    }
    if (ticketJsonObject.get("expire_seconds") != null && !ticketJsonObject.get("expire_seconds").isJsonNull()) {
      ticket.setExpire_seconds(GsonHelper.getAsPrimitiveInt(ticketJsonObject.get("expire_seconds")));
    }
    if (ticketJsonObject.get("url") != null && !ticketJsonObject.get("url").isJsonNull()) {
      ticket.setUrl(GsonHelper.getAsString(ticketJsonObject.get("url")));
    }
    return ticket;
  }
  
}
