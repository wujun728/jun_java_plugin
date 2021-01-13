package com.java1234.chap10.sec02;

import java.io.File;
import java.io.IOException;

public class Demo4 {

	public static void listFile(File file){
		if(file!=null){
			if(file.isDirectory()){ // 是目录情况
				System.out.println(file); // 打印目录
				File files[]=file.listFiles(); // 遍历目录
				if(files!=null){
					for(int i=0;i<files.length;i++){
						listFile(files[i]); // 递归调用自身
					}
				}
			}else{ // 是文件情况
				System.out.println(file); // 是文件 直接打印文件的路径
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		File file=new File("C://apache-tomcat-7.0.63");
		listFile(file);
	}
}
