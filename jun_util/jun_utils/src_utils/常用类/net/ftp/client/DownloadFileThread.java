package book.net.ftp.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import sun.net.ftp.FtpClient;

/**
 * 下载文件的线程
 */
public class DownloadFileThread extends Thread {
	
	private boolean running = false;
	
	// FTP服务器的IP
	String ip = "";
	// 连接服务器的用户名和密码
	String username = "";
	String password = "";
	// 待下载文件在FTP上的目录和文件名
	String ftpDir = "";
	String ftpFileName = "";
	// 下载到本地后的文件名
	String localFileName = "";
	
	MainFrame frame = null;

	// 构造方法
	public DownloadFileThread(MainFrame frame, String server, String username,
			String password, String path, String filename, String userpath) {
		this.ip = server;
		this.username = username;
		this.password = password;
		this.ftpDir = path;
		this.ftpFileName = filename;
		this.localFileName = userpath;
		this.frame = frame;
	}

	public void run() {
		running = true;
		FileOutputStream os = null;
		InputStream is = null;
		FtpClient ftpClient = null;
		try {
			String savefilename = localFileName;
			// 新建FTP客户端
			ftpClient = new FtpClient();
			ftpClient.openServer(ip);
			// 登陆
			ftpClient.login(username, password);
			if (ftpDir.length() != 0){
				// 切换到指定的目录下
				ftpClient.cd(ftpDir);
			}
			// 以二进制格式打开FTP
			ftpClient.binary();
			// 打开文件
			is = ftpClient.get(ftpFileName);
			// 保存到本地
			File file_out = new File(savefilename);
			os = new FileOutputStream(file_out);
			byte[] bytes = new byte[1024];
			// 开始复制
			int c;
			frame.taskList.add(ftpFileName);
			frame.consoleTextArea.append("downloading the file " + ftpFileName
					+ " , wait for a moment!\n");
			while (running && ((c = is.read(bytes)) != -1)) {
				os.write(bytes, 0, c);
			}
			if (running){
				// 下载成功后，清除任务和线程
				frame.taskList.remove(ftpFileName);
				frame.consoleTextArea.append(" the file " + ftpFileName
						+ " download has finished!\n");
				frame.performTaskThreads.removeElement(this);
			}

		} catch (Exception e) {
			// 下载失败
			frame.consoleTextArea.append(" the file " + ftpFileName
					+ " ,download has problem!\n");
			frame.performTaskThreads.removeElement(this);
		} finally {
			try {
				if (is != null){
					is.close();
				}
				if (os != null){
					os.close();
				}
				if (ftpClient != null){
					ftpClient.closeServer();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void toStop(){
		this.running = false;
	}

}