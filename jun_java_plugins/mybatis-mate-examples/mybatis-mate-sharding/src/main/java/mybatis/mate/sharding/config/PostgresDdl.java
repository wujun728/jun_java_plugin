package mybatis.mate.sharding.config;

import mybatis.mate.ddl.IDdl;
import mybatis.mate.sharding.ShardingDatasource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
public class PostgresDdl implements IDdl {
    @Resource
    private ShardingDatasource shardingDatasource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        // 多数据源指定，主库初始化从库自动同步
        // postgrest2 = postgres（数据源group） + t1（数据源key）
        consumer.accept(shardingDatasource.getDataSource("postgrest1"));
    }

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
                "db/user-postgres.sql"
                // ,"db/user-data.sql"
        );
    }
}
