package io.github.wujun728.admin.db.service;

import io.github.wujun728.admin.common.BaseData;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JdbcService extends JdbcDao{
    void insert(BaseData obj);
    void update(BaseData obj);
    void insert(Map<String,Object> obj,String tableName);
    void update(Map<String,Object> obj,String tableName);
    void saveOrUpdate(BaseData obj);
    void saveOrUpdate(Map<String,Object> obj,String tableName);
    void bathSaveOrUpdate(List<? extends BaseData> objs);
    void bathSaveOrUpdate(List<Map<String,Object>> objs,String tableName);
    void delete(BaseData obj);
    void delete(Long id,String tableName);
    void delete(Long id,Class<? extends BaseData> clz);
    void delete(String sql,Object ... args);

    void transactionOption(TransactionOption transactionOption);
    void forceSaveOrUpdate(BaseData obj);
    boolean isRepeat(String sql,Map<String,Object> params);
    Set<Long> findChildIds(String parentSql,String childSql);
    Set<Long> findChildIds(Collection<Long> parentIds, String childSql);

    /**
     * 对于数据表含有enterprise_id列的数据进行更新和删除操作时验证数据enterprise_id值与操作用户的enterprise_id是否一致。
     * @param tableName 表名
     * @param id 数据id
     * @param userEnterpriseId 登录用户企业
     * @return boole
     */
    boolean ownerEnterprise(String tableName, Long id, Long userEnterpriseId);
}
