package com.xiruibin.db.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 文件处理工具类
 * 
 * @author hxl
 * @version 2012-07-13
 */
public final class FileUtils {
	/**
	 * 获得一个File对象
	 * 
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		return new File(path);
	}

	/**
	 * 获得一个FileInputStream对象
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static FileInputStream getFileInputStream(String path)
			throws FileNotFoundException {
		return new FileInputStream(getFile(path));
	}
	
	/**
	 * 获得一个FileInputStream对象
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static FileInputStream getFileInputStream(File file)
			throws FileNotFoundException {
		return new FileInputStream(file);
	}
	
	/**
	 * 获得一个DataInputStream流对象
	 * @param is
	 * @return
	 */
	public static DataInputStream getDataInputStream(InputStream is){
		return new DataInputStream(is);
	}
	 
	/**
	 * 获得一个BufferedReader流对象
	 * @param inputStream
	 * @return
	 */
	public static BufferedReader getBufferedReader(InputStream inputStream){
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * 获得一个FileOutputStream对象
	 * 
	 * @param path
	 * @param append
	 *            true:向文件尾部追见数据;
	 *            false:清楚旧数据
	 * @return
	 * @throws FileNotFoundException
	 */
	public static FileOutputStream getFileOutputStream(String path,
			boolean append) throws FileNotFoundException {
		return new FileOutputStream(getFile(path), append);
	}
	
	/**
	 * 获得一个FileOutputStream对象
	 * 
	 * @param path
	 * @param append
	 *            true:向文件尾部追见数据;
	 *            false:清楚旧数据
	 * @return
	 * @throws FileNotFoundException
	 */
	public static FileOutputStream getFileOutputStream(File file,
			boolean append) throws FileNotFoundException {
		return new FileOutputStream(file, append);
	}
	
	/**
	 * 获得一个DataOutputStream流对象
	 * @param os
	 * @return
	 */
	public static DataOutputStream getDataOutputStream(OutputStream os){
		return new DataOutputStream(os);
	}
	
	/**
	 * 获得一个BufferedWriter流对象
	 * @param os
	 * @return
	 */
	public static BufferedWriter getBufferedWriter(OutputStream os){
		return new BufferedWriter(new OutputStreamWriter(os));
	}
}
