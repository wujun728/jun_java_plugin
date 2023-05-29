package mybatis.mate.sharding.jta.atomikos.controller;

import lombok.AllArgsConstructor;
import mybatis.mate.sharding.jta.atomikos.service.BuyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {
    private BuyService buyService;

    // 数据库 test 表 t_order 在事务一致情况无法插入数据，能够插入说明多数据源事务无效
    // 测试访问 http://localhost:8080/test
    // 制造事务回滚 http://localhost:8080/test?error=true 也可通过修改表结构制造错误
    // 注释 ShardingConfig 注入 dataSourceProvider 可测试事务无效情况
    @GetMapping("/test")
    public String test(Boolean error) {
        return buyService.buy(null != error && error);
    }
}
