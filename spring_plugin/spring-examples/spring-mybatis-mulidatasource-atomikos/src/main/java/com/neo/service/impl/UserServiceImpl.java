package com.neo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neo.dao.UserDao;
import com.neo.dao.UserInformationsDao;
import com.neo.entity.UserEntity;
import com.neo.entity.UserInformationsEntity;
import com.neo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource UserDao userDao;
    @Resource UserInformationsDao userInformationsDao;

    @Override
    @Transactional
    public void updateUserinfo() {
	
	UserEntity user=new UserEntity();
	user.setId(1);
	user.setUserName("李四1");
	
	UserInformationsEntity userInfo=new UserInformationsEntity();
	userInfo.setUserId(1);
	userInfo.setAddress("陕西1");
	
	userDao.updateUser(user);
	userInformationsDao.updateUserInformations(userInfo);
	/*if(!user.equals("")){
	    throw new RuntimeException("test db exception");
	}*/
    }
    
    

}

