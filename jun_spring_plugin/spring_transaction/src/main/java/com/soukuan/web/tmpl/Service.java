package com.soukuan.web.tmpl;

import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Title Service层基础接口，其他Service接口 继承该接口
 * Time 2017/8/17.
 * Version v1.0
 */
public interface Service<T>{
    void save(T model);//持久化
    void save(List<T> models);//批量持久化
    int update(T model);//更新
    int deleteById(Object id);//通过主鍵刪除
    int deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”
    T findById(Object id);//通过ID查找
    T findOne(T t);//通过对象查找
    List<T> find(T t);//通过对象查找
    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”
    List<T> findByCondition(Condition condition);//根据条件查找
    List<T> findAll();//获取所有
    int countByCondition(Condition condition);
    T findBy(String fieldName, Object value); //通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
}
