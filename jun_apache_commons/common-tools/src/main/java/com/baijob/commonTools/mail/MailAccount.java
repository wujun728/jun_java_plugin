package com.baijob.commonTools.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.Setting;
import com.baijob.commonTools.Exceptions.SettingException;

/**
 * 邮件账户对象
 * @author Luxiaolei
 *
 */
public class MailAccount {
	private static Logger logger = LoggerFactory.getLogger(MailAccount.class);
	
	public static final String MAIL_SETTING_PATH = "config/mailAccount.setting";
	
	private String host;
	private String port;
	private boolean auth;
	private String user;
	private String pass;
	private String from;
	
	public MailAccount() {
	}

	public MailAccount(String accountSettingFileBaseClassLoader) {
		try {
			new Setting(accountSettingFileBaseClassLoader, "utf8", false).settingToObject(this);
		} catch (SettingException e) {
			logger.error("Init mail account fail!", e);
		}
		logger.debug(toString());
	}
	
	public MailAccount(Setting setting) {
		try {
			setting.settingToObject(this);
		} catch (SettingException e) {
			logger.error("Init mail account fail!", e);
		}
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean isAuth) {
		this.auth = isAuth;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	@Override
	public String toString() {
		return "MailAccount [host=" + host + ", port=" + port + ", isAuth=" + auth + ", user=" + user + ", pass=******, from=" + from + "]";
	}
}
