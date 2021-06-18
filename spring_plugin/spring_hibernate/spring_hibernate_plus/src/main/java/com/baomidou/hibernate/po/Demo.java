package com.baomidou.hibernate.po;

import com.baomidou.hibernate.model.AutoPrimaryKey;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Demo extends AutoPrimaryKey {

	private static final long serialVersionUID = 1L;

	private String demo1;
	private String demo2;
	private String demo3;

	@Column(name = "demo1")
	public String getDemo1() {
		return demo1;
	}

	public void setDemo1(String demo1) {
		this.demo1 = demo1;
	}

	@Column(name = "demo2")
	public String getDemo2() {
		return demo2;
	}

	public void setDemo2(String demo2) {
		this.demo2 = demo2;
	}

	@Column(name = "demo3")
	public String getDemo3() {
		return demo3;
	}

	public void setDemo3(String demo3) {
		this.demo3 = demo3;
	}
}