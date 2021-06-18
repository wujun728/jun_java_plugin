package com.xiaoji.ssh.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

/** 

 * Struts2基于注解的Action配置

 *  ParentPackage 继承父包
 *  Namespace命名空间
 *  Results跳转页面
 *  Action访问方法
 */   

@ParentPackage("struts-default") 

@Namespace("/book") 

@Results( { @Result(name = "success", location = "/views/main.jsp"), 

        @Result(name = "error", location = "/views/error.jsp") }) 

@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") }) 
public class BookAction extends ActionSupport{
	private String username;
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Action(value="test")
	public String test(){
		this.setUsername("strutsTest");
		return SUCCESS;
	}
	
	
	@Action(value = "bookView", results = { @Result(name = "success", location = "/index.jsp") })  
	public String bookView(){
		return SUCCESS;
	}
}
