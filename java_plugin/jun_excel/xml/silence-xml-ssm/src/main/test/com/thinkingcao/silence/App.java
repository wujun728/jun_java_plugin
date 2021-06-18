package com.thinkingcao.silence;

import com.thinkingcao.silence.ssm.mapper.UserMapper;
import com.thinkingcao.silence.ssm.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <pre>
 * @desc:  测试Spring整合Mybatis
 * @title: App
 * @author: cao_wencao
 * @date: 2019-06-05 23:58
 * @version: 1.0
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class App {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;

    //使用userMapper测试
    @Test
    public void testSelectAll() throws Exception {
        //JDK8新用法
        userMapper.selectAll().forEach(System.out::println);
    }

    //使用userService测试
    @Test
    public void testListAll() throws Exception {
        //JDK8新用法
        userService.listAll().forEach(System.out::println);
    }
}
