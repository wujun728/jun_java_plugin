package mybatis.mate.sharding.jta.atomikos.config;

import mybatis.mate.ddl.IDdl;
import mybatis.mate.ddl.IDdlGenerator;
import mybatis.mate.ddl.PostgresDdlGenerator;
import mybatis.mate.sharding.ShardingDatasource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
public class Db3 implements IDdl {
    @Resource
    private ShardingDatasource shardingDatasource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        consumer.accept(shardingDatasource.getDataSource("test3t1"));
    }

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList("db/log-db3.sql");
    }

    /**
     * 该方法也可以不指定 ddl 生成器默认自动识别
     */
    @Override
    public IDdlGenerator getDdlGenerator() {
        // 指定数据库 ddl 创建处理器
        return PostgresDdlGenerator.newInstance();
    }
}
