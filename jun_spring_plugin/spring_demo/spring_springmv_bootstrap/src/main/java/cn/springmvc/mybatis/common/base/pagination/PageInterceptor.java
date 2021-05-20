package cn.springmvc.mybatis.common.base.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
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

import cn.springmvc.mybatis.common.base.model.Page;
import cn.springmvc.mybatis.common.base.pagination.Dialect.DialectType;

/**
 * 分页拦截器
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {

    /** 数据库方言对应的实现类路径 */
    private String dialect;

    /** 分页对应的SQL语句id(拦截以page结尾的SQL) */
    private String pageSqlId;

    /**
     * 拦截器对应的拦截方法方法
     * 
     * @param invocation
     * @return Object
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // 1、分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环，可以分离出最原始的的目标类)
        while (metaObject.hasGetter("h")) {
            Object obj = metaObject.getValue("h");
            metaObject = SystemMetaObject.forObject(obj);
        }

        // 2、分离最后一个代理对象的目标类
        while (metaObject.hasGetter("target")) {
            Object obj = metaObject.getValue("target");
            metaObject = SystemMetaObject.forObject(obj);
        }

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 3、分页处理
        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，只重写以page结尾的MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null)
                throw new NullPointerException("parameterObject is null!");
            else {
                // 分页参数作为参数对象parameterObject的一个属性
                Page<?> page = (Page<?>) parameterObject;
                String sql = boundSql.getSql();
                // 构造分页SQL
                String pageSql = constructPageSql(sql, page);
                metaObject.setValue("delegate.boundSql.sql", pageSql);
                // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                Connection connection = (Connection) invocation.getArgs()[0];

                // 如果分页统计标识为false表示不重设分页参数里的总页数等信息
                Map<String, Object> paramMap = page.getParamMap();
                if (paramMap.containsKey("pageCountFlag") && !Boolean.valueOf("Boolean.valueOf(pageCountObj.toString())".toString())) {
                    return invocation.proceed();
                }
                resetPageParameter(sql, connection, mappedStatement, boundSql, page);
            }
        }

        // 将执行权交给下一个拦截器
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler)
            return Plugin.wrap(target, this);
        else
            return target;
    }

    /**
     * 将Mybatis总配置文件中对应的该插件的属性设置到本类中
     * 
     * @param properties
     *            属性文件对象
     */
    @Override
    public void setProperties(Properties properties) {
        dialect = properties.getProperty("dialect");
        pageSqlId = properties.getProperty("pageSqlId");
    }

    /**
     * 构造分页SQL
     * 
     * @param sql
     *            原始SQL语句
     * @param page
     *            分页对象
     * @return String 构造后的分页SQL语句
     */
    private String constructPageSql(String sql, Page<?> page) {
        try {
            // 验证配置
            if (StringUtils.isEmpty(dialect))
                throw new IllegalArgumentException("dialect property is not found!");
            if (StringUtils.isEmpty(pageSqlId))
                throw new IllegalArgumentException("pageSqlId property is not found!");

            // 根据dialect获取DialectType
            DialectType dialectType = DialectType.valueOf(dialect.toUpperCase());
            if (dialectType == null)
                throw new IllegalArgumentException("dialect configuration error!");
            Class<? extends Dialect> clazz = dialectType.getClazz();
            Dialect instance = clazz.newInstance();
            return instance.constructPageSql(sql, page);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("dialect configuration error!");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("dialect configuration error!");
        }
    }

    /**
     * 重设分页参数等信息
     * 
     * @param sql
     *            原始SQL
     * @param connection
     *            数据库连接
     * @param mappedStatement
     *            映射表达式对象
     * @param boundSql
     *            绑定SQL对象
     * @param page
     *            分页对象
     */
    private void resetPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page<?> page) {
        // 总记录数SQL语句
        String countSql = "SELECT COUNT(1) FROM (" + sql + ") T";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameter(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            // 总记录数
            long totalCount = 0;
            if (rs.next())
                totalCount = rs.getLong(1);
            page.setTotalCount(totalCount);

            // 总页数
            long totalPage = Page.computeTotalPage(totalCount, page.getPageSize());
            page.setTotalPage(totalPage);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (countStmt != null)
                    countStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对SQL参数设值
     * 
     * @param ps
     *            预处理表达式对象
     * @param mappedStatement
     *            映射表达式对象
     * @param boundSql
     *            绑定SQL对象
     * @param parameterObject
     *            参数对象
     * @throws SQLException
     *             SQL异常
     */
    private void setParameter(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

}
