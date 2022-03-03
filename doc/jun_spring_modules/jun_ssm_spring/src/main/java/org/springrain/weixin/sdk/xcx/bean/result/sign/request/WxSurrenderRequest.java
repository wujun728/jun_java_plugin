package org.springrain.weixin.sdk.xcx.bean.result.sign.request;

import org.springrain.weixin.sdk.common.annotation.Required;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class WxSurrenderRequest extends WxSignBaseRequest{

	/**
	 * <pre>
	 * 模板Id
	 * 否
	 * String(28)
	 * 协议模板id
	 * 商户在微信商户平台配置的代扣模版id，选择plan_id+contract_code解约，则此参数必填
	 * </pre>
	 */
	@Required
	@XStreamAlias("plan_id")
	private String planId;

	/**
	 * <pre>
	 * 签约协议号
	 * 否
	 * String(32)
	 * 商户请求签约时传入的签约协议号，商户侧须唯一。选择plan_id+contract_code解约，则此参数必填
	 * </pre>
	 */
	@Required
	@XStreamAlias("contract_code")
	private String contractCode;

	/**
	 * <pre>
	 * 委托代扣协议id
	 * 否
	 * String
	 * 委托代扣签约成功后由微信返回的委托代扣协议id，选择contract_id解约，则此参数必填
	 * </pre>
	 */
	@Required
	@XStreamAlias("contract_id")
	private String contractId;

	/**
	 * <pre>
	 * 解约备注
	 * 是
	 * String(256)
	 * 解约原因
	 * 解约原因的备注说明，如：签约信息有误，须重新签约
	 * </pre>
	 */
	@Required
	@XStreamAlias("contract_termination_remark")
	private String contractTerminationRemark;


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
	
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractTerminationRemark() {
		return contractTerminationRemark;
	}

	public void setContractTerminationRemark(String contractTerminationRemark) {
		this.contractTerminationRemark = contractTerminationRemark;
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
		private String contractId;
		private String contractTerminationRemark;
		private String version;

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

		public WxSignRequestBuilder contractTerminationRemark(
				String contractTerminationRemark) {
			this.contractTerminationRemark = contractTerminationRemark;
			return this;
		}

		public WxSignRequestBuilder contractId(String contractId) {
			this.contractId = contractId;
			return this;
		}

		public WxSignRequestBuilder version(String version) {
			this.version = version;
			return this;
		}


		public WxSignRequestBuilder from(WxSurrenderRequest origin) {
			this.appid(origin.appid);
			this.mchId(origin.mchId);
			this.sign(origin.sign);
			this.planId(origin.planId);
			this.contractCode(origin.contractCode);
			this.contractId(origin.contractId);
			this.contractTerminationRemark(origin.contractTerminationRemark);
			this.version(origin.version);
			return this;
		}

		public WxSurrenderRequest build() {
			WxSurrenderRequest m = new WxSurrenderRequest();
			m.appid = this.appid;
			m.mchId = this.mchId;
			m.sign = this.sign;
			m.planId = this.planId;
			m.contractCode = this.contractCode;
			m.contractId = this.contractId;
			m.contractTerminationRemark = this.contractTerminationRemark;
			m.version = this.version;
			return m;
		}
	}
	  

}
