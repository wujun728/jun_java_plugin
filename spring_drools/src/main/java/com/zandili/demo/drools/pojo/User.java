package com.zandili.demo.drools.pojo;

import java.io.Serializable;

/**
 * 传说中的小明
 * 
 * @author airfey
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2660763436851514036L;
	private int money; // 手中的钱
	private int kp; // 空瓶数
	private int totals; // 喝掉的瓶数

	public int getMoney() {
		return money;
	}

	public User setMoney(int money) {
		this.money = money;
		return this;
	}

	public int getKp() {
		return kp;
	}

	public void setKp(int kp) {
		this.kp = kp;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}
}
