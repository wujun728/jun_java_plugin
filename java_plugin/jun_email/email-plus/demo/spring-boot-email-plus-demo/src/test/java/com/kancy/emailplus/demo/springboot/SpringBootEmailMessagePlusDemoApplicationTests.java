package com.kancy.emailplus.demo.springboot;

import com.kancy.emailplus.spring.boot.service.EmailplusService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootEmailMessagePlusDemoApplicationTests {

    @Autowired
    private EmailplusService emailplusService;

    @Test
    void contextLoads() {
        emailplusService.sendTemplateEmail("test-a");
    }

}
