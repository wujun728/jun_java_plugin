package org.itkk.udf.dal.mybatis.plugin.tablesplit;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 描述 : QueryTableSplitInterceptor
 *
 * @author wangkang
 */
@Intercepts(
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
)
public class QueryTableSplitInterceptor extends BaseTableSplitInterceptor implements Interceptor {

    /**
     * 描述 : 构造函数
     *
     * @param strategy strategy
     */
    public QueryTableSplitInterceptor(IStrategy strategy) {
        super(strategy);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return this.intercept(invocation, true);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) { //NOSONAR
    }

}
