package demo.io;


import java.io.IOException;
import java.io.StringReader;
/*
 * StringReader²Ù×÷µÄÊÇ×Ö·û´®
 */
public class StringReaderDemo {
	public static void main(String[] args) throws IOException {
		StringReader in = new StringReader(FileReaderAndBufferedReaderDemo
				.read("file/test3.txt"));
		int c;
		while ((c = in.read()) != -1)
			System.out.print((char) c);
	}
}
