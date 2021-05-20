package cn.springboot.service.key;

import java.util.List;

import cn.springboot.config.db.table.Key;

/** 
 * @Description 主键生成
 * @author Wujun
 * @date Apr 12, 2017 1:45:27 PM  
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