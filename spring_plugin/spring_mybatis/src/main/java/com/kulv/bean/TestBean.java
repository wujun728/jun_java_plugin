package com.kulv.bean;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @作者:dds  <br/>
 * @时间:  <br/>
 * @描述:  <br/>
 * @版权信息:www.kulv.com <br/>
 *
 */
@Alias(value = "TestBean")
public class TestBean implements Serializable{
	

	private static final long serialVersionUID = -5213097151125022138L;
	
	@NotBlank(message="姓名不能为空")
	private String name;
	
	@Max(value=150,message="年龄不能大于150")
	@Min(value=1,message="年龄不能小于1")
	private int age;


	
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
