package com.jin.calendar.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jin.calendar.bo.DwzResponseBO;
import com.jin.calendar.common.CommonConstant;
import com.jin.calendar.model.Menu;

/**
 * 菜单Controller控制器类
 * @author Jin
 * @datetime 2014年1月16日 上午12:07:00
 */
public class MenuController extends Controller {
	
	public void index(){
		Page<Menu> page = Menu.dao.paginate(CommonConstant.PAGENUMBER, CommonConstant.PAGESIZE, "select *", "from menu order by id DESC");
		setAttr("page", page);
		render("menu.jsp");
	}

	public void getPageList(){
		Page<Menu> page = Menu.dao.paginate(getParaToInt("pageNum"), getParaToInt("numPerPage"), "select *", "from menu order by id DESC");
		setAttr("page", page);
		render("menu.jsp");
	}
	
	public void getMenu() {
		List<Menu> list = new ArrayList<>();
		if (getParaToInt() == 1) {
			list = Menu.dao.getForenoonFoods();
		} else if (getParaToInt() == 2) {
			list = Menu.dao.getAfternoonFoods();
		} else {
			renderNull();
			return;
		}
		renderJson(list);
	}
	
	public void goAdd(){
		render("menu_add.jsp");
	}
	
	public void add(){
		DwzResponseBO responseBO = new DwzResponseBO(getPara("navTabId"),"closeCurrent");
		boolean flag=new Menu().set("name", getPara("name"))
				.set("price", getPara("price"))
				.set("state", getPara("state"))
				.save();
		if(!flag){
			responseBO.setStatusCode("300");
			responseBO.setMessage("操作失败！");
		}
		renderJson(responseBO);
						
	}
	
	public void del(){
		DwzResponseBO responseBO = new DwzResponseBO();
		boolean flag=Db.deleteById("menu", getParaToInt());
		if(!flag){
			responseBO.setStatusCode("300");
			responseBO.setMessage("操作失败！");
		}
		renderJson(responseBO);
	}
	
	public void isUnique(){
		List<Record> list = Db.find("select * from menu where name= ?", getPara("name"));
		if(list.size()>0){
			renderText("false");
		}else{
			renderText("true");
		}
	}
}
