package com.jun.plugin;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jun.plugin.common.utils.StringUtils;
import com.jun.plugin.project.util.WindowsReqistry;

@Component
public class CommandRunner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(CommandRunner.class);

	@Autowired
	Environment environment;
	
	@Override
	public void run(String... args) throws Exception {
		String port = environment.getProperty("local.server.port");
		String url = "http://127.0.0.1:" + port;
		openBrowserUrl(url);
	}

	/**
	 * 打开浏览器
	 * @param url
	 */
	private void openBrowserUrl(String url) {
		try {
			String appPath = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\chrome.exe", "/ve");
			if (StringUtils.isNotEmpty(appPath)) {
				appPath = appPath.split("REG_SZ")[1].trim();
			} else {
				appPath = WindowsReqistry.readRegistry("HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\chrome.exe", "/ve");
				if (StringUtils.isNotEmpty(appPath)) {
					appPath = appPath.split("REG_SZ")[1].trim();
				}
			}
			if (StringUtils.isNotEmpty(appPath)) {
				if (new File(appPath).exists()) {
	        		ProcessBuilder proc = new ProcessBuilder(appPath,url);
	    			proc.start();
				} else {
					openFireFox(url);
				}
			} else {
				openFireFox(url);
			}
		} catch (Exception e) {
			openFireFox(url);
		}
	}
	
	/**
	 * 用火狐浏览器打开网页
	 * @param url
	 */
	private void openFireFox(String url) {
		try {
			String appPath = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\firefox.exe", "/ve");
			if (StringUtils.isNotEmpty(appPath)) {
				appPath = appPath.split("REG_SZ")[1].trim();
			} else {
				appPath = WindowsReqistry.readRegistry("HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\firefox.exe", "/ve");
				if (StringUtils.isNotEmpty(appPath)) {
					appPath = appPath.split("REG_SZ")[1].trim();
				}
			}
			if (StringUtils.isNotEmpty(appPath)) {
				if (new File(appPath).exists()) {
	        		ProcessBuilder proc = new ProcessBuilder(appPath,url);
	    			proc.start();
				} else {
					openDefaultBrowserUrl(url);
				}
			} else {
				openDefaultBrowserUrl(url);
			}
		} catch (Exception e) {
			openDefaultBrowserUrl(url);
		}
	}
	
	/**
	 * 打开默认浏览器
	 * @param url
	 */
	private void openDefaultBrowserUrl(String url) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {
			logger.error("打开默认浏览器异常：{}", e);
		}
	}
}
