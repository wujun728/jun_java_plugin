package com.opensource.nredis.proxy.monitor.service;


import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.exception.DBFailException;

import java.util.List;

/**
* 通用的业务接口的实现
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public abstract class AbstractBaseService<T> implements IBaseService<T> {


    /** 子类需要注入特定的DAO实现 */
    protected abstract IMyBatisRepository<T> getMyBatisRepository();

    /**
    * 注意：只能做一些对于参数的预操作，不能涉及事务。涉及事务请覆盖create
    * 子类可根据情况覆盖此方法
    * 用于在执行新增操作之前进行预处理
    * @param entity 需新增的实体
    */
    protected void preCreate(T entity) throws DBFailException {
        return;
    }

    @Override
    public void create(T entity) throws DBFailException {
        
         preCreate(entity);
        int successCount = getMyBatisRepository().insert(entity);
        if (1 != successCount) {
        throw new DBFailException("新增对象-数据库失败");
    }
    }
     /**
    * 注意：只能做一些对于参数的预操作，不能涉及事务。涉及事务请覆盖modifyEntityById
    * 子类可根据情况覆盖此方法
    * 用于在执行修改操作之前进行预处理
    * @param entity 需修改的实体
    */
    protected void preModify(T entity) throws DBFailException {
    return;
    }

    @Override
    public void modifyEntityById(T entity) throws DBFailException {
        
         preModify(entity);
        int successCount = getMyBatisRepository().updateById(entity);
        if (1 != successCount) {
        throw new DBFailException("修改对象-数据库失败");
        }
    }
     /**
    * 注意：只能做一些对于参数的预操作，不能涉及事务。涉及事务请覆盖deleteEntityById
    * 子类可根据情况覆盖此方法
    * 用于在执行删除操作之前进行预处理
    * @param id 需删除实体的ID
    */
    protected void preDelete(Integer id) throws DBFailException {
        return;
    }

    @Override
    public void deleteEntityById(Integer id) throws DBFailException {
        
         preDelete(id);
        int successCount = getMyBatisRepository().deleteById(id);
        if (1 != successCount) {
        throw new DBFailException("删除对象-数据库失败");
        }
    }

    /**
    * 注意：只能做一些对于参数的预操作，不能涉及事务。涉及事务请覆盖getEntityById
    * 子类可根据情况覆盖此方法
    * 用于在执行查询操作之前进行预处理
    * @param id 需查询实体的ID
    */
    protected void preGet(Integer id) throws DBFailException {
        return;
    }
    @Override
    public T getEntityById(Integer id) throws DBFailException {
        
         preGet(id);
        return getMyBatisRepository().getById(id);
    }
     /**
    * 注意：只能做一些对于参数的预操作，不能涉及事务。涉及事务请覆盖queryEntityList
    * 子类可根据情况覆盖此方法
    * 用于在执行查询列表操作之前进行预处理
    * @param queryObject 查询参数对象
    */
    protected void preQuery(T queryObject) throws DBFailException {
        return;
    }

    @Override
    public List<T> queryEntityList(T queryObject) throws DBFailException {
        
        preQuery(queryObject);
        return getMyBatisRepository().getListByCriteria(queryObject);
    }
}