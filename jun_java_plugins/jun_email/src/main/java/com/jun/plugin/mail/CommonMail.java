package com.jun.plugin.mail;

import java.net.MalformedURLException;
import java.net.URL;

import javax.mail.internet.MimeUtility;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class CommonMail {
	public static void main(String[] args) throws Exception {
		CommonMail mail = new CommonMail();
//		mail.sendSimpleMail();
//		mail.sendMutiMail();
		 mail.sendHtml();
	}

	@SuppressWarnings("deprecation")
	public void sendSimpleMail() throws Exception {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.163.com");
		email.setAuthentication("jsjs9494@163.com", "password.jsjs");
		email.addTo("245783660@qq.com", "245783660@qq.com");
		email.setFrom("jsjs9494@163.com", "发信人列表");
		email.setSubject("邮件大标题");//
		email.setMsg(" 邮件内容  "); //
		email.setSmtpPort(465); //
		email.setSSL(true); //
		email.setCharset("GBK"); //
		email.send();
	}

	public void sendMutiMail() throws Exception {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("python resource");
		attachment.setPath("src_mail/com/jun/plugin/mail/File.html");
		attachment.setName(MimeUtility.encodeText("File.html"));

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.163.com"); 
		email.setAuthentication("jsjs9494@163.com", "password.jsjs");
		email.addTo("245783660@qq.com", "收信显示名称");
		email.setFrom("jsjs9494@163.com", " 发信人列表  ");
		email.setSubject("邮件大标题");
		email.setMsg("  邮件内容 sendMutiMail  ");
		email.setCharset("GBK");
		email.attach(attachment);
		email.send();
	}


	/**
	 * 慢
	 */
	@SuppressWarnings("deprecation")
	public   void sendHtml() {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setTLS(true);
			email.setHostName("smtp.qq.com");
			email.setAuthentication("245783660@qq.com", "qq245783660.");
			email.addTo("jsjs9494@163.com", " 收信显示名称 ");
			email.setFrom("245783660@qq.com");
			email.setSubject(" 邮件标题 ");
			email.setCharset("GBK"); 
			email.setMsg(" sendHtml  content ");

			URL url;
			url = new URL("http://img.baidu.com/img/image/ilogob.gif");
			String cid = email.embed(url, "baidu logo");
			email.setHtmlMsg("<html><a href='http://www.baidu.com'>baidu.com</a> <img src=\"cid:" + cid + "\"><br><P>haha</P></html>");
			email.send();
			System.out.println(" sendHtml sucessed ");
		} catch (EmailException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}