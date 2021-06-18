package cn.springmvc.jpa.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.springmvc.jpa.common.exception.DaoException;

public interface BaseRepository {

    /**
     * 保存一个实体对象
     *
     * @param entity
     *            实体对象
     * @return 保存完成后，已存入数据库的实体对象
     */
    public <E> void save(E entity);

    /**
     * 更新一个实体对象
     *
     * @param <E>
     *            泛型的Entity
     * @param entity
     *            实体对象
     */
    public <E> void saveOrUpdate(E entity);

    /**
     * 删除一个实体对象
     *
     * @param <E>
     *            泛型的Entity
     * @param entity
     *            实体对象
     * @throws DaoException
     */
    public <E> void delete(E entity);

    /**
     * 指定具体的实体对象根据主键删除该实体对象
     *
     * @param <E>
     *            泛型的Entity
     * @param <P>
     * @param clazz
     *            实体类得类型
     * @param id
     *            主键Id
     * @throws DaoException
     */
    public <E, P extends Serializable> void deleteByPrimaryKey(Class<E> clazz, P id) throws DaoException;

    /**
     * 根据 QL语句查询出一个对象的集合
     *
     * @param <E>
     * @param ql
     *            QL语句或者动态模板
     * @return 某个实体对象的集合
     */
    public <E> List<E> query(String ql);

    /**
     * 根据QL语句和参数查询出一个对象的集合
     *
     * @param <E>
     *            泛型Entity
     * @param ql
     *            QL语句或者动态模板
     * @param parameter
     *            QL语句内对应的参数
     * @return 某个实体对象的集合
     */
    public <E> List<E> query(String ql, Map<String, Object> parameter);

    /**
     * 根据QL语句查询出一个实体对象
     *
     * @param <E>
     *            泛型Entity
     * @param ql
     *            QL语句或者动态模板
     * @return 某个实体对象
     */
    public <E> E queryOne(String ql);

    /**
     * 根据QL语句和参数查询出一个实体对象
     *
     * @param <E>
     *            泛型Entity
     * @param ql
     *            QL语句或者动态模板
     * @param parameter
     *            QL语句内对应的参数
     * @return 某个泛型对象
     */
    public <E> E queryOne(String ql, Map<String, Object> parameter);

    /**
     * 查询记录数
     *
     * @param ql
     *            QL语句或者动态模板
     * @return 记录数
     */
    public Integer queryOneForInteger(String ql);

    /**
     * 查询记录数
     *
     * @param ql
     *            QL语句或者动态模板
     * @param parameter
     *            QL语句内对应的参数
     * @return 记录数
     */
    public Integer queryOneForInteger(String ql, Map<String, Object> parameter);

    /**
     * 指定具体的实体类，根据主键查询出该实体对象
     *
     * @param <E>
     *            泛型Entity
     * @param <P>
     * @param id
     *            实体对象的主键
     * @return 该实体对象
     * @throws DaoException
     */
    public <E, P extends Serializable> E getByPrimaryKey(Class<E> clazz, P id);

    /**
     * 执行SQL进行增、删和改
     *
     * @param sql
     *            SQL语句或者动态模板
     * @return 执行成功的记录数
     * @throws DaoException
     */

    public int execute(String sql);

    /**
     * 根据参数执行SQL进行增、删、改
     *
     * @param sql
     *            SQL语句或者动态模板
     * @param parameter
     *            SQL语句内对应的参数
     * @return 执行成功的记录数
     */
    public int execute(String sql, Map<String, Object> parameter);

    /**
     * 根据参数执行SQL进行增、删、改
     *
     * @param sql
     * @param parameter
     *            可以把对象作为参数，但是sql中的参数名称必须和对象属性相同,如果属性是对象，则可以使用属性名.属性名来传入参数值
     * @return 执行成功的记录数
     */
    public <E> int executeByEntity(String sql, E parameter);

    /**
     * 批量批量SQL进行增、删和改
     *
     * @param sql
     *            SQL语句或者动态模板
     * @param parameter
     *            SQL语句内对应的参数
     */
    public void executeBatch(String sql, Collection<Map<String, Object>> parameter);

