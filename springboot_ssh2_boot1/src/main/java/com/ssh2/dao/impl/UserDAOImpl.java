package com.ssh2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssh2.dao.BaseDAO;
import com.ssh2.dao.UserDAO;
import com.ssh2.po.User;

@Repository
public class UserDAOImpl extends BaseDAO implements UserDAO {

	@Override
	public List<User> findAll() {
		String hql="from User";
		return this.getHibernateTemplate().find(hql);
	}
}
