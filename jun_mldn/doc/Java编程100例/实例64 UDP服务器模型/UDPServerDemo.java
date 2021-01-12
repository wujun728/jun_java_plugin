import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;

public class UDPServerDemo extends JFrame{	
	int port=2345;	//端口号
	
	public UDPServerDemo(){
		try{
			byte[] buffer=new byte[256]; //缓冲区数组
			DatagramSocket socket=new DatagramSocket(port);	 //实例化数据报Socket
			DatagramPacket packet; 
			System.out.println("Server start..."); //输出运行信息
			
			while (true){
				packet=new DatagramPacket(buffer,buffer.length); //实例化数据报
				socket.receive(packet);	 //接受请求							
				InetAddress target=packet.getAddress(); 
				System.out.println("Received from"+target); //输出请求地址
				int port=packet.getPort(); //得到接收端口
				byte[] message="This is server,Who are you?".getBytes(); //服务器返回信息
				packet=new DatagramPacket(message,message.length,target,port); //实例化数据报
				socket.send(packet); //发送数据报
			}			
		}
		catch (Exception ex){
			ex.printStackTrace(); //输出出错信息
		}
	}
	
	public static void main(String[] args){
		new UDPServerDemo();
	}
}