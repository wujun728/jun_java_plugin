package com.jun.plugin.demo.chap10.sec02;

import java.io.File;
import java.io.IOException;

public class Demo1 {

	public static void main(String[] args) throws IOException {
		File file=new File("d://java������Ŀ¼");
		boolean b=file.mkdir();  // ��������Ŀ¼
		if(b){
			System.out.println("Ŀ¼�����ɹ���");
			file=new File("d://java������Ŀ¼//java�������ļ�.txt");
			boolean b2=file.createNewFile();  // �����ļ�
			if(b2){
				System.out.println("�ļ������ɹ���");
			}else{
				System.out.println("�ļ�����ʧ�ܣ�");
			}
		}else{
			System.out.println("Ŀ¼����ʧ�ܣ�");
		}
	}
}
