import java.net.*;
import java.io.*;
import java.util.Date;

public class TimeServer{
	int port=13; //端口号
	ServerSocket serverSocket; //服务器套接字
	
	public TimeServer(){
		try{
			serverSocket=new ServerSocket(port); //实例化套接字
			System.out.println("start server at port "+port); //在命令窗口输出提示信息
			
			while (true){	//一直等待客户端连接		
				Socket client=serverSocket.accept();  //等待连接
				System.out.println("Connect from: "+client.getInetAddress());  //输出客户机地址
				PrintStream out=new PrintStream(client.getOutputStream()); //得到输出流
				Date now=new Date(); //获取当前时间
				out.println(now);  //输出当前时间到客户端					
			}
		}
		catch (IOException ex){
				ex.printStackTrace(); //输出错误信息
		}					
	}
	
	public static void main(String[] args){
		new TimeServer();
	}
}