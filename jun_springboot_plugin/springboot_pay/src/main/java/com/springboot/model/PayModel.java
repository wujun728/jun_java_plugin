package com.springboot.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String outTradeNno;
	/**
	 * 商品信息
	 */
	private String body;
	/**
	 * 商品金额
	 */
	private BigDecimal amount;

	/**
	 * 客户端IP
	 */
	private String ip;

	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 商品标题
	 */
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutTradeNno() {
		return outTradeNno;
	}

	public void setOutTradeNno(String outTradeNno) {
		this.outTradeNno = outTradeNno;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
