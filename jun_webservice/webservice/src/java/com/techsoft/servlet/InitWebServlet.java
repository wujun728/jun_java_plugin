package com.techsoft.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.xml.DOMConfigurator;

import com.techsoft.container.DataServer;

public class InitWebServlet extends HttpServlet {
	private static final long serialVersionUID = -2244907179466739916L;

	public InitWebServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String webpath = config.getServletContext().getRealPath("/");
		// 设定日志路径
		String Logpath = webpath + "logs";
		System.setProperty("logsdir", Logpath);
		DOMConfigurator.configure(webpath + "WEB-INF" + File.separator + "lib"
				+ File.separator + "log4j.xml");
		try {
			new DataServer(new File(webpath)).start();
		} catch (Exception e) {
			// 服务器在启动时出错要手动停止服务器， 配置正常后再启动
		}
	}
}
