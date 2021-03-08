package com.baomidou.hibernate.vo;

import com.baomidou.hibernate.model.AutoPrimaryKey;

public class DemoVO extends AutoPrimaryKey {

	private static final long serialVersionUID = 1L;

	private String demo1;
	private String demo2;
	private String demo3;

	public String getDemo1() {
		return demo1;
	}

	public void setDemo1(String demo1) {
		this.demo1 = demo1;
	}

	public String getDemo2() {
		return demo2;
	}

	public void setDemo2(String demo2) {
		this.demo2 = demo2;
	}

	public String getDemo3() {
		return demo3;
	}

	public void setDemo3(String demo3) {
		this.demo3 = demo3;
	}

	@Override
	public String toString() {
		return "Vdemo{" +
				"demo1='" + demo1 + '\'' +
				", demo2='" + demo2 + '\'' +
				", demo3='" + demo3 + '\'' +
				", id='" + getId() + '\'' +
				'}';
	}
}