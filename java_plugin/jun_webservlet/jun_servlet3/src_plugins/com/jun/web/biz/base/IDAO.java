package com.jun.web.biz.base;

import java.util.List;
import java.util.Map;

public interface IDAO<K, V> {
	public boolean doCreate(V vo) throws Exception;

	public boolean doUpdate(V vo) throws Exception;

	public boolean doRemove(K id) throws Exception;

	public V findById(K id) throws Exception;

	public List<V> findAll(String column, String keyWord) throws Exception;

	public List<V> findAll(String column, String keyWord, int currentPage, int lineSize) throws Exception;

	public int getAllCount(String column, String keyWord) throws Exception;

	/*@SuppressWarnings("rawtypes")
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
	public String getTotal(Map map);*/
}
