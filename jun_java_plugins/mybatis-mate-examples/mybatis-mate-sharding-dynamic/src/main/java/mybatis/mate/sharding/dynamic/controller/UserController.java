package mybatis.mate.sharding.dynamic.controller;

import mybatis.mate.config.DataSourceProperty;
import mybatis.mate.sharding.ShardingDatasource;
import mybatis.mate.sharding.dynamic.entity.User;
import mybatis.mate.sharding.dynamic.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private ShardingDatasource shardingDatasource;
    @Autowired
    private UserMapper mapper;

    // 测试访问 http://localhost:8080/test 观察返回结果集
    // 切换数据源 http://localhost:8080/test?db=test2
    @GetMapping("/test")
    public List<User> test(String db) throws Exception {
        // 这里始终使用默认数据源切换规则，更多细节可以查看 MyShardingProcessor 处理器打印信息
        System.err.println("~~~ count =  " + mapper.selectCount(null));
        if ("test2".equals(db)) {
            // 切换到指定数据源，如果数据源之前不存在会装载配置源
            // 数据源的装载可以放到初始化或者添加新数据源的逻辑里面执行
            shardingDatasource.change(db, key -> DataSourceProperty.of(
                    "com.mysql.cj.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/" + db + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                    "root",
                    null
            ));
            // 卸载数据源
            // shardingDatasource.removeDataSource(db);
        }
        // 请求地址 db=test2 这里会切换到数据源 test2 界面显示数据会发生变好
        return mapper.selectList(null);
    }
}
