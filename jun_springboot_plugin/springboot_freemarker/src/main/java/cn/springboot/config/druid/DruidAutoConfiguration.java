package cn.springboot.config.druid;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

/** 
 * @Description Druid单数据源配置
 * @author Wujun
 * @date Mar 16, 2017 5:18:24 PM  
 */
//@Configuration
//@EnableTransactionManagement
public class DruidAutoConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.propertyResolver = new RelaxedPropertyResolver(env, "druid.datasource.");
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    public DataSource writeDataSource() {

        // url;// 数据库URL
        // username;// 数据库账号
        // password;// 数据库密码
        // driverClassName;// 数据库驱动类
        // initialSize;// 初始化时建立物理连接的个数
        // maxActive;// 最大连接数
        // minIdle;// 最小连接数
        // maxWait;// 获取连接时最大等待时间，单位毫秒
        // useUnfairLock;// 配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，所以使用非公平锁
        // validationQuery;// 检测连接是否有效SQL
        // testOnBorrow;// 申请连接时执行validationQuery检测连接是否有效，设置为true会影响性能
        // testOnReturn;// 退还连接时执行validationQuery检测连接是否有效，设置为true会影响性能
        // testWhileIdle;// 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
        // timeBetweenEvictionRunsMillis;// 两个含义：1、Destroy线程会检测连接的间隔时间 2、testWhileIdle的判断依据
        // minEvictableIdleTimeMillis;// 一个连接在池中最小生存的时间，单位是毫秒
        // filters;// 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat 日志用的filter:log4j 防御sql注入的filter:wall

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(propertyResolver.getProperty("url"));
        dataSource.setUsername(propertyResolver.getProperty("username"));
        dataSource.setPassword(propertyResolver.getProperty("password"));
        dataSource.setDriverClassName(propertyResolver.getProperty("driverClassName"));
        dataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
        dataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
        dataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        dataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
        dataSource.setUseUnfairLock(Boolean.valueOf(propertyResolver.getProperty("useUnfairLock")));
        dataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        dataSource.setTestOnBorrow(Boolean.valueOf(propertyResolver.getProperty("testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.valueOf(propertyResolver.getProperty("testOnReturn")));
        dataSource.setTestWhileIdle(Boolean.valueOf(propertyResolver.getProperty("testWhileIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
        try {
            dataSource.setFilters(propertyResolver.getProperty("Filters"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
