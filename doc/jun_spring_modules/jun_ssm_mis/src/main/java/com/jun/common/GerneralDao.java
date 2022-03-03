package com.jun.common;

import java.util.List;
import java.util.Map;

import com.jun.common.page.PageData;

/**
 * 通用DAO
 * @author Wujun
 * @date   2010-11-30 
 *
 */
public interface GerneralDao<K,T> {
	/**
	 * 根据Id加载实体
	 * @param id
	 * @return
	 */
	public T get(K id); 
	
	/**
	 * 根据Id加载实体
	 * @param id
	 * @return
	 */
	public T load(K id); 
	
	/**
	 * 更新实体
	 * @param entity
	 */
	public int update(T entity);
	/**
	 * 批量更新实体
	 * @param entities 要更新的对象集合
	 * @return 返回受影响的行数
	 */
	public int bulkUpdate(List<T> entities);
	/**
	 * 更新实体Merge方式
	 * 
	 * @param entity
	 */
	public void merge(T entity);
	
	/**
	 * 根据Id删除某个实体
	 * @param id
	 */
	public int delete(K id);
	/**
	 * 删除指定实体
	 */
	public int del(T entity);
	/**
	 * 保存实体
	 * @param entity
	 */
	public Long save(T entity);
	/**
	 * 批量保存实体
	 * @param entites  要保存的实体对象集合
	 * @return  返回保存的对象主键id集合
	 */
	public List<K> bulkSave(List<T> entites);
	/**
	 * 保存或更新实体
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	/**
	 * 查询所有
	 * @return List<T>
	 */
	public List<T> findAll(Class targetClass);
	
	/**
	 * 根据给定hql查询
	 */
	public List findForHql(String hql);
	
	/**
	 * 根据给定hql查询
	 */
	public List<T> findByHql(String hql);
	
	/**
	 * 根据给定hql查询(带参)
	 */
	public List<T> findByHql(String hql, Object[] condition);

	/**
	 * 根据给定hql查询(带参)--重载
	 */
	public List<T> findByHql(String hql, List condition);
	/**
	 * hql分页查询
	 * 
	 * @return
	 */
	public PageData findForPage(final String hql, final int currentPage,
			final int pageSize);

	/**
	 * hql分页查询(带参)
	 * 
	 * @return
	 */
	public PageData findForPage(final String hql, final Object[] condition,
			final int currentPage, final int pageSize);

	/**
	 * hql分页查询(带参)重载
	 * 
	 * @return
	 */
	public PageData findForPage(final String hql, final List condition,
			final int currentPage, final int pageSize);
	/**
	 * hql批量删除更新
	 * @param hql,array
	 */
	public int batchUpdateOrDelete(final String hql,final Object[] condition);
	
	/**
	 * hql批量删除更新(重载1)
	 * @param hql，Map
	 */
	public int batchUpdateOrDelete(String hql,Map condition);
	
	/**
	 * hql批量删除更新(重载2)
	 * @param hql，List
	 */
	public int batchUpdateOrDelete(String hql,List condition);
	
	/**
	 * hql批量删除更新
	 * @param hql,array
	 */
	public int bulkUpdateOrDelete(final String hql,final Object[] condition);
	
	/**
	 * hql批量删除更新
	 * @param hql,array
	 */
	public int bulkUpdateOrDelete(final String hql,final Map condition);
    
	/**
	 * hql批量删除更新
	 * @param hql,array
	 */
	public int bulkUpdateOrDelete(final String hql,final List condition);
	/**
	 * hql批量删除更新(hql类名不能有别名 wher xxx.id in(:condition)) 推荐使用此方法
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDeleteNew(final String hql, final Object[] condition);
	/**
	 * hql批量删除更新(hql类名不能有别名 wher xxx.id in(:condition))
	 * @param hql,array
	 */
	public int bulkUpdateOrDeleteNew(final String hql,final List condition);
	/**
	 * hql批量删除更新(hql类名不能有别名 wher xxx.id in(:condition))
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDeleteNew(final String hql, final Map condition);
	/**
	 * 根据给定Id判定某个实体是否存在
	 * @param id
	 * @return
	 */
	public boolean has(K id);
	
	/**
	 * 查询数据总条数
	 * @param hql
	 * @param condition
	 * @return
	 */
	public Long getTotal(String hql);
	
	/**
	 * 查询数据总条数(带参)
	 * @param hql
	 * @param condition
	 * @return
	 */
	public Long getTotal(String hql,Object[] condition);
	
	/**************************************华丽的分割线 begin********************************************/	
	/*************************************JDBC原生态sql封装封装******************************************/
	/**************************************华丽的分割线 end*********************************************/	
	
	/**
	 * 适用于增删改操作(不推荐使用此方法，因为此方法会破坏Hibernate的二级缓存体系)
	 * @param sql 例如  delete from tableName where id = ?
	 * @param condition
	 * @return 是否执行成功  true/false
	 */
	public boolean excuteUpdate(String sql,Object[] condition);
	
	/**
	 * 返回单列值（适用于查询总数、平均数、最大值、最小值）
	 * @param sql  例如：select count(*) from user where age > ? 
	 * @param condition
	 * @return
	 */
	public int queryForInt(String sql,Object[] condition);
	
	/**
	 * 返回单个对象(适用于主键查询)
	 * @param sql 例如： select * from user where id = ?
	 * @param condition
	 * @return
	 */
	public T queryForObject(String sql,Object[] condition);
	
	/**
	 * 返回对象集合
	 * @param sql
	 * @param condition
	 * @return
	 */
	public List<T> queryForList(String sql,Object[] condition);
	
	/**
	 * JDBC分页查询
	 * @param sql          初始sql
	 * @param condition    查询参数
	 * @param currentPage  当前第几页
	 * @param pageSize     每页大小
	 * @return
	 */
	public PageData queryForPage(String sql,Object[] condition,int currentPage,int pageSize,String dataBaseName);
}
