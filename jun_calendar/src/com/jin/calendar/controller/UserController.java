package com.jin.calendar.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jin.calendar.bo.DwzResponseBO;
import com.jin.calendar.common.CommonConstant;
import com.jin.calendar.model.User;

public class UserController extends Controller {
	
	public void index(){
		Page<User> page = User.dao.paginate(CommonConstant.PAGENUMBER, CommonConstant.PAGESIZE, "select *", "from user");
		setAttr("page", page);
		render("user.jsp");
	}

	public void getPageList(){
		Page<User> page = User.dao.paginate(getParaToInt("pageNum"), getParaToInt("numPerPage"), "select *", "from user");
		setAttr("page", page);
		render("user.jsp");
	}
	
	public void goAdd(){
		render("user_add.jsp");
	}
	
	public void add(){
		DwzResponseBO responseBO = new DwzResponseBO(getPara("navTabId"),"closeCurrent");
		boolean flag=new User().set("name", getPara("name"))
				.set("loginName", getPara("loginName"))
				.set("password", getPara("password"))
				.set("tel", getPara("tel"))
				.set("email", getPara("email"))
				.set("create_date", new Date())
				.save();
		if(!flag){
			responseBO.setStatusCode("300");
			responseBO.setMessage("操作失败！");
		}
		renderJson(responseBO);
						
	}
	
	public void del(){
		DwzResponseBO responseBO = new DwzResponseBO();
		boolean flag=Db.deleteById("user", getParaToInt());
		if(!flag){
			responseBO.setStatusCode("300");
			responseBO.setMessage("操作失败！");
		}
		renderJson(responseBO);
	}
	
	public void isUnique(){
		List<Record> list = Db.find("select * from user where loginName= ?", getPara("loginName"));
		if(list.size()>0){
			renderText("false");
		}else{
			renderText("true");
		}
	}
}
