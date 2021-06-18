package com.common.network;

import java.net.*;
import java.io.*;

/**
 * 
 * 功能描述：<br />
 * 客户端SOCKET应用程序与服务器端SOCKET应用程序的流程很相似，最大的差别在于：<br />
 * 1、服务器端SOCKET应用程序主要用于侦听及接收客户端的连接，而客户端的SOCKET 应用程序则用于尝试与服务器端建立连接
 * 
 * 2、客户端SOCKET应用程序发送信息指令至服务器端，并接收服务器端所返回的结果；
 * 而服务器端SOCEKT应用程序则处理指令逻辑，并将结果或错误信息发送至客户端
 * 
 * 常见的客户端应用有：<br />
 * CHAT客户端，FTP客户端，POP3客户端，SMTP客户端和TELNET客户端<br />
 * 
 * 建立客户端SOCKET应用程序的步骤大致如下：
 * 
 * 1、建立客户端SOCKET，在建立时需指定欲连接服务器端的主机名称（或IP）与INTERNET 通信端口 <br />
 * 
 * 2。发送特定信息或指令至服务器端
 * 
 * 3、接收服务器端返回的执行结果或错误信息，并以特定格式显示，例如HTTP通信协议会通过 HTML显示
 * 
 * 4、当客户端不需服务器端的处理时，便关闭SOCEKT通信链接
 * 
 * 
 * @author Administrator
 * @Date Jul 19, 2008
 * @Time 9:53:47 AM
 * @version 1.0
 * 
 */
public class simpleClient {

	private static Socket socket;

	public static void main(String[] args) throws Exception {
		String host;
		int port;
		if (args.length < 2) {
			System.out
					.println("Usage:java simpleClient [remote IP/Host] [port]");
			System.exit(1);
		}
		host = args[0];
		port = Integer.parseInt(args[1]);
		connectServer(host, port);
	}

	/**
	 * 功能描述：连接服务器
	 * 
	 * @param host
	 *            服务器端的主机名称或者IP地址
	 * @param port
	 *            服务器端通信端口
	 */
	public static void connectServer(String host, int port) {
		try {
			socket = new Socket(InetAddress.getByName(host), port);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			@SuppressWarnings("unused")
			DataOutputStream out = new DataOutputStream(socket
					.getOutputStream());// 建立通信通道

			byte[] inByte = new byte[1024];
			in.read(inByte);
			String response = new String(inByte, 0, inByte.length);
			System.out.println("Message from server: ");
			System.out.println(response.trim());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
