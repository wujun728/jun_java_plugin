package com.jun.base.email.javamail;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MailTest {
	public static void main(String[] args) {
		MailTest.testSendEmail();
	}

	public static void testSendEmail() {

		// 测试163邮箱
		// 测试qq邮箱
		// 新浪邮箱 13077403326m@sina.cn
		// 139邮箱 13077403326@139.com

		String userName = "jsjs9494@163.com"; // 用户邮箱地址
		String password = "********"; // 密码或者授权码
		String targetAddress = "245783660@qq.com"; // 接受者邮箱地址

		// 设置邮件内容
		MimeMessageDTO mimeDTO = new MimeMessageDTO();
		mimeDTO.setSentDate(new Date());
		mimeDTO.setSubject("邮件的标题");
		mimeDTO.setText("邮件的内容<img src='https://avatars0.githubusercontent.com/u/20160804?s=460&u=9c3f4f51c4fafda7ca22431b6764166703928a2b&v=4'>"
				+ targetAddress);

		// 发送单邮件--不带附件
		if (MailUtil.sendEmail(userName, password, targetAddress, mimeDTO)) {
			System.out.println("邮件发送成功！");
		} else {
			System.out.println("邮件发送失败!!!");
		}
		// 发送单邮件(带附件)
		List<String> filepath=new ArrayList<String>();
		filepath.add("D:/testmail.txt");
		filepath.add("D:/testmail2.txt");
		if (MailUtil.sendEmailByFile(userName, password, targetAddress, mimeDTO,filepath)) {
			System.out.println("邮件发送成功！");
		} else {
			System.out.println("邮件发送失败!!!");
		}
		
		// 群发邮件--不带附件
		targetAddress = "wujun728@163.com,245783660@qq.com";
		if (MailUtil.sendGroupEmail(userName, password, targetAddress, mimeDTO)) {
			System.out.println("邮件发送成功！");
		} else {
			System.out.println("邮件发送失败!!!");
		}
//		// 群发邮件(带附件)
		if (MailUtil.sendGroupEmailByFile(userName, password, targetAddress, mimeDTO,filepath)) {
			System.out.println("邮件发送成功！");
		} else {
			System.out.println("邮件发送失败!!!");
		}

	}

}
