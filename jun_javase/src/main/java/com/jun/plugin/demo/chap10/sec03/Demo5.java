package com.jun.plugin.demo.chap10.sec03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Demo5 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://�����ļ�.txt");
		OutputStream out=new FileOutputStream(file,true);
		String str="��ã��Һã���Һã�Java��2321312312";
		byte b[]=str.getBytes();
		out.write(b); //  ��b�ֽ�����д�뵽�����
		out.close();  // �ر������
	}
}
