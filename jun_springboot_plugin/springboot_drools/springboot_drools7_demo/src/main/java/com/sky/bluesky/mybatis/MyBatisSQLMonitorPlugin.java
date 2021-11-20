package com.sky.bluesky.mybatis;

import com.sky.bluesky.util.JsonUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * MyBatis的SQL监控插件拦截器
 */
public final class MyBatisSQLMonitorPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisSQLMonitorPlugin.class);
    /**
     * 是否监控显示SQL
     */
    private static final boolean SHOWSQL = true;
    /**
     * 慢SQL时间阀值，单位毫秒
     */
    private static final int SLOWER = 3000;
    /**
     * 大集合监控阀值，单位条
     */
    private static final int MAXCOUNT = 80;

    /**
     * MyBatis的SQL监控插件拦截器
     *
     * @param invocation 调用方法
     * @return Object
     * @throws Throwable 异常
     */
    public static Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //请求参数
        Object parameter = null;
        if (1 < invocation.getArgs().length) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql();
        //执行的sql所在的mapper文件
        String resource = mappedStatement.getResource();
        //执行sql的dao文件的包名+方法名
        String daoMethod = mappedStatement.getId();
        //去除sql文中的换行
        sql = sql.replace("\n", "").replaceAll("\\s+", " ");
        if (SHOWSQL) {
            LOGGER.warn("\n" +
                            "[SQLMonitorPlugin]SQL监控：{}|{}\n" +//方法
                            "执行SQL：\n" +
                            "{}\n" +//SQL
                            "参数：{}", resource, daoMethod,
                    sql, JsonUtil.toJson(parameter));
        }
        try {
            long start = System.currentTimeMillis();
            Object e = invocation.proceed();
            long timeTicket = System.currentTimeMillis() - start;
            if (SLOWER < timeTicket) {
                LOGGER.warn("[SQLMonitorInterceptor]SQL监控：{}|{}，慢SQL（{}/{}ms）{}，参数：{}",
                        resource, daoMethod, timeTicket, SLOWER, sql, JsonUtil.toJson(parameter));
            }
            if (e instanceof Collection) {
                int ct = ((Collection<?>) e).size();
                if (MAXCOUNT < ct) {
                    LOGGER.warn("[SQLMonitorInterceptor]SQL监控：{}|{}，大集合（{}/{}）{}，参数：{}",
                            resource, daoMethod, ct, MAXCOUNT, sql, JsonUtil.toJson(parameter));
                }
            }
            return e;
        } catch (Exception e) {
            LOGGER.warn("[SQLMonitorPlugin]SQL监控：{}|{}，执行出错：{}，参数：{}", resource, daoMethod,
                    sql, JsonUtil.toJson(parameter), e);
            throw e;
        }
    }
}
