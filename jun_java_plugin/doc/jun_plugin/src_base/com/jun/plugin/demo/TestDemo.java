package com.jun.plugin.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TestDemo {
	public static void main(String[] args) throws Exception {
		InputStream in = new MyInputStream("d:/a/a.txt");
		byte[] b = new byte[1024];
		int len = 0;
		while((len=in.read(b))!=-1){
			String s = new String(b,0,len);
			System.err.print(s);
		}
		in.close();
	}
}
class MyInputStream extends InputStream{
	private InputStream in;
	public MyInputStream(String fileName) {
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public int read(byte[] b){
		int len=-1;
		try {
			len = in.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return len;
	}
	public void close(){
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int read() throws IOException {
		return 0;
	}
}
