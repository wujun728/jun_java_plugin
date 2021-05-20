package com.jun.plugin.email.java_mail;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class mail_imap {
	/**
	 * 使用imap协议获取未读邮件数
	 * 
	 * @author Wujun
	 * 
	 */
	public static void main(String[] args) throws Exception {
		String user = "username@sohu.com";// 邮箱的用户名
		String password = "password"; // 邮箱的密码
		Properties prop = System.getProperties();
		prop.put("mail.store.protocol", "imap");
		prop.put("mail.imap.host", "imap.sohu.com");
		Session session = Session.getInstance(prop);
		int total = 0;
		IMAPStore store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器
		store.connect(user, password);
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
		folder.open(Folder.READ_WRITE);
		// 获取总邮件数
		total = folder.getMessageCount();
		System.out.println("-----------------共有邮件：" + total + " 封--------------");
		// 得到收件箱文件夹信息，获取邮件列表
		System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
		Message[] messages = folder.getMessages();
		int messageNumber = 0;
		for (Message message : messages) {
			System.out.println("发送时间：" + message.getSentDate());
			System.out.println("主题：" + message.getSubject());
			System.out.println("内容：" + message.getContent());
			Flags flags = message.getFlags();
			if (flags.contains(Flags.Flag.SEEN))
				System.out.println("这是一封已读邮件");
			else {
				System.out.println("未读邮件");
			}
			System.out.println("========================================================");
			System.out.println("========================================================");
			// 每封邮件都有一个MessageNumber，可以通过邮件的MessageNumber在收件箱里面取得该邮件
			messageNumber = message.getMessageNumber();
		}
		Message message = folder.getMessage(messageNumber);
		System.out.println(message.getContent() + message.getContentType());
		// 释放资源
		if (folder != null)
			folder.close(true);
		if (store != null)
			store.close();
	}
}
