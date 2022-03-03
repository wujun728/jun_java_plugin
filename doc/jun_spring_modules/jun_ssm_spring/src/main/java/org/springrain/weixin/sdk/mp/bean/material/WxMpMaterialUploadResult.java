package org.springrain.weixin.sdk.mp.bean.material;

import java.io.Serializable;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

public class WxMpMaterialUploadResult implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -128818731449449537L;
  private String mediaId;
  private String url;

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public static WxMpMaterialUploadResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialUploadResult.class);
  }

  @Override
  public String toString() {
    return "WxMpMaterialUploadResult [media_id=" + this.mediaId + ", url=" + this.url + "]";
  }

}

