package org.springrain.weixin.sdk.mp.bean.kefu.result;

import java.util.List;

import org.springrain.weixin.sdk.common.util.ToStringUtils;
import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author springrain
 *
 */
public class WxMpKfSessionList {
  /**
   * 会话列表
   */
  @SerializedName("sessionlist")
  private List<WxMpKfSession> kfSessionList;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public static WxMpKfSessionList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json,
        WxMpKfSessionList.class);
  }

  public List<WxMpKfSession> getKfSessionList() {
    return this.kfSessionList;
  }

  public void setKfSessionList(List<WxMpKfSession> kfSessionList) {
    this.kfSessionList = kfSessionList;
  }
}
