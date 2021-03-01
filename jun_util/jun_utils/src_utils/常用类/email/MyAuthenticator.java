package book.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件身份认证器，在发送邮件时使用
 */
public class MyAuthenticator extends Authenticator{
	// 登陆发送邮件服务器的用户名
	private String userName;
	// 登陆发送邮件服务器的密码
	private String password;
	public MyAuthenticator(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	/**
	 * 覆盖父类的该方法，获得密码认证器
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
