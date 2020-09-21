package org.coody.framework.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.coody.framework.core.logger.BaseLogger;

@SuppressWarnings("deprecation")
public class FileUtils {
	
	static BaseLogger logger=BaseLogger.getLogger(FileUtils.class);
	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param folderName 目录名
	 * @param fileName 文件名
	 * @param content 内容
	 */
	public static void writeFile(final String folderName,
			final String fileName, final String content) {
		if (content == null || content.isEmpty()) {
			return;
		}
		FileWriter writer = null;
		File file = null;
		try {
			file = new File(folderName);
			if (!file.exists()) {
				// 文件夹不存在，创建
				file.mkdir();
			}
			file = new File(folderName + "/" + fileName);
			if (!file.exists()) {
				// 文件夹不存在，创建
				file.createNewFile();
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(folderName + "/" + fileName, true);
			writer.write(content);
		} catch (IOException e) {
			PrintException.printException(logger, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				PrintException.printException(logger, e);
			}
		}

	}

	public static void write(String path, String context) {
		write(path, context, "utf-8");
	}


	public static void write(String path, String context, String encode) {
		OutputStream pt = null;
		try {
			pt = new FileOutputStream(URLDecoder.decode(path));
			pt.write(context.getBytes(encode));
		} catch (Exception e) {
			PrintException.printException(logger, e);
		} finally {
			try {
				pt.close();
			} catch (IOException e) {
				PrintException.printException(logger, e);
			}
		}
	}

	public static void writeAppend(String path, String context) {
		writeAppend(path, context, "utf-8");
	}

	public static void writeAppend(String path, String context, String encode) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(URLDecoder.decode(path), true)));
			out.write(context);
		} catch (Exception e) {
			PrintException.printException(logger, e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				PrintException.printException(logger, e);
			}
		}
	}

	public static void writeAppendLine(String path, String context) {
		writeAppendLine(path, context, "utf-8");
	}

	public static void writeAppendLine(String path, String context,
			String encode) {
		writeAppend(path, context + "\r\n", encode);
	}

	public static String readFile(String path) {
		return readFile(path, "utf-8");
	}

	public static String readFile(String path, String encode) {
		FileInputStream in = null;
		try {
			File file = new File(URLDecoder.decode(path));
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				in = new FileInputStream(file);
				return new String(input2byte(in),encode);
			}
		} catch (Exception e) {
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return null;
	}
	public static final byte[] input2byte(InputStream inStream) throws IOException  
	{  
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
		byte[] buff = new byte[100];  
		int rc = 0;  
		while ((rc = inStream.read(buff, 0, 100)) > 0) {  
			swapStream.write(buff, 0, rc);  
		}  
		byte[] in2b = swapStream.toByteArray();  
		return in2b;  
	}  
	@SuppressWarnings("resource")
	public static byte[] readFileByte(String path) {
		try {
			File file = new File(URLDecoder.decode(path));
			if(!file.exists()){
				return null;
			}
			long fileSize = file.length();
			if (fileSize > Integer.MAX_VALUE) {
				return null;
			}
			FileInputStream fi = new FileInputStream(file);
			byte[] buffer = new byte[(int) fileSize];
			int offset = 0;
			int numRead = 0;
			while (offset < buffer.length
					&& (numRead = fi.read(buffer, offset, buffer.length
							- offset)) >= 0) {
				offset += numRead;
			}
			// 确保所有数据均被读取
			if (offset != buffer.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			fi.close();
			return buffer;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		}
	}

	/**
	 * 
	 * 获取目录下所有文件
	 * 
	 * @param realpath 目录路径
	 * @return 文件列表
	 */
	public static List<File> getFiles(String realpath) {
		List<File> files = new ArrayList<File>();
		File realFile = new File(URLDecoder.decode(realpath));
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					files.addAll(getFiles(file.getAbsolutePath()));
					continue;
				}
				files.add(file);
			}
		}
		return files;
	}

	public static void rewrite(File file, String data) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
		} catch (IOException e) {
			PrintException.printException(logger, e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					PrintException.printException(logger, e);
				}
			}
		}
	}

	public static List<String> readList(File file) {
		BufferedReader br = null;
		List<String> data = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			for (String str = null; (str = br.readLine()) != null;) {
				data.add(str);
			}
		} catch (IOException e) {
			PrintException.printException(logger, e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					PrintException.printException(logger, e);
				}
			}
		}
		return data;
	}
	public static void main(String[] args) {
	}
	public static void writeFile(String path, byte[] content) {
		try {
			FileOutputStream fos = new FileOutputStream(path);

			fos.write(content);
			fos.close();
		} catch (Exception e) {
		}

	}
	
	public static void makeFileDir(String path){
		while (path.contains("\\")) {
			path = path.replace("\\", "/");
		}
		while (path.contains("//")) {
			path = path.replace("//", "/");
		}
		int lastTag=path.lastIndexOf('/');
		if(lastTag==-1){
			return;
		}
		path=path.substring(0,lastTag);
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
	}
}
