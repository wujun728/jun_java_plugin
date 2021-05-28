package org.supercall.mybatis.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.supercall.mybatis.IDialect;
import org.supercall.mybatis.Pagination;
import org.supercall.mybatis.dialect.MySqlDialect;
import org.supercall.mybatis.dialect.OracleDialect;


/**
 * 分页拦截器
 * 
 * @author Wujun
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {
    private String dialectType;
    private String dialectClazz;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();

        if (target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) target;
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
            // 不需要分页的场合
            if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                return invocation.proceed();
            }
            // 定义数据库方言
            IDialect dialect = null;
            if (dialectType != null && !"".equals(dialectType)) {
                switch (dialectType.toLowerCase()) {
                case "mysql":
                    dialect = new MySqlDialect();
                    break;
                case "oracle":
                    dialect = new OracleDialect();
                    break;
                default:
                    break;
                }
            } else {
                if (dialectClazz != null && !"".equals(dialectClazz)) {
                    try {
                        Class<?> clazz = Class.forName(dialectClazz);
                        if (IDialect.class.isAssignableFrom(clazz))
                            dialect = (IDialect) clazz.newInstance();
                    } catch (ClassNotFoundException e) {
                        throw new IllegalArgumentException("Class :" + dialectClazz + " is not found");
                    }
                }
            }
            // 未配置方言则抛出异常
            if (dialect == null)
                throw new IllegalArgumentException("The value of the dialect property in mybatis configuration.xml is not defined.");

            // 原始语句
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            // 组装分页语句
            String originalSql = (String) boundSql.getSql();
            String paginationSql = dialect.buildPaginationSql(originalSql, rowBounds.getOffset(), rowBounds.getLimit());
            metaStatementHandler.setValue("delegate.boundSql.sql", paginationSql);
            // 禁用内存分页
            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

            // 判断是否需要查询总记录条数
            if (rowBounds instanceof Pagination) {
                Pagination pagination = (Pagination) rowBounds;
                if (pagination.getTotal() == 0) {
                    MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
                    Connection connection = (Connection) invocation.getArgs()[0];
                    count(originalSql, connection, mappedStatement, boundSql, pagination);
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 查询总记录条数
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    public void count(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Pagination page) {
        // 记录总记录数
        String countSql = "SELECT COUNT(0) FROM (" + sql + ") AS TOTAL";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBS);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            int total = 0;
            if (rs.next()) {
                total = rs.getInt(1);
            }
            page.setTotal(total);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler)
            return Plugin.wrap(target, this);
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (dialectType != null && !"".equals(dialectType))
            this.dialectType = dialectType;
        if (dialectClazz != null && !"".equals(dialectClazz))
            this.dialectClazz = dialectClazz;
    }

    public void setDialectType(String dialectType) {
        this.dialectType = dialectType;
    }

    public void setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
    }

}
