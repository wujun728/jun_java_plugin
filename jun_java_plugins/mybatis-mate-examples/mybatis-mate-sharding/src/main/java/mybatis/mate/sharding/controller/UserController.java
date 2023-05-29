package mybatis.mate.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import mybatis.mate.ddl.DdlScript;
import mybatis.mate.sharding.ShardingKey;
import mybatis.mate.sharding.entity.User;
import mybatis.mate.sharding.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.StringReader;

@RestController
public class UserController {
    @Resource
    private DdlScript ddlScript;
    @Resource
    private UserMapper mapper;

    // 测试访问 http://localhost:8080/test
    @GetMapping("/test")
    public boolean testSharding() throws Exception {
        this.initMysqlT2();
        this.test();
        return true;
    }

    public void initMysqlT2() throws Exception {
        // 切换到 mysql 从库，执行 SQL 脚本
        ShardingKey.change("mysqlt2");
        ddlScript.run(new StringReader("DELETE FROM user;\n" +
                "INSERT INTO user (id, username, password, sex, email) VALUES\n" +
                "(20, 'Duo', '123456', 0, 'Duo@baomidou.com');"));
    }

    public void test() {
        // 插入 mysql t1 主库
        User user = new User();
        user.setId(IdWorker.getId());
        user.setUsername("青苗");
        user.setSex(1);
        user.setEmail("jobob@qq.com");
        user.setPassword("123");
        mapper.insert(user);

        // mysql t2 从库未做同步，这里是查不出结果的
        Long count = mapper.selectCount(getWrapperByUsername(user.getUsername()));
        System.err.println(count == 0);

        // mysql t2 查初始化 Duo 记录
        System.err.println(mapper.selectCount(getWrapperByUsername("Duo")) == 1);

        // 切换数据源 postgres 查询 Tom 记录
        Long id = mapper.selectByUsername("Tom");
        System.err.println(null != id && id.equals(3L));
    }

    protected LambdaQueryWrapper<User> getWrapperByUsername(String username) {
        return Wrappers.<User>lambdaQuery().eq(User::getUsername, username);
    }
}
