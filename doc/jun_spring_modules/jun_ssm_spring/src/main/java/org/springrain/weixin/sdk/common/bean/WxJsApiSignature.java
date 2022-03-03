package org.springrain.weixin.sdk.common.bean;

import java.io.Serializable;

/**
 * jspai signature
 */
public class WxJsApiSignature implements Serializable {
  private static final long serialVersionUID = -1116808193154384804L;

  private String appid;

  private String noncestr;

  private long timestamp;

  private String url;

  private String signature;

  public String getSignature() {
    return this.signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getNoncestr() {
    return this.noncestr;
  }

  public void setNoncestr(String noncestr) {
    this.noncestr = noncestr;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAppid() {
    return this.appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

}
