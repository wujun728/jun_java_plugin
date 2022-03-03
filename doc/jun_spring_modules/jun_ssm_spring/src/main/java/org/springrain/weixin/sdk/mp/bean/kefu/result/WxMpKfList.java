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
public class WxMpKfList {
  @SerializedName("kf_list")
  private List<WxMpKfInfo> kfList;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public List<WxMpKfInfo> getKfList() {
    return this.kfList;
  }

  public void setKfList(List<WxMpKfInfo> kfList) {
    this.kfList = kfList;
  }

  public static WxMpKfList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfList.class);
  }
}
