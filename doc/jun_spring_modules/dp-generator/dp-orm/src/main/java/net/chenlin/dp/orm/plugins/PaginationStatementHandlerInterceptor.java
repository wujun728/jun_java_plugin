package net.chenlin.dp.orm.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
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
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.orm.dialect.Dialect;
import net.chenlin.dp.orm.dialect.DialectFactory;

/**
 * PaginationStatementHandlerInterceptor
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月11日 上午11:33:26
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationStatementHandlerInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(PaginationStatementHandlerInterceptor.class);

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        BoundSql boundSql = statementHandler.getBoundSql();

        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        // 没有分页参数
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }

        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        Dialect dialect = DialectFactory.buildDialect(configuration);
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        // 获取总记录数
        Page<?> page = (Page<?>) rowBounds;
        String countSql = dialect.getCountString(originalSql);
        Connection connection = (Connection) invocation.getArgs()[0];
        int total = getTotal(parameterHandler, connection, countSql);
        page.setTotal(total);

        // 设置物理分页语句
        metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, page.getOffset(), page.getLimit()));
        // 屏蔽mybatis原有分页
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        if (logger.isDebugEnabled()) {
            logger.debug("分页SQL : " + boundSql.getSql());
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 获取总计录
     * 
     * @param parameterHandler
     * @param connection
     * @param countSql
     * @return
     * @throws Exception
     */
    private int getTotal(ParameterHandler parameterHandler, Connection connection, String countSql) throws Exception {
        PreparedStatement prepareStatement = connection.prepareStatement(countSql);
        parameterHandler.setParameters(prepareStatement);
        ResultSet rs = prepareStatement.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        prepareStatement.close();
        return count;
    }
}
