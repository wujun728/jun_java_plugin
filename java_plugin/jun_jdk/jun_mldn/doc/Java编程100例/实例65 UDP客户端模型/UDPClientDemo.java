import java.awt.event.*;
import java.net.*;
import javax.swing.*;

public class UDPClientDemo extends JFrame{
	String host="localhost"; //服务器地址
	int port=2345; //端口号
	
	public UDPClientDemo(){
		try{		
			DatagramSocket socket=new DatagramSocket(); //实例化一个数据报Socket
			InetAddress address=InetAddress.getByName(host);  //服务器地址
			byte[] buffer=new byte[256]; //缓冲区
			DatagramPacket packet=new DatagramPacket(buffer,buffer.length,address,port); //实例化一个数据报
			socket.send(packet); //发送报文
			
			packet=new DatagramPacket(buffer,buffer.length); 
			socket.receive(packet); //接受回应
			
			String message=new String(packet.getData()); //得到报文信息
			System.out.println("Received from: "+packet.getAddress()); //显示信息源地址
			System.out.println(message); //显示服务器返回信息
			
			socket.close(); //关闭端口
		}
		catch (Exception ex){
			ex.printStackTrace(); //输出出错信息
		}		
	}
	
	public static void main(String[] args){
		new UDPClientDemo();
	}
}