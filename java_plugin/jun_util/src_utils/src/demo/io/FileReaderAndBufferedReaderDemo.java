package demo.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Read和Write分别对应InputStream和OutputStream
 * 前者用于字符流，后者用于字节流
 * FileReader和FileWrite分别对应与FileInputStream和FileOutputStream
 * BufferedReader和BufferedWrite分别对应与BufferedInputStream和
 * BufferedOutputStream
 * 此示例实现文本文件的字符读写
 */
public class FileReaderAndBufferedReaderDemo {
	public static String read(String fileName) throws IOException {
		StringBuilder str = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		while ((s = in.readLine()) != null)
			str.append(s + '\n');
		in.close();
		return str.toString();
	}

	public static void write(String fileName, boolean append)
			throws IOException {
		BufferedWriter out = new BufferedWriter(
				new FileWriter(fileName, append));
		out.write("我是dahai!java hello!");
		out.close();
	}

	public static void main(String[] args) {
		try {
			write("file/test3.txt", false);
			System.out.println(read("file/test3.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
