package org.springrain.weixin.sdk.mp.bean;

import java.io.Serializable;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * @author springrain
 */
public class WxMpMassPreviewMessage implements Serializable {
  private static final long serialVersionUID = 9095211638358424020L;
  private String toWxUsername;
  private String msgType;
  private String content;
  private String mediaId;

  public WxMpMassPreviewMessage() {
    super();
  }

  public String getToWxUsername() {
    return this.toWxUsername;
  }

  public void setToWxUsername(String toWxUsername) {
    this.toWxUsername = toWxUsername;
  }

  public String getMsgType() {
    return this.msgType;
  }

  /**
   * <pre>
   * 请使用
   * {@link org.springrain.weixin.sdk.common.api.WxConsts#MASS_MSG_IMAGE}
   * {@link org.springrain.weixin.sdk.common.api.WxConsts#MASS_MSG_NEWS}
   * {@link org.springrain.weixin.sdk.common.api.WxConsts#MASS_MSG_TEXT}
   * {@link org.springrain.weixin.sdk.common.api.WxConsts#MASS_MSG_VIDEO}
   * {@link org.springrain.weixin.sdk.common.api.WxConsts#MASS_MSG_VOICE}
   * 如果msgtype和media_id不匹配的话，会返回系统繁忙的错误
   * </pre>
   *
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}
