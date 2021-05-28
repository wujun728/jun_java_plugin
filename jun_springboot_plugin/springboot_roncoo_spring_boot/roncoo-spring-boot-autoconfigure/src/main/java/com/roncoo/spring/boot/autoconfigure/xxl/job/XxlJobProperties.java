package com.roncoo.spring.boot.autoconfigure.xxl.job;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Wujun
 */
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

	private Admin admin = new Admin();

	private String accessToken;

	private Executor executor = new Executor();

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public static class Admin {
		private String addresses;

		public String getAddresses() {
			return addresses;
		}

		public void setAddresses(String addresses) {
			this.addresses = addresses;
		}

	}

	public static class Executor {
		private String appName;

		private String ip;

		private int port;

		private String logPath;

		private int logRetentionDays;

		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getLogPath() {
			return logPath;
		}

		public void setLogPath(String logPath) {
			this.logPath = logPath;
		}

		public int getLogRetentionDays() {
			return logRetentionDays;
		}

		public void setLogRetentionDays(int logRetentionDays) {
			this.logRetentionDays = logRetentionDays;
		}

	}

}
