package demo.io;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/*
 * 继承自FilterOutputStream，其中DataOutputStream处理数据的存储，PrintStream处理显示
 * 用于格式化打印
 */
public class PrintStreamDemo {
	public static void main(String[] args) throws FileNotFoundException {
		// 把数据可视化格式显示到文本文件中
		PrintStream printStream = new PrintStream("file/test2.txt");
		printStream.println('a');
		printStream.println(2);
		printStream.println(3.2);
		printStream.println("liuhaifang");
		printStream.println("刘海房");
		// 可视化显示到控制台
		printStream = new PrintStream(System.out);
		printStream.println("hello  java");
	}
}
