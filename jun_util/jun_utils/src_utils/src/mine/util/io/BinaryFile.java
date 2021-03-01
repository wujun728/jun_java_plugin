package mine.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 此工具类用于二进制文件的读写
 * 
 * @author Touch
 */
public class BinaryFile {
	// 把二进制文件读入字节数组，如果没有内容，字节数组为null
	public static byte[] read(String filePath) {
		byte[] data = null;
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(filePath));
			try {
				data = new byte[in.available()];
				in.read(data);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	// 把字节数组为写入二进制文件，数组为null时直接返回
	public static void write(String filePath, byte[] data) {
		if (data == null)
			return;
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(filePath));
			try {
				out.write(data);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
