package com.jun.plugin.jdbc.jdbc.test;

import java.util.Date;

/**
 * 会员实体
 * @author Wujun
 *
 */
public class Member {
	private int id;
	private String gender;
	private int countNum;
	private String telephone;
	private String mno;
	private String uname;
	private Date birthDay;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public void print() {
		System.out.print("会员卡号:" + this.getMno());
		System.out.print("\t姓名：" + this.getUname());
		System.out.println("\t积分：" + this.getCountNum());
	}

}
