package book.net.simplesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 一个简单的socket服务器，能接受客户端请求，并将请求返回给客户端
 */
public class SimpleServer {
	// 服务端侦听的Socket
	ServerSocket serverSkt = null;
	// 客户端
	Socket clientSkt = null;

	// 客户端输入流
	BufferedReader in = null;
	// 客户端输出流
	PrintStream out = null;

	// 构造方法
	public SimpleServer(int port){
		System.out.println("服务器代理正在监听，端口：" + port);
		try {
			// 创建监听socket
			serverSkt = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("监听端口" + port + "失败");
		}
		try {
			// 接收连接请求
			clientSkt = serverSkt.accept();
		} catch (IOException e) {
			System.out.println("连接失败");
		}
		try {
			// 获得输出输出流
			in = new BufferedReader(new InputStreamReader(clientSkt
					.getInputStream()));
			out = new PrintStream(clientSkt.getOutputStream());
		} catch (IOException e) {
		}
	}

	// 收到客户端请求
	public String getRequest() {
		String frmClt = null;
		try {
			// 从客户端的输入流中读取一行数据
			frmClt = in.readLine();
			System.out.println("Server 收到请求:" + frmClt);
		} catch (Exception e) {
			System.out.println("无法读取端口.....");
			System.exit(0);
		}
		return frmClt;
	}

	// 发送响应给客户端
	public void sendResponse(String response) {
		try {
			// 往客户端输出流中写数据
			out.println(response);
			System.out.println("Server 响应请求:" + response);
		} catch (Exception e) {
			System.out.println("写端口失败......");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException {
		// 启动服务器
		SimpleServer sa = new SimpleServer(8888);
		while (true) {
			// 读取客户端的输入并返回给客户端。
			sa.sendResponse(sa.getRequest());
		}
	}

}