package com.jun.plugin.javase.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOTest {
	public static void main(String[] args) throws IOException {
		File file=new File("d:/test/work.txt");
		FileOutputStream out=new FileOutputStream(file);
		byte buy[]="����һͷСë¿.".getBytes();
		out.write(buy);
		out.close();//д��
		
		
		FileInputStream in=new FileInputStream(file);
		byte byt[]=new byte[1024];
		int len = in.read(byt);
		System.out.println("�ַ����ȣ�"+file.length());
		System.out.println(new String(byt,0,len));
		in.close();
		
	}
}
