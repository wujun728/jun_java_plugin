package com.jun.web.biz.utils;

public class Condition {

	private String foodName;
	private int foodType_id;
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public int getFoodType_id() {
		return foodType_id;
	}
	public void setFoodType_id(int foodType_id) {
		this.foodType_id = foodType_id;
	}
	@Override
	public String toString() {
		return "Condition [foodName=" + foodName + ", foodType_id="
				+ foodType_id + "]";
	}
	
}
