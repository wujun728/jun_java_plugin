package com.baijob.commonTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collection;

/**
 * 文件工具类
 * @author Luxiaolei
 *
 */
public class FileUtil {
	/**
	 * 创建文件，如果这个文件存在，直接返回这个文件
	 * @param fullFilePath 文件的全路径，使用POSIX风格
	 * @return 文件
	 * @throws IOException
	 */
	public static File touch(String fullFilePath) throws IOException {
		File file = new File(fullFilePath);
		file.getParentFile().mkdirs();
		if(!file.exists()) file.createNewFile();
		return file;
	}
	
	/**
	 * 创建文件夹，如果存在直接返回此文件夹
	 * @param dirPath 文件夹路径，使用POSIX格式，无论哪个平台
	 * @return 创建的目录
	 */
	public static File mkdir(String dirPath){
		File dir = new File(dirPath);
		dir.mkdirs();
		return dir;
	}
	
	/**
	 * 获取绝对路径<br/>
	 * 此方法不会判定给定路径是否有效（文件或目录存在）
	 * @param path 相对路径
	 * @param baseClass 相对路径所相对的类
	 * @return 绝对路径
	 */
	public static String getAbsolutePath(String path, Class<?> baseClass){
		if(path == null) return null;
		if(baseClass == null) {
			ClassLoader classLoader = FileUtil.class.getClassLoader();
			URL url = classLoader.getResource(path);
			if(url != null) {
				return url.getPath();
			}else {
				return classLoader.getResource("").getPath() + path;
			}
		}
		return baseClass.getResource(path).getPath();
	}
	
	/**
	 * 获取绝对路径，相对于classes的根目录
	 * @param pathBaseClassLoader 相对路径
	 * @return 绝对路径
	 */
	public static String getAbsolutePath(String pathBaseClassLoader){
		return getAbsolutePath(pathBaseClassLoader, null);
	}

	/**
	 * 文件是否存在
	 * @param path 文件路径
	 * @return 是否存在
	 */
	public static boolean isExist(String path){
		return  new File(path).exists();
	}
	
	/**
	 * 关闭
	 * @param closeable 被关闭的对象
	 */
	public static void close(Closeable closeable){
		if(closeable == null) return;
		try {
			closeable.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 获得一个带缓存的写入对象
	 * @param path 输出路径，绝对路径
	 * @param charset 字符集
	 * @param isAppend 是否追加
	 * @return BufferedReader对象
	 * @throws IOException
	 */
	public static BufferedWriter getBufferedWriter(String path, String charset, boolean isAppend) throws IOException {
		return new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(touch(path), isAppend), charset
					)
		);
	}
	
	/**
	 * 获得一个打印写入对象，可以有print
	 * @param path 输出路径，绝对路径
	 * @param charset 字符集
	 * @param isAppend 是否追加
	 * @return 打印对象
	 * @throws IOException
	 */
	public static PrintWriter getPrintWriter(String path, String charset, boolean isAppend) throws IOException {
		return new PrintWriter(getBufferedWriter(path, charset, isAppend));
	}

	/**
	 * 获得一个输出流对象
	 * @param path 输出到的文件路径，绝对路径
	 * @return 输出流对象
	 * @throws IOException
	 */
	public static OutputStream getOutputStream(String path) throws IOException {
		return new FileOutputStream(touch(path));
	}
	
	/**
	 * 清空一个目录
	 * @param dirPath 需要删除的文件夹路径
	 */
	public static void cleanDir(String dirPath){
		File dir = new File(dirPath);
		if(dir.exists() && dir.isDirectory()){
			File[] files = dir.listFiles();
			for (File file : files) {
				if(file.isDirectory()) cleanDir(file.getAbsolutePath());
				file.delete();
			}
		}
	}
	
	/**
	 * 获得一个文件读取器
	 * @param path 绝对路径
	 * @param charset 字符集
	 * @return BufferedReader对象
	 * @throws IOException
	 */
	public static BufferedReader getReader(String path, String charset) throws IOException{
		return new BufferedReader(new InputStreamReader(new FileInputStream(path), charset));
	}
	
	/**
	 * 从文件中读取每一行数据
	 * @param path	文件路径
	 * @param charset	字符集
	 * @param collection	集合
	 * @return	文件中的每行内容的集合
	 * @throws IOException
	 */
	public static <T extends Collection<String>> T loadFileLines(String path, String charset, T collection) throws IOException{
		BufferedReader reader = getReader(path, charset);
		while(true){
			String line = reader.readLine();
			if(line == null) break;
			collection.add(line);
		}
		close(reader);
		return collection;
	}
	
	/**
	 * 按照给定的readerHandler读取文件中的数据
	 * @param readerHandler Reader处理类
	 * @param path 文件的绝对路径
	 * @param charset 字符集
	 * @return 从文件中load出的数据
	 * @throws IOException
	 */
	public static <T> T loadDataFromfile(ReaderHandler<T> readerHandler, String path, String charset) throws IOException {
		BufferedReader reader = null;
		T result = null;
		try {
			reader = getReader(path, charset);
			result = readerHandler.handle(reader);
		} catch (IOException e) {
			throw new IOException(e);
		}finally {
			FileUtil.close(reader);
		}
		return result;
	}
	
	/**
	 * 获得文件的扩展名
	 * @param fileName 文件名
	 * @return 扩展名
	 */
	public static String getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return "";
		} else {
			String ext = fileName.substring(index + 1);
			//扩展名中不能包含路径相关的符号
			return (ext.contains("/") || ext.contains("\\")) ? "" : ext;
		}
	}
	
	/**
	 * Reader处理接口
	 * @author Luxiaolei
	 *
	 * @param <T>
	 */
	public interface ReaderHandler<T> {
		public T handle(BufferedReader reader) throws IOException;
	}
}
