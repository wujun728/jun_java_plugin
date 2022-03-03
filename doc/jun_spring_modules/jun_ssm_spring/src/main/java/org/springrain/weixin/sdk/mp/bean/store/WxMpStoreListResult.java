package org.springrain.weixin.sdk.mp.bean.store;

import java.util.List;

import org.springrain.weixin.sdk.common.util.ToStringUtils;
import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * 门店列表结果类
 * @author <a href="http://git.oschina.net/chunanyong/springrain">springrain(springrain)</a>
 *         Created by springrain on 2016-09-27.
 *
 */
public class WxMpStoreListResult {
  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public static WxMpStoreListResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpStoreListResult.class);
  }

  /**
   * 错误码，0为正常
   */
  @SerializedName("errcode")
  private Integer errCode;

  /**
   * 错误信息
   */
  @SerializedName("errmsg")
  private String errMsg;

  /**
   * 门店信息列表
   */
  @SerializedName("business_list")
  private List<WxMpStoreInfo> businessList;

  /**
   * 门店信息总数
   */
  @SerializedName("total_count")
  private Integer totalCount;

  public Integer getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getErrCode() {
    return this.errCode;
  }

  public void setErrCode(Integer errCode) {
    this.errCode = errCode;
  }

  public String getErrMsg() {
    return this.errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public List<WxMpStoreInfo> getBusinessList() {
    return this.businessList;
  }

  public void setBusinessList(List<WxMpStoreInfo> businessList) {
    this.businessList = businessList;
  }

}
