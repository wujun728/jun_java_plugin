package com.erp.page;

import java.util.List;

@SuppressWarnings("unchecked")
public class Data {
	private String name;
	
	private List data;

	public Data(){}
	
	public Data(String _name) {
		this.name = _name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}



}
