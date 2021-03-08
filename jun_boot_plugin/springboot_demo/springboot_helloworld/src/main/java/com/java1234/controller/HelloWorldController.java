package com.java1234.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java1234.properties.MysqlProperties;

@RestController
public class HelloWorldController {

	@Value("${helloWorld}")
	private String helloWorld;
	
	@Resource
	private MysqlProperties mysqlProperties;
	
	@RequestMapping("/helloWorld")
	public String say(){
		return helloWorld;
	}
	
	@RequestMapping("/showJdbc")
	public String showJdbc(){
		return "mysql.jdbcName:"+mysqlProperties.getJdbcName()+"<br/>"
			  +"mysql.dbUrl:"+mysqlProperties.getDbUrl()+"<br/>"
			  +"mysql.userName:"+mysqlProperties.getUserName()+"<br/>"
			  +"mysql.password:"+mysqlProperties.getPassword()+"<br/>";
	}
}
