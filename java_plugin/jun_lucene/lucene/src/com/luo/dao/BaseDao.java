package com.luo.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.luo.util.ReflectUtil;


public class BaseDao<T,PK extends Serializable> {

	private SessionFactory sessionFactory;
	private Class<?> entityClass;
	
	public BaseDao(){
		entityClass = ReflectUtil.getGenericParmeterType(this.getClass());
	}

	public void save(T t) {
		getSession().saveOrUpdate(t);
	}
	
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		return (T) getSession().get(entityClass, id);
	}
	
	public void del(PK id) {
		getSession().delete(findById(id));
	}
	
	public void del(T t) {
		getSession().delete(t);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria c = getSession().createCriteria(entityClass);
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByPage(int start,int count) {
		Criteria c = getSession().createCriteria(entityClass);
		c.setFirstResult(start);
		c.setMaxResults(count);
		return c.list();
	}
	
	
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
}
