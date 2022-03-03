package com.erp.dao;

import java.io.Serializable;
import java.util.List;

import com.erp.jee.entity.JeecgOrderCustomSingleEntity;

/**
 * 基础数据库操作类
 * 
 * @author Wujun
 * 
 */
public interface IBaseDao<T> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void save(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void update(T o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 *            对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 合并一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void merge(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(T o);

	/**
	 * 查找对象集合
	 * 
	 * @param hql
	 * @param param
	 * @return List<T>
	 */
	public List<T> find(String hql, Object... param);

	/**
	 * 查找对象集合
	 * 
	 * @param hql
	 * @param param
	 * @return List<T>
	 */
	public List<T> find(String hql, List<Object> param);

	/**
	 * 查找对象集合,带分页
	 * 
	 * @param hql
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示记录数
	 * @param param
	 * @return 分页后的List<T>
	 */
	public List<T> find(String hql, int page, int rows, List<Object> param);

	/**
	 * 查找对象集合,带分页
	 * 
	 * @param hql
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示记录数
	 * @param param
	 * @return 分页后的List<T>
	 */
	public List<T> find(String hql, int page, int rows, Object... param);

	/**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 */
	public T get(String hql, Object... param);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 */
	public T get(String hql, List<Object> param);

	/**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 */
	public T load(Class<T> c, Serializable id);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return Long
	 */
	public Long count(String hql, Object... param);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return Long
	 */
	public Long count(String hql, List<Object> param);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 相应数目
	 */
	public Integer executeHql(String hql);
	
	public Integer executeHql(String hql, Object... param);
//*****************************************************************************
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 *//*
	public void save(T o);

	*//**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 *//*
	public void update(T o);

	*//**
	 * 保存或更新对象
	 * 
	 * @param o
	 *            对象
	 *//*
	public void saveOrUpdate(T o);

	*//**
	 * 合并一个对象
	 * 
	 * @param o
	 *            对象
	 *//*
	public void merge(T o);

	*//**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 *//*
	public void delete(T o);

	*//**
	 * 查找对象集合
	 * 
	 * @param hql
	 * @param param
	 * @return List<T>
	 *//*
	public List<T> find(String hql, Object... param);

	*//**
	 * 查找对象集合
	 * 
	 * @param hql
	 * @param param
	 * @return List<T>
	 *//*
	public List<T> find(String hql, List<Object> param);

	*//**
	 * 查找对象集合,带分页
	 * 
	 * @param hql
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示记录数
	 * @param param
	 * @return 分页后的List<T>
	 *//*
	public List<T> find(String hql, int page, int rows, List<Object> param);

	*//**
	 * 查找对象集合,带分页
	 * 
	 * @param hql
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示记录数
	 * @param param
	 * @return 分页后的List<T>
	 *//*
	public List<T> find(String hql, int page, int rows, Object... param);

	*//**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 *//*
	public T get(Class<T> c, Serializable id);

	*//**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 *//*
	public T get(String hql, Object... param);

	*//**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 *//*
	public T get(String hql, List<Object> param);

	*//**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 *//*
	public T load(Class<T> c, Serializable id);

	*//**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return Long
	 *//*
	public Long count(String hql, Object... param);

	*//**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return Long
	 *//*
	public Long count(String hql, List<Object> param);

	*//**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 相应数目
	 *//*
	public Integer executeHql(String hql);*/

	public void evict(T t);

}
