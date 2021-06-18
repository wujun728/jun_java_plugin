package com.wangxin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangxin.entity.mail.MailAddress;
import com.wangxin.service.mail.MailAddressService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-datasource.xml", "classpath:spring/applicationContext-mybatis.xml", "classpath:spring/applicationContext-transaction.xml", "classpath:spring/applicationContext.xml" })
public class MailAddressServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MailAddressServiceTest.class);

    @Autowired
    private MailAddressService mailAddressService;

    @Test
    public void addMailAddresss() {
        try {
            log.info("# 生成测试数据");
            // String string = "master@rayootech.com";
            // String string = "slave@rayootech.com";
            String string = "fail@rayootech.com";
            for (int i = 1; i < 101; i++) {
                // int i = 99999;
                // int i = 102;
                MailAddress ma = new MailAddress();
                ma.setMailType("0" + i);
                ma.setToAddress(string);
                ma.setToCc(string);
                ma.setToBcc(string);
                mailAddressService.addMailAddress(ma);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
