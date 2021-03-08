package com.zh.springbootjpa;

import com.zh.springbootjpa.model.User;
import com.zh.springbootjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootJpaApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void createTest() {
        log.info("=================插入单个用户=================");
        User user = new User("张三",26);
        this.userService.save(user);
        log.info("=================插入多个用户=================");
        List<User> list = Arrays.asList(new User("李四",25),new User("王五",26));
        this.userService.save(list);
    }

    @Test
    public void retrieveTest() {
        User user = this.userService.findById(1);
        log.info("=============查询单个用户:{}================",user);
        List<User> list = this.userService.listByAge(26);
        log.info("=============查询多个用户:{}================",list);
    }

    @Test
    public void updateTest() {
        log.info("=================更新用户=================");
        User user = this.userService.findById(1);
        user.setAge(27);
        this.userService.save(user);
        user = this.userService.findById(1);
        log.info("=============查询单个用户:{}================",user);
        log.info("=================根据名字更新用户年龄=================");
        this.userService.updateAgeByName("张三",10);
        user = this.userService.findByName("张三");
        log.info("=============查询单个用户:{}================",user);
    }

    @Test
    public void deleteTest() {
        log.info("=================删除用户=================");
        this.userService.deleteById(3);
    }

}
