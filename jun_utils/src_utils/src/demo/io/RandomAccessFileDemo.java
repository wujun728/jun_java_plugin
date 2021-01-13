package demo.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * RandomAccessFile直接继承Object，可以进行随机输入和输出，类似于c语言的文件操作
 * 要指明以什么方式打开文件，用这个类时要知道文件的排版，该类有读写基本类型和UTF-8字符串
 * 的各种方法，可以定位到文件的某一位置进行读写
 */
public class RandomAccessFileDemo {
	public static void main(String[] args) throws FileNotFoundException {
		RandomAccessFile out = new RandomAccessFile("file/test5", "rw");
		try {
			out.writeInt(1);
			out.writeDouble(3.3);
			out.writeChar('a');
			out.writeUTF("hello,java!");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RandomAccessFile in = new RandomAccessFile("file/test5", "r");
		try {
			in.seek(4);
			System.out.println(in.readDouble());
			in.seek(4+8+2);
			System.out.println(in.readUTF());
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
