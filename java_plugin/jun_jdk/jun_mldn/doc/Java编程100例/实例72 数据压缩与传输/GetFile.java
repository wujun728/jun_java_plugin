import java.net.*;
import java.io.*;
import java.util.zip.GZIPInputStream;

public class GetFile{
	int port=2345; //端口号
	String host="localhost"; //服务器地址
	Socket socket; //客户端套接字
	
	public GetFile(){
		try{
			socket=new Socket(InetAddress.getByName(host),port); //实例化套按字
			
			DataInputStream in=new DataInputStream(socket.getInputStream()); //得到输入流
			GZIPInputStream gin=new GZIPInputStream(in); //压缩输入流
			FileOutputStream fileOut=new FileOutputStream("e:/2.txt"); //文件输出流
			
			
			byte[] buffer=new byte[1024]; //缓冲区
			int length;
			while ((length=gin.read(buffer))!=-1){  //读取数据
				fileOut.write(buffer,0,length);  //写入数据到文件
			} 
			System.out.println("Received Success!");
			gin.close(); //关闭输入流
			socket.close(); //关闭套接字
			fileOut.close(); //关闭输出流
		}
		catch (IOException ex){
			ex.printStackTrace(); //输出错误信息
		}			
	}
	
	public static void main(String[] args){
		new GetFile();
	}
}