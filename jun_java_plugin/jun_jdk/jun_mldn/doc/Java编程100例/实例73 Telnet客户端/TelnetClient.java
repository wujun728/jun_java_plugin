import java.net.*;
import java.io.*;

public class TelnetClient {
	String host="162.105.31.222";  //Telnet服务器地址
	int port=23;  //端口号

	public TelnetClient() {
		System.out.println("Host " + host + "; port " + port);
		try {
			Socket socket = new Socket(host, port); //实例化套接字
			new Listener(socket.getInputStream(), System.out).start(); //输出服务器信息到控制台
			new Listener(System.in, socket.getOutputStream()).start();  //输出控制台信息到服务器
		} catch(IOException ex) {
			ex.printStackTrace(); //输出错误信息
			return;
		}
		System.out.println("Connected Success");
	}

	class Listener extends Thread {
		BufferedReader reader;  //输入流
		PrintStream ps;  //输出流

		Listener(InputStream is, OutputStream os) {
			reader = new BufferedReader(new InputStreamReader(is)); //实例化输入流
			ps = new PrintStream(os); //实例化输出流
		}

		public void run() {
			String line;
			try {
				while ((line = reader.readLine()) != null) {  //读取数据
					ps.print(line); //输出数据
					ps.print("\r\n");
					ps.flush();
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] argv) {
		new TelnetClient();
	}
}

