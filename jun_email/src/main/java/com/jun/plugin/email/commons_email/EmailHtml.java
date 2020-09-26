/**
 * @author Wujun
 **/

package com.jun.plugin.email.commons_email;

import java.net.URL;

import org.apache.commons.mail.HtmlEmail;


public class  EmailHtml {
	public static void main(String[] args) throws Exception {
		// Create the email message
		HtmlEmail email = new HtmlEmail();
		//email.setSmtpPort(587);
		email.setHostName("smtp.163.com");
		email.setStartTLSEnabled(true);
		//email.setSSLOnConnect(true);
		email.setFrom("jsjs9494@163.com", "Html 邮件  009 ");
		email.setAuthentication("jsjs9494@163.com", "password.jsjs");
		
		email.addTo("245783660@qq.com", "QQ用户");
		
		email.setSubject("Html 邮件测试");

		// embed the image and get the content id
		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		String cid = email.embed(url, "Apache logo");

		// set the html message
		email.setHtmlMsg("<html><h1>Html 邮件测试</h1>The apache logo - <img src=\"cid:" + cid
				+ "\"></html>");

		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// send the email
		email.send();
	}
}
