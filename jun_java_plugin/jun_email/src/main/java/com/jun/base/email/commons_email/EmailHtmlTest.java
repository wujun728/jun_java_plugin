/**
 * @author Wujun
 **/

package com.jun.base.email.commons_email;

import java.net.URL;

import org.apache.commons.mail.HtmlEmail;


public class  EmailHtmlTest {
	public static void main(String[] args) throws Exception {
		EmailHtmlTest.sendMail(args);
	}
	public static void sendMail(String[] args) throws Exception {
		// Create the email message
		HtmlEmail email = new HtmlEmail();
		//email.setSmtpPort(587);
		email.setHostName("smtp.163.com");
		email.setStartTLSEnabled(true);
		//email.setSSLOnConnect(true);
		email.setFrom("jsjs9494@163.com", "邮件主题-Html邮件测试");
		email.setAuthentication("jsjs9494@163.com", "    ");
		
		email.addTo("245783660@qq.com", "用户名称1");
		
		email.setSubject("邮件主题2-Html邮件测试");

		// embed the image and get the content id
		URL url = new URL("https://avatars0.githubusercontent.com/u/20160804?s=460&u=9c3f4f51c4fafda7ca22431b6764166703928a2b&v=4");
		String cid = email.embed(url, "Bingo-logo");

		// set the html message
		email.setHtmlMsg("<html><h1>Html类型邮件测试</h1>The Bingo logo - <img src=\"cid:" + cid + "\"></html>");

		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// send the email
		email.send();
	}
}
