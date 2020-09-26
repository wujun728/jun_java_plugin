package com.jun.plugin.lucene;

import java.util.Date;

/**
 * lucence测试对象
 * 包含luncen常用的支持类型
 * @author DF
 *
 */
public class User {

	private String id;//id
	private String name;//姓名
	private String sex;//性别
	private int age;//年纪
	private String introduce; //自我介绍
	private double money;//资产
	private String address;//家庭住址
	private Date birth;//生日
	public User() {
		super();
	}
	public User(String id, String name, String sex, int age, String introduce,
			double money, String address,Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.introduce = introduce;
		this.money = money;
		this.address = address;
		this.birth = birth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
}
