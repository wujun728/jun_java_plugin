package com.jun.plugin.file.ftp4j;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

/**
 * FTP操作测试
 * 
 * @说明
 * @author cuisuqiang
 * @version 1.0
 * @since
 */
public class Ftp4jTest {
	public static void main(String[] args) {
		try {
			// 创建客户端
			FTPClient client = new FTPClient();
			// 不指定端口，则使用默认端口21
			client.connect("192.168.1.122", 21);
			// 用户登录
			client.login("123", "123123");
			// 打印地址信息
			System.out.println(client);

			// 安全退出
			client.disconnect(true);
			// 强制退出
			client.disconnect(false);

			// 当前文件夹
			String dir = client.currentDirectory();
			System.out.println(dir);

			// 创建目录
			client.createDirectory("123");
			// 改变当前文件夹 绝对路径
			// client.changeDirectory(dir + "/123");
			// 改变当前文件夹 相对路径
			client.changeDirectory("123");
			// 当前文件夹
			dir = client.currentDirectory();
			System.out.println(dir);

			client.changeDirectoryUp();
			// 重新获得 当前文件夹
			dir = client.currentDirectory();
			System.out.println(dir);

			// 浏览文件
			FTPFile[] list = client.list();
			// 使用通配浏览文件
			// FTPFile[] list = client.list("*.txt");
			// 显示文件或文件夹的修改时间 你不能获得 . 或 .. 的修改日期，否则Permission denied
			for (FTPFile f : list) {
				if (!f.getName().equals(".") && !f.getName().equals("..")) {
					System.out.print(f.getName() + "\t");
					System.out.println(client.modifiedDate(f.getName()));
				}
			}

			// 下载文件
			File file = new File("C:\\localFile.txt");
			client.download("remoteFile.txt", file);

			// 上传文件到当前目录
			client.upload(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void demo() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}