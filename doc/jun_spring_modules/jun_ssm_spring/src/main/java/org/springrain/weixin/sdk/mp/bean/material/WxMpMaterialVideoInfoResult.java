package org.springrain.weixin.sdk.mp.bean.material;

import java.io.Serializable;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

public class WxMpMaterialVideoInfoResult implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1269131745333792202L;
  private String title;
  private String description;
  private String downUrl;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDownUrl() {
    return this.downUrl;
  }

  public void setDownUrl(String downUrl) {
    this.downUrl = downUrl;
  }

  public static WxMpMaterialVideoInfoResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialVideoInfoResult.class);
  }

  @Override
  public String toString() {
    return "WxMpMaterialVideoInfoResult [title=" + this.title + ", description=" + this.description + ", downUrl=" + this.downUrl + "]";
  }

}
