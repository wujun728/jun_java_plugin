package cn.ipanel.app.newspapers.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

/**
 * FTP工具类<br>
 * 注：上传，可上传文件、文件夹；下载，仅实现下载文件功能，不能识别子文件夹
 * 
 * @author Link Wang
 * @version 1.0.0
 */
public class FtpUtil
{

	private static Logger logger = Logger.getLogger(FtpUtil.class);

	private FtpClient ftpClient;

	/**
	 * 连接FTP服务器，使用默认FTP端口
	 * 
	 * @author Link Wang
	 * @since
	 * @param serverIp
	 *            服务器Ip地址
	 * @param user
	 *            登陆用户
	 * @param password
	 *            密码
	 * @throws IOException
	 */
	public void connect(String serverIp, String user, String password) throws Exception
	{
		try
		{
			// serverIp：FTP服务器的IP地址；
			// user:登录FTP服务器的用户名
			// password：登录FTP服务器的用户名的口令；
			ftpClient = new FtpClient();
			ftpClient.openServer(serverIp);
			ftpClient.login(user, password);
			// 用二进制传输数据
			ftpClient.binary();
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 连接ftp服务器，指定端口
	 * 
	 * @author Link Wang
	 * @since
	 * @param serverIp
	 * @param port
	 *            服务器FTP端口号
	 * @param user
	 * @param password
	 * @throws IOException
	 */
	public void connect(String serverIp, int port, String user, String password) throws Exception
	{
		try
		{
			// serverIp：FTP服务器的IP地址；
			// post:FTP服务器端口
			// user:登录FTP服务器的用户名
			// password：登录FTP服务器的用户名的口令；
			ftpClient = new FtpClient();
			ftpClient.openServer(serverIp, port);
			ftpClient.login(user, password);
			// 用2进制传输数据
			ftpClient.binary();
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 断开与ftp服务器的连接
	 * 
	 * @author Link Wang
	 * @since
	 * @throws IOException
	 */
	public void disConnect() throws Exception
	{
		try
		{
			if (ftpClient != null)
			{
				ftpClient.sendServer("QUIT\r\n");
				ftpClient.readServerResponse();
				// ftpClient.closeServer();
			}
		}
		catch (IOException ex)
		{
			logger.error("DisConnect to FTP server failure! Detail:", ex);
			throw new Exception(ex);
		}
	}

	/**
	 * 上传文件至FTP服务器，保持原文件名
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在或不能读取; >0 成功上传，返回文件的大小
	 * @param localFile
	 *            待上传的本地文件
	 */
	public long upload(File localFile) throws Exception
	{
		if (localFile == null)
		{
			return -1;
		}
		return this.upload(localFile, localFile.getName());
	}

	/**
	 * 上传文件至FTP服务器，保持原文件名
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在或不能读取; >0 成功上传，返回文件的大小
	 * @param localFilePath
	 *            待上传的本地文件路径
	 */
	public long upload(String localFilePath) throws Exception
	{
		return this.upload(new File(localFilePath));
	}

	/**
	 * 上传文件至FTP服务器，并重命名文件
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在或不能读取; >0 成功上传，返回文件的大小
	 * @param localFilePath
	 *            待上传的本地文件路径
	 * @param rename
	 *            远程文件重命名
	 */
	public long upload(String localFilePath, String rename) throws Exception
	{
		return this.upload(new File(localFilePath), rename);
	}

	/**
	 * 上传文件至FTP服务器，并重命名文件
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在或不能读取; >0 成功上传，返回文件的大小
	 * @param localFile
	 *            待上传的本地文件
	 * @param rename
	 *            远程文件重命名
	 */
	public long upload(File localFile, String rename) throws Exception
	{
		if (localFile == null || !localFile.exists() || !localFile.canRead())
		{
			return -1;
		}
		long fileSize = localFile.length();
		try
		{
			if (localFile.isDirectory())
			{
				ftpClient.sendServer("XMKD " + rename + "\r\n");
				ftpClient.readServerResponse();
				File[] subFiles = localFile.listFiles();
				ftpClient.cd(rename);
				try
				{
					for (int i = 0; i < subFiles.length; i++)
					{
						fileSize += upload(subFiles[i]);
					}
				}
				finally
				{
					ftpClient.cdUp();
				}
			}
			else
			{
				this.writeFileToServer(localFile, rename);
			}
			return fileSize;
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 从ftp下载文件到本地
	 * 
	 * @throws java.lang.Exception
	 * @return
	 * @param localFilePath
	 *            本地生成的文件名
	 * @param remoteFilePath
	 *            服务器上的文件名
	 */
	public long download(String remoteFilePath, String localFilePath) throws Exception
	{
		long result = 0;
		TelnetInputStream tis = null;
		RandomAccessFile raf = null;
		DataInputStream puts = null;
		try
		{
			tis = ftpClient.get(remoteFilePath);
			raf = new RandomAccessFile(new File(localFilePath), "rw");
			raf.seek(0);
			int ch;
			puts = new DataInputStream(tis);
			while ((ch = puts.read()) >= 0)
			{
				raf.write(ch);
			}
		}
		catch (Exception ex)
		{
			logger.error("Downloading file failure! Detail:", ex);
			throw new Exception(ex);
		}
		finally
		{
			try
			{
				puts.close();
				
				if (tis != null)
				{
					tis.close();
				}
				if (raf != null)
				{
					raf.close();
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex);
			}
		}
		return result;
	}

	/**
	 * 设置FTP服务器的当前路径<br>
	 * 可以是绝对路径，也可以是相对路径
	 * 
	 * @author Link Wang
	 * @since
	 * @param dirPath
	 *            服务器文件夹路径，空代表ftp根目录
	 * @throws IOException
	 */
	public void cd(String dirPath) throws Exception
	{
		try
		{
			// path：FTP服务器上的路径,是ftp服务器下主目录的子目录
			if (dirPath != null && dirPath.length() > 0)
			{
				ftpClient.cd(dirPath);
			}
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 在服务器上创建指定路径的目录，并转到此目录
	 * 
	 * @author Link Wang
	 * @since
	 * @param dirPath
	 * @throws Exception
	 */
	public void mkd(String dirPath) throws Exception
	{
		try
		{
			if (dirPath != null && dirPath.length() > 0)
			{
				StringTokenizer st = new StringTokenizer(dirPath.replaceAll("\\\\", "/"), "/");
				String dirName = "";
				while (st.hasMoreElements())
				{
					dirName = (String) st.nextElement();
					ftpClient.sendServer("XMKD " + dirName + "\r\n");
					ftpClient.readServerResponse();
					ftpClient.cd(dirName);
				}
			}
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 删除FTP服务器目录
	 * 
	 * @author Link Wang
	 * @since
	 * @param directory
	 * @throws Exception
	 */
	public void rmd(String directory) throws Exception
	{
		try
		{
			if (directory != null && directory.length() > 0)
			{
				ftpClient.cd(directory);
				try
				{
					this.cld();
				}
				finally
				{
					ftpClient.cdUp();
				}
				ftpClient.sendServer("XRMD " + directory + "\r\n");
				ftpClient.readServerResponse();
			}
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 清空当前目录
	 * 
	 * @author Link Wang
	 * @since
	 * @throws Exception
	 */
	public void cld() throws Exception
	{
		// 删除文件
		for (Iterator<String> it = getFileList().iterator(); it.hasNext();)
		{
			this.delf(it.next());
		}
		// 删除文件夹
		for (Iterator<String> it = getDirList().iterator(); it.hasNext();)
		{
			this.rmd(it.next());
		}
	}

	/**
	 * 删除FTP服务器文件
	 * 
	 * @author Link Wang
	 * @since
	 * @param filePath
	 * @throws Exception
	 */
	public void delf(String filePath) throws Exception
	{
		try
		{
			if (filePath != null && filePath.length() > 0)
			{
				ftpClient.sendServer("DELE " + filePath + "\r\n");
				ftpClient.readServerResponse();
			}
		}
		catch (Exception ex)
		{
			throw new Exception(ex);
		}
	}

	/**
	 * 以指定文件名，将本地文件写到FTP服务器
	 * 
	 * @author Link Wang
	 * @create 2007-11-9 下午03:06:53
	 * @since
	 * @param localFile
	 *            待上传的本地文件
	 * @param fileName
	 *            写入远程FTP服务器的文件名
	 * @throws Exception
	 */
	private void writeFileToServer(File localFile, String fileName) throws Exception {
		TelnetOutputStream tos = null;
		FileInputStream fis = new FileInputStream(localFile);
		try {
			tos = ftpClient.put(fileName);
			byte[] bytes = new byte[102400];
			int c;
			while ((c = fis.read(bytes)) != -1) {
				tos.flush();
				tos.write(bytes, 0, c);
			}
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (tos != null) {
				tos.flush();
				tos.close();
			}
		}
	}

	/**
	 * 取得FTP上某个目录下的所有文件名列表
	 * 
	 * @author Link Wang
	 * @since
	 * @return
	 * @throws Exception
	 */
	public List<String> getFileList() throws Exception
	{
		List<String> fileList = new ArrayList<String>();
		BufferedReader br = null;
		try
		{
			String fileItem;
			br = new BufferedReader(new InputStreamReader(ftpClient.list()));
			while ((fileItem = br.readLine()) != null)
			{
				if (fileItem.startsWith("-") && !fileItem.endsWith(".") && !fileItem.endsWith(".."))
				{
					fileList.add(parseFileName(fileItem));
				}
			}
		}
		catch (Exception ex)
		{
			logger.error("Failure to get directory list from ftp server!", ex);
			throw new Exception(ex);
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (Exception ex)
				{
					throw new Exception(ex);
				}
			}
		}
		return fileList;
	}

	/**
	 * 取得FTP上某个目录下的所有子文件夹名列表
	 * 
	 * @author Link Wang
	 * @since
	 * @return
	 * @throws Exception
	 */
	public List<String> getDirList() throws Exception
	{
		List<String> dirList = new ArrayList<String>();
		BufferedReader br = null;
		try
		{
			String fileItem;
			br = new BufferedReader(new InputStreamReader(ftpClient.list()));
			while ((fileItem = br.readLine()) != null)
			{
				if (fileItem.startsWith("d") && !fileItem.endsWith(".") && !fileItem.endsWith(".."))
				{
					dirList.add(parseFileName(fileItem));
				}
			}
		}
		catch (Exception ex)
		{
			logger.info("Failure to get directory list from ftp server!", ex);
			throw new Exception(ex);
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (Exception ex)
				{
					throw new Exception(ex);
				}
			}
		}
		return dirList;
	}

	/**
	 * 从文件信息中解析出文件（文件夹）名
	 * 
	 * @author Link Wang
	 * @since
	 * @param fileItem
	 * @return
	 * @throws Exception
	 */
	private String parseFileName(String fileItem) throws Exception
	{
		StringTokenizer st = new StringTokenizer(fileItem);
		int index = 0;
		while (st.hasMoreTokens())
		{
			if (index < 8)
			{
				st.nextToken();
			}
			else
			{
				return st.nextToken("").trim();
			}
			index++;
		}
		return null;
	}

	/**
	 * 上传下载测试
	 * 
	 * @author Link Wang
	 * @since
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		FtpUtil ftp = new FtpUtil();
		try
		{
			// 连接ftp服务器
			ftp.connect("192.168.36.151", "sasftp", "sasftp");
			// 上传文件
			ftp.cd("/home/sasftp");
			// ftp.cld();
			// long fileSize = ftp.upload("D:/My Documents/酷6视频",
			// "xufeitewwst");
			// if (fileSize == -1) {
			// logger.info("Uploading file failure! Because file do not exists!");
			// } else if (fileSize == -2) {
			// logger.info("Uploading file failure! Because file is empty!");
			// } else {
			// logger.info("Uploading file success! File size: " + fileSize);
			// }
			// 取得bbbbbb文件夹下的所有文件列表,并下载到本地保存

			List<String> list = new ArrayList<String>();
			list.add("czybxw.txt");
			list.add("zjyb.txt");
			for (int i = 0; i < list.size(); i++)
			{
				String fileName = (String) list.get(i);
				System.out.println(fileName);
				ftp.download(fileName, "E:\\text" + File.separator + fileName);
			}
		}
		catch (Exception ex)
		{
			logger.error("Uploading file failure! Detail:", ex);
		}
		finally
		{
			ftp.disConnect();
		}
	}
}
