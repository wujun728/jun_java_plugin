package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.ResourcesDaoI;

@Repository("resourcesDao")
public class ResourcesDaoImpl<T> extends BaseDaoImpl<T> implements ResourcesDaoI<T> {

}
