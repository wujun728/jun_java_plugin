package com.jun.plugin.common.base.interfaces;

import org.json.JSONObject;

import java.util.List;

public interface IDAO<K, V> {

	public boolean save(V vo) throws Exception;

	public boolean update(V vo) throws Exception;

	public boolean delete(V vo) throws Exception;

	public boolean deleteById(K id) throws Exception;

	public V findById(K id) throws Exception;

	public List<V> list(V vo) throws Exception;

	public List<V> page(V vo, int page, int size) throws Exception;

	public int count(V vo) throws Exception;

}
