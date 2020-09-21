package org.itkk.udf.connection.pool.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.itkk.udf.core.domain.datasource.DataSourceMeta;
import org.itkk.udf.core.domain.datasource.IBuildDataSource;
import org.itkk.udf.core.exception.SystemRuntimeException;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 描述 : 构建Druid数据源
 *
 * @author wangkang
 */
public class DruidBuild implements IBuildDataSource {

    /**
     * 描述 : 构建数据源驱动属性
     *
     * @param properties 配置
     * @param datasource 数据源
     * @return 数据源
     */
    private DruidDataSource buildDriver(DataSourceMeta properties, DruidDataSource datasource) {
        //driver
        datasource.setUrl(properties.getUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriverClassName());
        return datasource;
    }

    /**
     * 描述 : 构建数据源连接池属性
     *
     * @param properties 配置
     * @param datasource 数据源
     * @return 数据源
     */
    private DruidDataSource buildPool(DataSourceMeta properties, DruidDataSource datasource) {
        //pool
        if (properties.getInitialSize() != null) {
            datasource.setInitialSize(properties.getInitialSize());
        }
        if (properties.getMinIdle() != null) {
            datasource.setMinIdle(properties.getMinIdle());
        }
        if (properties.getMaxActive() != null) {
            datasource.setMaxActive(properties.getMaxActive());
        }
        if (properties.getMaxWait() != null) {
            datasource.setMaxWait(properties.getMaxWait());
        }
        if (properties.getTimeBetweenEvictionRunsMillis() != null) {
            datasource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        }
        if (properties.getMinEvictableIdleTimeMillis() != null) {
            datasource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        }
        return datasource;
    }

    /**
     * 描述 : 构建数据源断线重连属性
     *
     * @param properties 配置
     * @param datasource 数据源
     * @return 数据源
     */
    private DruidDataSource buildValidation(DataSourceMeta properties, DruidDataSource datasource) {
        if (properties.getValidationQuery() != null) {
            datasource.setValidationQuery(properties.getValidationQuery());
        }
        if (properties.getTestWhileIdle() != null) {
            datasource.setTestWhileIdle(properties.getTestWhileIdle());
        }
        if (properties.getTestOnBorrow() != null) {
            datasource.setTestOnBorrow(properties.getTestOnBorrow());
        }
        if (properties.getTestOnReturn() != null) {
            datasource.setTestOnReturn(properties.getTestOnReturn());
        }
        return datasource;
    }

    /**
     * 描述 : 构建数据源其他属性
     *
     * @param properties 配置
     * @param datasource 数据源
     * @return 数据源
     */
    private DruidDataSource buildOther(DataSourceMeta properties, DruidDataSource datasource) {
        if (properties.getPoolPreparedStatements() != null) {
            datasource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
        }
        if (properties.getMaxPoolPreparedStatementPerConnectionSize() != null) {
            datasource
                    .setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (properties.getConnectionProperties() != null) {
            datasource.setConnectionProperties(properties.getConnectionProperties());
        }
        if (properties.getUseGlobalDataSourceStat() != null) {
            datasource.setUseGlobalDataSourceStat(properties.getUseGlobalDataSourceStat());
        }
        if (properties.getFilters() != null) {
            try {
                datasource.setFilters(properties.getFilters());
            } catch (SQLException e) {
                throw new SystemRuntimeException(e);
            }
        }
        if (properties.getConnectionProperties() != null) {
            datasource.setConnectionProperties(properties.getConnectionProperties());
        }
        return datasource;
    }

    @Override
    public DataSource build(DataSourceMeta properties) {
        DruidDataSource datasource = new DruidDataSource();
        buildDriver(properties, datasource);
        buildValidation(properties, datasource);
        buildPool(properties, datasource);
        buildOther(properties, datasource);
        return datasource;
    }

}
