package com.jun.plugin.commons.util.apiext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.commons.util.exception.ExceptAll;
import com.jun.plugin.commons.util.exception.ProjectException;

/**
 * @ClassName: IOUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 周俊辉
 * @date 2010-11-13 上午11:01:04
 * 
 */
public abstract class IOUtil {
	public static Logger logger = LoggerFactory.getLogger(IOUtil.class);

	/**
	 * 转换输入流为字符串
	 * 
	 * @param in
	 *            输入流
	 * @return java.lang.String 转换后的字符串
	 * @throws IOException
	 */
	public static String slurp(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * 属性文件转为属性
	 * 
	 * @param filePath
	 *            文件路径
	 * @return Properties 属性对象
	 * @throws ProjectException
	 */
	public static Properties fileToProperties(String filePath) {
		Properties returnPro = new Properties();
		try {
			InputStream inputFile = IOUtil.class.getResourceAsStream(filePath);
			returnPro.load(inputFile);
		} catch (Exception e) {
			logger.error("读属性文件出错", e);
		}
		return returnPro;
	}

	/**
	 * 合并目录与文件名
	 * 
	 * @param folderPath
	 *            目录路径
	 * @param fileName
	 *            文件名
	 * @return String 合并后的文件路径
	 * */
	public static String mergeFolderAndFilePath(String folderPath,
			String fileName) {
		if (StringUtil.isNull(folderPath)) {
			return fileName;
		}
		if (StringUtil.isNull(fileName)) {
			return folderPath;
		}
		if (!folderPath.endsWith(File.separator) && !folderPath.endsWith("/")) {
			return folderPath + File.separator + fileName;
		} else {
			return folderPath + fileName;
		}
	}

	/***
	 * 得到指定Class下的文件的目录
	 * 
	 * @param classStr
	 * @param filePath
	 * @return
	 */
	public static String getDirForFilePath(
			@SuppressWarnings("rawtypes") Class classStr, String filePath) {
		URL url = classStr.getResource(filePath);
		int lastIndex = url.getPath().lastIndexOf("/");
		if (lastIndex > 0) {
			return url.getPath().substring(0, lastIndex);
		}
		return null;
	}

	/***
	 * 得到此项目下的文件目录路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getDirForCommonUtilFilePath(String filePath) {
		return getDirForFilePath(IOUtil.class, filePath);
	}

	/***
	 * 把InputStream复制到OutputStream
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws IOException
	 */
	public static long copyInToOut(InputStream from, OutputStream to)
			throws IOException {
		byte[] buf = new byte[1024];
		long total = 0;
		while (true) {
			int r = from.read(buf);
			if (r == -1) {
				break;
			}
			to.write(buf, 0, r);
			total += r;
		}
		return total;
	}

}
