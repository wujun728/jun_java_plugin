package com.java1234.chap10.sec02;

import java.io.File;

public class Demo3 {

	public static void main(String[] args) {
		File file=new File("D://SQLYogEnterprise//SQLyog_Enterprise");
		File files[]=file.listFiles();  // ±éÀúÄ¿Â¼
		for(int i=0;i<files.length;i++){
			System.out.println(files[i]);
		}
	}
}
