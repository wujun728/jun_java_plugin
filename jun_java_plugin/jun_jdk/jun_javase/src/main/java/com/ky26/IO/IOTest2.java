package com.ky26.IO;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOTest2 {
	public static void main(String[] args) throws Exception {
		FileWriter fw=new FileWriter("d:\\test\\work.txt",true);
		fw.write("hello");
		fw.write("world");
		fw.write("java");
		fw.flush();
		fw.close();//写入
		
		FileReader fr=new FileReader("d:\\test\\work.txt");
		char[] ch=new char[1024];
		int len=0;
		while((len=fr.read(ch))!=-1){
			System.out.println(new String(ch,0,len));
		}//读取
		
		
		BufferedWriter bfw=new BufferedWriter(fw);
		bfw.newLine();
		bfw.write("shfskhjs");
		bfw.newLine();
		bfw.write("kgfljhklyj");
		bfw.flush();
		bfw.close();//带缓存的写入
	}
}
