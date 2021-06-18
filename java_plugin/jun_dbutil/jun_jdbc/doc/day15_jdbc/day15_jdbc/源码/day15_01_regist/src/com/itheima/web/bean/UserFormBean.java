package com.itheima.web.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

//字段都是私有的，且都是String类型。  避免类型转换
//提供验证的方法，验证不同的还有提示信息

//字段和表单的字段完全一致
public class UserFormBean {
	private String username;
	private String password;
	private String repassword;
	private String email;
	private String birthday;
	//封装错误信息：key，字段；value：错误信息
	private Map<String, String> errors = new HashMap<String, String>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	//服务器端验证：开发--js和服务器端都写。
	public boolean validate(){
		//验证的数据不符合要求：向errors中添加字段和错误提示
//		用户名：3~8个字母组成，不能为空<br/>
		if(username.trim().equals("")){
			errors.put("username", "请输入用户名");
		}else{
			if(!username.matches("[a-zA-Z]{3,8}")){
				errors.put("username", "用户名必须由3~8位字母组成");
			}
		}
//  	密码：3~8位数字组成，不能为空<br/>
		if(password.trim().equals("")){
			errors.put("password", "请输入密码");
		}else{
			if(!password.matches("\\d{3,8}")){
				errors.put("password", "密码必须由3~8位数字组成");
			}
		}
//  	重复密码：必须和密码一致<br/>
		if(!password.equals(repassword)){
			errors.put("repassword", "两次密码必须一致");
		}
//  	邮箱：不能为空，且要符合邮箱的格式<br/>
		if(email.trim().equals("")){
			errors.put("email", "请输入邮箱");
		}else{
			if(!email.matches("\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b")){
				errors.put("email", "邮箱格式不正确");
			}
		}
//  	生日：不能为空，且要符合yyyy-MM-dd的格式<br/>
		if(birthday.trim().equals("")){
			errors.put("birthday", "请输入出生日期");
		}else{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				df.parse(birthday);
			} catch (ParseException e) {
				errors.put("birthday", "日期格式不合法");
			}
		}
		return errors.isEmpty();
	}
}
