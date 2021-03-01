package book.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
/**
 * 从标准输入流中读取数据，并存储到文件中
 */
public class CatStdinToFile {

	public static boolean catStdinToFile(String fileName){
		File file = new File(fileName);
		//将数据按照文本输出到文件
		PrintWriter writer = null;
		BufferedReader in = null;
        try {
        	//为输出文件建立一个写入器
        	writer = new PrintWriter(new FileWriter(file));
        	System.out.println("请输入文件内容，输入quit结束");
        	//用BufferedReader包装标准输入流
            in = new BufferedReader(new InputStreamReader(System.in));
            String inputLine = null;
            while (((inputLine = in.readLine( )) != null) && (!inputLine.equals("quit"))) {
            	writer.println(inputLine);
            }
            //如果采用new PrintWriter(new FileWriter(file), true)则会自动flush。
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
        	if (in != null){
        		try {
        			in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}

	public static void main(String[] args) {
		String fileName = "C:/temp/temp.java";
		CatStdinToFile.catStdinToFile(fileName);
		System.out.println();
		System.out.println("输出文件的内容：");
		ReadFromFile.readFileByLines(fileName);
	}
}
