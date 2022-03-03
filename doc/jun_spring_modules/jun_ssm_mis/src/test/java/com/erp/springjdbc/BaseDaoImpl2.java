package com.erp.springjdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jun.plugin.utils.BeanUtil;

/**
 * 基础DAO
 * 
 * @author Wujun
 * 
 */
@Repository("baseDao")
public class BaseDaoImpl2<T>  {

    private JdbcTemplate jdbcTemplate;
    


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 */
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		//封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql,page,rows);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<T> findObjForJdbc(String sql, int page, int rows,Class<T> clazz) {
		List<T> rsList = new ArrayList<T>();
		//封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql,page,rows);
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
		
		T po = null;
		for(Map<String,Object> m:mapList){
			try {
				po = clazz.newInstance();
				BeanUtil.copyMap2Bean_Nobig(po, m);
				rsList.add(po);
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rsList;
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	public  List<Map<String, Object>>  findForJdbcParam(String  sql,  int page, int rows,Object... objs){
		//封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql,page,rows);	
		return this.getJdbcTemplate().queryForList(sql,objs);
	}
	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */
	public Long getCountForJdbc(String  sql) {
		return  this.getJdbcTemplate().queryForLong(sql);
	}
	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	public Long getCountForJdbcParam(String  sql,Object[] objs) {
		return  this.getJdbcTemplate().queryForLong(sql, objs);
	}

	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql,objs);
	}

	public Integer executeSql(String sql,List<Object> param) {
		return this.jdbcTemplate.update(sql,param);
	}

	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql,param);
	}

	public Integer countByJdbc(String sql, Object... param) {
		return this.jdbcTemplate.queryForInt(sql, param);
	}

	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		try{ 
			return this.jdbcTemplate.queryForMap(sql, objs);
		}catch (EmptyResultDataAccessException e) {   
		    return null;   
		}  
	}
	
	//**********************************************************************************
/*
    private JdbcTemplate jdbcTemplate;
    
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T o) {
		this.getCurrentSession().save(o);
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public void merge(T o) {
		this.getCurrentSession().merge(o);
	}

	public void evict(T o) {
		getCurrentSession().evict(o);
	}
	
	
	
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	public List<T> find(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public List<T> find(String hql, int page, int rows, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> find(String hql, int page, int rows, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object... param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}

	public T get(String hql, List<Object> param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}

	public Long count(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	*//**
	 * 使用指定的检索标准检索数据并分页返回数据
	 *//*
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		//封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql,page,rows);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	*//**
	 * 使用指定的检索标准检索数据并分页返回数据
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 *//*
	public List<T> findObjForJdbc(String sql, int page, int rows,Class<T> clazz) {
		List<T> rsList = new ArrayList<T>();
		//封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql,page,rows);
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
		
		T po = null;
		for(Map<String,Object> m:mapList){
			try {
				po = clazz.newInstance();
				BeanUtil.copyMap2Bean_Nobig(po, m);
				rsList.add(po);
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rsList;
	}

	*//**
	 * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 *//*
	public  List<Map<String, Object>>  findForJdbcParam(String  sql,  int page, int rows,Object... objs){
		//封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql,page,rows);	
		return this.getJdbcTemplate().queryForList(sql,objs);
	}
	*//**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 *//*
	public Long getCountForJdbc(String  sql) {
		return  this.getJdbcTemplate().queryForLong(sql);
	}
	*//**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 *//*
	public Long getCountForJdbcParam(String  sql,Object[] objs) {
		return  this.getJdbcTemplate().queryForLong(sql, objs);
	}

	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql,objs);
	}

	public Integer executeSql(String sql,List<Object> param) {
		return this.jdbcTemplate.update(sql,param);
	}

	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql,param);
	}

	public Integer countByJdbc(String sql, Object... param) {
		return this.jdbcTemplate.queryForInt(sql, param);
	}

	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		try{ 
			return this.jdbcTemplate.queryForMap(sql, objs);
		}catch (EmptyResultDataAccessException e) {   
		    return null;   
		}  
	}*/

}
