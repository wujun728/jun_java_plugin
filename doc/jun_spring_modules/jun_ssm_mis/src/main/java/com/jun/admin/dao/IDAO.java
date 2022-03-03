package com.jun.admin.dao;

import java.util.List;

public interface IDAO<K, V> {
	public boolean doCreate(V vo) throws Exception;

	public boolean doUpdate(V vo) throws Exception;

	public boolean doRemove(K id) throws Exception;

	public V findById(K id) throws Exception;

	public List<V> findAll(String column, String keyWord) throws Exception;

	public List<V> findAll(String column, String keyWord, int currentPage,
			int lineSize) throws Exception;

	public int getAllCount(String column, String keyWord) throws Exception;
}
