package org.typroject.tyboot.core.foundation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;


/**
 * 
 * <pre>
 *  Tyrest
 *  File: FileUtill.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:文件操作工具类
 *  TODO
 * 
 *  Notes:
 *  $Id: FileUtill.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class FileUtill {


	/**
	 * 返回某目录下所有文件对象
	 * 
	 * @param str
	 * @return
	 */
	public static File[] getFiles(String str) {
		File dir = new File(StringUtil.utf8Decoding(str));
		File[] result = null;
		if (dir.isDirectory()) {
			result = dir.listFiles();
		}

		return result;
	}

	/**
	 * 返回某个类所在包最顶层文件夹
	 * 
	 * @param clazz
	 *            类
	 * @return 顶层文件夹路径
	 */
	public static String getTopClassPath(Class<?> clazz) {
		String path = StringUtil.utf8Decoding(clazz.getResource("/").getPath());
		return path;
	}

	public static void main(String[] args) {
		System.out.println(getCurrPath(FileUtill.class));
	}

	/**
	 * get the jars path
	 * 
	 * @return
	 */
	public static String getJarPath() {
		return FileUtill.getParent(FileUtill.getTopClassPath(FileUtill.class), 1) + File.separator + "lib";
	}

	public static String getClassPath(String folderName) {
		return getJarPath().replace("lib", folderName);
	}

	/**
	 * 获得类所在文件路径
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getCurrPath(Class<?> clazz) {
		return StringUtil.utf8Decoding(clazz.getResource("/").getPath() + clazz.getName().replace(".", File.separator));
	}

	/**
	 * 创建一个文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createDir(String path) {
		boolean flag = false;
		File file = new File(StringUtil.utf8Decoding(path));
		if (!file.exists()) {
			if (!file.isDirectory()) {
				flag = file.mkdir();
			}
		}
		return flag;
	}

	/**
	 * 创建一个文件
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @throws IOException
	 */
	public static boolean createFile(String path) throws IOException {
		return createFile(path, false);
	}

	/**
	 * 是否强制新建文件
	 * 
	 * @see 1.原文件存在的情况下会删除原来的文件，重新创建一个新的同名文件，本方法返回文件创建成功标记
	 * @see 2.原文件存在但isDelete参数设置为false，表示不删除源文件，本方法返回文件创建失败标记
	 * @param path
	 *            文件路径
	 * @param isDelete
	 *            文件存在后是否删除标记
	 * @return 文件创建是否成功标记
	 * @throws IOException
	 */
	public static boolean createFile(String path, boolean isDelete) throws IOException {
		// 加载文件
		File file = new File(StringUtil.utf8Decoding(path));
		// 文件是否创建成功
		boolean flag = true;
		// 判断文件是否存在
		if (file.exists()) {
			if (isDelete) { // 文件存在后删除文件
							// 删除原文件
				file.delete();
				// 创建新文件
				file.createNewFile();
			} else {
				flag = false;
			}
		} else {
			file.createNewFile();
		}

		return flag;
	}

	/**
	 * 将oldFile移动到指定目录
	 * 
	 * @param oldFile
	 * @param newDir
	 * @return
	 */
	public static boolean moveFileTo(File oldFile, String newDir) {
		StringBuilder sb = new StringBuilder(newDir);
		sb.append(File.separator).append(oldFile.getName());
		File toDir = new File(StringUtil.utf8Decoding(sb.toString()));
		boolean flag = false;
		if (!toDir.exists()) {
			flag = oldFile.renameTo(toDir);
		}
		return flag;
	}

	/**
	 * 不使用renameTo,如果文件(isFile)不存在则不复制.
	 *
	 * @param sourceFile
	 * @param target
	 * @throws Exception
	 */
	public static void moveFile(File sourceFile, String target) throws Exception {
		if (!sourceFile.exists() || !sourceFile.isFile()) {
			return;
		}
		InputStream inputStream = null;
		File targetFile = new File(target + File.separator + sourceFile.getName());
		OutputStream outputStream = null;
		inputStream = new FileInputStream(sourceFile);
		outputStream = new FileOutputStream(targetFile);
		int readBytes = 0;
		byte[] buffer = new byte[10000];
		while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
			outputStream.write(buffer, 0, readBytes);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}

	/**
	 * 返回当前文件的上层文件夹路径（第几层由参数floor决定）
	 * 
	 * @param f
	 * @param floor
	 * @return
	 */
	public static String getParent(File f, int floor) {
		String result = "";
		if (f != null && f.exists()) {
			for (int i = 0; i < floor; ++i) {
				f = f.getParentFile();
			}

			if (f != null && f.exists()) {
				result = f.getPath();
			}
		}

		return StringUtil.utf8Decoding(result) + File.separator;
	}

	public static String getParent(String path, int floor) {
		return getParent(new File(path), floor);
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		boolean flag = false;
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					deleteFile(f);
				}
			}
			flag = file.delete();
		}

		return flag;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查文件名是否合法
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255)
			return false;
		else {
			return fileName.matches(
					"[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dst);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return;
	}

	/**
	 * 取指定文件的扩展名
	 * 
	 * @param filePathName
	 *            文件路径
	 * @return 扩展名
	 */
	public static String getFileExt(String filePathName) {
		int pos = 0;
		pos = filePathName.lastIndexOf('.');
		if (pos != -1)
			return filePathName.substring(pos + 1, filePathName.length());
		else
			return "";

	}

	/**
	 * 去掉文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}

	/**
	 * 读取文件大小
	 * 
	 * @param filename
	 *            指定文件路径
	 * @return 文件大小
	 */
	public static int getFileSize(String filename) {
		try {
			File fl = new File(filename);
			int length = (int) fl.length();
			return length;
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 判断是否是图片
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isImage(File file) {
		boolean flag = false;
		try {
			ImageInputStream is = ImageIO.createImageInputStream(file);
			if (null == is) {
				return flag;
			}
			is.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 * TODO. 读取文件内容
	 *
	 * @param file
	 * @param fullFilePath
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static String readFileContent(File file, String fullFilePath) throws IOException {
		String returnStr = "";
		if (ValidationUtil.isEmpty(file) && ValidationUtil.isEmpty(fullFilePath)) {
			return "";
		}
		if (ValidationUtil.isEmpty(file)) {
			file = new File(fullFilePath);
		}
		FileInputStream in = null;

		try {
			in = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = in.read(buf)) > 0) {
				returnStr += new String(buf, "utf-8");
				buf = new byte[1024];
			}
		} catch (FileNotFoundException e) {
			//logger.error(e.getMessage() + ";" + file.getPath(), e);
			throw e;
		} catch (IOException e) {
			//logger.error(e.getMessage() + ";" + file.getPath(), e);
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return returnStr;
	}

	public static boolean writeToFile(String content, File file, String fullFilePath) throws IOException {
		if ((ValidationUtil.isEmpty(file) && ValidationUtil.isEmpty(fullFilePath)) || ValidationUtil.isEmpty(content)) {
			return false;
		}
		if (ValidationUtil.isEmpty(file)) {
			file = new File(fullFilePath);
		}
		FileOutputStream out = null;

		try {

			out = new FileOutputStream(file);

			out.write(content.getBytes("utf-8"));
		} catch (FileNotFoundException e) {
			//logger.error(e.getMessage() + ";" + file.getPath(), e);
			throw e;
		} catch (IOException e) {
			//logger.error(e.getMessage() + ";" + file.getPath(), e);
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

}

/*
 * $Log: av-env.bat,v $
 */