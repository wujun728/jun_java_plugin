package cn.springmvc.mybatis.web.command;

import java.io.Serializable;

/**
 * @author Vincent.wang
 *
 */
public class UserCommand implements Serializable {

	private static final long serialVersionUID = 5057570256133640356L;

	private String username;

	private String email;

	private String password;

	private String newPassword;

	private String passwordAgain;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

}
