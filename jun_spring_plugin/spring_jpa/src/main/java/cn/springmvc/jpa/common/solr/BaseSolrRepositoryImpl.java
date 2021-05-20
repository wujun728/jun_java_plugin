package cn.springmvc.jpa.common.solr;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.springmvc.jpa.common.utils.Pagination;

/**
 * @author Vincent.wang
 *
 */
public abstract class BaseSolrRepositoryImpl<T, PK extends Serializable> implements BaseSolrRepository<T, PK> {

    protected static final Logger log = LoggerFactory.getLogger(BaseSolrRepository.class);

    protected Class<T> modelClass;

    // @Resource(name = "solrcloud_server")
    protected SolrServer solrServer;

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public SolrServer getSolrServer() {
        return solrServer;
    }

    @PostConstruct
    public void init() {
        this.modelClass = getGenericModelClass();
    }

    @Override
    public T save(T model) {
        try {
            solrServer.addBean(model);
            solrServer.commit();
            return model;
        } catch (Exception e) {
            throw new SolrException("Save failed for model " + model, e);
        }
    }

    public void batchSave(List<T> modelList) {
        if (modelList == null || modelList.size() == 0)
            return;
        try {
            solrServer.addBeans(modelList);
            solrServer.commit();
        } catch (Exception e) {
            throw new SolrException("Batch save model failed for modelClass " + modelClass);
        }
    }

    @Override
    public T update(T model) {
        log.warn("Please use save instead.");
        return save(model);
    }

    @Override
    public T update(T model, String... attributes) {
        throw new SolrException("Partial update is not supported.");
    }

    @Override
    public T saveOrUpdate(T model) {
        log.warn("Please use save instead.");
        return save(model);
    }

    @Override
    public Long count() {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        try {
            QueryResponse rResponse = solrServer.query(query);
            return rResponse.getResults().getNumFound();
        } catch (SolrServerException e) {
            throw new SolrException(e);
        }
    }

    @Override
    public T findOne(PK primaryKey) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*").addFilterQuery("id:" + primaryKey);
        try {
            QueryResponse rResponse = solrServer.query(query);
            List<T> result = rResponse.getBeans(modelClass);
            if (result == null || result.size() == 0)
                return null;
            return result.get(0);
        } catch (SolrServerException e) {
            throw new SolrException(e);
        }
    }

    @Override
    public T findOneByProperty(String columnName, String value) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*").addFilterQuery("field:" + value);
        try {
            QueryResponse rResponse = solrServer.query(query);
            List<T> result = rResponse.getBeans(modelClass);
            if (result == null || result.size() == 0)
                return null;
            return result.get(0);
        } catch (SolrServerException e) {
            throw new SolrException(e);
        }
    }

    @Override
    public List<T> findAll() {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        try {
            QueryResponse rResponse = solrServer.query(query);
            return rResponse.getBeans(modelClass);
        } catch (SolrServerException e) {
            throw new SolrException(e);
        }
    }

    protected abstract PK getId(T obj);

    @Override
    public void delete(T model) {
        try {
            delete(getId(model));
            solrServer.commit();
        } catch (Exception e) {
            throw new SolrException("Save failed for model " + model, e);
        }
    }

    @Override
    public void delete(PK primaryKey) {
        try {
            solrServer.deleteById(primaryKey.toString());
            solrServer.commit();
        } catch (Exception e) {
            throw new SolrException("Delete failed for model with PK:" + primaryKey);
        }
    }

    public void batchDelete(List<String> pkList) {
        if (pkList == null || pkList.size() == 0)
            return;
        try {
            solrServer.deleteById(pkList);
            solrServer.commit();
        } catch (Exception e) {
            throw new SolrException("Batch delete model failed for modelClass " + modelClass);
        }
    }

    @Override
    public boolean exists(PK primaryKey) {
        return findOne(primaryKey) != null;
    }

    protected List<T> findByQuery(SolrQuery solrQuery) {
        if (solrQuery == null)
            throw new IllegalArgumentException();

        try {
            QueryResponse rResponse = solrServer.query(solrQuery);
            return rResponse.getBeans(modelClass);
        } catch (SolrServerException e) {
            throw new SolrException(e);
        }
    }

    protected Pagination<T> findByQuery(int start, int size, SolrQuery solrQuery) {
        if (solrQuery == null)
            throw new IllegalArgumentException();
        solrQuery.setStart(start).setRows(size);

        try {
            QueryResponse rResponse = solrServer.query(solrQuery);
            Pagination<T> page = new Pagination<T>();
            page.setCount(rResponse.getResults().getNumFound());
            page.setCurrentPage((start / size) + 1);
            page.setItems(rResponse.getBeans(modelClass));
            page.setStart(start);
            page.setTotalPages((int) page.getCount() / size + (page.getCount() % size == 0 ? 0 : 1));
            page.setSize(size);
            return page;
        } catch (SolrServerException e) {
            throw new SolrException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericModelClass() {
        Class<?> clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        while (!(type instanceof ParameterizedType) && clazz != null && clazz != Object.class) {
            clazz = clazz.getSuperclass();
            type = clazz.getGenericSuperclass();
        }

        if (!(type instanceof ParameterizedType)) {
            Class<?>[] iclazzs = clazz.getInterfaces();
            if (iclazzs.length > 0) {
                int index = -1;
                for (int i = 0; i < iclazzs.length; i++) {
                    if (BaseSolrRepository.class.isAssignableFrom(iclazzs[i])) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    if (clazz.getGenericInterfaces()[index] instanceof ParameterizedType)
                        type = clazz.getGenericInterfaces()[index];
                }
            }

        }

        if (!(type instanceof ParameterizedType)) {
            throw new RuntimeException("Can not find the right Generic Class.");
        }

        ParameterizedType pType = (ParameterizedType) type;
        return (Class<T>) pType.getActualTypeArguments()[0];
    }
}