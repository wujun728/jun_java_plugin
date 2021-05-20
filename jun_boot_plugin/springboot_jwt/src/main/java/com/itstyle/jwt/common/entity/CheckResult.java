package com.itstyle.jwt.common.entity;

import io.jsonwebtoken.Claims;
/**
 * 验证信息 
 * 创建者  科帮网 
 * 创建时间  2017年11月27日
 */
public class CheckResult {
	private int errCode;

	private boolean success;

	private Claims claims;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Claims getClaims() {
		return claims;
	}

	public void setClaims(Claims claims) {
		this.claims = claims;
	}
}
