package com.jun.plugin.demo.chap10.sec04;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Demo3 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://�����ļ�.txt");
		Writer out=new FileWriter(file);
		String str="�Ұ��л�";
		out.write(str);  // ���ַ���д�������
		out.close();  // �ر������
	}
}
