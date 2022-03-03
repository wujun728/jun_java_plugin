package net.chenlin.dp.orm.plugins;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;

import net.chenlin.dp.common.entity.Page;

/**
 * PaginationResultSetHandlerInterceptor
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月11日 上午11:33:03
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class PaginationResultSetHandlerInterceptor implements Interceptor {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        FastResultSetHandler resultSetHandler = (FastResultSetHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(resultSetHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("rowBounds");

        Object result = invocation.proceed();

        if (rowBounds instanceof Page) {
            metaStatementHandler.setValue("rowBounds.rows", result);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
