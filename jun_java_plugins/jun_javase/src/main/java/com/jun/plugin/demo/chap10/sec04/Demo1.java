package com.jun.plugin.demo.chap10.sec04;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class Demo1 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://�����ļ�.txt");
		Reader reader=new FileReader(file);
		char c[]=new char[1024]; // �ַ�����
		int len=reader.read(c);
		reader.close();  // �ر�������
		System.out.println("��ȡ�������ǣ�"+new String(c,0,len));
	}
}
