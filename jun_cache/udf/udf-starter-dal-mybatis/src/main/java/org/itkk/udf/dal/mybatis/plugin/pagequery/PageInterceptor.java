package org.itkk.udf.dal.mybatis.plugin.pagequery;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.itkk.udf.core.exception.SystemRuntimeException;
import org.itkk.udf.dal.mybatis.MyBatisProperties;
import org.itkk.udf.dal.mybatis.plugin.InterceptorUtil;
import org.itkk.udf.dal.mybatis.plugin.pagequery.parser.SqlParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * ClassName: PageInterceptor
 * </p>
 * <p>
 * Description: 分页插件 ,参考 : https://gitee.com/free/Mybatis_PageHelper
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2016年3月23日
 * </p>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Slf4j
public class PageInterceptor implements Interceptor {

    /**
     * 本地变量
     */
    private static final ThreadLocal<PageResult> LOCAL_PAGE = new ThreadLocal<>();

    /**
     * myBatisProperties
     */
    @Autowired
    private MyBatisProperties myBatisProperties;

    /**
     * additionalParametersField
     */
    private Field additionalParametersField;

    /**
     * countSuffix
     */
    private String countSuffix = "_COUNT";

    @Override
    public Object intercept(Invocation invocation) throws Throwable { // NOSONAR
        try {
            //常量
            final int num2 = 2;
            final int num3 = 3;
            final int num4 = 4;
            final int num5 = 5;
            // 获得分页对象
            PageResult pr = getLocalPage();
            // 判断是否需要分页
            if (pr != null) {
                //获得必要参数
                Object[] args = invocation.getArgs();
                MappedStatement ms = (MappedStatement) args[0];
                Object parameter = args[1];
                RowBounds rowBounds = (RowBounds) args[num2];
                ResultHandler resultHandler = (ResultHandler) args[num3];
                Executor executor = (Executor) invocation.getTarget();
                CacheKey cacheKey;
                BoundSql boundSql;
                //4个参数和6个参数的时候取值的判断逻辑区分
                if (args.length == num4) { //4 个参数时
                    boundSql = ms.getBoundSql(parameter);
                    cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
                } else { //6 个参数时
                    cacheKey = (CacheKey) args[num4];
                    boundSql = (BoundSql) args[num5];
                }
                //反射获取 BoundSql 中的 additionalParameters 属性
                additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
                additionalParametersField.setAccessible(true);
                //反射获取动态参数
                String msId = ms.getId();
                Configuration configuration = ms.getConfiguration();
                Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
                // 查询总行数,如果当前PageResult对象包含总页数,则这里不做查询
                if (!pr.isHasTotalRecords()) {
                    //设置ID
                    String countMsId = msId + countSuffix;
                    //创建 count 查询
                    MappedStatement countMs = InterceptorUtil.newCountMappedStatement(ms, countMsId);
                    //执行
                    Long count = executeAutoCount(executor, countMs, parameter, boundSql, resultHandler);
                    //设置
                    pr.setTotalRecords(count);
                }
                // 计算页数
                if (pr.getTotalRecords() > 0) {
                    long totalPages = pr.getTotalRecords() / pr.getPageSize() + ((pr.getTotalRecords() % pr.getPageSize() > 0) ? 1 : 0);
                    pr.setTotalPages(totalPages); // 设置总页数
                }
                //判断是否需要查询(如果count为0,则不需要执行下面的查询)
                if (pr.getTotalRecords() != 0) {
                    //获得分页sql
                    String pageSql = generatePageSql(boundSql.getSql(), pr);
                    BoundSql pageBoundSql = new BoundSql(configuration, pageSql, boundSql.getParameterMappings(), parameter);
                    //设置动态参数
                    for (String key : additionalParameters.keySet()) { // NOSONAR
                        pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                    }
                    //执行分页查询
                    List resultList = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
                    // 得到处理结果
                    pr.addAll(resultList);
                } else {
                    //设置空值
                    pr.addAll(new ArrayList());
                }
                // 分页返回值
                return pr;
            }
            // 常规返回值
            return invocation.proceed();
        } finally {
            // 清理本地变量
            clearLocalPage();
        }
    }

