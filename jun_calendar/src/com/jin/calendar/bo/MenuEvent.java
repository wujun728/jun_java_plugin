package com.jin.calendar.bo;

import java.util.Date;

public class MenuEvent extends Event {
	
	private static final long serialVersionUID = 1L;
	private float price;
	private int state;
	private int menuId;
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public MenuEvent() {
		super();
	}
	public MenuEvent(int id, String title, Date start, float price, int state, int menuId) {
		super();
		this.id = id;
		this.title = state ==1 ? "[午餐]" + title : "[晚餐]" + title;
		this.start = start;
		this.price = price;
		this.state = state;
		this.menuId = menuId;
	}
	
	
}
