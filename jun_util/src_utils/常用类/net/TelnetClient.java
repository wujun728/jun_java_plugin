package book.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Telnet客户端，可以登陆到Telnet服务器。
 */
public class TelnetClient {

	// 默认的Telnet服务器名
	public static final String DEFAULT_HOST = "127.0.0.1";
	// 默认的Telnet服务器端口
	public static final int DEFAULT_PORT = 23;
	
	// 主机名（或者IP）和端口号
    private String host;
    private int port;

    // 连接主机的socket
    Socket socket = null;
    // 发送数据和接收数据的管道，Pipe为自定义类
    Pipe sendPipe = null;
    Pipe receivePipe = null;
    
    // 默认构造方法
    public TelnetClient(){
    	this.host = DEFAULT_HOST;
    	this.port = DEFAULT_PORT;
    }
    public TelnetClient(String host, int port){
    	this.host = host;
    	this.port = port;
    }

    /**
     * 登陆到服务器
     */
    public void telnet() {
        System.out.println("Connecting to telnet server " + host + ": " + port);
        try {
            socket = new Socket(host, port);

            // 将socket的输入端数据（来自服务器端）流向本地标准输出，即接收数据
            receivePipe = new Pipe(socket.getInputStream(), System.out);
            receivePipe.start();
            // 将本地的标准输入数据流向socket的输出端，即流向服务器端，发送数据
            sendPipe = new Pipe(System.in, socket.getOutputStream());
            sendPipe.start();
        } catch(IOException e) {
            System.out.println("连接失败：" + e);
            return;
        }
        System.out.println("连接成功");
    }
    /**
     * 断开连接
     */
    public void disconnect() {
    	if (socket != null){
    		try {
				socket.close();
				System.out.println("成功断开连接");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static void main(String[] argv) {
    	new TelnetClient().telnet();
    }
}

/**
 * 管道类，将输入流的数据写入到输出流中。
 * 是一个线程，可以独立运行
 */
class Pipe extends Thread {

	// 管道的输入流和输出流
    BufferedReader is;
    PrintStream os;

    /**
     * 构造方法，构造输入输出流
     * @param is
     * @param os
     */
    Pipe(InputStream is, OutputStream os) {
        this.is = new BufferedReader(new InputStreamReader(is));
        this.os = new PrintStream(os);
    }
    
    /**
     * 线程体方法，将输入流的数据写入到输出流
     */
    public void run() {

        String line;
        try {
        	// 读取输入流的数据
            while ((line = is.readLine()) != null) {
            	// 写到输出流中
                os.print(line);
                os.print("\r\n");
                os.flush();
            }
        } catch(IOException e){
        }
    }
}
