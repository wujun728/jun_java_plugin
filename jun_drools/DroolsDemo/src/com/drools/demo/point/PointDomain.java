package com.drools.demo.point;


/**
 * 积分计算对象
 * @author quzishen
 */
public class PointDomain {
	// 用户名
	private String userName;
	// 是否当日生日
	private boolean birthDay;
	// 增加积分数目
	private long point;
	// 当月购物次数
	private int buyNums;
	// 当月退货次数
	private int backNums;
	// 当月购物总金额
	private double buyMoney;
	// 当月退货总金额
	private double backMondy;
	// 当月信用卡还款次数
	private int billThisMonth;
	
	/**
	 * 记录积分发送流水，防止重复发放
	 * @param userName 用户名
	 * @param type 积分发放类型
	 */
	public void recordPointLog(String userName, String type){
		System.out.println("增加对"+userName+"的类型为"+type+"的积分操作记录.");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isBirthDay() {
		return birthDay;
	}

	public void setBirthDay(boolean birthDay) {
		this.birthDay = birthDay;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public int getBuyNums() {
		return buyNums;
	}

	public void setBuyNums(int buyNums) {
		this.buyNums = buyNums;
	}

	public int getBackNums() {
		return backNums;
	}

	public void setBackNums(int backNums) {
		this.backNums = backNums;
	}

	public double getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(double buyMoney) {
		this.buyMoney = buyMoney;
	}

	public double getBackMondy() {
		return backMondy;
	}

	public void setBackMondy(double backMondy) {
		this.backMondy = backMondy;
	}

	public int getBillThisMonth() {
		return billThisMonth;
	}

	public void setBillThisMonth(int billThisMonth) {
		this.billThisMonth = billThisMonth;
	}

}
