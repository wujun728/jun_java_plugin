package com.jun.plugin.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;


import sun.misc.BASE64Encoder;

/**
 * 
 * 功能描述�?
 * 
 * @author Administrator
 * @Date Jul 19, 2008
 * @Time 9:46:11 AM
 * @version 1.0
 */
public class FileUtil {


	private static final Logger logger = Logger.getLogger(FileUtil.class);

	private static final int BUFFER = 1024;
	
	/**
	 * 功 能: 拷贝文件(只能拷贝文件)
	 * 
	 * @param strSourceFileName
	 *            指定的文件全路径名
	 * @param strDestDir
	 *            拷贝到指定的文件夹
	 * @return 如果成功true;否则false
	 */
	public static boolean copyTo(String strSourceFileName, String strDestDir) {
		File fileSource = new File(strSourceFileName);
		File fileDest = new File(strDestDir);

		// 如果源文件不存或源文件是文件夹
		if (!fileSource.exists() || !fileSource.isFile()) {
			logger.debug("源文件[" + strSourceFileName + "],不存在或是文件夹!");
			return false;
		}

		// 如果目标文件夹不存在
		if (!fileDest.isDirectory() || !fileDest.exists()) {
			if (!fileDest.mkdirs()) {
				logger.debug("目录文件夹不存，在创建目标文件夹时失败!");
				return false;
			}
		}

		try {
			String strAbsFilename = strDestDir + File.separator + fileSource.getName();

			FileInputStream fileInput = new FileInputStream(strSourceFileName);
			FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);

			logger.debug("开始拷贝文件:");

			int count = -1;

			long nWriteSize = 0;
			long nFileSize = fileSource.length();

			byte[] data = new byte[BUFFER];

			while (-1 != (count = fileInput.read(data, 0, BUFFER))) {

				fileOutput.write(data, 0, count);

				nWriteSize += count;

				long size = (nWriteSize * 100) / nFileSize;
				long t = nWriteSize;

				String msg = null;

				if (size <= 100 && size >= 0) {
					msg = "\r拷贝文件进度:   " + size + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				} else if (size > 100) {
					msg = "\r拷贝文件进度:   " + 100 + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				}

			}

			fileInput.close();
			fileOutput.close();

			logger.debug("拷贝文件成功!");
			return true;

		} catch (Exception e) {
			logger.debug("异常信息：[");
			logger.error(e);
			logger.debug("]");
			return false;
		}
	}

	/**
	 * 删除指定的文件
	 * 
	 * @param strFileName
	 *            指定绝对路径的文件名
	 * @return 如果删除成功true否则false
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			logger.debug("错误: " + strFileName + "不存在!");
			return false;
		}

		return fileDelete.delete();
	}

	/**
	 * 移动文件(只能移动文件)
	 * 
	 * @param strSourceFileName
	 *            是指定的文件全路径名
	 * @param strDestDir
	 *            移动到指定的文件夹中
	 * @return 如果成功true; 否则false
	 */
	public boolean moveFile(String strSourceFileName, String strDestDir) {
		if (copyTo(strSourceFileName, strDestDir))
			return this.delete(strSourceFileName);
		else
			return false;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param strDir
	 *            要创建的文件夹名称
	 * @return 如果成功true;否则false
	 */
	public boolean makedir(String strDir) {
		File fileNew = new File(strDir);

		if (!fileNew.exists()) {
			logger.debug("文件夹不存在--创建文件夹");
			return fileNew.mkdirs();
		} else {
			logger.debug("文件夹存在");
			return true;
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param strDir
	 *            要删除的文件夹名称
	 * @return 如果成功true;否则false
	 */
	public boolean rmdir(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();

			for (int i = 0; i < fileList.length; i++) {
				String subFile = strDir + File.separator + fileList[i];
				File tmp = new File(subFile);
				if (tmp.isFile())
					tmp.delete();
				else if (tmp.isDirectory())
					rmdir(subFile);
				else {
					logger.debug("error!");
				}
			}
			rmDir.delete();
		} else
			return false;
		return true;
	}
	
	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-7-9修改日期
	* 修改内容
	* @Title: downFile 
	* @Description: TODO:下载文件
	* @param @param path
	* @param @param response
	* @param @param allPath
	* @param @throws FileNotFoundException
	* @param @throws IOException
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public static void downFile(String path, HttpServletResponse response, String allPath )
			throws FileNotFoundException, IOException, UnsupportedEncodingException
	{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		File uploadFile = new File(allPath);
		fis = new FileInputStream(uploadFile);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(path, "utf-8"));
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		bos.flush();
		fis.close();
		bis.close();
		fos.close();
		bos.close();
	}
	
	
	
	
	
	
	
	
	
//	private static final Logger logger = Logger.getLogger(FileUtil.class);

//	private final static int BUFFER = 1024;

	/**
	 * 功 能: 移动文件(只能移动文件) 参 数: strSourceFileName:指定的文件全路径名 strDestDir: 移动到指定的文件夹 返回值: 如果成功true;否则false
	 * 
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean copyTo2(String strSourceFileName, String strDestDir) {
		File fileSource = new File(strSourceFileName);
		File fileDest = new File(strDestDir);

		// 如果源文件不存或源文件是文件夹
		if (!fileSource.exists() || !fileSource.isFile()) {
			logger.debug("源文件[" + strSourceFileName + "],不存在或是文件夹!");
			return false;
		}

		// 如果目标文件夹不存在
		if (!fileDest.isDirectory() || !fileDest.exists()) {
			if (!fileDest.mkdirs()) {
				logger.debug("目录文件夹不存，在创建目标文件夹时失败!");
				return false;
			}
		}

		try {
			String strAbsFilename = strDestDir + File.separator + fileSource.getName();

			FileInputStream fileInput = new FileInputStream(strSourceFileName);
			FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);

			logger.debug("开始拷贝文件");

			int count = -1;

			long nWriteSize = 0;
			long nFileSize = fileSource.length();

			byte[] data = new byte[BUFFER];

			while (-1 != (count = fileInput.read(data, 0, BUFFER))) {

				fileOutput.write(data, 0, count);

				nWriteSize += count;

				long size = (nWriteSize * 100) / nFileSize;
				long t = nWriteSize;

				String msg = null;

				if (size <= 100 && size >= 0) {
					msg = "\r拷贝文件进度:   " + size + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				} else if (size > 100) {
					msg = "\r拷贝文件进度:   " + 100 + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				}

			}

			fileInput.close();
			fileOutput.close();

			logger.debug("拷贝文件成功!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功 能: 删除指定的文件 参 数: 指定绝对路径的文件名 strFileName 返回值: 如果删除成功true否则false;
	 * 
	 * @param strFileName
	 * @return
	 */
	public static boolean delete2(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			logger.debug(strFileName + "不存在!");
			return false;
		}

		return fileDelete.delete();
	}

	/**
	 * 功 能: 移动文件(只能移动文件) 参 数: strSourceFileName: 是指定的文件全路径名 strDestDir: 移动到指定的文件夹中 返回值: 如果成功true; 否则false
	 * 
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean moveFile2(String strSourceFileName, String strDestDir) {
		if (copyTo2(strSourceFileName, strDestDir))
			return delete2(strSourceFileName);
		else
			return false;
	}

	/**
	 * 功 能: 创建文件夹 参 数: strDir 要创建的文件夹名称 返回值: 如果成功true;否则false
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean makeDir(String strDir) {
		File fileNew = new File(strDir);

		if (!fileNew.exists()) {
			return fileNew.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * 功 能: 删除文件夹 参 数: strDir 要删除的文件夹名称 返回值: 如果成功true;否则false
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean removeDir(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();

			for (int i = 0; i < fileList.length; i++) {
				String subFile = strDir + File.separator + fileList[i];
				File tmp = new File(subFile);
				if (tmp.isFile())
					tmp.delete();
				else if (tmp.isDirectory())
					removeDir(subFile);
			}
			rmDir.delete();
		} else {
			return false;
		}
		return true;
	}
	//****************************************************************************************************
	//****************************************************************************************************

	
	// 文件名转码
	public static String encodeDownloadFileName(String fileName, String agent)
			throws IOException {
		String codedfilename = null;
		if (agent != null) {
			agent = agent.toLowerCase();
		}
		if (null != agent && -1 != agent.indexOf("msie")) {
			String prefix = fileName.lastIndexOf(".") != -1 ? fileName
					.substring(0, fileName.lastIndexOf(".")) : fileName;
			String extension = fileName.lastIndexOf(".") != -1 ? fileName
					.substring(fileName.lastIndexOf(".")) : "";
			String name = prefix;
			int limit = 150 - extension.length();
			if (name.getBytes().length != name.length()) {// zn
				if (getEncodingByteLen(name) >= limit) {
					name = subStr(name, limit);
				}
			} else {// en
				limit = prefix.length() > limit ? limit : prefix.length();
				name = name.substring(0, limit);
			}
			name = URLEncoder.encode(name + extension, "UTF-8").replace('+',
					' ');
			codedfilename = name;
		} else if (null != agent && -1 != agent.indexOf("firefox")) {
			codedfilename = "=?UTF-8?B?"
					+ (new String(Base64.encodeBase64(fileName
							.getBytes("UTF-8")))) + "?=";
		} else if (null != agent && -1 != agent.indexOf("safari")) {
			codedfilename = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else if (null != agent && -1 != agent.indexOf("applewebkit")) {
			codedfilename = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else {
			codedfilename = URLEncoder.encode(fileName, "UTF-8").replace('+',
					' ');
		}
		return codedfilename;
	}
	
	
	private static int getEncodingByteLen(String sub) {
		int zhLen = (sub.getBytes().length - sub.length()) * 2;
		int enLen = sub.length() * 2 - sub.getBytes().length;
		return zhLen + enLen;
	}
	
	// 限制名字的长度
	private static String subStr(String str, int limit) {
		String result = str.substring(0, 17);
		int subLen = 17;
		for (int i = 0; i < limit; i++) {
			if (limit < getEncodingByteLen(str.substring(0, (subLen + i) > str
					.length() ? str.length() : (subLen)))) {
				result = str.substring(0, subLen + i - 1);
				break;
			}
			if ((subLen + i) > str.length()) {
				result = str.substring(0, str.length() - 1);
				break;
			}
		}
		return result;
	}
	
	/**
	 * 生成随机的文件名 将原始文件名去掉,改为一个UUID的文件名,后缀名以原文件名的后缀为准
	 * 
	 * @param fileName
	 *            原始文件名+后缀
	 * @return
	 */
	public static String generateUUIDFileName(String fileName) {

		UUID uuid = UUID.randomUUID();
		String str = fileName;
		System.out.println(str);
		str = uuid.toString() + "." + str.substring(str.lastIndexOf(".") + 1);
		return str;
	}

	  

	//****************************************************************************************************
	//****************************************************************************************************
	/**
	 * 删除指定目录下的所有文件
	 * @param folderPath 目录路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delAllFile(String folderPath) {
		boolean flag = false;
		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件
	 * @param filePath 指定文件的路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			return flag;
		}
		flag = (new File(filePath)).delete();
		return flag;
	}

	/**
	 * 删除指定文件夹(包括文件夹下的所有文件)
	 * @param folderPath 指定文件夹路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
 	/**
 	 * 读取文本文件的内容
 	 * @param curfile   文本文件路径
 	 * @return 返回文件内容
 	 */
 	public static String readFile(String curfile)
 	{
			File f = new File(curfile);
		    try
		    {
	 		  if (!f.exists()) throw new Exception();
	 		  FileReader cf=new FileReader(curfile);
			  BufferedReader is = new BufferedReader(cf);
			  String filecontent = ""; 			  
			  String str = is.readLine();
			  while (str != null){
			  	filecontent += str;
		    	str = is.readLine();
		    	if (str != null)
		    		filecontent += "\n";
			  }
			  is.close();
			  cf.close();
			  return filecontent;
		    }
		    catch (Exception e)
		    {
		      System.err.println("不能读属性文件: "+curfile+" \n"+e.getMessage());
		      return "";
		    }
 		
 	}
	/**
	 * 取指定文件的扩展名
	 * @param filePathName  文件路径
	 * @return 扩展名
	 */
	public static String getFileExt(String filePathName)
	{
	    int pos = 0;
	    pos = filePathName.lastIndexOf('.');
	    if(pos != -1)
	        return filePathName.substring(pos + 1, filePathName.length());
	    else
	        return "";
		
	}
	
	/**
	 * 读取文件大小
	 * @param filename 指定文件路径
	 * @return 文件大小
	 */
	public static int getFileSize(String filename)
	{
		try
		{
			File fl = new File(filename);
			int length = (int)fl.length();
			return length;
	  	} 
	  	catch (Exception e)
	  	{
	  		return 0;
	  	}
		
	}
	
	/**
	 * 拷贝文件到指定目录
	 * @param srcPath 源文件路径
	 * @param destPath 目标文件路径
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile(String srcPath,String destPath){
		try
		{
			File fl = new File(srcPath);
			int length = (int)fl.length();
		  	FileInputStream is = new FileInputStream(srcPath);
		  	FileOutputStream os = new FileOutputStream(destPath);
			byte[] b = new byte[length];
		  	is.read(b);
		  	os.write(b);
		  	is.close();
		  	os.close();
		  	return true;
	  	} 
	  	catch (Exception e)
	  	{
	  		return false;
	  	}
	}
	
	/**
	 * 通过正则获取文件名
	 */
	public static String getFileName(String filePath){
		String regEx = "";
		if(filePath.indexOf("\\") == -1){
			regEx = ".+\\/(.+)$"; 
		}
		else{
			regEx = ".+\\\\(.+)$"; 
		}
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(filePath);
        String fileName = "";
        if(m.find()){
        	fileName = m.group(1);
        }
        return fileName;
	}
	
	/**
	 * 通过正则获取文件后缀名
	 * @param fileName 文件名(不包含路径)
	 */
	public static String getFileExtendion(String fileName){
		String regEx="^(.+)\\..+";
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(fileName); 
        String extendtion = "";
        if(m.find()){
        	extendtion = m.group(1);
        }
        return extendtion;
	}
	//****************************************************************************************************
	//****************************************************************************************************
	  
 	//****************************************************************************************************
	//****************************************************************************************************
	
