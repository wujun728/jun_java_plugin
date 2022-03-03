/**   
* @Title: PublicDao.java TODO:
* @Package com.erp.dao
* @Description: TODO
* @author Wujun
* @date 2013-4-19 上午08:40:54
* @version V1.0   
*/
package com.erp.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类功能说明 TODO:公用dao接口
 * 类修改者	修改日期
 * 修改说明
 * <p>Title: PublicDao.java</p>
 * <p>Description:福产流通科技</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company:福产流通科技</p>
 * @author Wujun
 * @date 2013-4-19 上午08:40:54
 * @version V1.0
 */
public interface PublicDao<T>
{
	/**
	 * @return  
	* @Title: save 
	* @Description: TODO:保存一个对象
	* @param @param o
	* @param @return    设定文件 
	* @return Serializable    返回类型 
	* @throws 
	*/
	public Serializable save(T o);

	/** 
	* @Title: delete 
	* @Description: TODO:删除一个对象
	* @param @param o    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void delete(T o);

	/** 
	* @Title: update 
	* @Description: TODO:更新一个对象
	* @param @param o    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void update(T o);

	/** 
	* @Title: saveOrUpdate 
	* @Description: TODO:保存或更新对象
	* @param @param o    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveOrUpdate(T o);

	/** 
	* @Title: find 
	* @Description: TODO:查询
	* @param @param hql
	* @param @return    设定文件 
	* @return List<T>    返回类型 
	* @throws 
	*/
	public List<T> find(String hql);

	/** 
	* @Title: get 
	* @Description: TODO:获得一个对象
	* @param @param c
	* @param @param id
	* @param @return    设定文件 
	* @return T    返回类型 
	* @throws 
	*/
	public T get(Class<T> c, Serializable id);

	/** 
	* @Title: count 
	* @Description: TODO:select count(*) from 类
	* @param @param hql
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws 
	*/
	public Long count(String hql);

	/** 
	* @Title: executeHql 
	* @Description: TODO:执行HQL语句
	* @param @param hql
	* @param @return    设定文件 响应数目
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer executeHql(String hql);

	/** 
	* @Title: find 
	* @Description: TODO:查询集合
	* @param @param hql
	* @param @param params
	* @param @return    设定文件 
	* @return List<T>    返回类型 
	* @throws 
	*/
	List<T> find(String hql, Map<String, Object> params);

	/** 
	* @Title: find 
	* @Description: TODO:查询分页集合
	* @param @param hql
	* @param @param params
	* @param @param page
	* @param @param rows
	* @param @return    设定文件 
	* @return List<T>    返回类型 
	* @throws 
	*/
	List<T> find(String hql, Map<String, Object> params, Integer page,
			Integer rows);

	/** 
	* @Title: get 
	* @Description: TODO:根据参数查询实体类
	* @param @param hql
	* @param @param param
	* @param @return    设定文件 
	* @return T    返回类型 
	* @throws 
	*/
	T get(String hql, Map<String, Object> param);

	/** 
	* @Title: count 
	* @Description: TODO:根据参数查询集合条数
	* @param @param hql
	* @param @param params
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws 
	*/
	Long count(String hql, Map<String, Object> params);

	/** 
	* @Title: executeHql 
	* @Description: TODO:批量执行HQL (更新) 响应数目
	* @param @param hql
	* @param @param params
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/
	Integer executeHql(String hql, Map<String, Object> params);

	@SuppressWarnings("rawtypes")
	List findBySQL(String sql );

	void deleteToUpdate(T o );

}
