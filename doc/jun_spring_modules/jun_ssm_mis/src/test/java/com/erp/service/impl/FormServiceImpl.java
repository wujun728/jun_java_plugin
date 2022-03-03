package com.erp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.erp.dao.IFormDAO;
import com.erp.service.IFormService;

@Service("formServiceImpl")
public class FormServiceImpl implements IFormService {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	@Autowired(required = false)
	@Qualifier("formDAOImpl")
	protected IFormDAO formDAOImpl;

	@Override
	public int doCreate(Map map) throws Exception {
		return this.formDAOImpl.doCreate(map);
	}

	@Override
	public int doUpdate(Map map) throws Exception {
		return this.formDAOImpl.doUpdate(map);
	}

	@Override
	public int doRemove(Map map) throws Exception {
		return this.formDAOImpl.doRemove(map);
	}

	@Override
	public List findAll(Map map) throws Exception {
		return this.formDAOImpl.findAll(map);
	}
	
	@Override
	public List queryJsonBySQl(Map map) throws Exception {
		return this.formDAOImpl.queryJsonBySQl(map);
	}

	@Override
	public List findByID(Map map) throws Exception {
		return this.formDAOImpl.findByID(map);
	}

	@Override
	public int getCount(Map map) throws Exception {
		return this.formDAOImpl.getCount(map);
	}

	@Override
	public List getColumnValues(Map map) throws Exception {
		return this.formDAOImpl.getColumnValues(map);
	}

	 

	@Override
	public String getTotal(Map map) {
		return this.formDAOImpl.getTotal(map);
	}

               

 
	
}
