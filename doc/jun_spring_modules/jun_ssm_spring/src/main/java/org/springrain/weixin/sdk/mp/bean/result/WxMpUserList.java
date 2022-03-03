package org.springrain.weixin.sdk.mp.bean.result;

import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * 关注者列表
 * @author springrain
 *
 */
public class WxMpUserList {

  protected int total = -1;
  protected int count = -1;
  protected List<String> openIds = new ArrayList<>();
  protected String nextOpenId;
  public int getTotal() {
    return this.total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getCount() {
    return this.count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public List<String> getOpenIds() {
    return this.openIds;
  }
  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }
  public String getNextOpenId() {
    return this.nextOpenId;
  }
  public void setNextOpenId(String nextOpenId) {
    this.nextOpenId = nextOpenId;
  }
  
  public static WxMpUserList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpUserList.class);
  }

  @Override
  public String toString() {
    return  WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}
