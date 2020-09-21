package com.itmuch.cloud.common.utils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {

	@Autowired
	public static JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private static String username;

	public static void sendTemplateMail(String toUsername, String code, String toEmail ) throws Exception {

		MimeMessage message = null;
		try {
			message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(new InternetAddress(username, "日志管理", "UTF-8"));
			helper.setTo(toEmail);
			helper.setSubject("日志管理");

			StringBuffer sb = new StringBuffer();
			sb.append("<h1>日志管理</h1>").append("<p style=''>您好，验证码是：" + code + "，请在30分钟内按页面提示提交验证码，切勿将验证码泄露与他人</p>")
					.append("<p style='text-align:right'>日志管理</p>");
			helper.setText(sb.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		javaMailSender.send(message);
	}

}
