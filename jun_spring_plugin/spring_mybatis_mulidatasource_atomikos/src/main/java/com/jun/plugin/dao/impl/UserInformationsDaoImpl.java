package com.jun.plugin.dao.impl;

import org.springframework.stereotype.Service;

import com.jun.plugin.dao.BaseDao;
import com.jun.plugin.dao.UserInformationsDao;
import com.jun.plugin.entity.UserInformationsEntity;

@Service ("userInformationsDao")
public class UserInformationsDaoImpl extends BaseDao implements UserInformationsDao{
    
    @Override
    public void updateUserInformations(UserInformationsEntity userInfo) {
	this.test2Update(userInfo);
    }

}

