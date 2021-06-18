package com.itmuch.mybatis;

import com.itmuch.mybatis.entity.User;
import com.itmuch.mybatis.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

/**
 * @author Wujun
 */
@SpringBootApplication
@MapperScan("com.itmuch.mybatis.mapper")
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

//    @Autowired
//    private UserMapper userMapper;
//    /**
//     * 初始化用户
//     *
//     * @return
//     */
//    @Bean
//    public ApplicationRunner init() {
//        return args -> {
//            User user1 = new User(1L, "张三", 20);
//            User user2 = new User(2L, "李四", 28);
//            User user3 = new User(3L, "王五", 32);
//            Stream.of(user1, user2, user3)
//                    .forEach(userMapper::insert);
//        };
//    }
}
