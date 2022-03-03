package org.springrain.weixin.sdk.mp.bean.menu;

import org.springrain.weixin.sdk.common.util.ToStringUtils;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * Created by springrain on 2016-11-25.
 * @author <a href="http://git.oschina.net/chunanyong/springrain">springrain(springrain)</a>
 * </pre>
 */
public class WxMpGetSelfMenuInfoResult {
  @SerializedName("selfmenu_info")
  private WxMpSelfMenuInfo selfMenuInfo;

  @SerializedName("is_menu_open")
  private Integer isMenuOpen;

  public static WxMpGetSelfMenuInfoResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMpGetSelfMenuInfoResult.class);
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public WxMpSelfMenuInfo getSelfMenuInfo() {
    return selfMenuInfo;
  }

  public void setSelfMenuInfo(WxMpSelfMenuInfo selfMenuInfo) {
    this.selfMenuInfo = selfMenuInfo;
  }

  public Integer getIsMenuOpen() {
    return isMenuOpen;
  }

  public void setIsMenuOpen(Integer isMenuOpen) {
    this.isMenuOpen = isMenuOpen;
  }
}
