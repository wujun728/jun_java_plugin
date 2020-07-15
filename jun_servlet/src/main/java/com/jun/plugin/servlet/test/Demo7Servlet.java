package com.itheima;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * ServletContext读取资源文件
 */
public class Demo7Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		File file = new File("config.properties");
//		System.out.println(file.getAbsolutePath());
		//--如果写相对路径,则会到程序启动目录 即tomcat/bin目录下去找资源,找不到
		//--如果写绝对路径,则会到程序启动目录的根目录 即tomcat/bin 目录的磁盘目录,我的机器tomcat在e:/workspace/tomcatt6 所以找到了e:/下去找资源,找不到
		//--如果用硬盘路径,则能找到资源,但是一旦换一个运行环境,很可能硬盘路径就是不正确的了,也不行
		//----所以,在web环境下如果项读取资源文件,必须使用ServletContext提供的方法进行读取,
		//		原理是在传入的路径前拼接当前web应用的硬盘路径从而得到当前资源的硬盘路径,
		//		由于web应用的硬盘路径是动态获取的,即使换了发布环境也是正确的,所以路径就保证了永远是正确的
		Properties prop = new Properties();
		prop.load(new FileReader(this.getServletContext().getRealPath("config.properties")));
		
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
