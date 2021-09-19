package com.jun.plugin.demo.chap10.sec04;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class Demo2 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://�����ļ�.txt");
		Reader reader=new FileReader(file);
		char c[]=new char[1024]; // �ַ�����
		int temp=0;
		int len=0;
		while((temp=reader.read())!=-1){
			c[len++]=(char)temp;
		}
		reader.close();  // �ر�������
		System.out.println("��ȡ�������ǣ�"+new String(c,0,len));
	}
}
