package io.github.wujun728.db.dao;

import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.bean.Page;
import io.github.wujun728.db.bean.PageResult;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author 周宁
 */
public interface EntityDao<T, Id extends Serializable> {

    /**
     * 设置一些操作的常量
     */
    String SQL_SELECT = "select";
    String SQL_INSERT = "insert";
    String SQL_INSERTIGNORE = "insert ignore";
    String SQL_REPLACE = "replace";
    String SQL_UPDATE = "update";
    String SQL_DELETE = "delete";
    String SQL_TRUNCATE = "truncate";
    String SQL_DROP = "drop";
    String SQL_CREATE = "create";

    /**
     * 插入指定的持久化对象
     *
     * @param t 实体对象
     * @throws Exception sql错误抛出异常
     */
    int save(T t) throws Exception;

    /**
     * 修改指定的持久化对象
     *
     * @param t 实体对象
     * @throws Exception sql错误抛出异常
     */
    int update(T t) throws Exception;

    /**
     * 批量保存指定的持久化对象
     *
     * @param list 实体对象集合
     * @throws Exception sql错误抛出异常
     */
    void batchSave(List<T> list) throws Exception;

    /**
     * 保存或更新持久化对象
     *
     * @param t 实体对象
     * @throws Exception sql错误抛出异常
     */
    void saveOrUpdate(T t) throws Exception;

    /**
     * 批量保存指定的持久化对象
     *
     * @param list 实体对象集合
     * @return int插入记录的条数
     * @throws Exception
     */
    int saveAll(List<T> list) throws Exception;

    /**
     * 批量更新指定的持久化对象
     *
     * @param list 实体对象集合
     * @throws Exception sql错误抛出异常
     */
    void batchUpdate(List<T> list) throws Exception;

    /**
     * 根据主键删除
     *
     * @param id 实体主键
     * @throws Exception sql错误抛出异常
     */
    int delete(Id id) throws Exception;


    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @throws Exception sql错误抛出异常
     */
    int batchDelete(List<Id> ids) throws Exception;

    /**
     * 根据ID检索持久化对象
     *
     * @param id 主键
     * @return T 实体对象
     * @throws Exception sql错误抛出异常
     */
    T queryOne(Id id) throws Exception;

    /**
     * 根据ID检索持久化对象
     *
     * @param id         主键
     * @param tRowMapper 自定义实体映射mapper
     * @return T 实体对象
     * @throws Exception sql错误抛出异常
     */
    T queryOne(Id id, RowMapper<T> tRowMapper) throws Exception;

    /**
     * 检索所有持久化对象
     *
     * @return List 实体对象列表
     * @throws Exception sql错误抛出异常
     */
    List<T> queryAll() throws Exception;

    /**
     * 检索所有持久化对象
     *
     * @param tRowMapper 自定义实体映射mapper
     * @return List 实体对象列表
     * @throws Exception sql错误抛出异常
     */
    List<T> queryAll(RowMapper<T> tRowMapper) throws Exception;

    /**
     * 分页查询
     *
     * @param page 分页条件
     * @return PageResult 分页查询结果
     * @throws Exception sql错误抛出异常
     */
    PageResult<T> pageQuery(Page page) throws Exception;

    /**
     * 分页查询
     *
     * @param page       分页条件
     * @param tRowMapper 自定义实体映射mapper
     * @return PageResult 分页查询结果
     * @throws Exception sql错误抛出异常
     */
    PageResult<T> pageQuery(Page page, RowMapper<T> tRowMapper) throws Exception;


    /**
     * 删除表
     *
     * @throws Exception sql错误抛出异常
     */
    void drop() throws Exception;

    /**
     * 清除表数据和delete不同的是，该方法不需要where
     * 条件并且数据一旦清除不可恢复
     *
     * @throws Exception sql错误抛出异常
     */
    void truncate() throws Exception;



}
