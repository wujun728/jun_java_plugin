package com.jun.plugin.email.demo.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 类名称:  PopupAuthenticator
 * 功能描述: TODO
 * 创建人:  GavinNie 邮件 账号 密码
 * 创建时间: 2014-12-4 上午11:07:10 
 * @version  V1.0  
 */
public class PopupAuthenticator extends Authenticator {
	private String username = null;
	private String password = null;

	public PopupAuthenticator(String user, String pass) {
		this.username = user;
		this.password = pass;
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
