package com.kancy.emailplus.spring.boot;

import com.kancy.emailplus.core.exception.EmailException;
import com.kancy.emailplus.spring.boot.config.EmailplusAutoConfiguration;
import com.kancy.emailplus.spring.boot.service.EmailplusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * EmailplusServiceTest
 *
 * @author Wujun
 * @date 2020/2/21 18:10
 */
@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmailplusAutoConfiguration.class,RedisAutoConfiguration.class})
public class EmailplusServiceTest {
    @Autowired
    private EmailplusService emailplusService;

    /**
     * 发送邮件
     *
     * @throws EmailException
     */
    @Test
    public void sendSimpleEmail01() {
        emailplusService.sendSimpleEmail("test-b");
    }

    /**
     * 发送邮件
     *
     */
    @Test
    public void sendSimpleEmail02() {
        emailplusService.sendSimpleEmail("test-a","test success");
    }

    /**
     * 发送邮件
     */
    @Test
    public void sendFileEmail() {
        Map<String, File> files = new HashMap<>();
        files.put("test.java", new File(".\\src\\test\\java\\com\\kancy\\emailplus\\spring\\boot\\EmailplusServiceTest.java"));
        emailplusService.sendFileEmail("test-b","send files", files);
    }

    /**
     * 发送邮件
     */
    @Test
    public void sendTemplateEmail() {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("name", "kancy");
        emailplusService.sendTemplateEmail("test-template", templateData);
    }
}
