package org.springrain.weixin.sdk.mp.bean.store;

import org.springrain.weixin.sdk.common.util.ToStringUtils;

import com.google.gson.annotations.SerializedName;

public class WxMpStoreInfo {
  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  @SerializedName("base_info")
  private WxMpStoreBaseInfo baseInfo;

  public WxMpStoreBaseInfo getBaseInfo() {
    return this.baseInfo;
  }

  public void setBaseInfo(WxMpStoreBaseInfo baseInfo) {
    this.baseInfo = baseInfo;
  }

}
