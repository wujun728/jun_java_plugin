package com.jun.plugin.demo.chap10.sec02;

import java.io.File;

public class Demo4 {

	/**
	 * ��ӡ�ļ�
	 * @param file
	 */
	public static void listFile(File file){
		if(file!=null){
			if(file.isDirectory()){  // ��Ŀ¼
				File f[]=file.listFiles();  // ����Ŀ¼
				if(f!=null){
					for(int i=0;i<f.length;i++){
						listFile(f[i]);  // �ݹ����
					}
				}
			}else{   // ���ļ�
				System.out.println(file);  // ���ļ���ֱ�Ӵ�ӡ�ļ���·��
			}
		}
	}
	
	public static void main(String[] args) {
		File file=new File("D://eclipse_j2ee");
		listFile(file);
	}
}
