package org.springrain.weixin.sdk.mp.bean.datacube;

import org.springrain.weixin.sdk.common.util.ToStringUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 统计接口的共用属性类
 * @author <a href="http://git.oschina.net/chunanyong/springrain">springrain(springrain)</a>
 *         Created by springrain on 2017/1/8.
 */
public class WxDataCubeBaseResult {
  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  /**
   * ref_date
   * 数据的日期，需在begin_date和end_date之间
   */
  @SerializedName("ref_date")
  private String refDate;

  public String getRefDate() {
    return this.refDate;
  }

  public void setRefDate(String refDate) {
    this.refDate = refDate;
  }

}
