package demo.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
/*
 * ByteArrayInputStream（ByteArrayOutputStream）表示从字节数组产生输入（输出）
 * 这个类其实就是对一个字节数组进行操作，把这个字节数组看成一个缓冲区
 * 关闭方法是一个空方法，关闭后不影响其他方法
 * 可以将数组定位到指定位置开始读/写，可以将数组从头开始读/写，可以查看数组还有几个字节可用
 * 可以在某个位置做标记，可以返回到标记位置进行读/写
 */
public class ByteArrayInputStreamDemo {
	public static void main(String[] args) {
		// 输入流缓冲区（假设已经有若干字节）
		byte[] inputBuff = new byte[] { 1, 2, 3, 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] result = new byte[20];
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBuff);
		// 将缓冲区的字节读入result数组并输出result
		inputStream.read(result, 0, 20);
		System.out.println(Arrays.toString(result));
		// 将result数组写入输出流
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		outStream.write(result, 0, 20);
		System.out.println(Arrays.toString(outStream.toByteArray()));
	}

}
