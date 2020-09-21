package com.opensource.nredis.proxy.monitor.dao;



import java.util.List;

/**
* 标记接口。
* 使用说明：所有DAO 定义成接口并继承IMyBatisRepository，由Spring Context扫描，动态生成代理的实现类
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public interface IMyBatisRepository<T> {
    int insert(T t);
    int updateById(T t);// 根据主键来更新
    int deleteById(int key);// 根据主键来删除
    T getById(int key);
    List<T> getListByCriteria( T queryObjects);

}
