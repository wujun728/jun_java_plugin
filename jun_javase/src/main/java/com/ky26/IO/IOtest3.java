package com.ky26.IO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOtest3 {
	public static void main(String[] args) throws Exception {
//		File file=new File("f:\\work.txt");
		FileWriter fw=new FileWriter("e:\\work.txt",true);
		fw.write("hello");
		fw.write("world");
		fw.write("java");
		fw.flush();
		fw.close();
		
		
		FileReader frr=new FileReader("e:\\work.txt");
		char[] chh=new char[1024];
		int len=0;
		FileWriter fww=new FileWriter("f:\\work1.txt",true);
		while((len=frr.read(chh))!=-1){
			fww.write(new String(chh,0,len));
//			System.out.println(new String(chh,0,len));
		}
		fww.close();
		
		
	}
}
