package com.yc.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	private IOUtils() {
	}
	
	public static void In2Out(InputStream in,OutputStream out) throws IOException{
		byte [] bs = new byte[1024];
		int i = 0;
		while((i=in.read(bs))!=-1){
			out.write(bs,0,i);
		}
	}
	
	public static void close(InputStream in,OutputStream out){
		if(in!=null){
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				in = null;
			}
		}
		if(out!=null){
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				out = null;
			}
		}
	}
}
