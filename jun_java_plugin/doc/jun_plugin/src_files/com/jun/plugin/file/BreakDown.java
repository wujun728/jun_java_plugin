package com.jun.plugin.file;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class BreakDown {
	public static void main(String[] args) throws Exception {
		String fileName = "video.avi";
		String path = "http://localhost:6666/website/up/"+fileName;
		String savePath = "d:/a/"+fileName;
		File file = new File(savePath);
		long size = file.length();
		System.err.println(file.length());
		
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		//设置下载区间
		con.setRequestProperty("range","bytes="+size+"-");
		con.connect();
		int code = con.getResponseCode();//只要断点下载，返回的已经不是200，206
		System.err.println(code);
		if(code==206){
			InputStream in= con.getInputStream();
			int serverSize = con.getContentLength();
			System.err.println("服务器返回的长度:"+serverSize);
			System.err.println("这次从哪开开始写:"+size);
			//必须要使用
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
