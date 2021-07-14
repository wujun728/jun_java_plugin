package book.net.simplesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 一个简单的socket客户端，能够往服务器发送socket请求。
 */
public class SimpleClient {
	
	// 客户端输入输出流
	PrintStream out;
	BufferedReader in;

	// 构造方法
	public SimpleClient(String serverName, int port) {
		try {
			// 根据服务器名和端口号，连接服务器
			Socket clientSocket = new Socket(serverName, port); 
			// 获取socket的输入输出流
			out = new PrintStream(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket
					.getInputStream()));
		} catch (Exception e) {
			System.out.println("无法连接服务器!");
		}
	}

	// 发送请求
	public void sendRequest(String request) {
		// 向socket的输出流写数据
		out.println(request); 
		System.out.println("Client 发送请求: " + request);
	}

	public String getResponse() {
		String str = new String();
		try {
			// 从socket的输入流中读取数据
			str = in.readLine();
			System.out.println("Client收到Server返回: " + str);
		} catch (IOException e) {
		} 
		return str;
	}
}

