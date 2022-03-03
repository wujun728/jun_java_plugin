package org.springrain.weixin.sdk.mp.bean.pay.result;

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
public class WxPayUnifiedOrderResult extends WxPayBaseResult {

  @XStreamAlias("prepay_id")
  private String prepayId;

  @XStreamAlias("trade_type")
  private String tradeType;

  @XStreamAlias("code_url")
  private String codeURL;

  public String getPrepayId() {
    return this.prepayId;
  }

  public void setPrepayId(String prepayId) {
    this.prepayId = prepayId;
  }

  public String getTradeType() {
    return this.tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public String getCodeURL() {
    return this.codeURL;
  }

  public void setCodeURL(String codeURL) {
    this.codeURL = codeURL;
  }
}
