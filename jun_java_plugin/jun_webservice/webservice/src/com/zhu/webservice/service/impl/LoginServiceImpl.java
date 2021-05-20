package com.zhu.webservice.service.impl;

import com.zhu.webservice.bean.UserBean;
import com.zhu.webservice.dao.IUserOperate;
import com.zhu.webservice.service.ILoginService;

public class LoginServiceImpl implements ILoginService {
	IUserOperate userOperate;

	@Override
	public UserBean searchUser(UserBean user) {
		return userOperate.selectUserByID(user.getUid());
	}
	 


}
