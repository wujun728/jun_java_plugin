package com.springboot.dao.impl;

import org.springframework.stereotype.Repository;

import com.springboot.dao.BaseDao;
import com.springboot.dao.IAdminDao;
import com.springboot.model.Admin;

@Repository
public class AdminDaoImpl extends BaseDao<Admin> implements IAdminDao {

	@Override
	public Admin find(Integer id) {
		return findFirstByHql("from Admin where id=?", id);
	}

	@Override
	public String userName(Integer id) {
		return queryStr("select userName from admin where id=?", id);
	}

}
