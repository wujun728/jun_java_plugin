package com.kulv.base.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper <T>{
	
	/**
	 * 增加
	 * @param t
	 * @return
	 */
	public int addBean(T t);
	
	/**
	 * 根据id删除实体
	 * @param id
	 * @return
	 */
	public int deleteBeanById(String id);
	
	/**
	 * 根据实体 更新
	 * @param t
	 * @return
	 */
	public int updateBean(T t);
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	public T getBeanById(String id);
	
	/**
	 * 根据实体属性获取实体
	 * @param t 实体
	 * @return
	 */
	public T getBeanByT(T t);
	
	/**
	 * 获取全部实体
	 * @return
	 */
	public List<T> getBeans();
	
	
	/**
	 * 根据id批量删除
	 * @param ids
	 * @return
	 */
	public int deleteBeansByIds(String... ids);
	
	/**
	 * 动态sql获取实体列表
	 * @param map
	 * @return
	 */
	public List<T> getBeansByParams(Map<Object, Object> map);
	
	/**
	 * 根据条件获取总条数
	 * @return
	 */
	public int getCount(Map<Object, Object> map);
	

	/**
	 * 动态sql获取实体
	 * @param map
	 * @return
	 */
	public T getBeanByParams(Map<Object, Object> map);
}
