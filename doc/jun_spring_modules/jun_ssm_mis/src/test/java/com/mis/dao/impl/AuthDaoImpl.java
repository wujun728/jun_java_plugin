package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.AuthDaoI;

@Repository("authDao")
public class AuthDaoImpl<T> extends BaseDaoImpl<T> implements AuthDaoI<T> {

}
