package com.java1234.chap10.sec02;

import java.io.File;
import java.io.IOException;

public class Demo2 {

	public static void main(String[] args) throws IOException {
		File file=new File("c://java创建的目录//java创建的文件.txt");
		if(file.exists()){ // 判断文件是否存在
			boolean b=file.delete(); // 删除文件
			if(b){
				System.out.println("文件删除成功");
			}else{
				System.out.println("文件删除失败");
			}
			file=new File("c://java创建的目录");
			if(file.exists()){
				boolean b2=file.delete(); // 删除目录
				if(b2){
					System.out.println("目录删除成功");
				}else{
					System.out.println("目录删除失败");
				}
			}
		}
		
	}
}
