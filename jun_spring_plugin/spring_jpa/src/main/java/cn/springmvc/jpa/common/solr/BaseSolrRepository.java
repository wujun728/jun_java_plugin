package cn.springmvc.jpa.common.solr;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wujun
 *
 */
public interface BaseSolrRepository<T, PK extends Serializable> extends BaseCommonRepository<T, PK> {

    public void batchSave(List<T> modelList);

    public void batchDelete(List<String> pkList);

    public T findOneByProperty(String columnName, String value);
}
