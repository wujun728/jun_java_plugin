package demo.others;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Formatter;

/**
 * Formatter类用于格式化
 * 
 * @author Touch
 * 
 */
public class FormatterDemo {
	public static void main(String[] args) {
		int i = 1;
		double d = 2.2352353456345;
		// 1.两种最简单的格式化输出,类似c语言中的printf函数
		System.out.format("%-3d%-5.3f\n", i, d);
		System.out.printf("%-3d%-5.3f\n", i, d);
		// Formatter类的使用
		// 2.格式化输出到控制台
		Formatter f = new Formatter(System.out);
		f.format("%-3d%-8.2f%-10s\n", i, d, "touch");
		// 3.格式化输出到文件
		Formatter ff = null;
		try {
			ff = new Formatter(new PrintStream("file/formater.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ff.format("%-3d%-8.2f%-10s\n", i, d, "touch");
		// 4.String.format().同c语言中sprintf()
		System.out.println(String.format("(%d%.2f%s)", i, d, "touch"));
	}
}
