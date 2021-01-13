package com.java1234.chap10.sec02;

import java.io.File;
import java.io.IOException;

public class Demo3 {

	public static void main(String[] args) throws IOException {
		File file=new File("C://apache-cxf-3.1.5");
		File files[]=file.listFiles(); // ±éÀúÄ¿Â¼
		for(int i=0;i<files.length;i++){
			System.out.println(files[i]);
		}
	}
}
