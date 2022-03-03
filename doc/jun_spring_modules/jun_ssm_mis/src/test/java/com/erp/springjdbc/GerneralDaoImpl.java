package com.erp.springjdbc;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 通用dao封装
 * @author Wujun
 * @createTime 2011-03-11
 */
public class GerneralDaoImpl<K,T> implements GerneralDao<K,T> {
	private Class entityClass;
	private HibernateTemplate hibernateTemplate;
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public GerneralDaoImpl() {
		this.entityClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/**
	 * 根据Id加载实体
	 * 
	 * @param id
	 * @return
	 */
	public T get(K id) {
		return (T) getHibernateTemplate().get(entityClass, (Serializable) id);
	}
	
	/**
	 * 根据Id加载实体
	 * @param id
	 * @return
	 */
	public T load(K id){
		return (T) getHibernateTemplate().load(entityClass, (Serializable) id);
	}
	
	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public int update(T entity) {
		if(null == entity){
			return 0;
		}
		getHibernateTemplate().clear();//清理Session缓存(很关键)
		getHibernateTemplate().update(entity);
		return 1;
	}
	/**
	 * 批量更新实体
	 * @param entities 要更新的对象集合
	 * @return 返回受影响的行数
	 */
	public int bulkUpdate(List<T> entities){
		int count = 0;  //统计受影响的行数
		if(null == entities || entities.size() == 0){
			return count;
		}
		for(T t : entities){
			if(count % 20 == 0){
				getHibernateTemplate().flush(); //清理Hibernate缓存，防止内存溢出
			}
			getHibernateTemplate().update(t);
			count++;
		}
		return count;
	}
	
	/**
	 * 更新实体Merge方式
	 * 
	 * @param entity
	 */
	public void merge(T entity) {
		getHibernateTemplate().merge(entity);
	}
	/**
	 * 删除指定实体
	 */
	public int del(T entity){
		if(null == entity){
			return 0;
		}
		getHibernateTemplate().delete(entity);
		return 1;
	}
	/**
	 * 根据Id删除某个实体
	 * 
	 * @param id
	 */
	public int delete(K id) {
		Object object = get(id);
		if(null == object){
			return 0;
		}
		getHibernateTemplate().clear();//清理Session缓存(很关键)
		getHibernateTemplate().delete(object);
		return 1;
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public Long save(T entity) {
		return (Long) getHibernateTemplate().save(entity);
	}
	/**
	 * 批量保存实体
	 * @param entites  要保存的实体对象集合
	 * @return  返回保存的对象主键id集合
	 */
	public List<K> bulkSave(List<T> entites){
		List<K> list = new ArrayList<K>();
		int index = 0;
		for (T t : entites) {
			if(index % 20 == 0){
				getHibernateTemplate().flush();//清理Hibernate缓存
			}
			K id = (K)getHibernateTemplate().save(t);
			list.add(id);
			index++;
		}
		return list;
	}
	/**
	 * 保存或更新实体
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 查询所有
	 * 
	 * @return List<T>
	 */
	public List<T> findAll(Class targetClass) {
		return (List<T>) getHibernateTemplate().loadAll(targetClass);
	}

	/**
	 * 根据给定hql查询
	 */
	public List findForHql(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 根据给定hql查询
	 */
	public List<T> findByHql(String hql) {
		return (List<T>) getHibernateTemplate().find(hql);
	}

	/**
	 * 根据给定hql查询(带参)
	 */
	public List<T> findByHql(String hql, Object[] condition) {
		return (List<T>) getHibernateTemplate().find(hql, condition);
	}

	/**
	 * 根据给定hql查询(带参)--重载
	 */
	public List<T> findByHql(String hql, List condition) {
		return findByHql(hql,condition.toArray());
	}
	/**
	 * hql分页查询
	 * 
	 * @return
	 */
	public Object findForPage(final String hql, final int currentPage,
			final int pageSize) {
		return findForPage(hql, new Object[]{}, currentPage, pageSize);
	}

	/**
	 * hql分页查询(带参)
	 * 
	 * @return
	 */
	public Object findForPage(final String hql, final Object[] condition,
			final int currentPage, final int pageSize) {
		int total = getTotal(getTotalHql(hql), condition).intValue();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query = setParameters(query, condition);
				return query.setFirstResult((currentPage - 1) * pageSize)
						.setMaxResults(pageSize).list();
			}
		});
		return new Object();
	}
	/**
	 * hql分页查询(带参)重载
	 * 
	 * @return
	 */
	public Object findForPage(final String hql, final List condition,
			final int currentPage, final int pageSize){
		return findForPage(hql,condition.toArray(),currentPage,pageSize);
	}
	/**
	 * hql批量删除更新(hql类名不能有别名� where xx in(?,?,?,?))
	 * 
	 * @param hql
	 *            ,array
	 */
	public int batchUpdateOrDelete(final String hql, final Object[] condition) {
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query = setParameters(query, condition);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * hql批量删除更新(重载1)
	 * 
	 * @param hql
	 *            map
	 */
	public int batchUpdateOrDelete(final String hql, final Map condition) {
		return batchUpdateOrDelete(hql, condition.values().toArray());
	}

	/**
	 * hql批量删除更新(重载2)
	 * 
	 * @param hql
	 *            ，List
	 */
	public int batchUpdateOrDelete(final String hql, final List condition) {
		return batchUpdateOrDelete(hql, condition.toArray());
	}

	/**
	 * hql批量删除更新
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDelete(final String hql, final Object[] condition) {
		return getHibernateTemplate().bulkUpdate(hql, condition);
	}

	/**
	 * hql批量删除更新
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDelete(final String hql, final Map condition) {
		return bulkUpdateOrDelete(hql, condition.values().toArray());
	}

	/**
	 * hql批量删除更新
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDelete(final String hql, final List condition) {
		return bulkUpdateOrDelete(hql, condition.toArray());
	}

	/**
	 * hql批量删除更新(hql类名不能有别名 wher id in(:condition)) 推荐使用此方法
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDeleteNew(final String hql, final Object[] condition) {
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (null != condition && condition.length > 0) {
					query.setParameterList("condition", condition);
				}
				return query.executeUpdate();
			}
		});
	}

	/**
	 * hql批量删除更新(hql类名不能有别名 wher id in(:condition))
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDeleteNew(final String hql, final Map condition) {
		return bulkUpdateOrDeleteNew(hql,condition.values().toArray());
	}

	/**
	 * hql批量删除更新(hql类名不能有别名 wher id in(:condition))
	 * 
	 * @param hql
	 *            ,array
	 */
	public int bulkUpdateOrDeleteNew(final String hql, final List condition) {
		return bulkUpdateOrDeleteNew(hql,condition.toArray());
	}
	/**
	 * 根据传入id集合批量加载对象集合
	 *     hql：wher id in(:idList)
	 */
	public List bulkFindByIdList(final String hql,final List idList){
		return bulkFindByIdList(hql,idList.toArray());
	}
	/**
	 * 根据传入id集合批量加载对象集合(重载)
	 *     hql：wher id in(:idList)
	 */
	public List bulkFindByIdList(final String hql,final Object[] idList){
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (null != idList && idList.length > 0) {
					query.setParameterList("idList", idList);
				}
				return query.list();
			}
		});
	}
	/**
	 * 根据给定Id判定某个实体是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean has(K id) {
		if (id == null || id.equals("")) {
			return false;
		}
		return (get(id) != null);
	}

	/**
	 * 查询数据总条数
	 * 
	 * @param hql
	 * @param condition
	 * @return
	 */
	public Long getTotal(String hql) {
		return (Long) getHibernateTemplate().find(hql).get(0);
	}

	/**
	 * 查询数据总条数(带参)
	 * 
	 * @param hql
	 * @param condition
	 * @return
	 */
	public Long getTotal(String hql, Object[] condition) {
		return (Long) getHibernateTemplate().find(hql, condition).get(0);
	}

	/**
	 * 设置参数
	 * 
	 * @param query
	 * @param condition
	 * @return Query
	 */
	private Query setParameters(Query query, Object[] condition) {
		if (null != condition && condition.length != 0) {
			for (int i = 0; i < condition.length; i++) {
				query.setParameter(i, condition[i]);
			}
		}
		return query;
	}

	/************************************** 华丽的分割线 begin ********************************************/
	/************************************* JDBC原生态sql封装封装 ******************************************/
	/************************************** 华丽的分割线 end *********************************************/

	/**
	 * 适用于增删改操作(不推荐使用此方法，因为此方法会破坏Hibernate的二级缓存体系)
	 * 
	 * @param sql
	 *            例如 delete from tableName where id = ?
	 * @param condition
	 * @return 是否执行成功 true/false
	 */
	public boolean excuteUpdate(String sql, Object[] condition) {
		return getJdbcTemplate().update(sql, condition) > 0;
	}

	/**
	 * 返回单列值（适用于查询总数、平均数、最大值、最小值）
	 * 
	 * @param sql
	 *            例如：select count(*) from user where age > ?
	 * @param condition
	 * @return
	 */
	public int queryForInt(String sql, Object[] condition) {
		return getJdbcTemplate().queryForInt(sql, condition);
	}

	/**
	 * 返回单个对象(适用于主键查询)
	 * 
	 * @param sql
	 *            例如： select * from user where id = ?
	 * @param condition
	 * @return
	 */
	public T queryForObject(String sql, Object[] condition) {
		return (T) getJdbcTemplate()
				.queryForObject(sql, condition, entityClass);
	}

	/**
	 * 返回对象集合
	 * 
	 * @param sql
	 * @param condition
	 * @return
	 */
	public List<T> queryForList(String sql, Object[] condition) {
		return (List<T>) getJdbcTemplate().queryForList(sql, condition,
				entityClass);
	}

	/**
	 * JDBC分页查询
	 * 
	 * @param sql
	 *            初始sql
	 * @param condition
	 *            查询参数
	 * @param currentPage
	 *            当前第几页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public Object queryForPage(String sql, Object[] condition,
			int currentPage, int pageSize, String dataBaseName) {
		// 总记录数
		int total = queryForInt(getTotalSql(sql), null);
		if (total <= 0) {
			return null;
		}
		String pageSql = getPageSql(sql, currentPage, pageSize, dataBaseName);
		List list = getJdbcTemplate().queryForList(pageSql, condition);
		return new Object();
	}

	/**
	 * 获取分页sql
	 * 
	 * @param sql
	 *            初始sql
	 * @param currentPage
	 *            当前页�
	 * @param pageSize
	 *            每页大小
	 * @param dataBaseName
	 *            数据库类型�:sqlserver/mysql/orcale
	 * @return sql
	 */
	private String getPageSql(String sql, int currentPage, int pageSize,
			String dataBaseName) {
		if (null == sql || sql.equals("")) {
			return "";
		}

		StringBuffer stringBuffer = new StringBuffer();
		if (currentPage <= 0) {
			currentPage = 1;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}

		if (dataBaseName.toLowerCase().equals("sqlserver")) {
			stringBuffer
					.append("select top ")
					.append(pageSize)
					.append(" * from (")
					.append("select top ")
					.append(currentPage * pageSize)
					.append(" * from (")
					.append(sql)
					.append(") as a order by a.id desc) as b order by b.id asc");
		} else if (dataBaseName.toLowerCase().equals("mysql")) {
			stringBuffer.append("select * from (").append(sql).append(
					") limit ").append((currentPage - 1) * pageSize)
					.append(",").append(pageSize);
		} else if (dataBaseName.toLowerCase().equals("orcale")) {
			stringBuffer.append("select * from(").append(
					"select a.*,rownum r from(").append(sql).append(
					") as a where r <=").append(currentPage * pageSize).append(
					") where r >").append((currentPage - 1) * pageSize);
		}
		return stringBuffer.toString();
	}

	/**
	 * 获取查询总记录数sql
	 * 
	 * @param hql
	 *            初始sql
	 * @return
	 */
	private String getTotalSql(String sql) {
		return "select count(*) from (" + sql + ")";
	}

	/**
	 * 获取查询总记录数的hql
	 * 
	 * @param hql
	 *            初始hql
	 */
	private String getTotalHql(String hql) {
		String s = removeSubSelect(hql);
		int from = s.indexOf("from"); 
		return "select count(*) " + s.substring(from);
	}
	
	/**
	 * 移除key指定的子句，如order by,group by
	 */
	private String removeByKey(String hql,String key){
		int index = hql.indexOf(key.toLowerCase());
		if(index == -1){
			return hql;
		}
		return hql.replace(hql.substring(index),"");
	}
	
	/**
	 * 移除distinct关键字
	 */
	private String removeDistinct(String hql){
		if(hql.indexOf("distinct") == -1){
			return hql;
		}
		return hql.replace("distinct","");
	}
	
	/**
	 * 移除select部分子查询
	 * @param hql 传入的hql语句
	 */
	private String removeSubSelect(String hql){
		String regex = "[^in\\s]+\\([^)]*([^(^)]*?)\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(hql);
		while(m.find()){
			String sub = m.group();
			hql = hql.replace(sub, "");
 		}
		return hql;
	}
}