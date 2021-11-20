package com.jun.plugin.file;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
public class CommonDown {
	public static void main(String[] args) throws Exception {
		String path = "http://localhost:6666/day22_cos/up/video.avi";
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoInput(true);
		con.connect();
		int code = con.getResponseCode();
		System.err.println(code);
		if (code == 200) {
			//获取文件大小
			long size = con.getContentLength();
			System.err.println("总大小是:"+size);
			//声明下载到的字节
			long sum=0;
			BigDecimal bd = new BigDecimal(0D);
			double already = 0D;
			InputStream in = con.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			OutputStream out = new FileOutputStream("d:/a/video.avi");
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
				sum=sum+len;
				double percent = ((double)sum)/((double)size);
				percent*=100;
				bd = new BigDecimal(percent);
				bd = bd.divide(new BigDecimal(1),0,BigDecimal.ROUND_HALF_UP);
				if(bd.doubleValue()!=already){
					System.err.println(bd.intValue()+"%");
					already=bd.doubleValue();
				}
			}
			out.close();
		}
	}
}
