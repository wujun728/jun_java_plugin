package mybatis.mate.sharding.jta.atomikos.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import mybatis.mate.config.DataSourceProperty;
import mybatis.mate.provider.AtomikosDataSourceProvider;
import mybatis.mate.provider.IDataSourceProvider;
import mybatis.mate.sharding.IShardingProcessor;
import org.apache.ibatis.mapping.MappedStatement;
import org.postgresql.xa.PGXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.XADataSource;
import java.sql.SQLException;

@Configuration
public class ShardingConfig {

    /**
     * 可注释 @bean 注解测试无效事务
     */
    @Bean
    public IDataSourceProvider dataSourceProvider() {
        // jta atomikos 分布式事务
        return new AtomikosDataSourceProvider() {

            /**
             * 创建 XADataSource 数据源
             *
             * @param group              数据库分组
             * @param dataSourceProperty 数据源配置
             * @return
             */
            @Override
            public XADataSource createXADataSource(String group, DataSourceProperty dataSourceProperty) throws SQLException {
                // 根据数据库类型可以创建指定 XA 数据源
                final String driverClassName = dataSourceProperty.getDriverClassName();
                System.out.println("数据库类型：" + driverClassName);
                if (driverClassName.contains("postgresql")) {
                    // postgresql xa
                    PGXADataSource pgxaDataSource = new PGXADataSource();
                    pgxaDataSource.setUrl(dataSourceProperty.getUrl());
                    pgxaDataSource.setUser(dataSourceProperty.getUsername());
                    pgxaDataSource.setPassword(dataSourceProperty.getPassword());
                    return pgxaDataSource;
                }

                // mysql xa
                MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
                mysqlXaDataSource.setUrl(dataSourceProperty.getUrl());
                mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
                mysqlXaDataSource.setUser(dataSourceProperty.getUsername());
                mysqlXaDataSource.setPassword(dataSourceProperty.getPassword());
                mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
                return mysqlXaDataSource;
            }
        };
    }

    @Bean
    public IShardingProcessor shardingProcessor() {
        /**
         * 切换数据源，返回 false 使用默认数据源切换规则
         *
         * @param args            执行参数
         * @param mappedStatement {@link MappedStatement}
         * @param datasourceKey   数据源关键字
         * @return true 手动切换成功，底层不会处理  false 交给底层随机自行切换
         */
        // 改变系统切换策略可以实现 IShardingStrategy 策略
        return (args, mappedStatement, datasourceKey) -> false;
    }
}

