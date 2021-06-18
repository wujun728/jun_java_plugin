package com.circle.action;


import com.circle.entity.User;
import com.circle.service.IUserService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component()
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class UserAction extends ActionSupport {

	//Service对象
	@Autowired
	private IUserService userService;
	//User对象
	private User user;

	//userSerivice属性的get方法
	public IUserService getUserService() {
		return userService;
	}
	//userSerivice属性的set方法
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	//user属性的get方法
	public User getUser() {
		return user;
	}
	//user属性的set方法
	public void setUser(User user) {
		this.user = user;
	}

	// 注册方法
	public String register() {
		boolean result = userService.register(user);
		if (result) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// 登录方法
	public String login() {
		if (userService.login(user.getName(), user.getPassword())) {
			return SUCCESS;
		}
		return ERROR;
	}
	//删除方法
	public String delete() {
		boolean result = userService.delete(user);
		if (result) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	//修改方法
	public String update() {
		try{
			userService.update(user);
			return SUCCESS;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ERROR;
	}
	//添加方法
	public String add(){
		try{
			userService.add(user);
			return SUCCESS;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ERROR;
	}
}
