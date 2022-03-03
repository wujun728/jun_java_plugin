package com.jun.admin.dao;

import java.util.List;
import java.util.Map;

public interface ICommonDAO {

	@SuppressWarnings("rawtypes")
	public int doCreate(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public int doUpdate(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public int doRemove(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public List findAll(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public List findByID(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public int getCount(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getColumnValues(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public String getTotal(Map map);


}