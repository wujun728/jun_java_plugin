package cn.springmvc.jpa.common.solr;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vincent.wang
 *
 */
public interface BaseCommonRepository<T, PK extends Serializable> {

    public T save(T model);

    public T update(T model);

    public T update(T model, String... attributes);

    public T saveOrUpdate(T model);

    public T findOne(PK primaryKey);

    public List<T> findAll();

    public Long count();

    public void delete(T model);

    public void delete(PK primaryKey);

    public boolean exists(PK primaryKey);
}
