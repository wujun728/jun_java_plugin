package com.jin.calendar.controller;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jin.calendar.model.Menu;
import com.jin.calendar.model.Room;
import com.jin.calendar.model.User;

@ClearInterceptor(ClearLayer.ALL)
public class CommonController extends Controller {

	public void index() {
		if(getSession().getAttribute("username")==null || getSession().getAttribute("pageType")==null){
			render("login.jsp");
		}else if(getSession().getAttribute("pageType").equals("bookRoom")){
			setAttr("roomList", Room.dao.getAllRoom());
			setAttr("ServerTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			render("bookRoom.jsp");
		}else if(getSession().getAttribute("pageType").equals("orderFood")){
			setAttr("menuList", Menu.dao.getForenoonFoods());
			setAttr("ServerTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			render("orderFood.jsp");
		}else{
			renderNull();
		}
	}
	
	public void login(){
		User user=User.dao.getUserByLoginName(getPara("username"));
		if(user==null || ! user.getStr("password").equals(getPara("password"))){
			setAttr("msg", "用户名或密码错误");
			render("login.jsp");
			return;
		}else{
			getSession().setAttribute("userId", user.getInt("id"));
			getSession().setAttribute("username", user.getStr("loginName"));
			getSession().setAttribute("pageType", getPara("pageType"));
			redirect("/");
		}
	}
	
	public void exit(){
		getSession().invalidate();
		render("login.jsp");
	}
	
}
