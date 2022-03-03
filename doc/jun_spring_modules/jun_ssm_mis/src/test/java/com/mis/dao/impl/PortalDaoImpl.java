package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.PortalDaoI;

@Repository("portalDao")
public class PortalDaoImpl<T> extends BaseDaoImpl<T> implements PortalDaoI<T> {

}
