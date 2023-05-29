package com.jun.web.biz.common;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileBreakDown {
	public static void main(String[] args) throws Exception {
		String fileName = "title.jpg";
		String path = "http://192.168.31.254/webAuth/images/"+fileName;
		String savePath = "d:/"+fileName;
		File file = new File(savePath);
		long size = file.length();
		System.err.println(file.length());
		
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		//设置下载区间
		con.setRequestProperty("range","bytes="+size+"-");
		con.connect();
		int code = con.getResponseCode();//只要断点下载，返回的已经不是200�?06
		System.err.println(code);
		if(code==206||code==200){
			InputStream in= con.getInputStream();
			int serverSize = con.getContentLength();
			System.err.println("服务器返回的长度:"+serverSize);
			RandomAccessFile out = new RandomAccessFile(file,"rw");
			out.seek(size);
			
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			out.close();
		}
	}
}
