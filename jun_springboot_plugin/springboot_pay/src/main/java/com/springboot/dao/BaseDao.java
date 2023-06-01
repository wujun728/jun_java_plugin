
package com.springboot.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T> {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private static final Object NULL_PARA_ARRAY = new Object[0];

	private Class<T> poClass;

	public BaseDao() {
	}

	public BaseDao(Class<T> poClass) {
		this.poClass = poClass;
	}

	public Serializable save(T po) {
		try {
			return getSession().save(po);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		if (id != null && !"".equals(id.toString())) {
			try {
				Object entity = getSession().get(poClass, id);
				// 如果获取的是一个代理对象，则从代理对象中获取实际的实体对象返回。
				if (entity instanceof HibernateProxy) {
					entity = ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
				}
				return (T) entity;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public T findFirstByHql(String hql, Object... param) {
		Query query = createQuery(hql, param);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return (T) query.uniqueResult();
	}

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return List<T>
	 */
	public List<T> findListByHql(String hql, Object... param) {
		try {
			Query query = createQuery(hql, param);
			return query.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public void update(T po) {
		try {
			getSession().update(po);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public int deleteByHql(String hql, Object... values) {
		return executeUpdateByHql(hql, values);
	}

	public void deleteById(Serializable id) {
		delete(get(id));
	}

	public void delete(T vo) {
		try {
			getSession().delete(vo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public int updateByHql(String hql, Object... values) {
		return executeUpdateByHql(hql, values);
	}

	@SuppressWarnings("unchecked")
	private Page<T> paginate(Integer pageNo, Integer pageSize, Integer totalCount, String hql, Object... values) {
		if (totalCount < 1) {
			return new Page<T>(pageSize);
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		Page<T> page = new Page<T>(totalCount, pageNo, pageSize);
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult((page.getPageNumber() - 1) * pageSize).setMaxResults(pageSize).list();
		page.setContents(list);
		return page;
	}

	public Page<T> paginateByHql(Integer pageNo, Integer pageSize, String hql, Object... values) {
		return paginate(pageNo, pageSize, count(hql, values), hql, values);
	}

	private Integer count(String hql, Object... values) {
		String fromHql = hql;
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return Integer.valueOf(createQuery(countHql, values).uniqueResult().toString());
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T queryColumn(String sql, Object... args) {
		Query query = createSQLQuery(sql, args);
		List<T> result = query.list();
		if (result.size() > 0) {
			T temp = result.get(0);
			if (temp instanceof Object[])
				throw new RuntimeException("Only ONE COLUMN can be queried.");
			return temp;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T queryColumn(String sql) {
		return (T) queryColumn(sql, NULL_PARA_ARRAY);
	}

	public Number queryNumber(String sql, Object... paras) {
		return (Number) queryColumn(sql, paras);
	}

	public Number queryNumber(String sql) {
		return (Number) queryColumn(sql, NULL_PARA_ARRAY);
	}

	public Integer queryInt(String sql, Object... paras) {
		Number n = queryNumber(sql, paras);
		return n != null ? n.intValue() : null;
	}

	public Integer queryInt(String sql) {
		return queryInt(sql, NULL_PARA_ARRAY);
	}

	public Long queryLong(String sql, Object... paras) {
		Number n = queryNumber(sql, paras);
		return n != null ? n.longValue() : null;
	}

	public Long queryLong(String sql) {
		return queryLong(sql, NULL_PARA_ARRAY);
	}

	public Double queryDouble(String sql, Object... paras) {
		Number n = queryNumber(sql, paras);
		return n != null ? n.doubleValue() : null;
	}

	public Double queryDouble(String sql) {
		return queryDouble(sql, NULL_PARA_ARRAY);
	}

	public Float queryFloat(String sql, Object... paras) {
		Number n = queryNumber(sql, paras);
		return n != null ? n.floatValue() : null;
	}

	public Float queryFloat(String sql) {
		return queryFloat(sql, NULL_PARA_ARRAY);
	}

	public java.math.BigDecimal queryBigDecimal(String sql, Object... paras) {
		return (java.math.BigDecimal) queryColumn(sql, paras);
	}

	public java.math.BigDecimal queryBigDecimal(String sql) {
		return (java.math.BigDecimal) queryColumn(sql, NULL_PARA_ARRAY);
	}

	public String queryStr(String sql, Object... paras) {
		Object s = queryColumn(sql, paras);
		return s != null ? s.toString() : null;
	}

	public String queryStr(String sql) {
		return queryStr(sql, NULL_PARA_ARRAY);
	}

	private Session getSession() {
		return sessionFactory.openSession();
	}

	private Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	private Query createSQLQuery(String sql, Object... values) {
		Query querySQL = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			querySQL.setParameter(i, values[i]);
		}
		return querySQL;
	}

	private int executeUpdateByHql(String hqlSQL, Object... values) {
		Query query = getSession().createQuery(hqlSQL);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		try {
			return query.executeUpdate();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
