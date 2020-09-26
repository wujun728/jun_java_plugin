package com.camel.server.route.ftp.ftp_1;

import org.apache.camel.builder.RouteBuilder;

/**
 * 从FTP服务器上下载文件至本地.
 * 
 * @author CYX
 * @time 2017年12月19日下午8:12:35
 */
public class FTPToLocalRouteBuilder extends RouteBuilder {

	// 从FTP上下载文件
	@Override
	public void configure() throws Exception {
		from("sftp://192.168.0.214/?username=ftpuser&password=kingyea123&binary=true&passiveMode=true&delete=false&delay=500").to("file:D:/A/outbox");
	}

}
