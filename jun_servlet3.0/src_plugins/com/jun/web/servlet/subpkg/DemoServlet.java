package com.jun.web.servlet.subpkg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	int num = 5;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//下载
		
		//思考：能不能加下载的文件名称写成中文？
		
		//通知浏览器进行下载
		response.setHeader("content-disposition", "attachment;filename=22.jpg");
		
		//资源 1.jpg
		InputStream is = this.getServletContext().getResourceAsStream("1.jpg");
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while( (len = is.read(buf)) > -1 ){
			out.write(buf, 0, len);
		}
		out.close();
		is.close();
		
		
	}

	private void refresh(HttpServletResponse response) throws IOException {
		//refresh:服务器通知浏览器，刷新的时间以及url  单位：秒
		if(num > 0 ){
			//将内容输出
			response.getWriter().print(num --);
			response.setHeader("refresh", "1");
		} else {
			response.setHeader("refresh", "0;url=http://localhost:8080/day044/1.html");
		}
	}

	private void type(HttpServletResponse response) throws IOException {
		//content-type:服务器通知浏览器，服务器发送的数据的编码
		response.setHeader("content-type", "text/html;charset=UTF-8");
		response.getWriter().write("中文");
	}

	private void encoding(HttpServletResponse response) throws IOException {
		//通知浏览器，服务器发送的数据时压缩的，并且指定压缩的格式
		response.setHeader("content-encoding", "gzip");
		
		
		//将大数据压缩后，发送给浏览器
		//准备大数据
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < 80000 ; i ++){
			builder.append("abcd");
		}
		String data = builder.toString();
		
		//确定压缩的位置
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//压缩  gzip
		GZIPOutputStream gzip = new GZIPOutputStream(baos);  //压缩的位置
		//压缩数据
		gzip.write(data.getBytes());
		gzip.close();
		
		//获得压缩后的字节数组
		byte[] endData = baos.toByteArray();
		
		//将压缩的数据发送给浏览器  -- 
		response.getOutputStream().write(endData);
	}

	private void location(HttpServletResponse response) {
		//跳转 (重定向) -- 服务器通知浏览
		//设置http响应头
		response.setHeader("location", "http://localhost:8080/day044/1.html");
		//状态码确定行为
		response.setStatus(302);
	}

}
