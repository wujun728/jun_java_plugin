package com.jun.plugin.servlet.guice.core.db.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.jun.plugin.servlet.guice.core.db.dao.impl.BaseDaoImpl;
import com.jun.plugin.servlet.guice.core.db.model.Page;

@ImplementedBy(BaseDaoImpl.class)
public interface BaseDao<T, ID extends Serializable> {

	/**
	 * 添加
	 * 
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	void add(String sql, Object params[]) throws SQLException;

	/**
	 * 批量添加
	 * 
	 * @param sql
	 * @param params
	 */
	void batchAdd(String sql, Object[][] params) throws SQLException;
	
	/**
	 * 根据sql删除数据
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	void del(String sql,  Object params[]) throws SQLException;
	
	
	/**
	 * 更新接口
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	void update(String sql,Object params[]) throws SQLException;
	
	
	/**
	 *  通用查询接口
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	List<T> findAll(String sql,Object params[]) throws SQLException;

	
	
	/**
	 * 根据id 集合查询数据
	 * @param tableName
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	List<T> findByIds(String tableName,List<ID> ids) throws SQLException;
	
	
	/**
	 * 根据表名与主键id查询单条数据
	 * @param tableName
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	T findById(String tableName,ID id) throws SQLException;
	
	
	/**
	 * 删除当条数据
	 * @param tableName
	 * @param id
	 * @throws SQLException
	 */
	void delById(String tableName,ID id) throws SQLException;
	
	/**
	 * 删除多条数据
	 * @param tableName
	 * @param ids
	 * @throws SQLException
	 */
	void delByIds(String tableName,List<ID> ids) throws SQLException;
	
	
	/**
	 * 单表分页查询
	 * @param tableName
	 * @param offset
	 * @param size
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	Page<T> findByPage(String tableName,Integer offset, Integer size,Map<String, Object> map) throws SQLException;
	
	
	/**
	 * 通过sql自定义分页相关
	 * @param sql
	 * @param countSql 总条数sql
	 * @return
	 */
	Page<T> findByPage(String sql,String countSql) throws SQLException;
	
	
	
}