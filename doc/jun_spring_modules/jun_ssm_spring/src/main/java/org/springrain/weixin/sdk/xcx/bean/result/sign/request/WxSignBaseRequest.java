package org.springrain.weixin.sdk.xcx.bean.result.sign.request;

import org.springrain.weixin.sdk.common.util.ToStringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * Created by springrain on 2016-10-24.
 *  微信支付请求对象共用的参数存放类
 * 注释中各行每个字段描述对应如下：
 * <li>字段名
 * <li>变量名
 * <li>是否必填
 * <li>类型
 * <li>示例值
 * <li>描述
 * </pre>
 * @author <a href="http://git.oschina.net/chunanyong/springrain">springrain(springrain)</a>
 */
public abstract class WxSignBaseRequest {
  /**
   * <pre>
   * 公众账号ID
   * appid
   * 是
   * String(32)
   * wxd678efh567hg6787
   * 微信分配的公众账号ID（企业号corpid即为此appId）
   * </pre>
   */
  @XStreamAlias("appid")
  protected String appid;
  /**
   * <pre>
   * 商户号
   * mch_id
   * 是
   * String(32)
   * 1230000109
   * 微信支付分配的商户号
   * </pre>
   */
  @XStreamAlias("mch_id")
  protected String mchId;
  /**
   * <pre>
   * 签名
   * sign
   * 是
   * String(32)
   * C380BEC2BFD727A4B6845133519F3AD6
   * 签名，详见签名生成算法
   * </pre>
   */
  @XStreamAlias("sign")
  protected String sign;

  public String getAppid() {
    return this.appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getMchId() {
    return this.mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }
}
