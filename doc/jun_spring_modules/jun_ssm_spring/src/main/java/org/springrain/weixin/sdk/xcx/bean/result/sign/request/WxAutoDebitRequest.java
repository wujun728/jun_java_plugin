package org.springrain.weixin.sdk.xcx.bean.result.sign.request;

import org.springrain.weixin.sdk.common.annotation.Required;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayBaseRequest;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 统一下单请求参数对象
 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * 注释中各行每个字段描述对应如下：
 * <li>字段名
 * <li>变量名
 * <li>是否必填
 * <li>类型
 * <li>示例值
 * <li>描述
 * </pre>
 * 
 * Created by springrain on 2017/1/25.
 * 
 * @author springrain (http://git.oschina.net/chunanyong/springrain)
 */
@XStreamAlias("xml")
public class WxAutoDebitRequest extends WxPayBaseRequest {

	/**
	 * <pre>
	 * 商品描述
	 * body
	 * 是
	 * String(128)
	 * 腾讯充值中心-QQ会员充值
	 * 商品简单描述，该字段须严格按照规范传递，具体请见参数规定
	 * </pre>
	 */
	@Required
	@XStreamAlias("body")
	private String body;

	/**
	 * <pre>
	 * 商品详情
	 * detail
	 * 否
	 * String(6000)
	 * </pre>
	 */
	@XStreamAlias("detail")
	private String detail;

	/**
	 * <pre>
	 * 附加数据
	 * attach
	 * 否
	 * String(127)
	 * 深圳分店
	 *  附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * </pre>
	 */
	@XStreamAlias("attach")
	private String attach;

	/**
	 * <pre>
	 * 商户订单号
	 * out_trade_no
	 * 是
	 * String(32)
	 * 20150806125346
	 * 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
	 * </pre>
	 */
	@Required
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	/**
	 * <pre>
	 * 货币类型
	 * fee_type
	 * 否
	 * String(16)
	 * CNY
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 * </pre>
	 */
	@XStreamAlias("fee_type")
	private String feeType;

	/**
	 * <pre>
	 * 总金额
	 * total_fee
	 * 是
	 * Int
	 * 888
	 * 订单总金额，单位为分，详见支付金额
	 * </pre>
	 */
	@Required
	@XStreamAlias("total_fee")
	private Integer totalFee;

	/**
	 * <pre>
	 * 终端IP
	 * spbill_create_ip
	 * 是
	 * String(16)
	 * 123.12.12.123
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	 * </pre>
	 */
	@Required
	@XStreamAlias("spbill_create_ip")
	private String spbillCreateIp;

	/**
	 * <pre>
	 * 商品标记
	 * goods_tag
	 * 否
	 * String(32)
	 * WXG
	 * 商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
	 * </pre>
	 */
	@XStreamAlias("goods_tag")
	private String goodsTag;

	/**
	 * <pre>
	 * 通知地址
	 * notify_url
	 * 是
	 * String(256)
	 * http://www.weixin.qq.com/wxpay/pay.php
	 * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	 * </pre>
	 */
	@Required
	@XStreamAlias("notify_url")
	private String notifyURL;

	/**
	 * <pre>
	 * 交易类型
	 * trade_type
	 * 是
	 * String(16)
	 * JSAPI
	 * 取值如下：JSAPI，NATIVE，APP，详细说明见参数规定:
	 * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	 * </pre>
	 */
	@Required
	@XStreamAlias("trade_type")
	private String tradeType;

	/**
	 * <pre>
	 * 委托代扣协议id
	 * contract_id
	 * 是
	 * String(32)
	 * 签约成功后，微信返回的委托代扣协议id
	 * </pre>
	 */
	@Required
	@XStreamAlias("contract_id")
	private String contractId;

	/**
	 * <pre>
	 * 用户标识
	 * openid
	 * 否
	 * String(128)
	 * oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
	 * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
	 * openid如何获取，可参考【获取openid】。
	 * 企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	 * </pre>
	 */
	@XStreamAlias("openid")
	private String openid;

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return this.attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Integer getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return this.spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getGoodsTag() {
		return this.goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getNotifyURL() {
		return this.notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public static WxUnifiedOrderRequestBuilder builder() {
		return new WxUnifiedOrderRequestBuilder();
	}

	public static class WxUnifiedOrderRequestBuilder {
		private String appid;
		private String mchId;
		private String nonceStr;
		private String sign;
		private String body;
		private String detail;
		private String attach;
		private String outTradeNo;
		private String feeType;
		private Integer totalFee;
		private String spbillCreateIp;
		private String goodsTag;
		private String notifyURL;
		private String tradeType;
		private String openid;
		private String contractId;

		public WxUnifiedOrderRequestBuilder appid(String appid) {
			this.appid = appid;
			return this;
		}

		public WxUnifiedOrderRequestBuilder mchId(String mchId) {
			this.mchId = mchId;
			return this;
		}

		public WxUnifiedOrderRequestBuilder nonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
			return this;
		}

		public WxUnifiedOrderRequestBuilder sign(String sign) {
			this.sign = sign;
			return this;
		}

		public WxUnifiedOrderRequestBuilder body(String body) {
			this.body = body;
			return this;
		}

		public WxUnifiedOrderRequestBuilder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public WxUnifiedOrderRequestBuilder attach(String attach) {
			this.attach = attach;
			return this;
		}

		public WxUnifiedOrderRequestBuilder outTradeNo(String outTradeNo) {
			this.outTradeNo = outTradeNo;
			return this;
		}

		public WxUnifiedOrderRequestBuilder feeType(String feeType) {
			this.feeType = feeType;
			return this;
		}

		public WxUnifiedOrderRequestBuilder totalFee(Integer totalFee) {
			this.totalFee = totalFee;
			return this;
		}

		public WxUnifiedOrderRequestBuilder spbillCreateIp(String spbillCreateIp) {
			this.spbillCreateIp = spbillCreateIp;
			return this;
		}

		public WxUnifiedOrderRequestBuilder contractId(String contractId) {
			this.contractId = contractId;
			return this;
		}

		public WxUnifiedOrderRequestBuilder goodsTag(String goodsTag) {
			this.goodsTag = goodsTag;
			return this;
		}

		public WxUnifiedOrderRequestBuilder notifyURL(String notifyURL) {
			this.notifyURL = notifyURL;
			return this;
		}

		public WxUnifiedOrderRequestBuilder tradeType(String tradeType) {
			this.tradeType = tradeType;
			return this;
		}


		public WxUnifiedOrderRequestBuilder openid(String openid) {
			this.openid = openid;
			return this;
		}

		public WxUnifiedOrderRequestBuilder from(WxAutoDebitRequest origin) {
			this.appid(origin.appid);
			this.mchId(origin.mchId);
			this.nonceStr(origin.nonceStr);
			this.sign(origin.sign);
			this.body(origin.body);
			this.detail(origin.detail);
			this.attach(origin.attach);
			this.outTradeNo(origin.outTradeNo);
			this.feeType(origin.feeType);
			this.totalFee(origin.totalFee);
			this.spbillCreateIp(origin.spbillCreateIp);
			this.goodsTag(origin.goodsTag);
			this.notifyURL(origin.notifyURL);
			this.tradeType(origin.tradeType);
			this.openid(origin.openid);
			this.contractId(origin.contractId);
			return this;
		}

		public WxAutoDebitRequest build() {
			WxAutoDebitRequest m = new WxAutoDebitRequest();
			m.appid = this.appid;
			m.mchId = this.mchId;
			m.nonceStr = this.nonceStr;
			m.sign = this.sign;
			m.body = this.body;
			m.detail = this.detail;
			m.attach = this.attach;
			m.outTradeNo = this.outTradeNo;
			m.feeType = this.feeType;
			m.totalFee = this.totalFee;
			m.spbillCreateIp = this.spbillCreateIp;
			m.goodsTag = this.goodsTag;
			m.notifyURL = this.notifyURL;
			m.tradeType = this.tradeType;
			m.openid = this.openid;
			m.contractId = this.contractId;
			return m;
		}
	}

}
