package com.jun.web.biz.test;

import java.util.ArrayList;
import java.util.List;

public class GoodsModel {
	private int uuid;
	private String name;
	private String description;
	private float price;
	
	
	private transient List<GoodsModel> list = new ArrayList<GoodsModel>();
	
	public List<GoodsModel> getList() {
		return list;
	}
	public void setList(List<GoodsModel> list) {
		this.list = list;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uuid;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoodsModel other = (GoodsModel) obj;
		if (uuid != other.uuid)
			return false;
		return true;
	}
	public String toString(){
		return "uuid="+uuid+",name="+name;
	}
}
