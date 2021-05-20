package com.b510.sendmail.utils;

/**
 * 邮件信息
 * 
 * @author Hongten
 * 
 * @time 2012-4-4 2012
 */
public class MailMessage {
	/**
	 * 发件人
	 */
	private String from;
	/**
	 * 收件人
	 */
	private String to;
	/**
	 * 发件人，在邮件的发件人栏目中显示
	 */
	private String datafrom;
	/**
	 * 收件人，在邮件的收件人栏目中显示
	 */
	private String datato;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 发送日期
	 */
	private String date;
	/**
	 * 登陆邮箱的用户名
	 */
	private String user;
	/**
	 * 登陆邮箱的密码
	 */
	private String password;

	/**
	 * 获取发件人
	 * 
	 * @return 发件人
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * 设置发件人
	 * 
	 * @param from
	 *            发件人
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 获取收件人
	 * 
	 * @return 收件人
	 */
	public String getTo() {
		return to;
	}

	/**
	 * 设置收件人
	 * 
	 * @param to
	 *            收件人
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 获取发件人，在邮件的发件人栏目中显示
	 * 
	 * @return 发件人，在邮件的发件人栏目中显示
	 */
	public String getDatafrom() {
		return datafrom;
	}

	/**
	 * 设置发件人，在邮件的发件人栏目中显示
	 * 
	 * @param datafrom
	 *            发件人，在邮件的发件人栏目中显示
	 */
	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}

	/**
	 * 获取收件人，在邮件的收件人栏目中显示
	 * 
	 * @return 收件人，在邮件的收件人栏目中显示
	 */
	public String getDatato() {
		return datato;
	}

	/**
	 * 设置收件人，在邮件的收件人栏目中显示
	 * 
	 * @param datato
	 *            收件人，在邮件的收件人栏目中显示
	 */
	public void setDatato(String datato) {
		this.datato = datato;
	}

	/**
	 * 获取邮件主题
	 * 
	 * @return 邮件主题
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param subject
	 *            邮件主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 获取邮件内容
	 * 
	 * @return 邮件内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置邮件内容
	 * 
	 * @param content
	 *            邮件内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取发送日期
	 * 
	 * @return 发送日期
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 设置发送日期
	 * 
	 * @param date
	 *            发送日期
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 获取登陆邮箱的用户名
	 * 
	 * @return 登陆邮箱的用户名
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 设置登陆邮箱的用户名
	 * 
	 * @param user
	 *            登陆邮箱的用户名
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 获取登陆邮箱的密码
	 * 
	 * @return 登陆邮箱的密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置登陆邮箱的密码
	 * 
	 * @param password
	 *            登陆邮箱的密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
