package cn.springmvc.mybatis.mapper;

import java.util.List;


import cn.springmvc.mybatis.common.base.model.Key;

public interface KeyMapper {

    /**
     * @return 返回key集合
     */
    List<Key> getTableValues(List<Key> keys);

    /**
     * @return 返回key集合(只存储表名)
     */
    List<Key> getTables();

}
