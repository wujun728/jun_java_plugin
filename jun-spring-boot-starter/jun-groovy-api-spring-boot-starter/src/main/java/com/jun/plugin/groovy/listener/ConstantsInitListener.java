package com.jun.plugin.groovy.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.log.Log;
import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.common.constant.CommonConstant;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 初始化常量的监听器
 * <p>
 * 当spring装配好配置后，就去数据库读constants
 *
 * @date 2020/6/6 23:39
 */
public class ConstantsInitListener implements ApplicationListener<ApplicationContextInitializedEvent>, Ordered {

    private static final Log log = Log.get();

    private static final String CONFIG_LIST_SQL = "select code,value from sys_config where status = ?";

    private static final String CAPITAL_CODE = "CODE";

    private static final String CODE = "code";

    private static final String CAPITAL_VALUE = "VALUE";

    private static final String VALUE = "value";

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationContextInitializedEvent) {
        ConfigurableEnvironment environment = applicationContextInitializedEvent.getApplicationContext().getEnvironment();

        // 获取数据库连接配置
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");

        // 缓存中放入datasource链接，代码生成时候使用
        com.jun.plugin.groovy.listener.ConstantContext.putConstant(CommonConstant.DATABASE_URL_NAME, dataSourceUrl);
        com.jun.plugin.groovy.listener.ConstantContext.putConstant(CommonConstant.DATABASE_DRIVER_NAME, environment.getProperty("spring.datasource.driver-class-name"));
        com.jun.plugin.groovy.listener.ConstantContext.putConstant(CommonConstant.DATABASE_USER_NAME, dataSourceUsername);

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername)) {
            throw new BusinessException("SysConfigExceptionEnum.DATA_SOURCE_NOT_EXIST");
        }

        Connection conn = null;
        try {
            Class.forName(environment.getProperty("spring.datasource.driver-class-name"));
            assert dataSourceUrl != null;
            conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);

            // 获取sys_config表的数据
            List<Entity> entityList = SqlExecutor.query(conn, CONFIG_LIST_SQL, new EntityListHandler(), "0");

            // 将查询到的参数配置添加到缓存
            if (ObjectUtil.isNotEmpty(entityList)) {
                entityList.forEach(sysConfig ->
                        ConstantContext.putConstant(
                                sysConfig.getStr(CODE) == null ? sysConfig.getStr(CAPITAL_CODE) : sysConfig.getStr(CODE),
                                sysConfig.getStr(VALUE) == null ? sysConfig.getStr(CAPITAL_VALUE) : sysConfig.getStr(VALUE)
                        )
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            log.error(">>> 读取数据库constants配置信息出错：");
            e.printStackTrace();
            throw new BusinessException("SysConfigExceptionEnum.DATA_SOURCE_NOT_EXIST");
        } finally {
            DbUtil.close(conn);
        }

    }
}
