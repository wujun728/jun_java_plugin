package org.ssm.dufy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.dufy.dao.IUserDao;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.IUserService;

@Service("userService")
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao udao;
	
	@Override
	public User getUserById(int id) {
		return udao.selectByPrimaryKey(id);
	}

	@Override
	public User getUser(User u) {
		// TODO Auto-generated method stub
		return udao.findUser(u);
	}

}
