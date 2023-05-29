package mybatis.mate.sharding.jta.atomikos.config;

import mybatis.mate.ddl.IDdl;
import mybatis.mate.sharding.ShardingDatasource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
public class Db2 implements IDdl {
    @Resource
    private ShardingDatasource shardingDatasource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        consumer.accept(shardingDatasource.getDataSource("test2t1"));
    }

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList("db/sku-db2.sql");
    }
}
