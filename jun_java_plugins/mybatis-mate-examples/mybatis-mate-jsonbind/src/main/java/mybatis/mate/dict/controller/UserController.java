package mybatis.mate.dict.controller;

import lombok.AllArgsConstructor;
import mybatis.mate.dict.entity.User;
import mybatis.mate.dict.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private UserMapper userMapper;

    // 测试访问 http://localhost:8080/info
    // 观察 json 结果集会动态追加非 User 类绑定的虚拟属性
    @GetMapping("/info")
    public User info() {
        User user = userMapper.selectById(1L);
        User child = new User();
        child.setSex(1);
        child.setUsername("abc");
        return user;
    }
}
