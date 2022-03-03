package com.jun.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.jun.admin.dao.ICommonDAO;
import com.jun.admin.service.ICommonService;

@Service("commonServiceImpl")
public class CommonServiceImpl implements ICommonService {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	@Autowired(required = false)
	@Qualifier("commonDAOImpl")
	protected ICommonDAO commonDAOImpl;

	@Override
	public int doCreate(Map map) throws Exception {
		return this.commonDAOImpl.doCreate(map);
	}

	@Override
	public int doUpdate(Map map) throws Exception {
		return this.commonDAOImpl.doUpdate(map);
	}

	@Override
	public int doRemove(Map map) throws Exception {
		return this.commonDAOImpl.doRemove(map);
	}

	@Override
	public List findAll(Map map) throws Exception {
		return this.commonDAOImpl.findAll(map);
	}

	@Override
	public List findByID(Map map) throws Exception {
		return this.commonDAOImpl.findByID(map);
	}

	@Override
	public int getCount(Map map) throws Exception {
		return this.commonDAOImpl.getCount(map);
	}

	@Override
	public List getColumnValues(Map map) throws Exception {
		return this.commonDAOImpl.getColumnValues(map);
	}

	 

	@Override
	public String getTotal(Map map) {
		return this.commonDAOImpl.getTotal(map);
	}

               

 
	
}
