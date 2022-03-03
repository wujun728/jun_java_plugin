package org.springrain.weixin.sdk.mp.bean;


import org.springrain.weixin.sdk.common.util.ToStringUtils;

/**
 * 微信卡券
 *
 * @author springrain
 * @version 2017/1/8
 */
public class WxMpCard {

  private String cardId;

  private Long beginTime;

  private Long endTime;

  private String userCardStatus;

  private Boolean canConsume;

  public String getCardId() {
    return this.cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public Long getBeginTime() {
    return this.beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public Long getEndTime() {
    return this.endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  public String getUserCardStatus() {
    return this.userCardStatus;
  }

  public void setUserCardStatus(String userCardStatus) {
    this.userCardStatus = userCardStatus;
  }

  public Boolean getCanConsume() {
    return this.canConsume;
  }

  public void setCanConsume(Boolean canConsume) {
    this.canConsume = canConsume;
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }
}
