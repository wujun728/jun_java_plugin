package com.jun.plugin.file.ftp4j;
/** 
 * FTP实体对象 
 *  
 * @author 张明学 
 *  
 */  
public class FTPModel {  
    private String ftpId;  
    private String username;  
    private String password;  
    private String url;  
    private int port;  
    private String remoteDir;  
    public FTPModel() {  
    }  
    public FTPModel(String username, String password, String url, int port,  
            String remoteDir) {  
        this.username = username;  
        this.password = password;  
        this.url = url;  
        this.port = port;  
        this.remoteDir = remoteDir;  
    }  
}  