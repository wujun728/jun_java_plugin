package com.jun.plugin.demo.chap06.sec01;

public class C12 {

	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public C12(Object object) {
		super();
		this.object = object;
	}
	
	/**
	 * ��ӡobject������
	 */
	public void print(){
		System.out.println("object�������ǣ�"+object.getClass().getName());
	}
}
