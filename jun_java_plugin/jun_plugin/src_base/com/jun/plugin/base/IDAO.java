package com.jun.plugin.base;

import java.util.List;
import java.util.Map;

public interface IDAO<K, V> {
	public boolean doCreate(V vo) throws Exception;

	public boolean doUpdate(V vo) throws Exception;

	public boolean doRemove(K id) throws Exception;

	public V findById(K id) throws Exception;

	public List<V> findAll(String column, String keyWord) throws Exception;

	public List<V> findAll(String column, String keyWord, int currentPage, int lineSize) throws Exception;
	

	public int getCount() throws Exception;

	public int getCount(String column, String keyWord) throws Exception;

	public List queryForList(Map map) throws Exception;
	
	public String queryForStr(Map map) throws Exception;
	
	
	void save(V obj) throws Exception;

	void update(V obj) throws Exception;

	void delete(int id) throws Exception;

	List<V> findAll() throws Exception;

	V findById(int id) throws Exception;

	public List<V> queryAllV(Class classes);
	
}
