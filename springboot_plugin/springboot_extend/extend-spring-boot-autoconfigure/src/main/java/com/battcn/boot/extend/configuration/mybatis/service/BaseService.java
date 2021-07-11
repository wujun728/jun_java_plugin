package com.battcn.boot.extend.configuration.mybatis.service;

import java.util.List;

/**
 * 快速递公共 Mapper
 *
 * @param <T> 实体类
 * @author Levin
 */
public interface BaseService<T> {


    int deleteByPrimaryKey(Object key);

    int delete(T record);

    int insert(T record);

    int insertSelective(T record);

    boolean existsWithPrimaryKey(Object key);


    List<T> selectAll();

    T selectByPrimaryKey(Object key);


    int selectCount(T record);


    List<T> select(T record);


    T selectOne(T record);


    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    int insertList(List<? extends T> list);

    int insertUseGeneratedKeys(T record);


    List<T> selectByExample(Object var1);

    T selectOneByExample(Object var1);

    int updateByExampleSelective(T record, Object example);
}
