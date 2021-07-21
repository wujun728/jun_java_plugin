package cn.springmvc.mybatis.common.base.service;

import java.util.List;

import cn.springmvc.mybatis.common.base.model.Key;

/**
 * 
 * 主键生成
 *
 */
public interface KeyService {

    /**
     * 查询表名及表的主键字段名
     * 
     * @param keys
     * @return 返回key集合
     */
    public List<Key> getTableValues(List<Key> keys);

    /**
     * @return 返回key集合(只存储表名,主键最大值)
     */
    public List<Key> getTables();
}