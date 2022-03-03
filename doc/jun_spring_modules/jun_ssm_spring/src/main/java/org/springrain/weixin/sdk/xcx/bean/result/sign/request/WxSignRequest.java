package org.springrain.weixin.sdk.xcx.bean.result.sign.request;

import org.springrain.weixin.sdk.common.annotation.Required;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class WxSignRequest extends WxSignBaseRequest{

	/**
	 * <pre>
	 * 模板Id
	 * 是
	 * String(28)
	 * 协议模板id
	 * 商户签约的模板协议id，商户在微信商户平台设置模版id
	 * </pre>
	 */
	@Required
	@XStreamAlias("plan_id")
	private String planId;

	/**
	 * <pre>
	 * 签约协议号
	 * 是
	 * String(32)
	 * 商户侧的签约协议号，由商户生成
	 * </pre>
	 */
	@Required
	@XStreamAlias("contract_code")
	private String contractCode;

	/**
	 * <pre>
	 * 请求序列号
	 * 是
	 * int(64)
	 * 商户请求签约时的序列号，商户侧须唯一
	 * </pre>
	 */
	@Required
	@XStreamAlias("request_serial")
	private Integer requestSerial;

	/**
	 * <pre>
	 * 用户账户展示名称
	 * 是
	 * String(32)
	 * 微信代扣
	 * </pre>
	 */
	@Required
	@XStreamAlias("contract_display_account")
	private String contractDisplayAccount;

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
	 * 版本号
	 * 是
	 * String
	 * 固定值1.0
	 * </pre>
	 */
	@Required
	@XStreamAlias("version")
	private String version;

	/**
	 * <pre>
	 * 时间戳
	 * 是
	 * String（10）
	 * 系统当前时间
	 * </pre>
	 */
	@Required
	@XStreamAlias("timestamp")
	private String timestamp;

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

	/**
	 * <pre>
	 * 返回web
	 * return_web
	 * 否
	 * int
	 * 1表示返回签约页面的referrer url, 0 或不填或获取不到referrer则不返回; 跳转referrer url时会自动带上参数from_wxpay=1
	 * </pre>
	 */
	@XStreamAlias("return_web")
	private String returnWeb;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Integer getRequestSerial() {
		return requestSerial;
	}

	public void setRequestSerial(Integer requestSerial) {
		this.requestSerial = requestSerial;
	}

	public String getContractDisplayAccount() {
		return contractDisplayAccount;
	}

	public void setContractDisplayAccount(String contractDisplayAccount) {
		this.contractDisplayAccount = contractDisplayAccount;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
    public String getSign() {
		return sign;
	}

	@Override
    public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getReturnWeb() {
		return returnWeb;
	}

	public void setReturnWeb(String returnWeb) {
		this.returnWeb = returnWeb;
	}

	public static WxSignRequestBuilder builder() {
		return new WxSignRequestBuilder();
	}

	public static class WxSignRequestBuilder {
		private String appid;
		private String mchId;
		private String sign;
		private String planId;
		private String contractCode;
		private Integer requestSerial;
		private String contractDisplayAccount;
		private String version;
		private String notifyURL;
		private String timestamp;
		private String openid;
		private String returnWeb;

		public WxSignRequestBuilder appid(String appid) {
			this.appid = appid;
			return this;
		}

		public WxSignRequestBuilder mchId(String mchId) {
			this.mchId = mchId;
			return this;
		}

		public WxSignRequestBuilder sign(String sign) {
			this.sign = sign;
			return this;
		}

		public WxSignRequestBuilder planId(String planId) {
			this.planId = planId;
			return this;
		}

		public WxSignRequestBuilder contractCode(String contractCode) {
			this.contractCode = contractCode;
			return this;
		}

		public WxSignRequestBuilder contractDisplayAccount(
				String contractDisplayAccount) {
			this.contractDisplayAccount = contractDisplayAccount;
			return this;
		}

		public WxSignRequestBuilder requestSerial(Integer requestSerial) {
			this.requestSerial = requestSerial;
			return this;
		}

		public WxSignRequestBuilder version(String version) {
			this.version = version;
			return this;
		}

		public WxSignRequestBuilder notifyURL(String notifyURL) {
			this.notifyURL = notifyURL;
			return this;
		}

		public WxSignRequestBuilder timestamp(String timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public WxSignRequestBuilder openid(String openid) {
			this.openid = openid;
			return this;
		}

		public WxSignRequestBuilder returnWeb(String returnWeb) {
			this.returnWeb = returnWeb;
			return this;
		}

		public WxSignRequestBuilder from(WxSignRequest origin) {
			this.appid(origin.appid);
			this.mchId(origin.mchId);
			this.sign(origin.sign);
			this.planId(origin.planId);
			this.notifyURL(origin.notifyURL);
			this.contractCode(origin.contractCode);
			this.requestSerial(origin.requestSerial);
			this.contractDisplayAccount(origin.contractDisplayAccount);
			this.version(origin.version);
			this.timestamp(origin.timestamp);
			this.openid(origin.openid);
			this.returnWeb(origin.returnWeb);
			return this;
		}

		public WxSignRequest build() {
			WxSignRequest m = new WxSignRequest();
			m.appid = this.appid;
			m.mchId = this.mchId;
			m.sign = this.sign;
			m.planId = this.planId;
			m.contractCode = this.contractCode;
			m.requestSerial = this.requestSerial;
			m.contractDisplayAccount = this.contractDisplayAccount;
			m.notifyURL = this.notifyURL;
			m.version = this.version;
			m.timestamp = this.timestamp;
			m.openid = this.openid;
			m.returnWeb = this.returnWeb;
			return m;
		}
	}
	  

}
