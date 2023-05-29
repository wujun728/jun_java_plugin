package org.myframework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FtpUtil {
	private  Log log = LogFactory.getLog(this.getClass());
	
    String ip;   
    int port;   
    String user;   
    String pwd;   
    String remotePath;   
    String localPath;   
    FtpClient ftpClient;   
  
    /**
     * 根据默认配置连接ftp服务器
     * @return
     * @throws Exception
     */
    public boolean connectServer() throws Exception{
    	return connectServer(getIp(), getPort(), getUser(), getPwd());
    }
    /**  
     * 连接ftp服务器  
     * @param ip  
     * @param port  
     * @param user  
     * @param pwd  
     * @return  
     * @throws Exception  
     */  
    public boolean connectServer(String ip, int port, String user, String pwd)   
        throws Exception {   
        boolean isSuccess = false;   
        try {   
            ftpClient = new FtpClient();   
            ftpClient.openServer(ip, port);   
            ftpClient.login(user, pwd);   
            isSuccess = true;   
        } catch (Exception ex) {   
            throw new Exception("Connect ftp server error:" + ex.getMessage());   
        }   
        return isSuccess;   
    }   
  
    /**  
     * 下载文件  
     * @param remotePath  
     * @param localPath  
     * @param filename  
     * @throws Exception  
     */  
    public void downloadFile(String remotePath,String localPath, String filename) throws Exception {   
        try {   
            if (connectServer(getIp(), getPort(), getUser(), getPwd())) {   
                if (remotePath.length() != 0)   
                    ftpClient.cd(remotePath);   
                ftpClient.binary();   
                TelnetInputStream is = ftpClient.get(filename);   
                File file_out = new File(localPath + File.separator + filename);   
                FileOutputStream os = new FileOutputStream(file_out);   
                byte[] bytes = new byte[1024];   
                int c;   
                while ((c = is.read(bytes)) != -1) {   
                    os.write(bytes, 0, c);   
                }   
                is.close();   
                os.close();   
                ftpClient.closeServer();   
            }   
        } catch (Exception ex) {   
            throw new Exception("ftp download file error:" + ex.getMessage());   
        }   
    }   
    
 
  
    /**  
     * 上传文件  
     * @param remotePath  
     * @param localPath  
     * @param filename  
     * @throws Exception  
     */  
    public void uploadFile(String remotePath,String localPath, String filename) throws Exception {   
        try {   
            if (this.connectServer(getIp(), getPort(), getUser(), getPwd())) {   
                if (remotePath.length() != 0)   
                    ftpClient.cd(remotePath);   
                ftpClient.binary();   
                TelnetOutputStream os = ftpClient.put(filename);   
                File file_in = new File(localPath + File.separator + filename);   
                FileInputStream is = new FileInputStream(file_in);   
                byte[] bytes = new byte[1024];   
                int c;   
                while ((c = is.read(bytes)) != -1) {   
                    os.write(bytes, 0, c);   
                }   
                is.close();   
                os.close();   
                ftpClient.closeServer();   
            }   
        } catch (Exception ex) {   
            throw new Exception("ftp upload file error:" + ex.getMessage());   
        }   
    }   
    /**  
     * 下载文件  
     * @param filename  
     */  
    public void downloadFile( String filename)  {   
        try {   
        	 if (this.connectServer()) {      
                if (remotePath.length() != 0)   
                    ftpClient.cd(remotePath);   
                ftpClient.binary();   
                TelnetInputStream is = ftpClient.get(filename);   
                File file_out = new File(localPath + File.separator + filename);   
                FileOutputStream os = new FileOutputStream(file_out);   
                byte[] bytes = new byte[1024];   
                int c;   
                while ((c = is.read(bytes)) != -1) {   
                    os.write(bytes, 0, c);   
                }   
                is.close();   
                os.close();   
                ftpClient.closeServer();   
            }   
        } catch (Exception ex) {   
        	log.error("ftp download file error:" + ex.getMessage());   
        }   
    }   
    /**  
     * @param filename FTP存放文件名  
     */  
    public void uploadFile( String filename)  {   
        try {   
            if (this.connectServer()) {   
                if (remotePath.length() != 0)   
                    ftpClient.cd(remotePath);   
                ftpClient.binary();   
                TelnetOutputStream os = ftpClient.put(filename);   
                File file_in = new File(localPath + File.separator + filename);   
                FileInputStream is = new FileInputStream(file_in);   
                byte[] bytes = new byte[1024];   
                int c;   
                while ((c = is.read(bytes)) != -1) {   
                    os.write(bytes, 0, c);   
                }   
                is.close();   
                os.close();   
                ftpClient.closeServer();   
            }   
        } catch (Exception ex) {   
           log.error("ftp upload file error:" + ex.getMessage());   
        }   
    }  
    
    /**
     * @param fileStream 上传文件流
     * @param filename FTP存放文件名
     */
    public void uploadFile( InputStream is ,String filename ) {
		try {
			if (this.connectServer()&&remotePath.length() != 0) { 
	            ftpClient.cd(remotePath);
				ftpClient.binary();   
	            TelnetOutputStream os = ftpClient.put(filename);  
				int c; 
				byte[] bytes = new byte[1024]; 
	            while ((c = is.read(bytes)) != -1) {   
	                os.write(bytes, 0, c);   
	            }   
	            is.close();   
	            os.close();   
	            ftpClient.closeServer(); 
			}
		} catch (Exception ex) {
			log.error("ftp upload file error:" + ex.getMessage());   
		}
	}
    
    public void closeServer() throws Exception{
    	ftpClient.closeServer();
    }

    
    /**  
     * 下载文件  
     * @param 文件绝对目录filePath  
     */  
    public InputStream get(String filePath)  {   
    	String remotePath = filePath.substring(0,filePath.lastIndexOf("/")+1);
		String filename =  filePath.substring(filePath.lastIndexOf("/")+1);
        try {   
        	if (this.connectServer()) {      
                ftpClient.cd(remotePath);   
                ftpClient.binary();   
                return ftpClient.get(filename);
            }   
        } catch (Exception ex) {   
        	log.error("ftp download file error:" + ex.getMessage());   
        }
		return null;   
    } 
  
    /**  
     * @return  
     */  
    public String getIp() {   
        return ip;   
    }   
  
    /**  
     * @return  
     */  
    public int getPort() {   
        return port;   
    }   
  
    /**  
     * @return  
     */  
    public String getPwd() {   
        return pwd;   
    }   
  
    /**  
     * @return  
     */  
    public String getUser() {   
        return user;   
    }   
  
    /**  
     * @param string  
     */  
    public void setIp(String string) {   
        ip = string;   
    }   
  
    /**  
     * @param i  
     */  
    public void setPort(int i) {   
        port = i;   
    }   
  
    /**  
     * @param string  
     */  
    public void setPwd(String string) {   
        pwd = string;   
    }   
  
    /**  
     * @param string  
     */  
    public void setUser(String string) {   
        user = string;   
    }   
  
    /**  
     * @return  
     */  
    public FtpClient getFtpClient() {   
        return ftpClient;   
    }   
  
    /**  
     * @param client  
     */  
    public void setFtpClient(FtpClient client) {   
        ftpClient = client;   
    }   
  
    /**  
     * @return  
     */  
    public String getRemotePath() {   
        return remotePath;   
    }   
  
    /**  
     * @param string  
     */  
    public void setRemotePath(String string) {   
        remotePath = string;   
    }   
  
    /**  
     * @return  
     */  
    public String getLocalPath() {   
        return localPath;   
    }   
  
    /**  
     * @param string  
     */  
    public void setLocalPath(String string) {   
        localPath = string;   
    }   
    
    
	public FtpUtil(String ip, int port, String user, String pwd,
			String remotePath, String localPath) {
		super();
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		this.remotePath = remotePath;
		this.localPath = localPath;
	}
	
	

	public FtpUtil(String ip, int port, String user, String pwd) {
		super();
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}
	
	public FtpUtil() {
		super();
	}
}
