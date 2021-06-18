package com.lin.config.mybatis;

import com.lin.config.datasource.DynamicDataSourceContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @created 2017-07-10 17:34.
 */
@Aspect
@Component
public class DataSourceAop {

    private static Log logger = LogFactory.getLog(DataSourceAop.class);

    @Before("execution(* com.lin.mapper..*.find*(..)) || execution(* com.lin.mapper..*.get*(..))")
    public void setReadDataSourceType() {
        DynamicDataSourceContextHolder.useSlaveDataSource();
        logger.info("dataSource切换到：Read");
    }

    @Before("execution(* com.lin.mapper..*.insert*(..)) || execution(* com.lin.mapper..*.insert*(..)) || execution(* com.lin.mapper..*.update*(..))")
    public void setWriteDataSourceType() {
        DynamicDataSourceContextHolder.useMasterDataSource();
        logger.info("dataSource切换到：write");
    }

}
