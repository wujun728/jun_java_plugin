package com.dcf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 
 * <h1>FileUtil文件操作工具类</h1><br>
 * <p>提供File处理的相关方法</p>
 *
 */
public class FileUtil {
	/**
	 * 此方法将file文件转换成文件流的方式，使用时要注意InputStream的释放
	 * @param file 传入的文件
	 * @return InputStream  返回流
	 * @throws FileNotFoundException 抛出文件找不到的异常
	 */
	public  InputStream fileToInputStream(File file) throws FileNotFoundException{
		InputStream is=new FileInputStream(file);
		return is;
	}
	/**
	 * 将文件转换成二进制数组
	 * @param file 要转换的文件
	 * @return byte[] 返回的二进制数组
	 * @throws IOException 抛出IO读写异常
	 */
	public  byte[] fileToByteArr(File file) throws IOException{
		InputStream is=new FileInputStream(file);
		byte[]  buffer=new byte[is.available()];
		is.read(buffer);
		releaseInputStream(is);
		return buffer;
	}
	/***
	 * 将二进制数组写入到指定文件中
	 * @param arr 二进制byte数组
	 * @param file 要写入的目标文件
	 * @return 返回指定文件
	 * @throws IOException 抛出IO异常
	 */
	public File  byteArrToFile(byte[] arr ,File file) throws IOException{
		OutputStream os=new FileOutputStream(file);
		os.write(arr);
		releaseOutputStream(os);
		return  file;
	}
	/**
	 * 将二进制流写进指定的文件中
	 * @param is 二进制流
	 * @param file 指定的文件
	 * @return 返回指定的文件
	 * @throws IOException 抛出IO异常
	 */
	public File inputStreamToFile(InputStream is,File file) throws IOException{
		if(is!=null){
			byte[] buffer=new byte[is.available()]; 
			OutputStream os=new FileOutputStream(file);
			os.write(buffer);
			releaseOutputStream(os);
		}
		return file;
	}
	/**
	 * 释放InputStream资源
	 * @param is 要释放的InputStream
	 * @throws IOException 抛出IO异常
	 */
	public void releaseInputStream(InputStream is) throws IOException{
		if(is!=null){
			is.close();
		}
	}
	/**
	 * 释放OutputStream资源
	 * @param os 要释放的Outputstream
	 * @throws IOException 抛出IO异常
	 */
	public void releaseOutputStream(OutputStream os) throws IOException{
		if(os!=null){
			os.close();
		}
	}
}
