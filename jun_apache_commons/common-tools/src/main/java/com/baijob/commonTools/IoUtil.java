package com.baijob.commonTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

public class IoUtil {
	
	private static int ioBufferSize = 16;
	
	/**
	 * 将Reader中的内容复制到Writer中
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[ioBufferSize];
		int count = 0;
		int readSize;
		while ((readSize = input.read(buffer, 0, ioBufferSize)) >= 0) {
			output.write(buffer, 0, readSize);
			count += readSize;
		}
		output.flush();
		return count;
	}
	
	/**
	 * 从流中读取内容
	 * @param in	输入流
	 * @param charset	字符集
	 * @return	内容
	 * @throws IOException
	 */
	public static String getStringFromStream(InputStream in, String charset) throws IOException {
		StringBuilder content = new StringBuilder(); // 存储返回的内容

		// 从返回的内容中读取所需内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line = null;
		while ((line = reader.readLine()) != null) {
			content.append(line);
		}
		
		return content.toString();
	}
}