    /**
     * 执行自动生成的 count 查询
     *
     * @param executor      executor
     * @param countMs       countMs
     * @param parameter     parameter
     * @param boundSql      boundSql
     * @param resultHandler resultHandler
     * @return Long
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException           SQLException
     */
    private Long executeAutoCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql, ResultHandler resultHandler) throws IllegalAccessException, SQLException {
        //获得参数
        Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
        //创建 count 查询的缓存 key
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        //调用方言获取 count sql
        String countSql = new SqlParser().getSmartCountSql(boundSql.getSql(), "1");
        //获得BoundSql
        BoundSql countBoundSql = new BoundSql(countMs.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        //当使用动态 SQL 时，可能会产生临时的参数，这些参数需要手动设置到新的 BoundSql 中
        for (String key : additionalParameters.keySet()) { //NOSONAR
            countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
        }
        //执行 count 查询
        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        //返回
        return (Long) ((List) countResultList).get(0);
    }

    /**
     * 生成分页SQL语句
     *
     * @param sql sql语句
     * @param pr  分页信息
     * @return 分页sql语句
     */
    private String generatePageSql(String sql, PageResult pr) {
        if (pr != null && (this.myBatisProperties.getDialect() != null && !"".equals(this.myBatisProperties.getDialect()))) {
            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(this.myBatisProperties.getDialect())) {
                pageSql.append(sql).append(" LIMIT " + (pr.getCurPage() - 1) * pr.getPageSize() + "," + pr.getPageSize());
            } else if ("oracle".equals(this.myBatisProperties.getDialect())) {
                pageSql.append(" SELECT * FROM (SELECT TMP_TB.*,ROWNUM ROW_ID FROM ( ");
                pageSql.append(sql);
                pageSql.append(" )  TMP_TB WHERE ROWNUM <= ");
                pageSql.append(pr.getPageSize() + ((pr.getCurPage() - 1) * pr.getPageSize()));
                pageSql.append(" ) WHERE ROW_ID >= ");
                pageSql.append(1 + ((pr.getCurPage() - 1) * pr.getPageSize()));
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        log.debug("setProperties");
    }

    /**
     * 获取Page参数
     *
     * @return 分页对象
     */
    private static PageResult getLocalPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 设置Page参数
     *
     * @param page 分页结果
     */
    private static void setLocalPage(PageResult page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 移除本地变量
     */
    private static void clearLocalPage() {
        LOCAL_PAGE.remove();
    }

    /**
     * 开始分页
     *
     * @param pageSize 每页显示数量
     * @param curPage  页码
     */
    public static void startPage(int pageSize, int curPage) {
        // 规范参数
        if (pageSize <= 0) {
            throw new SystemRuntimeException("pageSize <= 0");
        }
        if (curPage <= 0) {
            throw new SystemRuntimeException("curpage <= 0");
        }
        // 实例化分页对象
        PageResult page = new PageResult();
        page.setPageSize(pageSize); // 设置每页条数
        page.setCurPage(curPage); // 设置当前页
        // 保存对象至本地变量中
        setLocalPage(page);
    }

    /**
     * 开始分页
     *
     * @param pageSize     每页数量
     * @param curPage      当前页数
     * @param totalRecords 总记录数
     */
    public static void startPage(int pageSize, int curPage, int totalRecords) {
        // 规范参数
        if (totalRecords < 0) {
            throw new SystemRuntimeException("totalRecords < 0");
        }
        // 设置总行数
        startPage(pageSize, curPage);
        PageResult page = getLocalPage();
        page.setTotalRecords(totalRecords);
        // 保存对象至本地变量中
        setLocalPage(page);
    }

    /**
     * 将结果集转换为PageResult
     *
     * @param result 结果集
     * @param <E>    泛型
     * @return 结果集
     */
    public static <E> Page<E> getPageResult(List<E> result) {
        return new Page((PageResult<E>) result);
    }

}
