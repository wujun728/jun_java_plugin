import java.net.*;
import java.io.*;

public class SocketServerDemo{
	int port=2345; //端口号
	ServerSocket serverSocket; //服务器套接字
	
	public SocketServerDemo(){
		try{
			serverSocket=new ServerSocket(port); //实例化套接字
			System.out.println("start server at port "+port); //在命令窗口输出提示信息
			
			while (true){	//一直等待客户端连接		
				Socket client=serverSocket.accept();  //等待连接
				System.out.println("Connect: "+client.getInetAddress());  //输出客户机地址
				DataInputStream in=new DataInputStream(client.getInputStream()); //得到输入流
				DataOutputStream out=new DataOutputStream(client.getOutputStream()); //得到输出流
				
				byte[] message="Connect ok.This message is from server.".getBytes(); //输出信息字符数组
				out.write(message,0,message.length); //输出信息
			}
		}
		catch (IOException ex){
				ex.printStackTrace(); //输出错误信息
		}					
	}
	
	public static void main(String[] args){
		new SocketServerDemo();
	}
}