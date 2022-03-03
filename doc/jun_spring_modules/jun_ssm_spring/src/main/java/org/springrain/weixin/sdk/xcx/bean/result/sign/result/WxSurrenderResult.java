package org.springrain.weixin.sdk.xcx.bean.result.sign.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 微信委托支付解约
 * 解约(详见https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_4&index=4)
 * </pre>
 *
 * @author springrain
 */
@XStreamAlias("xml")
public class WxSurrenderResult extends WxSignBaseResult {
  
	@XStreamAlias("contract_id")
	private String contractId;

	@XStreamAlias("plan_id")
	private String planId;
	
	@XStreamAlias("contract_code")
	private String contractCode;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}
