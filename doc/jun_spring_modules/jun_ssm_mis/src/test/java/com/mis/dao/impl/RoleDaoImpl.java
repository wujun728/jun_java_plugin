package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.RoleDaoI;

@Repository("roleDao")
public class RoleDaoImpl<T> extends BaseDaoImpl<T> implements RoleDaoI<T> {

}