    /**
     * 批量批量SQL进行增、删和改
     *
     * @param sql
     *            sql语句或者sql语句的模板
     * @param parameter
     *            可以把对象作为参数，但是sql中的参数名称必须和对象属性相同,如果属性是对象，则可以使用属性名.属性名来传入参数值
     * @throws DaoException
     */
    public <E> void executeBatchByEntity(String sql, Collection<E> parameter) throws DaoException;

    /**
     * 执行SQL查询出一个对象的集合
     *
     * @param sql
     *            SQL语句或者动态模板
     * @return Map的集合
     */
    public List<Map<String, Object>> executeNativeQuery(String sql) throws DaoException;

    /**
     * 根据参数执行SQL查询出一个对象的集合
     *
     * @param sql
     *            SQL语句或者动态模板
     * @param parameter
     *            SQL语句内对应的参数
     * @return Map的集合
     */
    public List<Map<String, Object>> executeNativeQuery(String sql, Map<String, Object> parameter);

    /**
     * @param sql
     *            SQL语句或者动态模板
     * @param clazz
     *            返回的实体类
     * @param parameter
     *            SQL语句内对应的参数
     * @return 对象
     * @throws DaoException
     */
    public <T> T executeNativeQuery(String sql, Class<T> clazz, Map<String, Object> parameter) throws DaoException;

    /**
     * @param sql
     *            SQL语句或者动态模板
     * @param clazz
     *            返回的实体类
     * @param parameter
     *            SQL语句内对应的参数
     * @return 对象
     * @throws DaoException
     */
    public <T> List<T> executeNativeQueryForList(String sql, Class<T> clazz, Map<String, Object> parameter) throws DaoException;

    /**
     * 根据参数执行SQL查询出一个对象的集合
     *
     * @param <E>
     *            泛型实体
     * @param sql
     *            SQL语句或者SQL语句模板
     * @param parameter
     *            可以把对象作为参数，但是sql中的参数名称必须和对象属性相同,如果属性是对象，则可以使用属性名.属性名来传入参数值
     * @return Map的集合
     */
    public <E> List<Map<String, Object>> executeNativeQueryByEntity(String sql, E parameter);

    /**
     * 查询出从某一条记录数开始获取到多少条记录数的集合
     *
     * @param <E>
     * @param ql
     *            hql语句
     * @param firstResult
     *            从多少条开始获取 ,默认从0条开始，如果从1开始，则不包含第1条 .例如：从第100条开始获取
     * @param maxResults
     *            获取的最大记录数 ，例如：获取100条记录,则maxResults=100
     * @return 返回获取到maxResults记录数的集合
     */
    public <E> List<E> query(String ql, int firstResult, int maxResults);

    /**
     * 根据限定条件查询出从某一条记录数开始获取到多少条记录数的集合
     *
     * @param <E>
     * @param ql
     *            hql语句
     * @param parameter
     *            条件参数
     * @param firstResult
     *            从多少条开始获取,默认从0条开始，如果从1开始，则不包含第1条 .例如：从第100条开始获取
     * @param maxResults
     *            获取的最大记录数 ，例如：获取100条记录,则maxResults=100
     * @return 返回获取到maxResults记录数的集合
     * @throws DaoException
     */
    public <E> List<E> query(String ql, Map<String, Object> parameter, int firstResult, int maxResults);

    public int executeHqlUpdate(String ql, Map<String, Object> parameter);

    /**
     * 调用存储过程
     *
     * @param stordProcedureName
     */
    public void executeStoredProcedure(String stordProcedureName);

    /**
     * 根据限定条件查询出从某一条记录数开始获取到多少条记录数的集合,指定所需要的记录字段
     *
     * @param ql
     *            hql语句
     * @param parameter
     *            条件参数
     * @param firstResult
     *            从多少条开始获取,默认从0条开始，如果从1开始，则不包含第1条 .例如：从第100条开始获取
     * @param maxResults
     *            获取的最大记录数 ，例如：获取100条记录,则maxResults=100
     * @return 返回获取到maxResults记录数的Map集合
     * @throws DaoException
     */
    public List<Map<String, Object>> queryByFields(String ql, Map<String, Object> parameter, int firstResult, int maxResults);

}
