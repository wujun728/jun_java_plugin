package com.java1234.chap10.sec02;

import java.io.File;
import java.io.IOException;

public class Demo2 {

	public static void main(String[] args) throws IOException {
		File file=new File("d://java创建的目录//java创建的文件.txt");
		if(file.exists()){  // 假如文件存在
			boolean b=file.delete();  // 删除文件
			if(b){
				System.out.println("删除文件成功！");
			}else{
				System.out.println("删除文件失败！");
			}
		}
		file=new File("d://java创建的目录");
		if(file.exists()){
			boolean b=file.delete();  // 删除目录
			if(b){
				System.out.println("删除目录成功！");
			}else{
				System.out.println("删除目录失败！");
			}
		}
	}
}
