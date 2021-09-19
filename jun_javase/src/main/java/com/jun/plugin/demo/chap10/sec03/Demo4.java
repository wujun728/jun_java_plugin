package com.jun.plugin.demo.chap10.sec03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Demo4 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://�����ļ�.txt");
		OutputStream out=new FileOutputStream(file);
		String str="��ã��Һã���Һã�Java��";
		byte b[]=str.getBytes();
		out.write(b); //  ��b�ֽ�����д�뵽�����
		out.close();  // �ر������
	}
}
