package org.itkk.udf.core.domain.datasource;

import lombok.Data;

/**
 * 描述 : 数据源属性
 *
 * @author wangkang
 */
@Data
public class DataSourceMeta {

    /**
     * 描述 : 数据源代码(唯一)
     */
    private String code;

    /**
     * 描述 : 是否启用(默认启用)
     */
    private Boolean enable = true;

    /**
     * 描述 : 数据库连接url
     */
    private String url;

    /**
     * 描述 : 数据库账号
     */
    private String username;

    /**
     * 描述 : 数据库密码
     */
    private String password;

    /**
     * 描述 : 数据库驱动类名
     */
    private String driverClassName;

    /**
     * 描述 : 连接池初始容量
     */
    private Integer initialSize;

    /**
     * 描述 : 连接池最小容量
     */
    private Integer minIdle;

    /**
     * 描述 : 连接池最大容量
     */
    private Integer maxActive;

    /**
     * 描述 : 获取连接等待超时的时间
     */
    private Long maxWait;

    /**
     * 描述 : 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private Long timeBetweenEvictionRunsMillis;

    /**
     * 描述 : 一个连接在池中最小生存的时间，单位是毫秒
     */
    private Long minEvictableIdleTimeMillis;

    /**
     * 描述 : 用来检测连接是否有效的sql，要求是一个查询语句
     */
    private String validationQuery;

    /**
     * 描述 : 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
     */
    private Boolean testWhileIdle;

    /**
     * 描述 : 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnBorrow;

    /**
     * 描述 : 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnReturn;

    /**
     * 描述 : 是否缓存preparedStatement
     */
    private Boolean poolPreparedStatements;

    /**
     * 描述 : 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true
     */
    private Integer maxPoolPreparedStatementPerConnectionSize;

    /**
     * 描述 : 属性类型是字符串，通过别名的方式配置扩展插件
     */
    private String filters;

    /**
     * 描述 : 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     */
    private String connectionProperties;

    /**
     * 描述 : 合并多个DruidDataSource的监控数据
     */
    private Boolean useGlobalDataSourceStat;

}
