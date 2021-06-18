package cn.springmvc.jpa.repository.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.springmvc.jpa.common.exception.DaoException;
import cn.springmvc.jpa.repository.BaseRepository;

@Repository
@Transactional
public class BaseRepositoryImpl implements BaseRepository {

    @PersistenceContext
    protected EntityManager manager;

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcTemplate")
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BaseRepositoryImpl() {
        super();
    }

    @Override
    public <E> void delete(E entity) {
        manager.remove(entity);
    }

    @Override
    public <E, P extends Serializable> void deleteByPrimaryKey(Class<E> clazz, P id) throws DaoException {
        E entity = manager.find(clazz, id);
        if (entity != null) {
            manager.remove(entity);
        } else {
            throw new PersistenceException("The entity you want to delete is not existed.");
        }
    }

    @Override
    public int execute(String sql) {
        Map<String, Object> parameter = null;
        return jdbcTemplate.update(sql, parameter);
    }

    @Override
    public int execute(String sql, Map<String, Object> parameter) {
        return jdbcTemplate.update(sql, parameter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeBatch(String sql, Collection<Map<String, Object>> parameter) {
        if (parameter == null) {
            throw new DaoException("parameter can not be null.");
        }
        Map<String, Object>[] paramArray = new Map[parameter.size()];
        int index = 0;
        for (Map<String, Object> paramMap : parameter) {
            paramArray[index++] = paramMap;
        }
        jdbcTemplate.batchUpdate(sql, paramArray);
    }

    @Override
    public List<Map<String, Object>> executeNativeQuery(String sql) {
        Map<String, Object> parameter = null;
        return jdbcTemplate.queryForList(sql, parameter);
    }

    @Override
    public List<Map<String, Object>> executeNativeQuery(String sql, Map<String, Object> parameter) {
        return jdbcTemplate.queryForList(sql, parameter);
    }

    @Override
    public <T> T executeNativeQuery(String sql, Class<T> clazz, Map<String, Object> parameter) {
        return jdbcTemplate.queryForObject(sql, parameter, BeanPropertyRowMapper.newInstance(clazz));
    }

    @Override
    public <T> List<T> executeNativeQueryForList(String sql, Class<T> clazz, Map<String, Object> parmeter) {
        return jdbcTemplate.query(sql, parmeter, BeanPropertyRowMapper.newInstance(clazz));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> query(String ql, Map<String, Object> parameter) {
        Query query = manager.createQuery(ql);
        for (String key : parameter.keySet()) {
            query.setParameter(key, parameter.get(key));
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> query(String ql) {
        return manager.createQuery(ql).getResultList();
    }

    @Override
    public <E, P extends Serializable> E getByPrimaryKey(Class<E> clazz, P id) {
        return manager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E queryOne(String ql, Map<String, Object> parameter) {
        Query query = manager.createQuery(ql);
        for (String key : parameter.keySet()) {
            query.setParameter(key, parameter.get(key));
        }
        List<Object> iter = query.getResultList();
        if (CollectionUtils.isNotEmpty(iter)) {
            return (E) iter.iterator().next();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E queryOne(String ql) {
        Query query = manager.createQuery(ql);
        List<Object> iter = query.getResultList();
        if (CollectionUtils.isNotEmpty(iter)) {
            return (E) iter.iterator().next();
        }
        return null;
    }

    @Override
    public Integer queryOneForInteger(String ql, Map<String, Object> parameter) {
        Query query = manager.createQuery(ql);
        for (String key : parameter.keySet()) {
            query.setParameter(key, parameter.get(key));
        }
        return query.getFirstResult();
    }

    @Override
    public Integer queryOneForInteger(String ql) {
        return manager.createQuery(ql).getFirstResult();
    }

    @Override
    public <E> void save(E entity) {
        manager.persist(entity);
    }

    @Override
    public <E> void saveOrUpdate(E entity) {
        manager.merge(entity);
    }

    @Override
    public <E> void executeBatchByEntity(String sql, Collection<E> parameter) throws DaoException {
        if (parameter == null) {
            throw new DaoException("parameter can not be null.");
        }
        jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(parameter.toArray()));
    }

    @Override
    public <E> int executeByEntity(String sql, E parameter) {
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(parameter));
    }

    @Override
    public <E> List<Map<String, Object>> executeNativeQueryByEntity(String sql, E parameter) {
        return jdbcTemplate.queryForList(sql, new BeanPropertySqlParameterSource(parameter));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> query(String ql, int firstResult, int maxResult) {
        Query query = manager.createQuery(ql);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> query(String ql, Map<String, Object> parameter, int firstResult, int maxResult) {
        Query query = manager.createQuery(ql);
        for (String key : parameter.keySet()) {
            query.setParameter(key, parameter.get(key));
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    public int executeHqlUpdate(String ql, Map<String, Object> parameter) {
        Query query = manager.createQuery(ql);
        for (String key : parameter.keySet()) {
            query.setParameter(key, parameter.get(key));
        }
        return query.executeUpdate();
    }

    @Override
    public void executeStoredProcedure(String procedureName) {
        manager.createNativeQuery(procedureName).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> queryByFields(String ql, Map<String, Object> parameter, int firstResult, int maxResults) {
        Query query = manager.createQuery(ql);
        for (String key : parameter.keySet()) {
            query.setParameter(key, parameter.get(key));
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

}
