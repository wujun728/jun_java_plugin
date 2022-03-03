package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.UserDaoI;

@Repository("userDao")
public class UserDaoImpl<T> extends BaseDaoImpl<T> implements UserDaoI<T> {

}
