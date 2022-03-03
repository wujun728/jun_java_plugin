package org.springrain.weixin.sdk.xcx.bean.result.sign.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 微信委托代扣签约返回的结果
 * 申请签约api(详见https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_1&index=1)
 * </pre>
 *
 * @author springrain
 */
@XStreamAlias("xml")
public class WxSignResult extends WxSignBaseResult {
	
	@XStreamAlias("plan_id")
	private String planId;
  
	@XStreamAlias("contract_code")
	private String contractCode;

	@XStreamAlias("openid")
	private String openid;

	@XStreamAlias("change_type")
	private String changeType;

	@XStreamAlias("operate_time")
	private String operateTime;

	@XStreamAlias("contract_id")
	private String contractId;

	@XStreamAlias("contract_expired_time")
	private String contractExpiredTime;

	@XStreamAlias("request_serial")
	private String requestSerial;
	

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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractExpiredTime() {
		return contractExpiredTime;
	}

	public void setContractExpiredTime(String contractExpiredTime) {
		this.contractExpiredTime = contractExpiredTime;
	}

	public String getRequestSerial() {
		return requestSerial;
	}

	public void setRequestSerial(String requestSerial) {
		this.requestSerial = requestSerial;
	}
  

}
