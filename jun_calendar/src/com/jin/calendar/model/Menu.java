package com.jin.calendar.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Menu extends Model<Menu> {
	public static final Menu dao = new Menu();
	
	/**
	 * 获取菜单所有菜品
	 * @return
	 */
	public List<Menu> getAllFoods(){
		return Menu.dao.find("select * from menu order by price asc");
	}
	
	/**
	 * 获取午餐菜品
	 * @return
	 */
	public List<Menu> getForenoonFoods(){
		return Menu.dao.find("select * from menu m where m.state in(1,3) order by price asc");
	}
	
	/**
	 * 获取晚餐菜品
	 * @return
	 */
	public List<Menu> getAfternoonFoods(){
		return Menu.dao.find("select * from menu m where m.state in(2,3) order by price asc");
	}
}
