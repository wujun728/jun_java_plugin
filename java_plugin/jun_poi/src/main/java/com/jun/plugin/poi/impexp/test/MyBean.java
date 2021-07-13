package com.jun.plugin.poi.impexp.test;

import java.util.Date;

import com.jun.plugin.poi.impexp.excel.annotation.ExcelField;


//只支持注解属性
public class MyBean {
	
	@ExcelField(title="姓名",groups=1,sort=1,width=3000)private String name = "闪电球";
	
	@ExcelField(title="年龄",groups=1,sort=2,width=4000)private Integer age = 19;
	
	@ExcelField(title="生日",groups=2,sort=3,width=5000)private Date brithDate = new Date();

	@ExcelField(title="手机号",groups=2,sort=4,width=6000)private String phone = "15912345678";
	
	@ExcelField(title="地址",groups=3,sort=5,width=5000)private String address = "北京市广东省AAA123号";
	
	@ExcelField(title="双精度",groups=3,sort=6,width=4000)private Double d = 19.68d;
	
	@ExcelField(title="float",groups=3,sort=7,width=3000)private float f = 22.222f;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBrithDate() {
		return brithDate;
	}
	public void setBrithDate(Date brithDate) {
		this.brithDate = brithDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getD() {
		return d;
	}
	public void setD(Double d) {
		this.d = d;
	}
	public float getF() {
		return f;
	}
	public void setF(float f) {
		this.f = f;
	}
	
	
	

}
