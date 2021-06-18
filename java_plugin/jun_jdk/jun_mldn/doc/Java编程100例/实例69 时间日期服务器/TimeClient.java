import java.net.*;
import java.io.*;

public class TimeClient{
	int port=13; //端口号
	String host="localhost"; //服务器地址
	Socket socket; //客户端套接字
	
	public TimeClient(){
		try{
			socket=new Socket(InetAddress.getByName(host),port); //实例化套按字			
			DataInputStream in=new DataInputStream(socket.getInputStream()); //得到输入流
			byte[] buffer=new byte[256]; //缓冲区
			in.read(buffer); //读入数据到缓冲区
			System.out.println(new String(buffer)); //输出信息
			socket.close(); //关闭套接字
		}
		catch (IOException ex){
			ex.printStackTrace(); //输出错误信息
		}			
	}
	
	public static void main(String[] args){
		new TimeClient();
	}
}