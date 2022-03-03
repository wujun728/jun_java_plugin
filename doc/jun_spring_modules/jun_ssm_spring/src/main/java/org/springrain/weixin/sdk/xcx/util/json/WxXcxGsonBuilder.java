package org.springrain.weixin.sdk.xcx.util.json;

import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.util.json.WxErrorAdapter;
import org.springrain.weixin.sdk.xcx.bean.result.WxMpOAuth2SessionKey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxXcxGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpOAuth2SessionKey.class, new WxMpOAuth2SessionKeyAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());

  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
