package org.itkk.udf.dal.mybatis.plugin.tablesplit;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * 描述 : UpdateTableSplitInterceptor
 *
 * @author wangkang
 */
@Intercepts(
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
)
public class UpdateTableSplitInterceptor extends BaseTableSplitInterceptor implements Interceptor {

    /**
     * 描述 : 构造函数
     *
     * @param strategy strategy
     */
    public UpdateTableSplitInterceptor(IStrategy strategy) {
        super(strategy);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return this.intercept(invocation, false);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) { //NOSONAR
    }

}
