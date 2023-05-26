package com.erp.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.erp.dao.BaseDao;
import com.erp.utils.Pagination;
 
@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T,ID extends Serializable>  implements BaseDao<T,ID> {

    private  static Logger log = LogManager.getLogger(BaseDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(T o) {

        entityManager.persist(o);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public void merge(T o) {
        entityManager.merge(o);
    }

    @Override
    public void delete(T o) {
        entityManager.remove(o);
    }

    @Override
    public void refresh(T o) {
        entityManager.remove(0);
    }

    @Override
    public List<T> find(String hql) {
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public List<T> find(String hql, Object[] param) {
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.list();
    }

    @Override
    public List<T> findAll(Class clazz) {
        String hql = "from " + clazz.getName();
        Query q = (Query) entityManager.createQuery(hql);
        return q.list();
    }

    @Override
    public List<T> find(String hql, List<Object> param) {
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.size() > 0) {
            for (int i = 0; i < param.size(); i++) {
                q.setParameter(i, param.get(i));
            }
        }
        return q.list();
    }

    @Override
    public List<T> find(String hql, Object[] param, Integer page, Integer rows) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (rows == null || rows < 1) {
            rows = 10;
        }
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> map, Integer page, Integer rows) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (rows == null || rows < 1) {
            rows = 10;
        }
        Query q = (Query) entityManager.createQuery(hql);
        setQueryParameters(q, map);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> find(String hql, List<Object> param, Integer page, Integer rows) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (rows == null || rows < 1) {
            rows = 10;
        }
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.size() > 0) {
            for (int i = 0; i < param.size(); i++) {
                q.setParameter(i, param.get(i));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public Long count(String hql) {
        Query q = (Query) entityManager.createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Long count(String hql, Object[] param) {
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return (Long) q.uniqueResult();
    }

    @Override
    public Long count(String hql, Map<String, Object> map) {
        Query q = (Query) entityManager.createQuery(hql);
        setQueryParameters(q, map);
        return (Long) q.uniqueResult();
    }

    @Override
    public Long count(String hql, List<Object> param) {
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.size() > 0) {
            for (int i = 0; i < param.size(); i++) {
                q.setParameter(i, param.get(i));
            }
        }
        return (Long) q.uniqueResult();
    }

    @Override
    public Integer executeHql(String hql) {
        return entityManager.createQuery(hql).executeUpdate();
    }

    @Override
    public Integer executeHql(String hql, List<Object> param) {
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.size() > 0) {
            for (int i = 0; i < param.size(); i++) {
                q.setParameter(i, param.get(i));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public Integer executeHql(String hql, Object[] param) {
        Query q = (Query) entityManager.createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.executeUpdate();
    }

    @Override
    public int executeHql(String hql, Map<String, Object> map) {
        Query query = (Query) entityManager.createQuery(hql);
        setQueryParameters(query, map);
        return query.executeUpdate();
    }

    @Override
    public List<T> getByHqlWithKey(String hql, Pagination pagination, Map<String, Object> map) {
        Query query = null;
        query = this.getHqlQuery(hql, map);
        query = this.setQueryParameters(query, pagination);
        return query.list();
    }

    @Override
    public List<T> getByHqlWithIndex(String hql, Pagination pagination, Object... objects) {
        Query query = null;
        query = this.getHqlQuery(hql, objects);
        query = this.setQueryParameters(query, pagination);
        return query.list();
    }

    @Override
    public Query getHqlQuery(String hql, Map<String, Object> map) {
        Query query = (Query) entityManager.createQuery(hql);
        if(map != null){
            return this.setQueryParameters(query, map);
        }
        return query;
    }

    @Override
    public Query getHqlQuery(String hql, Object... objects) {
        Query query = (Query) entityManager.createQuery(hql);
        if(objects != null && objects.length > 0){
            return this.setQueryParameters(query, objects);
        }
        return query;

    }

    @Override
    public List<T> queryHqlByPage(String hql, Pagination pagination, Map<String, Object> map) {
        return getByHqlWithKey(hql, pagination, map);
    }

    @Override
    public List<T> getSomePropertiesByHqlWithKey(String hql, Map<String, Object> map) {
        Query query = null;
        query = this.getHqlQuery(hql, map);
        return query.list();
    }

    /**
     * 根据“？”占位符和索引值设置参数值。
     * @param query
     * @param objects
     * @return {@link org.hibernate.Query}
     * @author hanyu Created on 2012-3-29.
     */
    protected Query setQueryParameters(Query query,Object...objects){
        int index = 0;
        try {
            if(objects != null && objects.length > 0){
                for(Object obj : objects){
                    query.setParameter(index, obj);
                    index++;
                }
            }
        } catch (HibernateException e) {
            log.error(e.getMessage(),e);
        }
        return query;
    }


    public int queryCount(String hql) {
        return ((Number)this.createHqlQuery("select count(*) " + hql).uniqueResult()).intValue();
    }

    public int queryCount(StringBuffer hql) {
        return ((Number)this.createHqlQuery("select count(*) " + hql.toString()).uniqueResult()).intValue();
    }

    public int queryCount(StringBuffer hql,Object...objects) {
        return ((Number)this.getHqlQuery("select count(*) " + hql.toString(),objects).uniqueResult()).intValue();
    }

    public int queryCount(StringBuffer hql,Map<String,Object> maps) {
        return ((Number)this.getHqlQuery("select count(*) " + hql.toString(),maps).uniqueResult()).intValue();
    }

    public int queryCount(String hql,Map<String,Object> maps) {
        return ((Number)this.getHqlQuery("select count(*) " + hql,maps).uniqueResult()).intValue();
    }

    protected Query createHqlQuery(String hql) {
        Query query = (Query) entityManager.createQuery(hql);
        return query;
    }




    /**
     * 根据“变量名”占位符设置参数值
     * @param query {@link org.hibernate.Query}
     * @param map Map<String,Object>
     * @return {@link org.hibernate.Query}
     * @author hanyu Created on 2012-3-30.
     */
    protected Query setQueryParameters(Query query,Map<String,Object> map){
        try {
            if(map != null){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.setParameter(key,value);
                    //下面的方法已经过时，不再使用
                   /* if(value instanceof String){
                        query.setString(key, value.toString());
                    }
                    else if(value instanceof Integer){
                        query.setInteger(key, Integer.valueOf(value.toString()));
                    }
                    else if(value instanceof Date){
                        query.setParameter(key, value);
                    }
                    else if(value instanceof Float){
                        query.setFloat(key, Float.valueOf(value.toString()));
                    }
                    else if(value instanceof Double){
                        query.setDouble(key, Double.valueOf(value.toString()));
                    }
                    else if(value instanceof Long){
                        query.setLong(key, Long.valueOf(value.toString()));
                    }

                    else if(value instanceof Collection<?>){
                        query.setParameterList(key, (Collection<?>) value);
                    }

                    else if(value instanceof Object[]){
                        query.setParameterList(key, (Object[]) value);
                    }
*/
                }
            }
        } catch (NumberFormatException e) {
            log.error("查询过程参数类型转换出错" + e.getMessage(),e);
        }
        return query;
    }
}
