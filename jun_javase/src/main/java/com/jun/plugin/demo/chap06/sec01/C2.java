package com.jun.plugin.demo.chap06.sec01;

public class C2 {

	private String a;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public C2(String a) {
		super();
		this.a = a;
	}
	
	/**
	 * ��ӡa������
	 */
	public void print(){
		System.out.println("a�������ǣ�"+a.getClass().getName());
	}
}
