package com.jun.plugin.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class UrlTest {
	
	public static void main(String[] args) throws Exception {
		
		
		//访问 http://localhost:8080/day08_web/loginServlet
		//URL类对web资源的定位
		URL url = new URL("http://localhost:8080/day08_web/loginServlet");
		//获得连接 ，做准备
		URLConnection conn = url.openConnection();
		//必须确定当前请求，是否可以读写
		conn.setDoInput(true); //确定是否可以读，默认是true
		conn.setDoOutput(true);//确定是否可以写，默认是false
		
		//准备发送的数据 -- request填充
		OutputStream out = conn.getOutputStream();
		out.write("user=abcd1234".getBytes());  //此方法将内容写到http请求体中
		
		//连接
		//conn.connect();
		//建立链接之后，获得web资源
		InputStream is = conn.getInputStream();  //会自动链接,只有调用方法，整个请求才生效
//		byte[] buf = new byte[1024];
//		int len = -1;
//		while( (len = is.read(buf)) > -1 ){
//			String str = new String(buf,0,len);
//			System.out.println(str);
//		}
		Scanner scanner = new Scanner(is);
		while(scanner.hasNext()){
			System.out.println("url -->" + scanner.nextLine());
		}
		is.close();
		
	}

}
