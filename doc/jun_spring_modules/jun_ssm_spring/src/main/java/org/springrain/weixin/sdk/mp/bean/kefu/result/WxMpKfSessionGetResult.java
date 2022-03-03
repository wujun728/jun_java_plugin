package org.springrain.weixin.sdk.mp.bean.kefu.result;

import org.springrain.weixin.sdk.common.util.ToStringUtils;
import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author springrain
 *
 */
public class WxMpKfSessionGetResult {
  /**
   * kf_account 正在接待的客服，为空表示没有人在接待
   */
  @SerializedName("kf_account")
  private String kfAccount;

  /**
   * createtime 会话接入的时间 ，UNIX时间戳
   */
  @SerializedName("createtime")
  private long createTime;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public static WxMpKfSessionGetResult fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfSessionGetResult.class);
  }

  public String getKfAccount() {
    return this.kfAccount;
  }

  public void setKfAccount(String kfAccount) {
    this.kfAccount = kfAccount;
  }

  public long getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

}
