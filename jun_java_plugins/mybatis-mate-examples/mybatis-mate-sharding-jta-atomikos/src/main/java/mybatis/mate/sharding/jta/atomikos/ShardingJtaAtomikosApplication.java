package mybatis.mate.sharding.jta.atomikos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// 注意事务管理器要打开
@EnableTransactionManagement
public class ShardingJtaAtomikosApplication {

    // 测试查看 TestController 方法注释
    public static void main(String[] args) {
        SpringApplication.run(ShardingJtaAtomikosApplication.class, args);
    }
}
