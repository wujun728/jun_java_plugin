package demo.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * FileInputStream从文件中产生输入流，FileOutputStream
 * 把输出流输出到文件。读/写、打开和关闭都是调用本地方法
 */
public class FileInputStreamDemo {
	public static void main(String[] args) throws IOException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("file/bb.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 读到一个字节数组
		byte[] result = new byte[500];
		// int b;
		// while ((b = inputStream.read()) != -1)//读一个字节
		// System.out.print((char) b);
		inputStream.read(result);
		// System.out.println(Arrays.toString(result));
		inputStream.close();
		FileOutputStream outputStream = null;
		// true表示以追加的形式打开
		outputStream = new FileOutputStream("file/bb.dat", true);
		// 写入
		outputStream.write((int) 'A');
		outputStream.write(result);
		outputStream.close();
	}
}
