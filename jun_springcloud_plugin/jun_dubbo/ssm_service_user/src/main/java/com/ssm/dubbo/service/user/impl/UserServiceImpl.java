package com.ssm.dubbo.service.user.impl;

import com.ssm.dubbo.api.user.entity.User;
import com.ssm.dubbo.api.user.service.UserService;
import com.ssm.dubbo.service.user.dao.UserDao;
import com.ssm.dubbo.util.AntiXssUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public List<User> findUser(Map<String, Object> map) {
		return userDao.findUsers(map);
	}

	@Override
	public int updateUser(User user) {
		//防止有人胡乱修改导致其他人无法正常登陆
		if ("admin".equals(user.getUserName())) {
			return 0;
		}
		user.setUserName(AntiXssUtil.replaceHtmlCode(user.getUserName()));
		return userDao.updateUser(user);
	}

	@Override
	public Long getTotalUser(Map<String, Object> map) {
		return userDao.getTotalUser(map);
	}

	@Override
	public int addUser(User user) {
		if (user.getUserName() == null || user.getPassword() == null || getTotalUser(null) > 90) {
			return 0;
		}
		user.setUserName(AntiXssUtil.replaceHtmlCode(user.getUserName()));
		return userDao.addUser(user);
	}

	@Override
	public int deleteUser(Integer id) {
		//防止有人胡乱修改导致其他人无法正常登陆
		if (2 == id) {
			return 0;
		}
		return userDao.deleteUser(id);
	}

}
