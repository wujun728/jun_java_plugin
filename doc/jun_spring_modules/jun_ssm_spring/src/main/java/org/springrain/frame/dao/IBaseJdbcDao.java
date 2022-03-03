package org.springrain.frame.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.SqlParameter;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

/**
 * 基础的Dao接口,所有的Dao都必须实现此接口,每个数据库都需要一个实现.</br>
 * 例如
 * demo数据的实现接口是org.springrain.springrain.dao.IBasespringrainDao,demo2数据的实现接口是org.springrain.demo2.dao.IBasedemo2Dao</br>
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.dao.IBaseJdbcDao
 */

public interface IBaseJdbcDao {

	/**
	 * 只有Finder查询语句,返回结果是List<Map>
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForList(Finder finder) throws Exception;

	/**
	 * 指定返回对象是T,查询结果是 List<T>
	 * 
	 * @param finder
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForList(Finder finder, Class<T> clazz) throws Exception;

	/**
	 * 指定分页对象 进行列表查询,返回 List<Map>
	 * 
	 * @param finder
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForList(Finder finder, Page page) throws Exception;

	/**
	 * 指定返回结果和分页对象,进行分页查询,查询结果是 List<T>
	 * 
	 * @param finder
	 * @param clazz
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForList(Finder finder, Class<T> clazz, Page page) throws Exception;

	/**
	 * 调用存储过程 查询结果是 List<Entity>
	 * 
	 * @param finder
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForListByProc(Finder finder, Class<T> clazz) throws Exception;

	/**
	 * 调用数据库函数 查询结果是 List<Entity>
	 * 
	 * @param finder
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForListByFunction(Finder finder, Class<T> clazz) throws Exception;

	/**
	 * 调用数据库存储过程 返回指定 对象
	 * 
	 * @param <T>
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObjectByProc(Finder finder, Class<T> clazz) throws Exception;

	/**
	 * 调用数据库函数 返回指定 对象
	 * 
	 * @param <T>
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObjectByByFunction(Finder finder, Class<T> clazz) throws Exception;

	/**
	 * 执行函数 返回执行结果为Map
	 * 
	 * @param finder
	 * @return
	 */
	public Map<String, Object> queryObjectByFunction(Finder finder) throws Exception;

	/**
	 * 执行存储过程 返回执行结果为
	 * 
	 * @param finder
	 * @return
	 */
	public Map<String, Object> queryObjectByProc(Finder finder) throws Exception;

	/**
	 * 调用数据库存储过程 查询结果是 List
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForListByProc(Finder finder) throws Exception;

	/**
	 * 调用数据库函数 查询结果是 List
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForListByFunction(Finder finder) throws Exception;

	/**
	 * 只有Finder查询语句,查询一个对象,返回Map对象
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryForObject(Finder finder) throws Exception;

	/**
	 * 指定返回对象 T,查询一个对象T,返回 T 对象
	 * 
	 * @param finder
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObject(Finder finder, Class<T> clazz) throws Exception;

	/**
	 * T 作为查询的query bean,查询一个对象T,返回T
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObject(T entity) throws Exception;

	/**
	 * Entity作为查询的query bean,并返回Entity
	 * 
	 * @param entity
	 * @param page
	 *            分页对象
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForListByEntity(T entity, Page page) throws Exception;

	/**
	 * 更新数据
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Integer update(Finder finder) throws Exception;

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 *            分页对象
	 * @param clazz
	 * @param queryBean
	 *            是queryBean
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object queryBean)
			throws Exception;

	/**
	 * 保存一个对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public Object save(Object entity) throws Exception;

	/**
	 * 批量更新
	 * 
	 * @param list
	 * @return List
	 * @throws Exception
	 */

	public List<Integer> save(List list) throws Exception;

	/**
	 * 更新一个对象,更新对象映射的所有字段
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public Integer update(Object entity) throws Exception;

	/**
	 * 更新一个对象,id必须有值,updatenotnull=true 不更新为null的字段,false更新所有字段
	 * 
	 * @param entity
	 * @param updatenotnull
	 * @return
	 * @throws Exception
	 */
	public Integer update(Object entity, boolean onlyupdatenotnull) throws Exception;

	/**
	 * 批量更新对象
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */

	public List<Integer> update(List list) throws Exception;

	/**
	 * 批量更新对象,id必须有值,updatenotnull=true 不更新为null的字段,false更新所有字段
	 * 
	 * @param list
	 * @param updatenotnull
	 * @return
	 * @throws Exception
	 */

	public List<Integer> update(List list, boolean onlyupdatenotnull) throws Exception;

	/**
	 * 根据Id 删除
	 * 
	 * @param id
	 * @throws Exception
	 */

	public void deleteById(Object id, Class clazz) throws Exception;

	/**
	 * 根据ID批量删除
	 * 
	 * @param ids
	 * @param clazz
	 * @throws Exception
	 */

	public void deleteByIds(List ids, Class clazz) throws Exception;

	/**
	 * 根据ID 查询对象,主要用于分表
	 * 
	 * @param <T>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public <T> T findByID(Object id, Class<T> clazz) throws Exception;

	/**
	 * 根据ID 查询对象,用于分表
	 * 
	 * @param id
	 * @param clazz
	 * @param tableExt
	 * @return
	 * @throws Exception
	 */
	public <T> T findByID(Object id, Class<T> clazz, String tableExt) throws Exception;

	/**
	 * 判断主键是否有值 save or update 对象
	 * 
	 * @param entity
	 * @return
	 */
	public Object saveorupdate(Object entity) throws Exception;

	/**
	 * 根据查询的queryBean,拼接Finder 的 Where条件,只包含 and 条件,用于普通查询
	 * 
	 * @param finder
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public Finder getFinderWhereByQueryBean(Finder finder, Object o) throws Exception;

	/**
	 * 执行 call 操作,执行存储过程,和数据库函数
	 * 
	 * @param callableStatementCreator
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Object executeCallBack(CallableStatementCreator callableStatementCreator, List<SqlParameter> parameter)
			throws Exception;

	/**
	 * 根据page对象中sort和order 添加order by 排序,一般用于前台传递的自定义排序
	 * 
	 * @param finder
	 * @param page
	 * @return
	 */
	Finder getFinderOrderBy(Finder finder, Page page) throws Exception;

	/**
	 * 处理严格检查事务的方法名称,默认是true
	 * 
	 * @return
	 */
	boolean isCheckMethodName();

	/**
	 * 获取数据库的版本
	 * 
	 * @return
	 */
	// public String getDataBaseVersion();
	/**
	 * 获取数据库的类型
	 * 
	 * @return
	 */
	// public String getDataBaseType() ;
	/**
	 * 获取数据库所有的表
	 * 
	 * @return
	 */
	// public List<String> getDataBaseAllTables();

}
