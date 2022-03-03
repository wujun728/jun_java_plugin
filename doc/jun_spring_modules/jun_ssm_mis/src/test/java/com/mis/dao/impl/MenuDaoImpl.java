package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.MenuDaoI;

@Repository("menuDao")
public class MenuDaoImpl<T> extends BaseDaoImpl<T> implements MenuDaoI<T> {

}
