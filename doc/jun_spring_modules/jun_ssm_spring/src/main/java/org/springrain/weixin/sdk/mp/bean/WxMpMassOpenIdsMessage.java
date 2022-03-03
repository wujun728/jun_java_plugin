package org.springrain.weixin.sdk.mp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * openid列表群发的消息
 * 
 * @author springrain
 */
public class WxMpMassOpenIdsMessage implements Serializable {
  private static final long serialVersionUID = -8022910911104788999L;
  
  private List<String> toUsers = new ArrayList<>();
  private String msgType;
  private String content;
  private String mediaId;

  public WxMpMassOpenIdsMessage() {
    super();
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

  /**
   * openid列表，最多支持10,000个
   */
  public List<String> getToUsers() {
    return this.toUsers;
  }

  /**
   * 添加openid，最多支持10,000个
   * @param openid
   */
  public void addUser(String openid) {
    this.toUsers.add(openid);
  }

  /**
   * 提供set方法，方便客户端直接设置所有群发对象的openid列表
   * @param toUsers
   */
  public void setToUsers(List<String> toUsers) {
    this.toUsers = toUsers;
  }

}
