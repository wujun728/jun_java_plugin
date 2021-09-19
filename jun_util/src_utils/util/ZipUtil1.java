/**
 * Program  : ZipUtil1.java
 * Author   : leigq
 * Create   : 2010-11-12 下午02:09:27
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;


/**
 * 
 * @author leigq
 * @version 1.0.0
 * @2010-11-12 下午02:09:27
 */
public class ZipUtil1 {
	private static final int BUFFER = 2048;

	private final static Logger logger = Logger.getLogger(ZipUtil.class);

	/**
	 * 把一个zip文件解压在一个指定的目录中
	 * 
	 * 注意：该解压后的目录，已经去掉了zip包的根目录
	 * 
	 * @author kongxiangpeng
	 * @create 2007-5-18 下午04:09:01
	 * @since
	 * @param zFile
	 * @param folder
	 * @throws Exception
	 */
	public static void zipToFolder(File zFile, String folder) throws Exception
	{
		String baseDir = folder;
		ZipFile zfile = new ZipFile(zFile);
		Enumeration<?> zList = zfile.entries();
		ZipEntry ze = null;
		byte buf[] = new byte[BUFFER];
		try
		{
			while (zList.hasMoreElements())
			{
				ze = (ZipEntry) zList.nextElement();
				if (ze.isDirectory())
				{
					logger.debug("Dir: " + ze.getName() + " skipped..");
					continue;
				}
				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(getRealFileName(baseDir, ze
								.getName())));
				InputStream is = new BufferedInputStream(zfile
						.getInputStream(ze));
				int readLen = 0;
				try
				{
					while ((readLen = is.read(buf, 0, BUFFER)) != -1)
					{
						os.write(buf, 0, readLen);
					}
				}
				finally
				{
					if (is != null)
					{
						is.close();
					}
					if (os != null)
					{
						os.close();
					}
				}
			}
		}
		finally
		{
			if (zfile != null)
				zfile.close();
		}
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名. 注意：该解压后的目录，已经去掉了其根目录
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	public static File getRealFileName(String baseDir, String absFileName)
	{
		String[] dirs = absFileName.split("/");

		File ret = new File(baseDir);
		if (dirs.length > 1)
		{
			for (int i = 1; i < dirs.length - 1; i++)
			{
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists())
		{
			ret.mkdirs();
		}
		// logger.info("real name is : " + ret);
		ret = new File(ret, dirs[dirs.length - 1]);
		return ret;
	}
	
	/**
	 * 将文件夹filesFolder的内容打包到zipFilePath路径,绝对路径
	 * 
	 * @param filesFolder
	 * @param zipFilePath
	 * @return zipFile
	 * @author lihuan
	 * @create 2010-10-19 下午07:05:16
	 */
	public static File getCompressToZip(String filesFolder,String zipFilePath,String zipFileName){
		File baseFolder = new File(filesFolder);
		ZipOutputStream zos = null;
		String _zipFileName = zipFileName + ".zip";
		File zipFile = new File(zipFilePath, _zipFileName);
		try {
			if (!zipFile.exists()) {
				File parentfolder = zipFile.getParentFile();
				if (!parentfolder.exists())
					parentfolder.mkdirs();
				zipFile.createNewFile();
			}
			
			zos = new ZipOutputStream(new FileOutputStream(zipFile));
			// 将文件夹压缩为的zip文件, 去掉第一层目录
			File[] listFiles = baseFolder.listFiles();
			int listFilesSize = listFiles.length;
			for (int i = 0; i < listFilesSize; i++) {
				zipFiles(zos, listFiles[i], "");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.flush();
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return zipFile;
	}
	
	/**
	 * 定义压缩文件及目录为zip文件的方法
	 * 
	 * @author lihuan
	 * @param zos
	 * @param file
	 * @throws Exception
	 */
	public static void zipFiles(ZipOutputStream zos, File file, String baseDir) {
		FileInputStream in = null;
		String fileName = file.getName();
		try {
			// 判断File是否为目录
			if (file.isDirectory()) {
				// 获取file目录下所有文件及目录,作为一个File数组返回
				File[] files = file.listFiles();
				baseDir =  baseDir.length() == 0 ? "" : baseDir;
				String entryName = baseDir + fileName + "/";
				zos.putNextEntry(new ZipEntry(entryName));
				int subFileSize = files.length;
				for (int i = 0; i < subFileSize; i++) {
					zipFiles(zos, files[i], entryName);
				}
			} else {
				zos.putNextEntry(new ZipEntry(baseDir + fileName));
				in = new FileInputStream(file);
				int lenth = 0;
				int buffSize = 1024;
				byte[] buff = new byte[buffSize];
				while ((lenth = in.read(buff, 0, buffSize)) != -1) {
					zos.write(buff, 0, lenth);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	
	public static void main(String[] args) {
		Date d1 = new Date();
		try {
			getCompressToZip("E:\\FTP_FOLDER\\_PUBLISH_NEWS", "E:\\FTP_FOLDER\\common_ftp", "abc");
			
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date d2 = new Date();
		System.out.println(d2.getTime() - d1.getTime() / 1000 + "s");
	}
}

