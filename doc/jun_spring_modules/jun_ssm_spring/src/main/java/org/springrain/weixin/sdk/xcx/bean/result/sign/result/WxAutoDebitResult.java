package org.springrain.weixin.sdk.xcx.bean.result.sign.result;

import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayBaseResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"返回的结果
 * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
 * </pre>
 *
 * @author springrain
 */
@XStreamAlias("xml")
public class WxAutoDebitResult extends WxPayBaseResult {

	  /**
	   * <pre>设备号
	   * device_info
	   * 否
	   * String(32)
	   * 013467007045764
	   * 微信支付分配的终端设备号，
	   * </pre>
	   */
	  @XStreamAlias("device_info")
	  private String deviceInfo;

	  /**
	   * <pre>用户标识
	   * openid
	   * 是
	   * String(128)
	   * oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
	   * 用户在商户appid下的唯一标识
	   * </pre>
	   */
	  @XStreamAlias("openid")
	  private String openid;

	  /**
	   * <pre>是否关注公众账号
	   * is_subscribe
	   * 否
	   * String(1)
	   * Y
	   * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	   * </pre>
	   */
	  @XStreamAlias("is_subscribe")
	  private String isSubscribe;

	  /**
	   * <pre>交易状态
	   * trade_state
	   * 是
	   * String(32)
	   * SUCCESS
	   * SUCCESS—支付成功,REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败)
	   * </pre>
	   */
	  @XStreamAlias("trade_state")
	  private String tradeState;

	  /**
	   * <pre>付款银行
	   * bank_type
	   * 是
	   * String(16)
	   * CMC
	   * 银行类型，采用字符串类型的银行标识
	   * </pre>
	   */
	  @XStreamAlias("bank_type")
	  private String bankType;

	  /**
	   * <pre>订单金额
	   * total_fee
	   * 是
	   * Int
	   * 100
	   * 订单总金额，单位为分
	   * </pre>
	   */
	  @XStreamAlias("total_fee")
	  private Integer totalFee;

	  /**
	   * <pre>应结订单金额
	   * settlement_total_fee
	   * 否
	   * Int
	   * 100
	   * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
	   * </pre>
	   */
	  @XStreamAlias("settlement_total_fee")
	  private Integer settlementTotalFee;

	  /**
	   * <pre>货币种类
	   * fee_type
	   * 否
	   * String(8)
	   * CNY
	   * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	   * </pre>
	   */
	  @XStreamAlias("fee_type")
	  private String feeType;

	  /**
	   * <pre>现金支付金额
	   * cash_fee
	   * 是
	   * Int
	   * 100
	   * 现金支付金额订单现金支付金额，详见支付金额
	   * </pre>
	   */
	  @XStreamAlias("cash_fee")
	  private Integer cashFee;

	  /**
	   * <pre>现金支付货币类型
	   * cash_fee_type
	   * 否
	   * String(16)
	   * CNY
	   * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	   * </pre>
	   */
	  @XStreamAlias("cash_fee_type")
	  private String cashFeeType;

	  /**
	   * <pre>代金券金额
	   * coupon_fee
	   * 否
	   * Int
	   * 100
	   * “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额
	   * </pre>
	   */
	  @XStreamAlias("coupon_fee")
	  private Integer couponFee;

	  /**
	   * <pre>代金券使用数量
	   * coupon_count
	   * 否
	   * Int
	   * 1
	   * 代金券使用数量
	   * </pre>
	   */
	  @XStreamAlias("coupon_count")
	  private Integer couponCount;


	  /**
	   * <pre>微信支付订单号
	   * transaction_id
	   * 是
	   * String(32)
	   * 1009660380201506130728806387
	   * 微信支付订单号
	   * </pre>
	   */
	  @XStreamAlias("transaction_id")
	  private String transactionId;

	  /**
	   * <pre>商户订单号
	   * out_trade_no
	   * 是
	   * String(32)
	   * 20150806125346
	   * 商户系统的订单号，与请求一致。
	   * </pre>
	   */
	  @XStreamAlias("out_trade_no")
	  private String outTradeNo;

	  /**
	   * <pre>附加数据
	   * attach
	   * 否
	   * String(128)
	   * 深圳分店
	   * 附加数据，原样返回
	   * </pre>
	   */
	  @XStreamAlias("attach")
	  private String attach;

	  /**
	   * <pre>支付完成时间
	   * time_end
	   * 是
	   * String(14)
	   * 20141030133525
	   * 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	   * </pre>
	   */
	  @XStreamAlias("time_end")
	  private String timeEnd;
	  
	  /**
	   * 用户签约协议id
	   */
	  @XStreamAlias("contract_id")
	  private String contractId;


	  public String getDeviceInfo() {
	    return this.deviceInfo;
	  }

	  public void setDeviceInfo(String deviceInfo) {
	    this.deviceInfo = deviceInfo;
	  }

	  public String getOpenid() {
	    return this.openid;
	  }

	  public void setOpenid(String openid) {
	    this.openid = openid;
	  }

	  public String getIsSubscribe() {
	    return this.isSubscribe;
	  }

	  public void setIsSubscribe(String isSubscribe) {
	    this.isSubscribe = isSubscribe;
	  }


	  public String getTradeState() {
	    return this.tradeState;
	  }

	  public void setTradeState(String tradeState) {
	    this.tradeState = tradeState;
	  }

	  public String getBankType() {
	    return this.bankType;
	  }

	  public void setBankType(String bankType) {
	    this.bankType = bankType;
	  }

	  public Integer getTotalFee() {
	    return this.totalFee;
	  }

	  public void setTotalFee(Integer totalFee) {
	    this.totalFee = totalFee;
	  }

	  public Integer getSettlementTotalFee() {
	    return this.settlementTotalFee;
	  }

	  public void setSettlementTotalFee(Integer settlementTotalFee) {
	    this.settlementTotalFee = settlementTotalFee;
	  }

	  public String getFeeType() {
	    return this.feeType;
	  }

	  public void setFeeType(String feeType) {
	    this.feeType = feeType;
	  }

	  public Integer getCashFee() {
	    return this.cashFee;
	  }

	  public void setCashFee(Integer cashFee) {
	    this.cashFee = cashFee;
	  }

	  public String getCashFeeType() {
	    return this.cashFeeType;
	  }

	  public void setCashFeeType(String cashFeeType) {
	    this.cashFeeType = cashFeeType;
	  }

	  public Integer getCouponFee() {
	    return this.couponFee;
	  }

	  public void setCouponFee(Integer couponFee) {
	    this.couponFee = couponFee;
	  }

	  public Integer getCouponCount() {
	    return this.couponCount;
	  }

	  public void setCouponCount(Integer couponCount) {
	    this.couponCount = couponCount;
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

	  public String getAttach() {
	    return this.attach;
	  }

	  public void setAttach(String attach) {
	    this.attach = attach;
	  }

	  public String getTimeEnd() {
	    return this.timeEnd;
	  }

	  public void setTimeEnd(String timeEnd) {
	    this.timeEnd = timeEnd;
	  }

	  public String getContractId() {
		return contractId;
	  }
	
	  public void setContractId(String contractId) {
		this.contractId = contractId;
	  }


	  
	
}
