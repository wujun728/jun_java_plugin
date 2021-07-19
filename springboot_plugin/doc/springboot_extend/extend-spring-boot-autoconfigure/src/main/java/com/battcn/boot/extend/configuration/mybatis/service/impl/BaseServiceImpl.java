package com.battcn.boot.extend.configuration.mybatis.service.impl;

import com.battcn.boot.extend.configuration.mybatis.BaseMapper;
import com.battcn.boot.extend.configuration.mybatis.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @param <T> 实体类
 * @author Levin
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {


    @Autowired
    private BaseMapper<T> mapper;


    @Override
    public int deleteByPrimaryKey(Object key) {
        return this.mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int delete(T record) {
        return this.mapper.delete(record);
    }

    @Override
    public int insert(T record) {
        return this.mapper.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return this.mapper.insertSelective(record);
    }

    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return this.mapper.existsWithPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return this.mapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    @Override
    public int selectCount(T record) {
        return this.mapper.selectCount(record);
    }

    @Override
    public List<T> select(T record) {
        return this.mapper.select(record);
    }

    @Override
    public T selectOne(T record) {
        return this.mapper.selectOne(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return this.mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertList(List<? extends T> list) {
        return this.mapper.insertList(list);
    }

    @Override
    public int insertUseGeneratedKeys(T record) {
        return this.mapper.insertUseGeneratedKeys(record);
    }

    @Override
    public List<T> selectByExample(Object var1) {
        return this.mapper.selectByExample(var1);
    }

    @Override
    public T selectOneByExample(Object var1) {
        return this.mapper.selectOneByExample(var1);
    }

    @Override
    public int updateByExampleSelective(T record, Object example) {
        return this.mapper.updateByExampleSelective(record, example);
    }


}
