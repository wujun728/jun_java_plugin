package book.net.ftp.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import sun.net.ftp.FtpClient;

/**
 * 上传文件到FTP服务器
 */
public class UploadFileThread extends Thread {

	private boolean running = false;
	// FTP服务器IP地址
	String ip = "";
	// 连接服务器的用户名和密码
	String username = "";
	String password = "";
	// 文件上传到FTP服务器上的目录和文件名
	String ftpDir = "";
	String ftpFileName = "";
	// 待上传的文件的文件全名
	String localFileFullName = "";
	
	MainFrame frame = null;

	// 构造方法
	public UploadFileThread(MainFrame frame, String serverIP, String username,
			String password, String ftpDir, String ftpFileName, String localFileName) {
		this.ip = serverIP;
		this.username = username;
		this.password = password;
		this.ftpDir = ftpDir;
		this.ftpFileName = ftpFileName;
		this.localFileFullName = localFileName;
		this.frame = frame;
	}

	public void run() {
		running = true;
		FtpClient ftpClient = null;
		OutputStream os = null;
		FileInputStream is = null;
		try {
			String savefilename = localFileFullName;
			// 新建一个FTP客户端连接
			ftpClient = new FtpClient();
			ftpClient.openServer(ip);
			// 登陆到FTP服务器
			ftpClient.login(username, password);
			if (ftpDir.length() != 0){
				// 切换到目标目录下
				ftpClient.cd(ftpDir);
			}
			// 以二进制打开FTP
			ftpClient.binary();
			// 准备在FTP服务器上存放文件
			os = ftpClient.put(ftpFileName);
			// 打开本地待上传的文件
			File file_in = new File(savefilename);
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];

			// 开始拷贝
			int c;
			frame.taskList.add(ftpFileName);
			frame.consoleTextArea.append("uploading the file " + ftpFileName
					+ " , wait for a moment!\n");
			while (running && ((c = is.read(bytes)) != -1)) {
				os.write(bytes, 0, c);
			}
			if (running) {
				// 此时已经上传完毕，从任务队列中删除本上传任务
				frame.taskList.remove(ftpFileName);
				// 控制台信息中添加文件上传完毕的信息
				frame.consoleTextArea.append(" the file " + ftpFileName
						+ " upload has finished!\n");
				// 更新表格数据
				frame.setTableData();
				// 清除任务线程
				frame.performTaskThreads.removeElement(this);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// 上传失败
			frame.consoleTextArea.append(" the file " + ftpFileName
					+ " ,upload has problem!\n");
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