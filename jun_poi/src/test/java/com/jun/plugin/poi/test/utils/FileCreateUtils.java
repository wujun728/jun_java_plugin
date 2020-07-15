package com.jun.plugin.poi.test.utils;

import java.io.File;
import java.io.IOException;
/**
 * 文件的创建删除类
 * @author bing
 *
 */
public class FileCreateUtils {
	public static File createFolderPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static File createchildFolder(String parent, String childName) {
		File file = new File(createFolderPath(parent), childName);
		file.mkdir();
		return file;
	}

	public static File createchildFolder(File parent, String childName) {
		File file = new File(parent, childName);
		file.mkdirs();
		return file;
	}

	public static File createFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		String fileName = file.getName();
		if (parent == null) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				
			}
			return file;
		}
		return createFile(parent, fileName);

	}

	public static boolean isExists(String Path) {
		File file = new File(Path);

		return file.exists();
	}

	public static boolean deleteFile(String fullPath) {
		File file = new File(fullPath);
		if (file.isFile()) {
			return file.delete();
		}
		return false;
	}

	public static File createFile(String parent, String fileName) {
		File file = new File(parent);
		return createFile(file, fileName);
	}

	public static File createFile(File parent, String fileName) {

		File file = new File(parent, fileName);

		try {

			if (!parent.exists()) {
				parent.mkdirs();
			}

			file.createNewFile();
		} catch (IOException e) {
			
			return null;
		}
		return file;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
