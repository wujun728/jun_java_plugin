package mybatis.mate.datascope.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatis.mate.datascope.entity.User;
import mybatis.mate.datascope.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public void dataScope() {
        System.err.println("----- selectList ---- 使用类注解 ");
        userMapper.selectList(null).forEach(System.out::println);

        System.err.println("----- 自定义方法 selectTestList ---- 使用方法注解 ");
        Page page = new Page<User>(1, 20);
        userMapper.selectTestList(page, 1L, "Jack").forEach(System.out::println);

        userMapper.selectTestJoin(page, 1L, "Jack").forEach(System.out::println);

        // 观察 sql 变化这个方法没有注解权限，使用类注解
        User user = userMapper.selectById(1L);
        user.setUsername("abc");

        // 执行 processUpdate 方法
        userMapper.updateById(user);

        // 执行 processInsert 方法
        userMapper.insert(new User(6L, 1L, "hello", "15315336667"));

        // 执行 processDelete 方法
        userMapper.deleteById(6L);

        List<Long> ids = Arrays.asList(1L, 2L, 3L, 5L);

        // xml sql
        userMapper.selectDeptNamesByUserIds(ids, "o").forEach(System.out::println);

        // ignore dataScope
        userMapper.selectTestIgnoreDataScope(ids, "o").forEach(System.out::println);

        // use mapper dataScope
        userMapper.selectTestXmlSql(ids, "o").forEach(System.out::println);

    }
}
