package com.itmuch.cloud.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itmuch.cloud.common.utils.MailUtils;
import com.itmuch.cloud.common.utils.SmsUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class My163MailTest {

	@Autowired
	private MailUtils mailUtils;
	@Autowired
	private SmsUtils smsUtils;

	@Test
	public void send() throws Exception {
		//mailUtils.sendTemplateMail("zhangsna", "1234", "1454668185@qq.com");
		smsUtils.sendSms();
	}

}
