package org.springrain.weixin.sdk.mp.bean;

import java.io.Serializable;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * 按标签群发的消息
 * 
 * @author springrain
 */
public class WxMpMassTagMessage implements Serializable {
  
  private static final long serialVersionUID = -6625914040986749286L;
  private Long tagId;
  private String msgtype;
  private String content;
  private String mediaId;

  public WxMpMassTagMessage() {
    super();
  }
  
  public String getMsgtype() {
    return this.msgtype;
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
   * @param msgtype
   */
  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
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

  public Long getTagId() {
    return this.tagId;
  }

  /**
   * 如果不设置则就意味着发给所有用户
   * @param tagId
   */
  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

}
