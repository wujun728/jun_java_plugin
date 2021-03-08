package wyz.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import wyz.com.service.UserRepository;
import wyz.com.domain.User;

/**
 * Created by qinyg on 2017/11/20.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/say")
    public String say() {

        String h = "Hello World!";
        return h;
    }

    // 列表<实体类>
    @GetMapping(value = "/users")
    public List<User> userList() {
        return userRepository.findAll();
    }

    // 添加一个实体类
    @PostMapping(value = "/users")
    public User userAdd(@RequestParam("username") String username,
                        @RequestParam("pw") String pw,
                        @RequestParam("age") Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setPw(pw);
        user.setAge(age);

        return userRepository.save(user);
    }

    // 根据ID查看一个实体类
    @GetMapping(value = "/users/{id}")
    public User userFindOne(@PathVariable("id") Integer id) {

        User user = userRepository.findOne(id);
        if (user == null) {
            System.out.println("---- 根据ID查看一个实体类 ----");
            System.out.println("你查询ID不存在！");
        }
        return user;
    }

    // 根据ID修改实体类
    @PutMapping(value = "/users/{id}")
    public User userUpdate(@PathVariable("id") Integer id,
                           @RequestParam("username") String username,
                           @RequestParam("pw") String pw,
                           @RequestParam("age") Integer age) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPw(pw);
        user.setAge(age);

        return userRepository.save(user);
    }

    // 根据ID删除一个实体类
    @DeleteMapping(value = "/users/{id}")
    public void userDelete(@PathVariable("id") Integer id) {
        userRepository.delete(id);
    }

    // 根据密码查看多个实体类
    @GetMapping(value = "/users/pw/{pw}")
    public List<User> userListByPw(@PathVariable("pw") String pw) {
        return userRepository.findByPw(pw);
    }

}
