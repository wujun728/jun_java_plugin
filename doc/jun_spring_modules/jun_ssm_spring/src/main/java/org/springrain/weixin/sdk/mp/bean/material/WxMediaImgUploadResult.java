package org.springrain.weixin.sdk.mp.bean.material;

import java.io.Serializable;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * @author springrain
 */
public class WxMediaImgUploadResult implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1996392453428768829L;
  private String url;

  public static WxMediaImgUploadResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMediaImgUploadResult.class);
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
