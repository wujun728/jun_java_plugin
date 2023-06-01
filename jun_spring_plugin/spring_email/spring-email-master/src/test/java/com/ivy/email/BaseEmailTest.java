package com.ivy.email;

import com.ivy.email.vo.EmailVO;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by fangjie04 on 2016/12/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmailApplicationContext.class})
public abstract class BaseEmailTest {

    protected AnnotationConfigApplicationContext context = null;
    protected EmailVO vo;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(EmailApplicationContext.class);
        vo = new EmailVO();
        vo.setCc(new String[]{});
        vo.setBcc(new String[]{});
        vo.setSubject("[主题][致亲爱的一封邮件]");
        vo.setEmailContent("<h1>走不去的是人生，忘不了的是真情</h1>");
        vo.setReceivers(new String[]{"631294101@qq.com"});
        vo.setSender("fangjiewd@163.com");
    }

}
