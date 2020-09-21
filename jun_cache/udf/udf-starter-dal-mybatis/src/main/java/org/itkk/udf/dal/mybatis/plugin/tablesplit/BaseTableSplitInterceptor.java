package org.itkk.udf.dal.mybatis.plugin.tablesplit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.RowBounds;
import org.itkk.udf.dal.mybatis.plugin.InterceptorUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * 描述 : TableSplitInterceptor
 *
 * @author wangkang
 */
@Slf4j
public class BaseTableSplitInterceptor {

    /**
     * 描述 : 分表策略
     */
    private IStrategy strategy;

    /**
     * 描述 : 构造函数
     *
     * @param strategy strategy
     */
    public BaseTableSplitInterceptor(IStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 描述 : intercept
     *
     * @param invocation invocation
     * @param isQuery    isQuery
     * @return intercept
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    protected Object intercept(Invocation invocation, boolean isQuery)
            throws InvocationTargetException, IllegalAccessException {
        // 获取必要参数
        Object parameter = invocation.getArgs()[1];
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql();
        //SQL语句处理
        if (StringUtils.isNotEmpty(sql)) { //判断sql语句是否为空
            //判断是否包含分表参数
            Integer splitNumber = null;
            if (!StringUtils.isBlank(TableSplitContextHolder.getSplitCode())) {
                splitNumber = new Integer(TableSplitContextHolder.getSplitCode());
            }
            //确认分表号不为空,并且大于等于0
            if (splitNumber != null && splitNumber >= 0) {
                //处理分表
                log.debug("分表前的SQL：" + sql);
                String convertedSql = strategy.convert(sql, splitNumber);
                log.debug("分表后的SQL：" + convertedSql);
                //将新SQL语句放入MappedStatement
                if (isQuery) { //NOSONAR
                    final int number2 = 2;
                    invocation.getArgs()[number2] = RowBounds.DEFAULT;
                }
                MappedStatement newMappedStatement = InterceptorUtil.copyFromNewSql(mappedStatement,
                        boundSql, convertedSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
                invocation.getArgs()[0] = newMappedStatement;
            }
        }
        //继续执行
        return invocation.proceed();
    }

}
