package org.springrain.weixin.sdk.mp.bean.pay.request;

import org.springrain.weixin.sdk.common.annotation.Required;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 微信支付-申请退款请求参数
 * 注释中各行每个字段描述对应如下：
 * <li>字段名
 * <li>变量名
 * <li>是否必填
 * <li>类型
 * <li>示例值
 * <li>描述
 * </pre>
 *
 * @author <a href="http://git.oschina.net/chunanyong/springrain">springrain(springrain)</a>
 *         Created by springrain on 2016-10-08.
 */
@XStreamAlias("xml")
public class WxPayRefundRequest {
  /**
   * <pre>
   * 公众账号ID
   * appid
   * 是
   * String(32)
   * wx8888888888888888
   * 微信分配的公众账号ID（企业号corpid即为此appId）
   * </pre>
   */
  @XStreamAlias("appid")
  private String appid;

  /**
   * <pre>
   * 商户号
   * mch_id
   * 是
   * String(32)
   * 1900000109
   * 微信支付分配的商户号
   * </pre>
   */
  @XStreamAlias("mch_id")
  private String mchId;

  /**
   * <pre>
   * 设备号
   * device_info
   * 否
   * String(32)
   * 13467007045764
   * 终端设备号
   * </pre>
   */
  @XStreamAlias("device_info")
  private String deviceInfo;

  /**
   * <pre>
   * 随机字符串
   * nonce_str
   * 是
   * String(32)
   * 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
   * 随机字符串，不长于32位。推荐随机数生成算法
   * </pre>
   */
  @XStreamAlias("nonce_str")
  private String nonceStr;

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
  private String sign;

  /**
   * <pre>
   * 微信订单号
   * transaction_id
   * 跟out_trade_no二选一
   * String(28)
   * 1217752501201400000000000000
   * 微信生成的订单号，在支付通知中有返回
   * </pre>
   */
  @XStreamAlias("transaction_id")
  private String transactionId;

  /**
   * <pre>
   * 商户订单号
   * out_trade_no
   * 跟transaction_id二选一
   * String(32)
   * 1217752501201400000000000000
   * 商户侧传给微信的订单号
   * </pre>
   */
  @XStreamAlias("out_trade_no")
  private String outTradeNo;

  /**
   * <pre>
   * 商户退款单号
   * out_refund_no
   * 是
   * String(32)
   * 1217752501201400000000000000
   * 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
   * </pre>
   */
  @Required
  @XStreamAlias("out_refund_no")
  private String outRefundNo;

  /**
   * <pre>
   * 订单金额
   * total_fee
   * 是
   * Int
   * 100
   * 订单总金额，单位为分，只能为整数，详见支付金额
   * </pre>
   */
  @Required
  @XStreamAlias("total_fee")
  private Integer totalFee;

  /**
   * <pre>
   * 退款金额
   * refund_fee
   * 是
   * Int
   * 100
   * 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
   * </pre>
   */
  @Required
  @XStreamAlias("refund_fee")
  private Integer refundFee;

  /**
   * <pre>
   * 货币种类
   * refund_fee_type
   * 否
   * String(8)
   * CNY
   * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
   * </pre>
   */
  @XStreamAlias("refund_fee_type")
  private String refundFeeType;

  /**
   * <pre>
   * 操作员
   * op_user_id
   * 是
   * String(32)
   * 1900000109
   * 操作员帐号, 默认为商户号
   * </pre>
   */
  //@Required
  @XStreamAlias("op_user_id")
  private String opUserId;

  /**
   * <pre>
   * 退款资金来源
   * refund_account
   * 否
   * String(30)
   * REFUND_SOURCE_RECHARGE_FUNDS
   * 仅针对老资金流商户使用，
   * <li>REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款），
   * <li>REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
   * </pre>
   */
  @XStreamAlias("refund_account")
  private String refundAccount;

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

  public String getDeviceInfo() {
    return this.deviceInfo;
  }

  public void setDeviceInfo(String deviceInfo) {
    this.deviceInfo = deviceInfo;
  }

  public String getNonceStr() {
    return this.nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getOutTradeNo() {
    return this.outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public String getOutRefundNo() {
    return this.outRefundNo;
  }

  public void setOutRefundNo(String outRefundNo) {
    this.outRefundNo = outRefundNo;
  }

  public Integer getTotalFee() {
    return this.totalFee;
  }

  public void setTotalFee(Integer totalFee) {
    this.totalFee = totalFee;
  }

  public Integer getRefundFee() {
    return this.refundFee;
  }

  public void setRefundFee(Integer refundFee) {
    this.refundFee = refundFee;
  }

  public String getRefundFeeType() {
    return this.refundFeeType;
  }

  public void setRefundFeeType(String refundFeeType) {
    this.refundFeeType = refundFeeType;
  }

  public String getOpUserId() {
    return this.opUserId;
  }

  public void setOpUserId(String opUserId) {
    this.opUserId = opUserId;
  }

  public String getRefundAccount() {
    return this.refundAccount;
  }

  public void setRefundAccount(String refundAccount) {
    this.refundAccount = refundAccount;
  }
}
