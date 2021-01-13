package com.kvn.poi.importvo;

import java.util.Date;

import com.kvn.poi.imp.anno.ExcelColum;
import com.kvn.poi.imp.anno.ExcelDateColum;

/**
 * @author wzy
 * @date 2017年7月12日 上午11:14:33
 */
public class OrderImportVo {
	@ExcelColum("序号")
	private int index;
	@ExcelColum("购买订单编号")
	private String orderNo;
	@ExcelColum("产品名称")
	private String productName;
	@ExcelColum("购买会员账号")
	private String customerId;
	@ExcelColum("购买份数")
	private int purchaseNum;
	@ExcelDateColum("购买时间")
	private Date beginTime;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(int purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
}
