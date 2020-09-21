package com.opensource.nredis.proxy.monitor.service;

import com.opensource.nredis.proxy.monitor.exception.DBFailException;

import java.util.List;

/**
* 定义常用业务功能接口
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public interface IBaseService<T> {
    void create(T entity) throws DBFailException;
    void modifyEntityById(T entity) throws DBFailException;
    void deleteEntityById(Integer id) throws DBFailException;
    T getEntityById(Integer id) throws DBFailException;
    List<T> queryEntityList(T queryObject) throws DBFailException;
 }
