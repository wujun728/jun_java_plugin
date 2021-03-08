package com.zh.springbootmybatis;

import com.zh.springbootmybatis.model.User;
import com.zh.springbootmybatis.service.UserService;
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
public class SpringBootMybatisApplicationTests {

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
        User user = this.userService.findById(3);
        log.info("=============查询单个用户:{}================",user.toString());
        List<User> list = this.userService.listByAge(26);
        log.info("=============查询多个用户:{}================",list.toString());
    }

    @Test
    public void updateTest() {
        log.info("=================更新用户=================");
        User user = new User(1,27);
        this.userService.save(user);
        user = this.userService.findById(1);
        log.info("=============查询单个用户:{}================",user.toString());
    }

    @Test
    public void deleteTest() {
        log.info("=================删除用户=================");
        this.userService.deleteById(3);
    }

}
