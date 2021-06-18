package com.jd.ssh.sshdemo.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.jd.ssh.sshdemo.bean.EnumManage.DocumentStateEnum;
import com.jd.ssh.sshdemo.bean.Pager;
import com.jd.ssh.sshdemo.dao.BaseDao;
import com.jd.ssh.sshdemo.service.BaseService;

/**
 * Service实现类 - Service实现类基类
 * ============================================================================
 * 版权所有 2013 qshihua。
 * ----------------------------------------------------------------------------
 * 
 * @author Wujun
 * 
 * @version 0.1 2013-01-06
 */

public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	public T get(PK id) {
		return baseDao.get(id);
	}

	public T load(PK id) {
		return baseDao.load(id);
	}
	
	public List<T> get(PK[] ids) {
		return baseDao.get(ids);
	}
	
	public T get(String propertyName, Object value) {
		return baseDao.get(propertyName, value);
	}
	
	public List<T> getList(String propertyName, Object value) {
		return baseDao.getList(propertyName, value);
	}

	public List<T> getAll() {
		return baseDao.getAll();
	}
	
	public Long getTotalCount() {
		return baseDao.getTotalCount();
	}

	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		return baseDao.isUnique(propertyName, oldValue, newValue);
	}
	
	public boolean isExist(String propertyName, Object value) {
		return baseDao.isExist(propertyName, value);
	}

	public PK save(T entity) {
		return baseDao.save(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}

	public void delete(T entity) {
		baseDao.delete(entity);
	}

	public void delete(PK id) {
		baseDao.delete(id);
	}

	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}
	
	public void flush() {
		baseDao.flush();
	}

	public void clear() {
		baseDao.clear();
	}
	
	public void evict(Object object) {
		baseDao.evict(object);
	}

	public Pager findByPager(Pager pager) {
		return baseDao.findByPager(pager);
	}
	
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		return baseDao.findByPager(pager, detachedCriteria);
	}
	
	public Pager findByPagerAndStates(Pager pager, DocumentStateEnum[] states) {
		return baseDao.findByPagerAndStates(pager, states);
	}
	
	public Pager queryEnableByPager(Pager pager) {
		return baseDao.findByPagerAndStates(pager, new DocumentStateEnum[] {DocumentStateEnum.Enable});
	}

	public Pager queryDeletedByPager(Pager pager) {
		return baseDao.findByPagerAndStates(pager, new DocumentStateEnum[] {DocumentStateEnum.Delete});
	}
	
	public Pager queryValidByPager(Pager pager) {
		return baseDao.queryValidByPager(pager);
	}
	
	public T getLastSn(){
		return baseDao.getLastSn();
	}
	public List<T> queryAllByHql(String hql) {
		// TODO Auto-generated method stub
		return baseDao.listAll(hql);
	}

	
}
