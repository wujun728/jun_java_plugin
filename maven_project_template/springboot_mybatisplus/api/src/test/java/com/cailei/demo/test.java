package com.cailei.demo;

import com.cailei.demo.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ：蔡磊
 * @date ：2022/10/20 16:38
 * @description：TODO
 */
@SpringBootTest
public class test {

    @Resource
    private UsersMapper usersMapper;

    @Test
    public void testSelect() {
        usersMapper.selectList(null).forEach(System.out::println);
    }
}