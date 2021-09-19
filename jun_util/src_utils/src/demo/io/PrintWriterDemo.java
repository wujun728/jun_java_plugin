package demo.io;


import java.io.IOException;
import java.io.PrintWriter;

/*
 * 对应于PrintStream
 * 用于格式化输出到文件
 */
public class PrintWriterDemo {
	public static void main(String[] args) throws IOException {
		// 简化的创建方式
		PrintWriter out = new PrintWriter("file/test4.txt");
		// 也可以这样创建： out=new Printer(new BufferedWriter(new
		// FileWriter("file/test4.txt")));
		// 格式化输出到文本
		out.println('a');
		out.println(3);
		out.println(3.5);
		out.print("我爱你！i love you");
		out.close();
		// 从文本读取刚才的写入
		System.out.println(FileReaderAndBufferedReaderDemo
				.read("file/test4.txt"));
	}

}
