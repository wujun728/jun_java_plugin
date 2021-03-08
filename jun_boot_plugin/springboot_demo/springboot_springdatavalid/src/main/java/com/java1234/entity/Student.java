package com.java1234.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 学生信息实体
 * @author Wujun
 *
 */
@Entity
@Table(name="t_student")
public class Student {

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotEmpty(message="姓名不能为空！")
	@Column(length=50)
	private String name;
	
	@NotNull(message="年龄不能为空！")
	@Min(value=18,message="年龄必须大于18岁")
	private Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	
}
