package com.erp.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.erp.utils.Pagination;

public interface BaseDao<T,ID extends Serializable>{

    public void save(T o);
    public void flush();
    public void clear();

    /**
     * 保存方法
     *
     * @param o
     * @return
     */
    public void  merge(T o);

    public void delete(T o);

    public void refresh(T o);

    List<T> find(String hql);

    public List<T> find(String hql, Object[] param);

    public List<T> findAll(Class clazz);


    List<T> find(String hql, List<Object> param);

    public List<T> find(String hql, Object[] param, Integer page, Integer rows);


    public List<T> find(String hql, Map<String, Object> map, Integer page, Integer rows);

    public List<T> find(String hql, List<Object> param, Integer page,Integer rows);

    public Long count(String hql);

    public Long count(String hql, Object[] param);

    public Long count(String hql, Map<String, Object> map);

    public Long count(String hql, List<Object> param);

    public Integer executeHql(String hql);

    public Integer executeHql(String hql, List<Object> param);
    public Integer executeHql(String hql, Object[] param);

    public int executeHql(String hql, Map<String,Object> map);

    public List<T> getByHqlWithKey(String hql, Pagination pagination, Map<String, Object> map);
    public List<T> getByHqlWithIndex(String hql, Pagination pagination, Object... objects);
    public Query getHqlQuery(String hql, Map<String,Object> map);
    public Query getHqlQuery(String hql, Object... objects);

    public List<T> queryHqlByPage(String hql, Pagination pagination,
                                  Map<String, Object> map);


    public List<T> getSomePropertiesByHqlWithKey(String hql, Map<String, Object> map);



}
