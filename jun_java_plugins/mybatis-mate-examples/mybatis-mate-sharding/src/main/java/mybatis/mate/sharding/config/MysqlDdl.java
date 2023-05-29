package mybatis.mate.sharding.config;

import mybatis.mate.config.ShardingGroupProperty;
import mybatis.mate.ddl.IDdl;
import mybatis.mate.sharding.ShardingDatasource;
import mybatis.mate.sharding.ShardingKey;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
public class MysqlDdl implements IDdl {
    @Resource
    private ShardingDatasource shardingDatasource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        // 多数据源指定，主库初始化从库自动同步
        String group = "mysql";
        ShardingGroupProperty sgp = ShardingKey.getDbGroupProperty(group);
        if (null != sgp) {
            // 主库
            sgp.getMasterKeys().forEach(key -> {
                consumer.accept(shardingDatasource.getDataSource(group + key));
            });
            // 从库
            sgp.getSlaveKeys().forEach(key -> {
                consumer.accept(shardingDatasource.getDataSource(group + key));
            });
        }
    }

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
                "db/user-mysql.sql"
                // ,"db/user-data.sql"
        );
    }
}
