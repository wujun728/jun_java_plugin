package com.kancy.emailplus.demo.springboot.service.impl;

import com.kancy.emailplus.demo.springboot.service.UserService;
import com.kancy.emailplus.spring.boot.aop.annotation.EmailNotice;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author kancy
 * @date 2020/2/22 22:38
 */
@Service
public class UserServiceImpl implements UserService {

    @EmailNotice(value = "test-email-notice")
    @Override
    public String exceptionTest() {
        throw new UnsupportedOperationException("测试异常");
    }
    @EmailNotice("test-email-notice")
    @Override
    public String getUser() {
       return "user";
    }
}