//	private static final Logger logger = Logger.getLogger(FileUtil.class);
//	private final static int BUFFER = 1024;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// File file = new File("D:\\个人资料\\MySQL 5");
		// list(file);
		Date myDate = new Date();
		DateFormat df = DateFormat.getDateInstance();
		System.out.println(df.format(myDate));
	}

	/**
	 * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
	 * 
	 * @param path
	 *            文件�?
	 */
	public static void list(File path) {
		if (!path.exists()) {
			System.out.println("文件名称不存�?");
		} else {
			if (path.isFile()) {
				if (path.getName().toLowerCase().endsWith(".pdf")
						|| path.getName().toLowerCase().endsWith(".doc")
						|| path.getName().toLowerCase().endsWith(".chm")
						|| path.getName().toLowerCase().endsWith(".html")
						|| path.getName().toLowerCase().endsWith(".htm")) {// 文件格式
					System.out.println(path);
					System.out.println(path.getName());
				}
			} else {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					list(files[i]);
				}
			}
		}
	}

	/**
	 * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径�?
	 * 
	 * @param source
	 *            源文�?
	 * @param target
	 *            目标文件路径
	 * @return void
	 */
	public static void copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的�?
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 锟睫革拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷时锟戒。 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷虼唇锟斤拷锟斤拷募锟斤拷锟�
	 * <b>目前锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟轿拷锟绞斤拷锟斤拷锟斤拷榷锟斤拷锟斤拷锟揭拷欠锟斤拷锟斤拷锟叫╋拷锟较
	 * 拷锟斤拷锟斤拷锟斤拷些锟斤拷息锟斤拷锟斤拷欠锟斤拷锟斤拷锟斤拷诳锟斤拷锟斤拷小锟�/b>
	 * 
	 * @param file
	 *            锟斤拷要锟睫革拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷募锟斤拷锟�
	 * @since 1.0
	 */
	public static void touch(File file) {
		long currentTime = System.currentTimeMillis();
		if (!file.exists()) {
			System.err.println("file not found:" + file.getName());
			System.err.println("Create a new file:" + file.getName());
			try {
				if (file.createNewFile()) {
					System.out.println("Succeeded!");
				} else {
					System.err.println("Create file failed!");
				}
			} catch (IOException e) {
				System.err.println("Create file failed!");
				e.printStackTrace();
			}
		}
		boolean result = file.setLastModified(currentTime);
		if (!result) {
			System.err.println("touch failed: " + file.getName());
		}
	}

	/**
	 * 锟睫革拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷时锟戒。 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷虼唇锟斤拷锟斤拷募锟斤拷锟�
	 * <b>目前锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟轿拷锟绞斤拷锟斤拷锟斤拷榷锟斤拷锟斤拷锟揭拷欠锟斤拷锟斤拷锟叫╋拷锟较
	 * 拷锟斤拷锟斤拷锟斤拷些锟斤拷息锟斤拷锟斤拷欠锟斤拷锟斤拷锟斤拷诳锟斤拷锟斤拷小锟�/b>
	 * 
	 * @param fileName
	 *            锟斤拷要锟睫革拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷募锟斤拷锟斤拷募锟斤拷锟�
	 * @since 1.0
	 */
	public static void touch(String fileName) {
		File file = new File(fileName);
		touch(file);
	}

	/**
	 * 锟睫革拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷时锟戒。 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷虼唇锟斤拷锟斤拷募锟斤拷锟�
	 * <b>目前锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟轿拷锟绞斤拷锟斤拷锟斤拷榷锟斤拷锟斤拷锟揭拷欠锟斤拷锟斤拷锟叫╋拷锟较
	 * 拷锟斤拷锟斤拷锟斤拷些锟斤拷息锟斤拷锟斤拷欠锟斤拷锟斤拷锟斤拷诳锟斤拷锟斤拷小锟�/b>
	 * 
	 * @param files
	 *            锟斤拷要锟睫革拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷募锟斤拷锟斤拷椤�
	 * @since 1.0
	 */
	public static void touch(File[] files) {
		for (int i = 0; i < files.length; i++) {
			touch(files[i]);
		}
	}

	/**
	 * 锟睫革拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷时锟戒。 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷虼唇锟斤拷锟斤拷募锟斤拷锟�
	 * <b>目前锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟轿拷锟绞斤拷锟斤拷锟斤拷榷锟斤拷锟斤拷锟揭拷欠锟斤拷锟斤拷锟叫╋拷锟较
	 * 拷锟斤拷锟斤拷锟斤拷些锟斤拷息锟斤拷锟斤拷欠锟斤拷锟斤拷锟斤拷诳锟斤拷锟斤拷小锟�/b>
	 * 
	 * @param fileNames
	 *            锟斤拷要锟睫革拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷椤�
	 * @since 1.0
	 */
	public static void touch(String[] fileNames) {
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(fileNames[i]);
		}
		touch(files);
	}

	/**
	 * 锟叫讹拷指锟斤拷锟斤拷锟侥硷拷锟角凤拷锟斤拷凇锟�
	 * 
	 * @param fileName
	 *            要锟叫断碉拷锟侥硷拷锟斤拷锟侥硷拷锟斤拷
	 * @return 锟斤拷锟斤拷时锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false锟斤拷
	 * @since 1.0
	 */
	public static boolean isFileExist(String fileName) {
		return new File(fileName).isFile();
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷锟斤拷目录锟斤拷
	 * 锟斤拷锟街革拷锟斤拷锟侥柯硷拷母锟侥柯硷拷锟斤拷锟斤拷锟斤拷虼唇锟斤拷锟侥柯硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷母锟侥柯硷拷锟�
	 * <b>注锟解：锟斤拷锟杰伙拷锟节凤拷锟斤拷false锟斤拷时锟津创斤拷锟斤拷锟街革拷目录锟斤拷</b>
	 * 
	 * @param file
	 *            要锟斤拷锟斤拷锟斤拷目录
	 * @return 锟斤拷全锟斤拷锟斤拷锟缴癸拷时锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false锟斤拷
	 * @since 1.0
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷锟斤拷目录锟斤拷
	 * 锟斤拷锟街革拷锟斤拷锟侥柯硷拷母锟侥柯硷拷锟斤拷锟斤拷锟斤拷虼唇锟斤拷锟侥柯硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷母锟侥柯硷拷锟�
	 * <b>注锟解：锟斤拷锟杰伙拷锟节凤拷锟斤拷false锟斤拷时锟津创斤拷锟斤拷锟街革拷目录锟斤拷</b>
	 * 
	 * @param fileName
	 *            要锟斤拷锟斤拷锟斤拷目录锟斤拷目录锟斤拷
	 * @return 锟斤拷全锟斤拷锟斤拷锟缴癸拷时锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false锟斤拷
	 * @since 1.0
	 */
	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return makeDirectory(file);
	}

	/**
	 * 锟斤拷锟街革拷锟侥柯硷拷械锟斤拷募锟斤拷锟� 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟缴撅拷锟斤拷锟斤拷械锟斤拷募锟斤拷锟斤拷锟斤拷锟街灰
	 * 拷锟揭伙拷锟斤拷募锟矫伙拷斜锟缴撅拷锟结返锟斤拷false锟斤拷
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟缴撅拷锟斤拷锟斤拷删锟斤拷锟斤拷目录锟斤拷锟斤拷锟斤拷锟捷★拷
	 * 
	 * @param directory
	 *            要锟斤拷盏锟侥柯�
	 * @return 目录锟铰碉拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟缴癸拷删锟斤拷时锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false.
	 * @since 1.0
	 */
	public static boolean emptyDirectory(File directory) {
		boolean result = true;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 锟斤拷锟街革拷锟侥柯硷拷械锟斤拷募锟斤拷锟� 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟缴撅拷锟斤拷锟斤拷械锟斤拷募锟斤拷锟斤拷锟斤拷锟街灰
	 * 拷锟揭伙拷锟斤拷募锟矫伙拷斜锟缴撅拷锟结返锟斤拷false锟斤拷
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟缴撅拷锟斤拷锟斤拷删锟斤拷锟斤拷目录锟斤拷锟斤拷锟斤拷锟捷★拷
	 * 
	 * @param directoryName
	 *            要锟斤拷盏锟侥柯硷拷锟侥柯硷拷锟�
	 * @return 目录锟铰碉拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟缴癸拷删锟斤拷时锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false锟斤拷
	 * @since 1.0
	 */
	public static boolean emptyDirectory(String directoryName) {
		File dir = new File(directoryName);
		return emptyDirectory(dir);
	}

	/**
	 * 删锟斤拷指锟斤拷目录锟斤拷锟斤拷锟叫碉拷锟斤拷锟斤拷锟斤拷锟捷★拷
	 * 
	 * @param dirName
	 *            要删锟斤拷锟侥柯硷拷锟侥柯硷拷锟�
	 * @return 删锟斤拷晒锟绞憋拷锟斤拷锟絫rue锟斤拷锟斤拷锟津返伙拷false锟斤拷
	 * @since 1.0
	 */
	public static boolean deleteDirectory(String dirName) {
		return deleteDirectory(new File(dirName));
	}

	/**
	 * 删锟斤拷指锟斤拷目录锟斤拷锟斤拷锟叫碉拷锟斤拷锟斤拷锟斤拷锟捷★拷
	 * 
	 * @param dir
	 *            要删锟斤拷锟侥柯�
	 * @return 删锟斤拷晒锟绞憋拷锟斤拷锟絫rue锟斤拷锟斤拷锟津返伙拷false锟斤拷
	 * @since 1.0
	 */
	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}
		File[] entries = dir.listFiles();
		int sz = entries.length;
		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}
		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	/**
	 * 锟叫筹拷目录锟叫碉拷锟斤拷锟斤拷锟斤拷锟捷ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷目录锟叫碉拷锟斤拷锟捷★拷
	 * 
	 * @param fileName
	 *            要锟叫筹拷锟斤拷目录锟斤拷目录锟斤拷
	 * @return 目录锟斤拷锟捷碉拷锟侥硷拷锟斤拷锟介。
	 * @since 1.0
	 */
	/*
	 * public static File[] listAll(String fileName) { return listAll(new
	 * File(fileName)); }
	 */
	/**
	 * 锟叫筹拷目录锟叫碉拷锟斤拷锟斤拷锟斤拷锟捷ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷目录锟叫碉拷锟斤拷锟捷★拷
	 * 
	 * @param file
	 *            要锟叫筹拷锟斤拷目录
	 * @return 目录锟斤拷锟捷碉拷锟侥硷拷锟斤拷锟介。
	 * @since 1.0
	 */
	/*
	 * public static File[] listAll(File file) { ArrayList list = new
	 * ArrayList(); File[] files; if (!file.exists() || file.isFile()) { return
	 * null; } list(list, file, new AllFileFilter()); list.remove(file); files =
	 * new File[list.size()]; list.toArray(files); return files; }
	 */
	/**
	 * 锟叫筹拷目录锟叫碉拷锟斤拷锟斤拷锟斤拷锟捷ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷目录锟叫碉拷锟斤拷锟捷★拷
	 * 
	 * @param file
	 *            要锟叫筹拷锟斤拷目录
	 * @param filter
	 *            锟斤拷锟斤拷锟斤拷
	 * @return 目录锟斤拷锟捷碉拷锟侥硷拷锟斤拷锟介。
	 * @since 1.0
	 */
	public static File[] listAll(File file,
			javax.swing.filechooser.FileFilter filter) {
		ArrayList list = new ArrayList();
		File[] files;
		if (!file.exists() || file.isFile()) {
			return null;
		}
		list(list, file, filter);
		files = new File[list.size()];
		list.toArray(files);
		return files;
	}

	/**
	 * 锟斤拷目录锟叫碉拷锟斤拷锟斤拷锟斤拷拥锟斤拷斜?
	 * 
	 * @param list
	 *            锟侥硷拷锟叫憋拷
	 * @param filter
	 *            锟斤拷锟斤拷锟斤拷
	 * @param file
	 *            目录
	 */
	private static void list(ArrayList list, File file,
			javax.swing.filechooser.FileFilter filter) {
		if (filter.accept(file)) {
			list.add(file);
			if (file.isFile()) {
				return;
			}
		}
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				list(list, files[i], filter);
			}
		}
	}

	/**
	 * 锟斤拷锟斤拷锟侥硷拷锟斤拷URL锟斤拷址锟斤拷
	 * 
	 * @param file
	 *            锟侥硷拷
	 * @return 锟侥硷拷锟斤拷应锟侥碉拷URL锟斤拷址
	 * @throws MalformedURLException
	 * @since 1.0
	 * @deprecated 
	 *             锟斤拷实锟街碉拷时锟斤拷没锟斤拷注锟解到File锟洁本锟斤拷锟揭伙拷锟絫oURL锟斤拷锟斤拷锟斤拷锟侥硷拷路锟斤拷转锟斤拷为URL锟斤拷
	 *             锟斤拷使锟斤拷File.toURL锟斤拷锟斤拷锟斤拷
	 */
	public static URL getURL(File file) throws MalformedURLException {
		String fileURL = "file:/" + file.getAbsolutePath();
		URL url = new URL(fileURL);
		return url;
	}

	/**
	 * 锟斤拷锟侥硷拷路锟斤拷锟矫碉拷锟侥硷拷锟斤拷
	 * 
	 * @param filePath
	 *            锟侥硷拷锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰凤拷锟揭诧拷锟斤拷锟斤拷蔷锟斤拷路锟斤拷
	 * @return 锟斤拷应锟斤拷锟侥硷拷锟斤拷
	 * @since 1.0
	 */
	public static String getFileName44(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 锟斤拷锟侥硷拷锟斤拷玫锟斤拷募锟斤拷锟斤拷路锟斤拷锟斤拷
	 * 
	 * @param fileName
	 *            锟侥硷拷锟斤拷
	 * @return 锟斤拷应锟斤拷锟侥硷拷路锟斤拷
	 * @since 1.0
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 锟斤拷DOS/Windows锟斤拷式锟斤拷路锟斤拷转锟斤拷为UNIX/Linux锟斤拷式锟斤拷路锟斤拷锟斤拷
	 * 锟斤拷实锟斤拷锟角斤拷路锟斤拷锟叫碉拷
	 * "\"全锟斤拷锟斤拷为"/"锟斤拷锟斤拷为锟斤拷某些锟斤拷锟斤拷锟斤拷锟斤拷锟阶拷锟轿拷锟斤拷址锟绞斤拷冉戏锟斤拷悖�
	 * 某锟叫程讹拷锟斤拷说"/"
	 * 锟斤拷"\"锟斤拷锟绞猴拷锟斤拷为路锟斤拷锟街革拷锟斤拷锟斤拷锟紻OS/Windows也锟斤拷锟斤拷锟斤拷路锟斤拷锟街革拷锟斤拷
	 * 
	 * @param filePath
	 *            转锟斤拷前锟斤拷路锟斤拷
	 * @return 转锟斤拷锟斤拷锟铰凤拷锟�
	 * @since 1.0
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace('\\', '/');
	}

	/**
	 * 锟斤拷锟侥硷拷锟斤拷玫锟経NIX锟斤拷锟斤拷锟侥硷拷锟斤拷锟铰凤拷锟斤拷锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷锟斤拷
	 * @return 锟斤拷应锟斤拷UNIX锟斤拷锟斤拷锟侥硷拷路锟斤拷
	 * @since 1.0
	 * @see #toUNIXpath(String filePath) toUNIXpath
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXpath(file.getAbsolutePath());
	}

	/**
	 * 锟矫碉拷锟侥硷拷锟斤拷锟斤拷锟酵★拷 实锟斤拷锟较撅拷锟角得碉拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟�锟斤拷锟斤拷锟斤拷牟锟斤拷帧锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷锟斤拷
	 * @return 锟侥硷拷锟斤拷锟叫碉拷锟斤拷锟酵诧拷锟斤拷
	 * @since 1.0
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 锟矫碉拷锟侥硷拷锟斤拷锟斤拷锟酵★拷 实锟斤拷锟较撅拷锟角得碉拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟�锟斤拷锟斤拷锟斤拷牟锟斤拷帧锟�
	 * 
	 * @param file
	 *            锟侥硷拷
	 * @return 锟侥硷拷锟斤拷锟叫碉拷锟斤拷锟酵诧拷锟斤拷
	 * @since 1.0
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 锟矫碉拷锟侥硷拷锟斤拷锟斤拷锟街诧拷锟街★拷 实锟斤拷锟较撅拷锟斤拷路锟斤拷锟叫碉拷锟斤拷锟揭伙拷锟铰凤拷锟斤拷指锟斤拷锟斤拷牟锟斤拷帧锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷锟斤拷
	 * @return 锟侥硷拷锟斤拷锟叫碉拷锟斤拷锟街诧拷锟斤拷
	 * @since 1.0
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 锟矫碉拷锟侥硷拷锟斤拷锟叫的革拷路锟斤拷锟斤拷锟街★拷 锟斤拷锟斤拷锟斤拷路锟斤拷锟街革拷锟斤拷锟斤拷效锟斤拷
	 * 锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷""锟斤拷
	 * 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟铰凤拷锟斤拷指锟斤拷锟斤拷尾锟斤拷锟津不匡拷锟角该分革拷锟斤拷锟斤拷锟斤拷"/path/"锟斤拷锟斤拷""锟斤拷
	 * 
	 * @param fileName
	 *            锟侥硷拷锟斤拷
	 * @return 锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷锟节伙拷锟斤拷锟窖撅拷锟角革拷目录时锟斤拷锟斤拷""
	 * @since 1.0
	 */
	public static String getPathPart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return "";
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				return "";
			} else {
				return fileName.substring(0, secondPoint);
			}
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 锟矫碉拷路锟斤拷锟街革拷锟斤拷锟斤拷锟侥硷拷路锟斤拷锟斤拷锟阶次筹拷锟街碉拷位锟矫★拷
	 * 锟斤拷锟斤拷DOS锟斤拷锟斤拷UNIX锟斤拷锟侥分革拷锟斤拷锟斤拷浴锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷路锟斤拷
	 * @return 路锟斤拷锟街革拷锟斤拷锟斤拷路锟斤拷锟斤拷锟阶次筹拷锟街碉拷位锟矫ｏ拷没锟叫筹拷锟斤拷时锟斤拷锟斤拷-1锟斤拷
	 * @since 1.0
	 */
	public static int getPathIndex(String fileName) {
		int point = fileName.indexOf('/');
		if (point == -1) {
			point = fileName.indexOf('\\');
		}
		return point;
	}

	/**
	 * 锟矫碉拷路锟斤拷锟街革拷锟斤拷锟斤拷锟侥硷拷路锟斤拷锟斤拷指锟斤拷位锟矫猴拷锟阶次筹拷锟街碉拷位锟矫★拷
	 * 锟斤拷锟斤拷DOS锟斤拷锟斤拷UNIX锟斤拷锟侥分革拷锟斤拷锟斤拷浴锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷路锟斤拷
	 * @param fromIndex
	 *            锟斤拷始锟斤拷锟揭碉拷位锟斤拷
	 * @return 路锟斤拷锟街革拷锟斤拷锟斤拷路锟斤拷锟斤拷指锟斤拷位锟矫猴拷锟阶次筹拷锟街碉拷位锟矫ｏ拷没锟叫筹拷锟斤拷时锟斤拷锟斤拷-1锟斤拷
	 * @since 1.0
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		int point = fileName.indexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.indexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 锟矫碉拷路锟斤拷锟街革拷锟斤拷锟斤拷锟侥硷拷路锟斤拷锟斤拷锟斤拷锟斤拷锟街碉拷位锟矫★拷
	 * 锟斤拷锟斤拷DOS锟斤拷锟斤拷UNIX锟斤拷锟侥分革拷锟斤拷锟斤拷浴锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷路锟斤拷
	 * @return 路锟斤拷锟街革拷锟斤拷锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷锟街碉拷位锟矫ｏ拷没锟叫筹拷锟斤拷时锟斤拷锟斤拷-1锟斤拷
	 * @since 1.0
	 */
	public static int getPathLsatIndex(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1) {
			point = fileName.lastIndexOf('\\');
		}
		return point;
	}

	/**
	 * 锟矫碉拷路锟斤拷锟街革拷锟斤拷锟斤拷锟侥硷拷路锟斤拷锟斤拷指锟斤拷位锟斤拷前锟斤拷锟斤拷锟街碉拷位锟矫★拷
	 * 锟斤拷锟斤拷DOS锟斤拷锟斤拷UNIX锟斤拷锟侥分革拷锟斤拷锟斤拷浴锟�
	 * 
	 * @param fileName
	 *            锟侥硷拷路锟斤拷
	 * @param fromIndex
	 *            锟斤拷始锟斤拷锟揭碉拷位锟斤拷
	 * @return 路锟斤拷锟街革拷锟斤拷锟斤拷路锟斤拷锟斤拷指锟斤拷位锟斤拷前锟斤拷锟斤拷锟街碉拷位锟矫ｏ拷没锟叫筹拷锟斤拷时锟斤拷锟斤拷-1锟斤拷
	 * @since 1.0
	 */
	public static int getPathLsatIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 锟斤拷锟侥硷拷锟斤拷锟叫碉拷锟斤拷锟酵诧拷锟斤拷去锟斤拷锟斤拷
	 * 
	 * @param filename
	 *            锟侥硷拷锟斤拷
	 * @return 去锟斤拷锟斤拷锟酵诧拷锟街的斤拷锟�
	 * @since 1.0
	 */
	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 * 锟矫碉拷锟斤拷锟铰凤拷锟斤拷锟� 锟侥硷拷锟斤拷锟斤拷目录锟斤拷锟斤拷咏诘锟绞憋拷锟斤拷锟斤拷募锟斤拷锟�
	 * 
	 * @param pathName
	 *            目录锟斤拷
	 * @param fileName
	 *            锟侥硷拷锟斤拷
	 * @return 
	 *         锟矫碉拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟侥柯硷拷锟斤拷锟斤拷锟铰凤拷锟斤拷锟侥柯硷拷虏锟斤拷锟斤拷诟锟斤拷募锟绞憋拷锟斤拷锟斤拷募锟斤拷锟
	 *         �
	 * @since 1.0
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	/**
	 * 锟斤拷锟斤拷目录锟侥达拷锟斤拷锟斤拷
	 * 锟斤拷证指锟斤拷锟斤拷路锟斤拷锟斤拷锟矫ｏ拷锟斤拷锟街革拷锟斤拷锟铰凤拷锟斤拷锟斤拷锟斤拷冢锟斤拷锟矫达拷锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷锟斤拷锟轿
	 * 拷嗉堵凤拷锟�
	 * 
	 * @param path
	 * @return 锟斤拷锟街�
	 * @since 1.0
	 */
	public static final boolean pathValidate(String path) {
		// String path="d:/web/www/sub";
		// System.out.println(path);
		// path = getUNIXfilePath(path);
		// path = ereg_replace("^\\/+", "", path);
		// path = ereg_replace("\\/+$", "", path);
		String[] arraypath = path.split("/");
		String tmppath = "";
		for (int i = 0; i < arraypath.length; i++) {
			tmppath += "/" + arraypath[i];
			File d = new File(tmppath.substring(1));
			if (!d.exists()) { // 锟斤拷锟絊ub目录锟角凤拷锟斤拷锟�
				System.out.println(tmppath.substring(1));
				if (!d.mkdir()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 锟斤拷取锟侥硷拷锟斤拷锟斤拷锟斤拷 锟斤拷取指锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param path
	 *            为要锟斤拷取锟侥硷拷锟侥撅拷锟铰凤拷锟�
	 * @return 锟斤拷锟叫讹拷取锟侥硷拷锟斤拷锟斤拷锟斤拷荨锟�
	 * @since 1.0
	 */
	public static final String getFileContent(String path) throws IOException {
		String filecontent = "";
		try {
			File f = new File(path);
			if (f.exists()) {
				FileReader fr = new FileReader(path);
				BufferedReader br = new BufferedReader(fr); // 锟斤拷锟斤拷BufferedReader锟斤拷锟襟，诧拷实锟斤拷为br
				String line = br.readLine(); // 锟斤拷锟侥硷拷锟斤拷取一锟斤拷锟街凤拷
				// 锟叫断讹拷取锟斤拷锟斤拷锟街凤拷锟角凤拷为锟斤拷
				while (line != null) {
					filecontent += line + "\n";
					line = br.readLine(); // 锟斤拷锟侥硷拷锟叫硷拷锟斤拷锟饺∫伙拷锟斤拷锟斤拷
				}
				br.close(); // 锟截憋拷BufferedReader锟斤拷锟斤拷
				fr.close(); // 锟截憋拷锟侥硷拷
			}
		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷
	 * 
	 * @param path要锟斤拷锟斤拷募锟斤拷木锟斤拷路锟斤拷锟斤拷
	 * @param 锟侥硷拷锟斤拷锟斤拷锟捷
	 *            ★拷
	 * @return 锟斤拷锟街�
	 * @since 1.0
	 */
	public static final boolean genModuleTpl(String path, String modulecontent)
			throws IOException {
		path = getUNIXfilePath(path);
		String[] patharray = path.split("\\/");
		String modulepath = "";
		for (int i = 0; i < patharray.length - 1; i++) {
			modulepath += "/" + patharray[i];
		}
		File d = new File(modulepath.substring(1));
		if (!d.exists()) {
			if (!pathValidate(modulepath.substring(1))) {
				return false;
			}
		}
		try {
			FileWriter fw = new FileWriter(path); // 锟斤拷锟斤拷FileWriter锟斤拷锟襟，诧拷实锟斤拷fw
			// 锟斤拷锟街凤拷写锟斤拷锟侥硷拷
			fw.write(modulecontent);
			fw.close();
		} catch (IOException e) {
			throw e;
		}
		return true;
	}

	/**
	 * 锟斤拷取图片锟侥硷拷锟斤拷锟斤拷展锟斤拷锟斤拷系统专锟矫ｏ拷
	 * 
	 * @param picname
	 *            为图片锟斤拷萍锟斤拷锟角帮拷锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷展锟斤拷
	 * @return 图片锟斤拷锟斤拷展锟斤拷
	 * @since 1.0
	 */
	public static final String getPicExtendName(String pic_path) {
		pic_path = getUNIXfilePath(pic_path);
		String pic_extend = "";
		if (isFileExist(pic_path + ".gif")) {
			pic_extend = ".gif";
		}
		if (isFileExist(pic_path + ".jpeg")) {
			pic_extend = ".jpeg";
		}
		if (isFileExist(pic_path + ".jpg")) {
			pic_extend = ".jpg";
		}
		if (isFileExist(pic_path + ".png")) {
			pic_extend = ".png";
		}
		return pic_extend; // 锟斤拷锟斤拷图片锟斤拷展锟斤拷
	}

	// 锟斤拷锟斤拷锟侥硷拷
	public static final boolean CopyFile(File in, File out) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(in);
			FileOutputStream fos = new FileOutputStream(out);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			fis.close();
			fos.close();
			return true;
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
	}

	// 锟斤拷锟斤拷锟侥硷拷
	public static final boolean CopyFile(String infile, String outfile)
			throws Exception {
		try {
			File in = new File(infile);
			File out = new File(outfile);
			return CopyFile(in, out);
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
	}

	/**
	 * 锟斤拷锟斤拷图片锟斤拷锟斤拷
	 * 
	 * @param id
	 * @param dtime
	 * @return
	 */
	/*public static final int countPics(String id, String dtime, String extensions) {
		int counts = 0;
		MyFileFilter mfilter = new MyFileFilter(extensions.split(","));
		ConfigUtil pu = new ConfigUtil();
		String PICROOT = pu.readSingleProps("DestinationsPICROOT").trim();
		String path = PICROOT + "/" + dtime.substring(0, 10) + "/";
		File lfile = new File(path);
		String filename;
		if (lfile.isDirectory()) {
			File[] files = lfile.listFiles(mfilter);
			for (int i = 0; i < files.length; i++) {
				filename = files[i].getName();
				if ((filename.indexOf(id + "_") == 0)
						&& (filename.indexOf("_small") > -1)) counts++;
			}
			files = null;
		}
		filename = null;
		lfile = null;
		pu = null;
		mfilter = null;
		return counts;
	}*/

	/**
	 * 功 能: 移动文件(只能移动文件) 参 数: strSourceFileName:指定的文件全路径名 strDestDir: 移动到指定的文件夹
	 * 返回值: 如果成功true;否则false
	 * 
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean copyTo1(String strSourceFileName, String strDestDir) {
		File fileSource = new File(strSourceFileName);
		File fileDest = new File(strDestDir);
		// 如果源文件不存或源文件是文件夹
		if (!fileSource.exists() || !fileSource.isFile()) {
			logger.debug("源文件[" + strSourceFileName + "],不存在或是文件夹!");
			return false;
		}
		// 如果目标文件夹不存在
		if (!fileDest.isDirectory() || !fileDest.exists()) {
			if (!fileDest.mkdirs()) {
				logger.debug("目录文件夹不存，在创建目标文件夹时失败!");
				return false;
			}
		}
		try {
			String strAbsFilename = strDestDir + File.separator
					+ fileSource.getName();
			FileInputStream fileInput = new FileInputStream(strSourceFileName);
			FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);
			logger.debug("开始拷贝文件");
			int count = -1;
			long nWriteSize = 0;
			long nFileSize = fileSource.length();
			byte[] data = new byte[BUFFER];
			while (-1 != (count = fileInput.read(data, 0, BUFFER))) {
				fileOutput.write(data, 0, count);
				nWriteSize += count;
				long size = (nWriteSize * 100) / nFileSize;
				long t = nWriteSize;
				String msg = null;
				if (size <= 100 && size >= 0) {
					msg = "\r拷贝文件进度:   " + size + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				} else if (size > 100) {
					msg = "\r拷贝文件进度:   " + 100 + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				}
			}
			fileInput.close();
			fileOutput.close();
			logger.debug("拷贝文件成功!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功 能: 删除指定的文件 参 数: 指定绝对路径的文件名 strFileName 返回值: 如果删除成功true否则false;
	 * 
	 * @param strFileName
	 * @return
	 */
	public static boolean delete1(String strFileName) {
		File fileDelete = new File(strFileName);
		if (!fileDelete.exists() || !fileDelete.isFile()) {
			logger.debug(strFileName + "不存在!");
			return false;
		}
		return fileDelete.delete();
	}

	/**
	 * 功 能: 移动文件(只能移动文件) 参 数: strSourceFileName: 是指定的文件全路径名 strDestDir:
	 * 移动到指定的文件夹中 返回值: 如果成功true; 否则false
	 * 
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean moveFile1(String strSourceFileName, String strDestDir) {
		if (copyTo(strSourceFileName, strDestDir))
			return delete(strSourceFileName);
		else
			return false;
	}

	/**
	 * 功 能: 创建文件夹 参 数: strDir 要创建的文件夹名称 返回值: 如果成功true;否则false
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean makeDir1(String strDir) {
		File fileNew = new File(strDir);
		if (!fileNew.exists()) {
			return fileNew.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * 功 能: 删除文件夹 参 数: strDir 要删除的文件夹名称 返回值: 如果成功true;否则false
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean removeDir1(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();
			for (int i = 0; i < fileList.length; i++) {
				String subFile = strDir + File.separator + fileList[i];
				File tmp = new File(subFile);
				if (tmp.isFile())
					tmp.delete();
				else if (tmp.isDirectory()) removeDir(subFile);
			}
			rmDir.delete();
		} else {
			return false;
		}
		return true;
	}

	public static String getDownloadFileName(String filename, String agent)
			throws UnsupportedEncodingException {
		if (agent.contains("MSIE")) {
			// IE浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐浏览器
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
		} else if (agent.contains("Chrome")) {
			// google浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			// 其它浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

	// ******************************************************************************
	// ******************************************************************************
	// ******************************************************************************
	// ���ϴ��ļ���������ʱ�趨����ʱ�ļ�λ�ã�ע���Ǿ��·��
	private String tempPath = null;
	// �ļ��ϴ�Ŀ��Ŀ¼��ע���Ǿ��·��
	private String dstPath = null;
	// ���ļ���ƣ�������ʱĬ��Ϊԭ�ļ���
	private String newFileName = null;
	// ��ȡ���ϴ�����
	private HttpServletRequest fileuploadReq = null;
	// �������ֻ�������ڴ��д洢�����,��λ:�ֽڣ��������Ҫ����̫��
	private int sizeThreshold = 4096;
	// ���������û��ϴ��ļ���С,��λ:�ֽ�
	// ��10M
	private long sizeMax = 10485760;
	// ͼƬ�ļ����
	private int picSeqNo = 1;
	private boolean isSmallPic = false;

	public FileUtil() {}

	public FileUtil(String tempPath, String destinationPath) {
		this.tempPath = tempPath;
		this.dstPath = destinationPath;
	}

	public FileUtil(String tempPath, String destinationPath,
			HttpServletRequest fileuploadRequest) {
		this.tempPath = tempPath;
		this.dstPath = destinationPath;
		this.fileuploadReq = fileuploadRequest;
	}

	/**
	 * �ļ�����
	 * 
	 * @return true ���� success; false ���� fail.
	 */
	public boolean Upload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		try {
			// ���û���ϴ�Ŀ��Ŀ¼���򴴽���
			FileUtil.makeDirectory(dstPath + "/ddd");
			/*
			 * if (!FileUtil.makeDirectory(dstPath+"/ddd")) { throw new
			 * IOException("Create destination Directory Error."); }
			 */
			// ���û����ʱĿ¼���򴴽���
			FileUtil.makeDirectory(tempPath + "/ddd");
			/*
			 * if (!FileUtil.makeDirectory(tempPath+"/ddd")) { throw new
			 * IOException("Create Temp Directory Error."); }
			 */
			// �ϴ���ĿֻҪ�㹻С����Ӧ�ñ������ڴ��
			// �ϴ����ĿӦ�ñ�д��Ӳ�̵���ʱ�ļ��ϡ�
			// �ǳ�����ϴ�����Ӧ�ñ��⡣
			// ������Ŀ���ڴ�����ռ�Ŀռ䣬���������ϴ����󣬲����趨��ʱ�ļ���λ�á�
			// �������ֻ�������ڴ��д洢�����,��λ:�ֽ�
			factory.setSizeThreshold(sizeThreshold);
			// the location for saving data that is larger than
			// getSizeThreshold()
			factory.setRepository(new File(tempPath));
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ���������û��ϴ��ļ���С,��λ:�ֽ�
			upload.setSizeMax(sizeMax);
			List fileItems = upload.parseRequest(fileuploadReq);
			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			Iterator iter = fileItems.iterator();
			// ����ƥ�䣬����·��ȡ�ļ���
			String regExp = ".+\\\\(.+)$";
			// ���˵����ļ�����
			String[] errorType = { ".exe", ".com", ".cgi", ".asp", ".php",
					".jsp" };
			Pattern p = Pattern.compile(regExp);
			while (iter.hasNext()) {
				System.out.println("++00++=====" + newFileName);
				FileItem item = (FileItem) iter.next();
				// �����������ļ�������б?��Ϣ
				if (!item.isFormField()) {
					String name = item.getName();
					System.out.println("++++=====" + name);
					long size = item.getSize();
					// �ж���ļ���ʱ��ֻ�ϴ����ļ���
					if ((name == null || name.equals("")) && size == 0)
						continue;
					Matcher m = p.matcher(name);
					boolean result = m.find();
					if (result) {
						for (int temp = 0; temp < errorType.length; temp++) {
							if (m.group(1).endsWith(errorType[temp])) {
								throw new IOException(name
										+ ": Wrong File Type");
							}
						}
						String ext = "." + FileUtil.getTypePart(name);
						try {
							// �����ϴ����ļ���ָ����Ŀ¼
							// ���������ϴ��ļ�����ݿ�ʱ�����������д
							// û��ָ�����ļ���ʱ��ԭ�ļ���������
							if (newFileName == null
									|| newFileName.trim().equals("")) {
								item.write(new File(dstPath + "/" + m.group(1)));
							} else {
								String uploadfilename = "";
								if (isSmallPic) {
									uploadfilename = dstPath + "/"
											+ newFileName + "_" + picSeqNo
											+ "_small" + ext;
								} else {
									uploadfilename = dstPath + "/"
											+ newFileName + "_" + picSeqNo
											+ ext;
								}
								// �������δ��ɵ�Ŀ¼
								System.out
										.println("++++=====" + uploadfilename);
								FileUtil.makeDirectory(uploadfilename);
								// item.write(new File(dstPath +"/"+
								// newFileName));
								item.write(new File(uploadfilename));
							}
							picSeqNo++;
							// out.print(name + "&nbsp;&nbsp;" + size + "<br>");
						} catch (Exception e) {
							// out.println(e);
							throw new IOException(e.getMessage());
						}
					} else {
						throw new IOException("fail to upload");
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (FileUploadException e) {
			System.out.println(e);
		}
		return true;
	}

	/**
	 * ��·���л�ȡ�����ļ���
	 * 
	 * @author
	 * 
	 *         TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ
	 *         �� ����ģ��
	 */
	public String GetFileName(String filepath) {
		String returnstr = "*.*";
		int length = filepath.trim().length();
		filepath = filepath.replace('\\', '/');
		if (length > 0) {
			int i = filepath.lastIndexOf("/");
			if (i >= 0) {
				filepath = filepath.substring(i + 1);
				returnstr = filepath;
			}
		}
		return returnstr;
	}

	/**
	 * ������ʱ����Ŀ¼
	 */
	public void setTmpPath(String tmppath) {
		this.tempPath = tmppath;
	}

	/**
	 * ����Ŀ��Ŀ¼
	 */
	public void setDstPath(String dstpath) {
		this.dstPath = dstpath;
	}

	/**
	 * ��������ϴ��ļ��ֽ�������ʱĬ��10M
	 */
	public void setFileMaxSize(long maxsize) {
		this.sizeMax = maxsize;
	}

	/**
	 * ����Http �������ͨ�������������ȡ�ļ���Ϣ
	 */
	public void setHttpReq(HttpServletRequest httpreq) {
		this.fileuploadReq = httpreq;
	}

	/**
	 * ����Http �������ͨ�������������ȡ�ļ���Ϣ
	 */
	public void setNewFileName(String filename) {
		this.newFileName = filename;
	}

	/**
	 * ���ô��ϴ��ļ��Ƿ�������ͼ�ļ������������Ҫ��������ͼ����
	 */
	public void setIsSmalPic(boolean isSmallPic) {
		this.isSmallPic = isSmallPic;
	}

	/**
	 * ����Http �������ͨ�������������ȡ�ļ���Ϣ
	 */
	public void setPicSeqNo(int seqNo) {
		this.picSeqNo = seqNo;
	}

	// ********************************************************
	// ********************************************************
	// ********************************************************
	/**
	 * 截取真实文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String subFileName(String fileName) {
		// 查找最后一个 \出现位置
		int index = fileName.lastIndexOf("\\");
		if (index == -1) {
			return fileName;
		}
		return fileName.substring(index + 1);
	}

	// 获得随机UUID文件名
	public static String generateRandonFileName(String fileName) {
		// 获得扩展名
		String ext = fileName.substring(fileName.lastIndexOf("."));
		return UUID.randomUUID().toString() + ext;
	}

	// 获得hashcode生成二级目录
	public static String generateRandomDir(String uuidFileName) {
		int hashCode = uuidFileName.hashCode();
		// 一级目录
		int d1 = hashCode & 0xf;
		// 二级目录
		int d2 = (hashCode >> 4) & 0xf;
		return "/" + d1 + "/" + d2;
	}

	// ************************************************************************************
	// ************************************************************************************
	// ************************************************************************************
	private static final Log log = LogFactory.getLog(FileUtil.class);

	// private static final Log log = LogFactory.getLog(getClass());
	/**
	 * 文件下载
	 * 
	 * @param file
	 * @param response
	 * @param request
	 */
	public static void download(File file, HttpServletResponse response,
			HttpServletRequest request) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			String agent = request.getHeader("USER-AGENT");
			request.setAttribute("pragma", "no-cache");
			String codedfilename = file.getName();
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				codedfilename = URLEncoder.encode(file.getName(), "UTF8");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				// codedfilename = "=?UTF-8?B?" + (new
				// String(Base64.encodeBase64(file.getName().getBytes("UTF-8"))))
				// + "?=";
			}
			response.setContentType("application/octet-stream; CHARSET=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ codedfilename);
			out = response.getOutputStream();
			byte[] b = new byte[2048];
			int i = 0;
			int j = 0;
			long k = file.length();// 下载文件的总大小
			log.info(k + "");
			while ((i = in.read(b)) > 0) {
				out.write(b, 0, i);
				j += i;
				log.info(j + "");
				if (j == 20480) {
					// in.reset();
				}
			}
			out.flush();
		} catch (IOException e) {
			log.error("用户取消下载", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("FileInputStream close  error!", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("Outputstream close  error! ", e);
				}
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * <pre>
	 * @desc
	 * @createtime 2012-10-9
	 * @modifytime 2012-10-9
	 * @author lq
	 * @modifyauthor lq
	 * @version 0303
	 * @filename FileManager.java
	 * @param fileName
	 * @param response
	 * @param request
	 * @param filelength
	 * </pre>
	 */
	public static void download(String fileName, HttpServletResponse response,
			HttpServletRequest request) {
		File file = new File(getPath() + fileName);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			String agent = request.getHeader("USER-AGENT");
			request.setAttribute("pragma", "no-cache");
			String codedfilename = file.getName();
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				codedfilename = URLEncoder.encode(fileName, "UTF8");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				// codedfilename = "=?UTF-8?B?" + (new
				// String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) +
				// "?=";
			}
			response.setContentType("application/octet-stream; CHARSET=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ codedfilename);
			out = response.getOutputStream();
			byte[] b = new byte[2048];
			int i = 0;
			int j = 0;
			long k = file.length();
			log.info(k + "");
			while ((i = in.read(b)) > 0) {
				out.write(b, 0, i);
				j += i;
				log.info(j + "");
			}
			out.flush();
		} catch (IOException e) {
			log.error("用户取消下载", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("FileInputStream close  error!", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("Outputstream close  error! ", e);
				}
			}
		}
	}

	public static String getExtName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index < 0) {
			return "";
		}
		return fileName.substring(index + 1);
	}

	/**
	 * 获取当前路径
	 * 
	 * @return
	 */
	public static String getPath() {
		return ConfigUtil.getProperty("upload.file.path") + File.separator;
	}

	/**
	 * 删除文件夹下的所有单个文件
	 * 
	 * @return moduleName
	 */
	public static void delFiles(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		File[] list = file.listFiles();
		for (File f : list) {
			if (f.isFile()) {
				f.delete();
			}
		}
	}

	/**
	 * 删除指定目录下的所有文件
	 * 
	 * @param folderPath
	 *            目录路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delAllFile1(String folderPath) {
		boolean flag = false;
		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param filePath
	 *            指定文件的路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFile1(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			return flag;
		}
		flag = (new File(filePath)).delete();
		return flag;
	}

	/**
	 * 删除指定文件夹(包括文件夹下的所有文件)
	 * 
	 * @param folderPath
	 *            指定文件夹路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFolder1(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 读取文本文件的内容
	 * 
	 * @param curfile
	 *            文本文件路径
	 * @return 返回文件内容
	 */
	public static String readFile55(String curfile) {
		File f = new File(curfile);
		try {
			if (!f.exists()) throw new Exception();
			FileReader cf = new FileReader(curfile);
			BufferedReader is = new BufferedReader(cf);
			String filecontent = "";
			String str = is.readLine();
			while (str != null) {
				filecontent += str;
				str = is.readLine();
				if (str != null) filecontent += "\n";
			}
			is.close();
			cf.close();
			return filecontent;
		} catch (Exception e) {
			System.err.println("不能读属性文件: " + curfile + " \n" + e.getMessage());
			return "";
		}
	}

	/**
	 * 取指定文件的扩展名
	 * 
	 * @param filePathName
	 *            文件路径
	 * @return 扩展名
	 */
	public static String getFileExt55(String filePathName) {
		int pos = 0;
		pos = filePathName.lastIndexOf('.');
		if (pos != -1)
			return filePathName.substring(pos + 1, filePathName.length());
		else
			return "";
	}

	/**
	 * 读取文件大小
	 * 
	 * @param filename
	 *            指定文件路径
	 * @return 文件大小
	 */
	public static int getFileSize55(String filename) {
		try {
			File fl = new File(filename);
			int length = (int) fl.length();
			return length;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 拷贝文件到指定目录
	 * 
	 * @param srcPath
	 *            源文件路径
	 * @param destPath
	 *            目标文件路径
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile55(String srcPath, String destPath) {
		try {
			File fl = new File(srcPath);
			int length = (int) fl.length();
			FileInputStream is = new FileInputStream(srcPath);
			FileOutputStream os = new FileOutputStream(destPath);
			byte[] b = new byte[length];
			is.read(b);
			os.write(b);
			is.close();
			os.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 通过正则获取文件名
	 */
	public static String getFileName1(String filePath) {
		String regEx = "";
		if (filePath.indexOf("\\") == -1) {
			regEx = ".+\\/(.+)$";
		} else {
			regEx = ".+\\\\(.+)$";
		}
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(filePath);
		String fileName = "";
		if (m.find()) {
			fileName = m.group(1);
		}
		return fileName;
	}

	/**
	 * 通过正则获取文件后缀名
	 * 
	 * @param fileName
	 *            文件名(不包含路径)
	 */
	public static String getFileExtendion55(String fileName) {
		String regEx = "^(.+)\\..+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(fileName);
		String extendtion = "";
		if (m.find()) {
			extendtion = m.group(1);
		}
		return extendtion;
	}

	public final static String separator = "/";
	public final static String split = "_";

	class FilenameFilterImpl implements FilenameFilter {
		private String filter = ".";

		public FilenameFilterImpl(String aFilter) {
			filter = aFilter;
		}

		public boolean accept(File dir, String name) {
			return name.startsWith(filter);
		}
	};

	/**
	 * ��õ�ǰ���ļ�·����ͨ��ǰ������ɣ�
	 * 
	 * @param basePath
	 * @return
	 */
	public static String getNowFilePath(String basePath) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String pathName = formater.format(new Date());
		File dir = new File(basePath + separator + pathName);
		if (!dir.exists()) dir.mkdir();
		return pathName;
	}

	public static String getNewFileName(String oldFileName) {
		oldFileName = oldFileName.replaceAll("'", "").replaceAll("\"", "");
		Calendar date = Calendar.getInstance();
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		int second = date.get(Calendar.SECOND);
		if (oldFileName.length() > 30)
			oldFileName = oldFileName.substring(oldFileName.length() - 30);
		return (new Integer(hour * 3600 + minute * 60 + second).toString())
				+ split + oldFileName;
	}

	public static String getThumbFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos >= 0)
			return fileName.substring(0, pos) + "s" + fileName.substring(pos);
		else
			return fileName + "s";
	}

	/**
	 * This method checks if the given file exists on disk. If it does it's
	 * ignored because that means that the file is allready cached on the
	 * server. If not we dump the text on it.
	 */
	public void dumpAttributeToFile(String attributeValue, String fileName,
			String filePath) throws Exception {
		File outputFile = new File(filePath + separator + fileName);
		PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
		pw.println(attributeValue);
		pw.close();
	}

	/**
	 * �����ļ� This method checks if the given file exists on disk. If it does
	 * it's ignored because that means that the file is allready cached on the
	 * server. If not we take out the stream from the digitalAsset-object and
	 * dumps it.
	 */
	public void dumpAsset(File file, String fileName, String filePath)
			throws Exception {
		long timer = System.currentTimeMillis();
		File outputFile = new File(filePath + separator + fileName);
		if (outputFile.exists()) {
			log.info("The file allready exists so we don't need to dump it again..");
			return;
		}
		FileOutputStream fis = new FileOutputStream(outputFile);
		BufferedOutputStream bos = new BufferedOutputStream(fis);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		int character;
		while ((character = bis.read()) != -1) {
			bos.write(character);
		}
		bos.flush();
		bis.close();
		fis.close();
		bos.close();
		log.info("Time for dumping file " + fileName + ":"
				+ (System.currentTimeMillis() - timer));
	}

	/**
	 * This method removes all images in the digitalAsset directory which
	 * belongs to a certain digital asset.
	 */
	public void deleteDigitalAssets(String filePath, String filePrefix)
			throws Exception {
		try {
			File assetDirectory = new File(filePath);
			File[] files = assetDirectory.listFiles(new FilenameFilterImpl(
					filePrefix));
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				log.info("Deleting file " + file.getPath());
				file.delete();
			}
		} catch (Exception e) {
			log.error("Could not delete the assets for the digitalAsset "
					+ filePrefix + ":" + e.getMessage(), e);
		}
	}

	/**
	 * 压缩
	 * 
	 * @param source
	 *            源文件(夹)路径
	 * @param dest
	 *            目标文件(夹)路径
	 */
	/*
	 * public static void zip(String source, String dest) throws IOException {
	 * OutputStream os = new FileOutputStream(dest); BufferedOutputStream bos =
	 * new BufferedOutputStream(os); ZipOutputStream zos = new
	 * ZipOutputStream(bos);
	 * 
	 * zos.setEncoding("GBK");
	 * 
	 * File file = new File(source);
	 * 
	 * String basePath = null; if (file.isDirectory()) { basePath =
	 * file.getPath(); } else { basePath = file.getParent(); }
	 * 
	 * zipFile(file, basePath, zos);
	 * 
	 * zos.closeEntry(); zos.close(); }
	 */
	/**
	 * 解压缩
	 * 
	 * @param source
	 *            源压缩文件路径
	 * @param dest
	 *            解压路径
	 */
	/*
	 * public static void unzip(String zipFile, String dest) throws IOException
	 * { ZipFile zip = new ZipFile(zipFile); Enumeration<ZipEntry> en =
	 * zip.getEntries(); ZipEntry entry = null; byte[] buffer = new byte[1024];
	 * int length = -1; InputStream input = null; BufferedOutputStream bos =
	 * null; File file = null;
	 * 
	 * while (en.hasMoreElements()) { entry = (ZipEntry) en.nextElement(); if
	 * (entry.isDirectory()) { file = new File(dest, entry.getName()); if
	 * (!file.exists()) { file.mkdir(); } continue; }
	 * 
	 * input = zip.getInputStream(entry); file = new File(dest,
	 * entry.getName()); if (!file.getParentFile().exists()) {
	 * file.getParentFile().mkdirs(); } bos = new BufferedOutputStream(new
	 * FileOutputStream(file));
	 * 
	 * while (true) { length = input.read(buffer); if (length == -1){ break; }
	 * bos.write(buffer, 0, length); } bos.close(); input.close(); }
	 * zip.close(); }
	 */
	private static void zipFile(File source, String basePath,
			ZipOutputStream zos) throws IOException {
		File[] files = new File[0];
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}
		String pathName;
		byte[] buf = new byte[1024];
		int length = 0;
		for (File file : files) {
			if (file.isDirectory()) {
				pathName = file.getPath().substring(basePath.length() + 1)
						+ "/";
				zos.putNextEntry(new ZipEntry(pathName));
				zipFile(file, basePath, zos);
			} else {
				pathName = file.getPath().substring(basePath.length());
				System.out.println(pathName);
				InputStream is = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				zos.putNextEntry(new ZipEntry(pathName));
				while ((length = bis.read(buf)) != -1) {
					zos.write(buf, 0, length);
				}
				is.close();
			}
		}
	}

	// private final static Log logger = LogFactory.getLog(ZipUtils.class);
	/**
	 * 解压缩,注意:文件名不能包含中文.
	 * 
	 * @param in
	 * @param outputDirectory
	 * @throws Exception
	 */
	public static void unzip(final ZipInputStream in, String outputDirectory)
			throws Exception {
		if (in == null) {// 输入流为空
			return;
		}
		if (outputDirectory == null) {
			final String MSG = "输出目录为空null,无法解压";
			logger.debug(MSG);
			return;
		}
		File dirFile = new File(outputDirectory);
		if (dirFile.exists() == false) {
			dirFile.mkdirs();
		}
		ZipEntry z;
		while ((z = in.getNextEntry()) != null) {
			if (z.isDirectory()) {
				String name = z.getName();
				name = name.substring(0, name.length() - 1);
				final String dir = outputDirectory + File.separator + name;
				File f = new File(dir);
				if (f.exists() == false) if (f.mkdir() == false) {
					final String MSG = "创建目录失败!" + dir;
					logger.error(MSG);
					throw new Exception(MSG);
				}
			} else {
				final String file = outputDirectory + File.separator
						+ z.getName();
				File f = new File(file);
				if (f.exists() == true) {
					f.delete();
				}
				FileOutputStream fos = new FileOutputStream(f);
				try {
					final int cache = 2048;
					byte[] b = new byte[cache];
					int aa = 0;
					while ((aa = in.read(b)) != -1) {
						fos.write(b, 0, aa);
					}
				} finally {
					fos.close();
				}
			}
		}
	}

	private static FileUtil instance = new FileUtil();

	public static FileUtil getInstance() {
		return instance;
	}

	public synchronized void zip(String inputFilename, String zipFilename)
			throws IOException {
		zip(new File(inputFilename), zipFilename);
	}

	public synchronized void zip(File inputFile, String zipFilename)
			throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFilename));
		try {
			zip(inputFile, out, "");
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
	}

	private synchronized void zip(File inputFile, ZipOutputStream out,
			String base) throws IOException {
		if (inputFile.isDirectory()) {
			File[] inputFiles = inputFile.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < inputFiles.length; i++) {
				zip(inputFiles[i], out, base + inputFiles[i].getName());
			}
		} else {
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(inputFile.getName()));
			}
			FileInputStream in = new FileInputStream(inputFile);
			try {
				int c;
				byte[] by = new byte[BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		}
	}

	/*
	 * public synchronized void unzip(String zipFilename, String
	 * outputDirectory) throws IOException { File outFile = new
	 * File(outputDirectory); if (!outFile.exists()) { outFile.mkdirs(); }
	 * ZipFile zipFile = new ZipFile(zipFilename); Enumeration en =
	 * zipFile.getEntries(); ZipEntry zipEntry = null; while
	 * (en.hasMoreElements()) { zipEntry = (ZipEntry) en.nextElement(); if
	 * (zipEntry.isDirectory()) { // mkdir directory String dirName =
	 * zipEntry.getName(); dirName = dirName.substring(0, dirName.length() - 1);
	 * 
	 * File f = new File(outFile.getPath() + File.separator + dirName);
	 * f.mkdirs();
	 * 
	 * } else { // unzip file File f = new File(outFile.getPath() +
	 * File.separator + zipEntry.getName()); f.createNewFile(); InputStream in =
	 * zipFile.getInputStream(zipEntry); FileOutputStream out = new
	 * FileOutputStream(f); try { int c; byte[] by = new byte[BUFFEREDSIZE];
	 * while ((c = in.read(by)) != -1) { out.write(by, 0, c); } // out.flush();
	 * } catch (IOException e) { throw e; } finally { out.close(); in.close(); }
	 * } } }
	 */
	private static final int BUFFEREDSIZE = 1024;

	public static void main1(String[] args) {
		FileUtil bean = new FileUtil();
		try {
			// bean.zip("E:\\js\\ext", "C:\\ext.zip");
			// bean.zip("E:\\js\\xtree", "C:\\xtree.zip");
			bean.zip("C:\\Users\\zyl\\Downloads\\easyui",
					"C:\\Users\\zyl\\Downloads\\easyui.zip");
			// bean.unzip("C:\\Users\\zyl\\Downloads\\easyui.zip",
			// "C:\\Users\\zyl\\Downloads\\easyui");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/***************************************************************************************/
	/***************************************************************************************/
public static void main12233(String[] args) throws Exception {
		
		
		//访问 http://localhost:8080/day08_web/loginServlet
		//URL类对web资源的定位
		URL url = new URL("http://localhost:8080/day08_web/loginServlet");
		//获得连接 ，做准备
		URLConnection conn = url.openConnection();
		//必须确定当前请求，是否可以读写
		conn.setDoInput(true); //确定是否可以读，默认是true
		conn.setDoOutput(true);//确定是否可以写，默认是false
		
		//准备发送的数据 -- request填充
		OutputStream out = conn.getOutputStream();
		out.write("user=abcd1234".getBytes());  //此方法将内容写到http请求体中
		
		//连接
		//conn.connect();
		//建立链接之后，获得web资源
		InputStream is = conn.getInputStream();  //会自动链接,只有调用方法，整个请求才生效
//		byte[] buf = new byte[1024];
//		int len = -1;
//		while( (len = is.read(buf)) > -1 ){
//			String str = new String(buf,0,len);
//			System.out.println(str);
//		}
		Scanner scanner = new Scanner(is);
		while(scanner.hasNext()){
			System.out.println("url -->" + scanner.nextLine());
		}
		is.close();
		
	}
	/***************************************************************************************/
	/***************************************************************************************/

// ȡ���ϴ�ʹ�õ���ʱ����ʵĿ¼
public static final String tempPath11 = "/WEB-INF/temp";
public static final String uploadPath = "/WEB-INF/upload";

// ȡ����ʵ�ļ���
public static String getRealFileName(String realFileName) {
	int index = realFileName.lastIndexOf("\\");
	if (index >= 0) {
		// IE6�����
		realFileName = realFileName.substring(index + 1);
	}
	return realFileName;
}

// ȡ��uuid�ļ���
public static String makeUuidFilePath(String uploadPath, String uuidFileName) {
	String uuidFilePath = null;
	int code = uuidFileName.hashCode();// 8
	int dir1 = code & 0xF;// 3
	int dir2 = code >> 4 & 0xF;// A
	File file = new File(uploadPath + "/" + dir1 + "/" + dir2);
	// ����Ŀ¼δ����
	if (!file.exists()) {
		// һ���Դ���N��Ŀ¼
		file.mkdirs();
	}
	uuidFilePath = file.getPath();
	return uuidFilePath;
}

// ȡ��upload/Ŀ¼�µķ�ɢĿ¼
public static String makeUuidFileName(String realFileName) {
	return UUID.randomUUID().toString() + "_" + realFileName;
}

// �ļ�����
public static void doSave(InputStream is, String uuidFileName,
		String uuidFilePath) {
	OutputStream os = null;
	try {
		os = new FileOutputStream(uuidFilePath + "/" + uuidFileName);
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = is.read(buf)) > 0) {
			os.write(buf, 0, len);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

// ���ϴ��ļ���װ��JavaBean������
public static void doUpload(HttpServletRequest request) throws Exception {
//	User user = new User();
	// �����ϴ��ļ�����
	DiskFileItemFactory factory = new DiskFileItemFactory();
	// �����ڴ��л�����Ĵ�С��Ĭ��10K
	factory.setSizeThreshold(100 * 1024);
	// �����ϴ��ļ���ʱ��ŵ�Ŀ¼
	String tempPath = request.getSession().getServletContext()
			.getRealPath(FileUtil.tempPath11);
	factory.setRepository(new File(tempPath));
	// �����ϴ��ļ�����[����]
	ServletFileUpload upload = new ServletFileUpload(factory);
	// �����ϴ��ļ������ı��뷽ʽ
	upload.setHeaderEncoding("UTF-8");
	// �ͻ����ϴ��ļ��Ƿ�ʹ��MIMEЭ�飬
	boolean flag = upload.isMultipartContent(request);
	if (!flag) {
		// ������MIMEЭ���ϴ��ļ�
		throw new ServletException();
	} else {
		/*
		 * ����MIMEЭ���ϴ����ļ�������request�е������ϴ�����
		 * ÿ�����ݷ�װ��һ������FileItem��FileItem�����ͨ�ֶκ��ϴ��ֶζ���
		 */
		List<FileItem> fileItemList = upload.parseRequest(request);
		for (FileItem fileItem : fileItemList) {
			if (fileItem.isFormField()) {
				// �ض�����ͨ�ֶ�
				String fieldName = fileItem.getFieldName();
				String fieldValue = fileItem.getString("UTF-8");
//				user.setUsername(fieldValue);
			} else {
				// �ض����ϴ��ֶ�
				// ������ϴ��ļ�
				if (fileItem.getSize() == 0) {
				}
				String realFileName = FileUtil.getRealFileName(fileItem
						.getName());
				/*
				 * ֻ���ϴ�JPG�ļ� if(!realFileName.endsWith("JPG")){ throw new
				 * UpfileTypeException(); }
				 */
				// ֻ���ϴ�<=200K���ļ�
				if (fileItem.getSize() > 200 * 1024) {
				}
			}
		}// end of for loop
	}
//	return null;
}
// public static void doSave(User user, String uploadPath,List<Up> upList)
// throws Exception {
// for(FileItem fileItem : fileItemList){
// //����Up����
// Up up = new Up();
// up.setUsername(user.getUsername());
// //ȡ��������
// InputStream is = fileItem.getInputStream();
// //ȡ����ʵ�ļ���
// String realFileName = fileItem.getName();
// realFileName = UploadUtil.getRealFileName(realFileName);
// //ȡ��UUID�ļ���
// String uuidFileName = UploadUtil.makeUuidFileName(realFileName);
// //ȡ��UUID�ļ�·��
// String uuidFilePath =
// UploadUtil.makeUuidFilePath(uploadPath,uuidFileName);
// //����
// UploadUtil.doSave(is,uuidFileName,uuidFilePath);
// //�ռ�Up��Ϣ
// up.setUuidFileName(uuidFileName);
// up.setRealFileName(realFileName);
// upList.add(up);
// //ɾ����ʱ�ļ�
// fileItem.delete();
// }
// }
	/***************************************************************************************/
	/***************************************************************************************/
	/***************************************************************************************/

/**
 * APDPlat中的重要打包机制
 * 将jar文件中的某个文件夹里面的内容复制到某个文件夹
 * @param jar 包含静态资源的jar包
 * @param subDir jar中包含待复制静态资源的文件夹名称
 * @param loc 静态资源复制到的目标文件夹
 * @param force 目标静态资源存在的时候是否强制覆盖
 */
public static void unZip(String jar, String subDir, String loc, boolean force){
    try {
        File base=new File(loc);
        if(!base.exists()){
            base.mkdirs();
        }
        
        ZipFile zip=new ZipFile(new File(jar));
        Enumeration<? extends ZipEntry> entrys = zip.entries();
        while(entrys.hasMoreElements()){
            ZipEntry entry = entrys.nextElement();
            String name=entry.getName();
            if(!name.startsWith(subDir)){
                continue;
            }
            //去掉subDir
            name=name.replace(subDir,"").trim();
            if(name.length()<2){
                System.out.println(name+" 长度 < 2");
                continue;
            }
            if(entry.isDirectory()){
                File dir=new File(base,name);
                if(!dir.exists()){
                    dir.mkdirs();
                    System.out.println("创建目录");
                }else{
                	  System.out.println("目录已经存在");
                }
                System.out.println(name+" 是目录");
            }else{
                File file=new File(base,name);
                if(file.exists() && force){
                    file.delete();
                }
                if(!file.exists()){
                    InputStream in=zip.getInputStream(entry);
                     copyFile(in,file);
                     System.out.println("创建文件");
                }else{
                     System.out.println("文件已经存在");
                }
                 System.out.println(name+" 不是目录");
            }
        }
    } catch (ZipException ex) {
    	  System.out.println("文件解压失败");
    } catch (IOException ex) {
    	  System.out.println("文件操作失败");
    }
}

/**
 * 创建ZIP文件
 * @param sourcePath 文件或文件夹路径
 * @param zipPath 生成的zip文件存在路径（包括文件名）
 */
public static void createZip(String sourcePath, String zipPath) {
    FileOutputStream fos = null;
    ZipOutputStream zos = null;
    try {
        fos = new FileOutputStream(zipPath);
        zos = new ZipOutputStream(fos);
        writeZip(new File(sourcePath), "", zos);
    } catch (FileNotFoundException e) {
    	  System.out.println("文件未找到，创建ZIP文件失败");
    } finally {
        try {
            if (zos != null) {
                zos.close();
            }
        } catch (IOException e) {
        	  System.out.println("创建ZIP文件失败");
        }

    }
}

private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
    if(file.exists()){
        if(file.isDirectory()){//处理文件夹
            parentPath+=file.getName()+File.separator;
            File [] files=file.listFiles();
            for(File f:files){
                writeZip(f, parentPath, zos);
            }
        }else{
            FileInputStream fis=null;
            try {
                fis=new FileInputStream(file);
                ZipEntry ze = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(ze);
                byte [] content=new byte[1024];
                int len;
                while((len=fis.read(content))!=-1){
                    zos.write(content,0,len);
                    zos.flush();
                }
                
            
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }finally{
                try {
                    if(fis!=null){
                        fis.close();
                    }
                }catch(IOException e){
                	  System.out.println("创建ZIP文件失败");
                }
            }
        }
    }
}    
public static void main3242(String[] args) {
    FileUtil.createZip("D:\\SysDevDaily.xls", "D:\\backup1.zip");
  //  ZipUtils.createZip("D:\\workspaces\\netbeans\\APDPlat\\APDPlat_Web\\target\\APDPlat_Web-2.2\\platform\\index.jsp", "D:\\workspaces\\netbeans\\APDPlat\\APDPlat_Web\\target\\APDPlat_Web-2.2\\platform\\index.zip");
    
}

public static void copyFile(InputStream in, File outFile){
    OutputStream out = null;
    try {
        byte[] data=readAll(in);
        out = new FileOutputStream(outFile);
        out.write(data, 0, data.length);
        out.close();
    } catch (Exception ex) {
        System.out.println("文件操作失败");
    } finally {
        try {
            if(in!=null){
                in.close();
            }
        } catch (IOException ex) {
         System.out.println("文件操作失败");
        }
        try {
            if(out!=null){
                out.close();
            }
        } catch (IOException ex) {
         System.out.println("文件操作失败");
        }
    }
}

public static byte[] readAll(File file){
    try {
        return readAll(new FileInputStream(file));
    } catch (Exception ex) {
        System.out.println("读取文件失败");
    }
    return null;
}

public static byte[] readAll(InputStream in){
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        byte[] buffer = new byte[1024];
        for (int n ; (n = in.read(buffer))>0 ; ) {
            out.write(buffer, 0, n);
        }
    } catch (IOException ex) {
        System.out.println("读取文件失败");
    }
    return out.toByteArray();
}

	/***************************************************************************************/
	/***************************************************************************************/
	/***************************************************************************************/
/**
 * @功能 读取流
 * @param inStream
 * @return 字节数组
 * @throws Exception
 */
public static byte[] readStream(InputStream inStream) throws Exception {
	int count = 0;
	while (count == 0) {
		count = inStream.available();
	}
	byte[] b = new byte[count];
	inStream.read(b);
	return b;

}
	/***************************************************************************************/
	/***************************************************************************************/
/***************************************************************************************/
/***************************************************************************************/
/***************************************************************************************/
/***************************************************************************************/

private static String path = "D:/";
private static String filenameTemp;

public static boolean creatTxtFile(String name) throws IOException
{
	boolean flag = false;
	filenameTemp = path + name + ".txt";
	File filename = new File(filenameTemp);
	if (!filename.exists())
	{
		filename.createNewFile();
		flag = true;
	}
	return flag;
}

@SuppressWarnings("unused")
public static boolean writeTxtFile(String newStr ) throws IOException
{

	// 先读取原有文件内容，然后进行写入操作
	boolean flag = false;
	String filein = newStr + "\r\n";
	String temp = "";

	FileInputStream fis = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	FileOutputStream fos = null;
	PrintWriter pw = null;
	try
	{
		// 文件路径
		File file = new File(filenameTemp);
		// 将文件读入输入流
		fis = new FileInputStream(file);
		isr = new InputStreamReader(fis);
		br = new BufferedReader(isr);
		StringBuffer buf = new StringBuffer();

		// 保存该文件原有的内容
		for (int j = 1; (temp = br.readLine()) != null; j++)
		{
			buf = buf.append(temp);
			// System.getProperty("line.separator")
			// 行与行之间的分隔符 相当于“\n”
			buf = buf.append(System.getProperty("line.separator"));
		}
		buf.append(filein);

		fos = new FileOutputStream(file);
		pw = new PrintWriter(fos);
		pw.write(buf.toString().toCharArray());
		pw.flush();
		flag = true;
	} catch (IOException e1){
		throw e1;
	} finally{
		if (pw != null)
		{
			pw.close();
		}
		if (fos != null)
		{
			fos.close();
		}
		if (br != null)
		{
			br.close();
		}
		if (isr != null)
		{
			isr.close();
		}
		if (fis != null)
		{
			fis.close();
		}
	}
	return flag;
}
public static void main1555(String[] args )
{
	
	
	
	try
	{
		creatTxtFile("tet");
		writeTxtFile("1312312312312312");
	} catch (IOException e)
	{
		e.printStackTrace();
	}
}

/***************************************************************************************/
/***************************************************************************************/
/***************************************************************************************/
}
