package mybatis.mate.sharding.dynamic.config;

import mybatis.mate.config.DataSourceProperty;
import mybatis.mate.config.ShardingProperties;
import mybatis.mate.provider.HikariDataSourceProvider;
import mybatis.mate.provider.IDataSourceProvider;
import mybatis.mate.sharding.ShardingDatasource;
import mybatis.mate.strategy.IShardingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShardingConfig {
    @Autowired(required = false)
    private IShardingStrategy shardingStrategy;

    @Bean
    public IDataSourceProvider dataSourceProvider() {
        return new HikariDataSourceProvider();
    }

    /**
     * 这里是代码初始化数据源方式
     */
//    @Primary
//    @Bean(name = "dataSource")
    public ShardingDatasource shardingDatasource(IDataSourceProvider dataSourceProvider, ShardingProperties shardingProperties) {
        shardingProperties.setPrimary("mysql");
        DataSourceProperty mysqlT1 = new DataSourceProperty();
        mysqlT1.setKey("t1");
        mysqlT1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlT1.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        mysqlT1.setUsername("root");
        mysqlT1.setSchema("test");
        shardingProperties.setDatasource(new HashMap<String, List<DataSourceProperty>>(16) {{
            put("mysql", Arrays.asList(mysqlT1));
        }});
        Map<String, DataSource> dataSources = new HashMap<>(16);
        shardingProperties.getDatasource().forEach((k, v) -> v.forEach(d -> {
            try {
                String datasourceKey = k + d.getKey();
                dataSources.put(datasourceKey, dataSourceProvider.createDataSource(k, d));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        if (null != shardingStrategy) {
            shardingProperties.setShardingStrategy(shardingStrategy);
        }
        return new ShardingDatasource(dataSourceProvider, dataSources, shardingProperties);
    }
}
