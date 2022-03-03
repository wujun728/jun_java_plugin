package com.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.impl.BaseDaoImpl;
import com.mis.dao.RepairDaoI;

@Repository("repairDao")
public class RepairDaoImpl<T> extends BaseDaoImpl<T> implements RepairDaoI<T> {

}
