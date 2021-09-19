package book.net.http;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * HTTP的服务器，接收来自客户端的HTTP请求。
 * 将要发布的HTML文件放置在工程的根目录下，
 * 然后在浏览器中输入类似"http://localhost:80/"的网址，
 * 将能够显示网页的内容。
 */
public class HttpServer {
	// 服务器名和端口
	String serverName;
	int serverPort;

	// 定义server的名字、版本号、端口 
	public HttpServer(String name, int port) {
		this.serverName = name;
		this.serverPort = port;
	}

	public void run() {
		// 显示名字和端口号 
		System.out.println("HttpServer: " + serverName + ": " + serverPort);
		try {
			// 得到服务器监听端口 
			ServerSocket server = new ServerSocket(serverPort);
			do {
				// 等待连接请求 
				Socket client = server.accept();
				// 为连接请求分配一个线程 
				(new HTTPServerThread(client)).start();
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	// 构造一个server，并运行 
	public static void main(String args[]) {
		HttpServer server = new HttpServer("MyHTTPServer", 80);
		server.run();
	}
}

/**
 * 处理HTTP请求的线程，一个HTTP请求对应一个线程
 */
class HTTPServerThread extends Thread {
	// 服务器与客户端之间的socket
	Socket client;
	public HTTPServerThread(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
			// 显示连接信息
			describeConnectionInfo(client);
			// 获取流向到客户端的输出流
			BufferedOutputStream outStream = new BufferedOutputStream(client
					.getOutputStream());
			// 获取来自客户端的输入流 
			HTTPInputStream inStream = new HTTPInputStream(client
					.getInputStream());
			// 得到客户端的请求头（自定义类） 
			HTTPRequest request = inStream.getRequest();
			// 显示头信息 
			request.log();
			// 目前只处理GET请求 
			if (request.isGetRequest()){
				processGetRequest(request, outStream);
			}
			System.out.println("Request completed. Closing connection.");
			//关闭socket 
			client.close();
		} catch (IOException e) {
			System.out.println("IOException occurred .");
			e.printStackTrace();
		}
	}

	// 显示socket连接信息
	void describeConnectionInfo(Socket client) {
		//客户端的主机名 
		String destName = client.getInetAddress().getHostName();
		//客户端的IP地址 
		String destAddr = client.getInetAddress().getHostAddress();
		//客户端的端口 
		int destPort = client.getPort();
		//打印信息，表示客户端已经连接到本服务器上 
		System.out.println("Accepted connection to " + destName + " ("
				+ destAddr + ")" + " on port " + destPort + ".");
	}

	// 处理GET请求 
	void processGetRequest(HTTPRequest request, BufferedOutputStream outStream)
			throws IOException {
		// 获得客户端要get的文件名
		String fileName = request.getFileName();
		File file = new File(fileName);
		// 如果文件存在，则将文件内容发送到socket的输出流，即客户端。 
		if (file.exists()){
			sendFile(outStream, file);
		} else {
			System.out.println("File " + file.getCanonicalPath()
					+ " does not exist.");
		}
	}

	//  发送文件内容到客户端，这里以HTTP 1.1的协议实现的
	void sendFile(BufferedOutputStream out, File file) {
		try {
			// 将文件内容全部读入到一个字节数组中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int len = (int) file.length();
			byte buffer[] = new byte[len];
			// 完全读取，然后关闭文件流
			in.readFully(buffer);
			in.close();
			
			// 写到socket的输出流中
			out.write("HTTP/1.1 200 OK\r\n".getBytes());
			out.write(("Content-Length: " + buffer.length + "\r\n").getBytes());
			out.write("Content-Type: text/HTML\r\n\r\n".getBytes());
			out.write(buffer);
			out.flush();
			out.close();
			// 写文件内容结束，log信息
			System.out.println("File sent: " + file.getCanonicalPath());
			System.out.println("Number of bytes: " + len);
		} catch (Exception e) {
			try {
				// 发送失败
				out.write(("HTTP/1.1 400 " + "No can do" + "\r\n").getBytes());
				out.write("Content-Type: text/HTML\r\n\r\n".getBytes());
			} catch (IOException ioe) {
			}
			System.out.println("Error retrieving " + file);
		}
	}
}

/**
 * 实现读客户端请求的帮助类
 */
class HTTPInputStream extends FilterInputStream {
	public HTTPInputStream(InputStream in) {
		super(in);
	}

	// 读一行，当输入流中没有数据，或者读到"\r\n"时，一行结束。 
	public String readLine() throws IOException {
		StringBuffer result = new StringBuffer();
		boolean finished = false;
		//'\r'为回车符，值为13，'\n'为换行符，值为10
		// cr变量表示是否已经读到换行符
		boolean cr = false;
		do {
			int ch = -1;
			// 读一个字节
			ch = read();
			if (ch == -1){
				return result.toString();
			}
			result.append((char) ch);
			//去掉最后的'\r\n' 
			if (cr && (ch == 10)) {
				result.setLength(result.length() - 2);
				return result.toString();
			}
			//读到回车符，设置标识 
			if (ch == 13){
				cr = true;
			} else {
				cr = false;
			}
		} while (!finished);
		return result.toString();
	}

	// 得到所有的请求 
	public HTTPRequest getRequest() throws IOException {
		HTTPRequest request = new HTTPRequest();
		String line;
		do {
			// 依次读取
			line = readLine();
			//将请求填入容器 
			if (line.length() > 0){
				request.addLine(line);
			} else {
				break;
			}
		} while (true);
		//　返回
		return request;
	}
}

// 客户端请求的封装类 
class HTTPRequest {
	// 请求的数据，按行存储
	Vector lines = new Vector();
	public HTTPRequest() {
	}

	public void addLine(String line) {
		lines.addElement(line);
	}

	// 判断是否是Get请求 
	boolean isGetRequest() {
		if (lines.size() > 0) {
			// 获取请求内容的第一行，如果头三个字符是"GET"，则为Get请求
			String firstLine = (String) lines.elementAt(0);
			if (firstLine.length() > 0){
				if (firstLine.substring(0, 3).equalsIgnoreCase("GET")){
					return true;
				}
			}
		}
		return false;
	}

	// 从请求中解析到文件名 
	// 一般第一行的消息如此类格式："GET /hehe.htm HTTP/1.1"
	/**
	 * 从请求中解析到文件名，只需要处理第一行即可。
	 * 第一行的格式有如下几种：
	 * （1）如果请求的URL为"http://localhost:80/test/hehe.htm"，
	 * 则第一行的内容为"GET /test/hehe.htm HTTP/1.1"。
	 * （2）如果请求的URL为"http://localhost:80"，
	 * 则第一行的内容为"GET HTTP/1.1"，此时应该找默认的html文件，如index.htm
	 * （3）如果请求的URL为"http://localhost:80/test"，
	 * 则第一行的内容为"GET /test/ HTTP/1.1"，此时应该找test目录下默认的html文件
	 */
	String getFileName() {
		if (lines.size() > 0) {
			//得到vector中第一个元素 
			String firstLine = (String) lines.elementAt(0);

			System.out.println("firstLine:  " + firstLine);
			//根据http消息格式得到文件名
			String fileName = firstLine.substring(firstLine.indexOf(" ") + 1);
			int n = fileName.indexOf(" ");
			//URL在两个空格之间 
			if (n != -1){
				fileName = fileName.substring(0, n);
			}

			//去掉第一个'/' 
			try {
				if (fileName.charAt(0) == '/'){
					fileName = fileName.substring(1);
				}
			} catch (StringIndexOutOfBoundsException ex) {
			}

			//默认首页，这里认为index.htm为默认的首页
			//类似于'http://localhost:80'的情况 
			if (fileName.equals("")){
				fileName = "index.htm";
			}
			//类似于'http://localhost:80/download/'的情况 
			if (fileName.charAt(fileName.length() - 1) == '/'){
				fileName += "index.htm";
			}
			System.out.println("fileName:  " + fileName);
			return fileName;
		} else {
			return "";
		}
	}

	// 显示请求信息 
	void log() {
		System.out.println("Received the following request:");
		for (int i = 0; i < lines.size(); ++i){
			System.out.println((String) lines.elementAt(i));
		}
	}
}