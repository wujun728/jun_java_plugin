package com.camel.server.route.ftp.ftp_1;

import org.apache.camel.builder.RouteBuilder;

/**
 * 本地文件上传至FTP服务器.<br>
 * 注意：每个FTP用户名和密码都对应单独一个目录.<br>
 * 操作FTP目录时，需要注意FTP目录的权限问题.<br>
 * 
 * @author CYX
 * @time 2017年12月19日下午8:08:01
 */
public class LocalToFTPRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// 注意：10.0.227.66 IP后面的斜杠'/'代表的就是FTP用户名密码对应的那个目录
//		from("file:D:\\A\\inbox?delay=30000&delete=false").to("sftp://192.168.0.214/../www?username=root&password=kingyea");
		from("file:D:/A/inbox?noop=true&idempotent=false&delete=false").to("sftp://192.168.0.214/?username=ftpuser&password=kingyea123");
	}

}
