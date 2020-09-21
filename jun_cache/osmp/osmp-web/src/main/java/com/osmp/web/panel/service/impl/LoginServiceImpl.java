/*   
 * Project: OSMP
 * FileName: LoginServiceImpl.java
 * version: V1.0
 */
package com.osmp.web.panel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.panel.dao.LoginMapper;
import com.osmp.web.panel.service.LoginService;
import com.osmp.web.user.entity.User;

/**
 * Description:登录服务
 * 
 * @author: wangkaiping
 * @date: 2015年1月4日 下午5:41:49
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public Map<String, Object> login(HttpServletRequest req, String userName, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<User> userlist = loginMapper.selectAll(User.class, "name = '"+userName+"' and password = '"+password +"'");
		if(userlist != null && userlist.size() > 0){
			result.put("LoginUser", userlist.get(0));
			result.put("flag", true);
		}else{
			result.put("flag", false);
			result.put("message", "用户名或密码错误!");
		}
		return result;
	}

}
