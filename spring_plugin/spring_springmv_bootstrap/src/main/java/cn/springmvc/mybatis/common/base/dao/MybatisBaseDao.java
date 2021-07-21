package cn.springmvc.mybatis.common.base.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import cn.springmvc.mybatis.common.base.model.Page;

/**
 * MyBatis基类Dao
 */
public abstract class MybatisBaseDao<T> extends SqlSessionDaoSupport {

    /** 接口泛型T对应的Class字节码 */
    private Class<T> clazz;

    /**
     * 基类Dao接口实现构造函数，主要用于获取接口泛型T对应的Class字节码
     */
    @SuppressWarnings("unchecked")
    public MybatisBaseDao() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];
    }

    /**
     * 
     * 查询单个接口泛型对象T
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            参数
     * @return T 接口泛型对象T
     */
    public T selectOne(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().selectOne(statement, parameter);

        return super.getSqlSession().selectOne(statement);
    }

    /**
     * 
     * 查询单个非接口泛型对象
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            参数
     * @return Object 单个非接口泛型对象
     */
    public Object selectOneByParameter(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().selectOne(statement, parameter);

        return super.getSqlSession().selectOne(statement);
    }

    /**
     * 查询接口泛型对象T列表
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            参数
     * @return List<T> 接口泛型对象T列表
     */
    public List<T> selectList(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().selectList(statement, parameter);

        return super.getSqlSession().selectList(statement);
    }

    /**
     * 查询接口泛型对象T列表
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            多参数
     * @return List<T> 接口泛型对象T列表
     */
    public List<?> selectListMoreArg(String sqlId, Object... parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().selectList(statement, parameter);

        return super.getSqlSession().selectList(statement);
    }

    /**
     * 查询非接口泛型对象T列表
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            参数
     * @return List<?> 非接口泛型对象T列表
     */
    public List<?> selectListByParameter(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().selectList(statement, parameter);

        return super.getSqlSession().selectList(statement);
    }

    /**
     * 分页查询(基于方言的分页)
     * 
     * @param sqlId
     *            SQL语句id
     * @param page
     *            分页对象参数
     * @return Page<T> 查询后返回的分页对象
     */
    public Page<T> selectForPage(String sqlId, Page<T> page) {
        String statement = constructStatement(sqlId);
        // 设置分页统计标识为true，让分页插件执行分页统计
        Map<String, Object> paramMap = page.getParamMap();
        paramMap.put("pageCountFlag", true);
        List<T> resultList = super.getSqlSession().selectList(statement, page);
        page.setResultList(resultList);
        return page;
    }

    /**
     * 分页查询(基于自定义的sql语句分页)
     * 
     * @param pageCountSqlId
     *            分页统计SQL语句id
     * @param pageSqlid
     *            分页查询SQL语句id
     * @param page
     *            查询后返回的分页对象
     * @return Page<T> 查询后返回的分页对象
     */
    public Page<T> selectForPage(String pageCountSqlId, String pageSqlid, Page<T> page) {
        // 1、查询总记录数、设置总页数
        String statement = constructStatement(pageCountSqlId);
        Long totalCount = super.getSqlSession().selectOne(statement, page);
        page.setTotalCount(totalCount);
        long totalPage = Page.computeTotalPage(totalCount, page.getPageSize());
        page.setTotalPage(totalPage);

        // 2、通过分页插件查询分页记录(设置分页统计标识为false，让分页插件不执行分页统计)
        statement = constructStatement(pageSqlid);
        Map<String, Object> paramMap = page.getParamMap();
        paramMap.put("pageCountFlag", false);
        List<T> resultList = super.getSqlSession().selectList(statement, page);
        page.setResultList(resultList);

        return page;
    }

    /**
     * 根据接口泛型对象T插入
     * 
     * @param sqlId
     *            SQL语句id
     * @param T
     *            接口泛型对象T
     * @return int 插入动作受影响的行数
     */
    public int insert(String sqlId, T T) {
        String statement = constructStatement(sqlId);
        if (T == null)
            throw new IllegalArgumentException("接口泛型对象T不能为空！");

        return super.getSqlSession().insert(statement, T);
    }

    /**
     * 根据非接口泛型对象参数插入
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            非接口泛型对象参数
     * @return int 插入动作受影响的行数
     */
    public int insertObject(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().insert(statement, parameter);

        return super.getSqlSession().insert(statement);
    }

    /**
     * 根据接口泛型对象T更新
     * 
     * @param sqlId
     *            SQL语句id
     * @param T
     *            接口泛型对象T
     * @return int 更新动作受影响的行数
     */
    public int update(String sqlId, T T) {
        String statement = constructStatement(sqlId);
        if (T == null)
            throw new IllegalArgumentException("接口泛型对象T不能为空！");

        return super.getSqlSession().update(statement, T);
    }

    /**
     * 根据非接口泛型对象参数更新
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            非接口泛型对象参数
     * @return int 更新动作受影响的行数
     */
    public int updateByParameter(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().update(statement, parameter);

        return super.getSqlSession().update(statement);
    }

    /**
     * 根据接口泛型对象T删除
     * 
     * @param sqlId
     *            SQL语句id
     * @param T
     *            接口泛型对象T
     * @return int 删除动作受影响的行数
     */
    public int delete(String sqlId, T T) {
        String statement = constructStatement(sqlId);
        if (T == null)
            throw new IllegalArgumentException("接口泛型对象T不能为空！");

        return super.getSqlSession().delete(statement, T);
    }

    /**
     * 根据非接口泛型对象参数删除
     * 
     * @param sqlId
     *            SQL语句id
     * @param parameter
     *            非接口泛型对象参数
     * @return int 删除动作受影响的行数
     */
    public int deleteByParameter(String sqlId, Object parameter) {
        String statement = constructStatement(sqlId);
        if (parameter != null)
            return super.getSqlSession().delete(statement, parameter);

        return super.getSqlSession().delete(statement);
    }

    /**
     * 构造SQL语句表达式
     * 
     * @param sqlId
     *            SQL语句id
     * @return String SQL语句表达式
     */
    private String constructStatement(String sqlId) {
        if (StringUtils.isBlank(sqlId))
            throw new IllegalArgumentException("SQL语句id不能为空！");

        return clazz.getName() + "." + sqlId;
    }
}
