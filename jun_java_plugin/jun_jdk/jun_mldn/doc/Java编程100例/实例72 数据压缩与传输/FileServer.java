import java.net.*;
import java.io.*;
import java.util.zip.GZIPOutputStream;

public class FileServer{
	int port=2345; //端口号
	ServerSocket serverSocket; //服务器套接字
	
	public FileServer(){
		try{
			serverSocket=new ServerSocket(port); //实例化套接字
			System.out.println("start server at port "+port); //在命令窗口输出提示信息
			
			while (true){	
				Socket client=serverSocket.accept();  //等待连接
				System.out.println("Connect: "+client.getInetAddress());  //输出客户机地址
				DataOutputStream out=new DataOutputStream(client.getOutputStream()); //得到输出流				
				GZIPOutputStream gout=new GZIPOutputStream(out); //压缩输出流	
				FileInputStream fileIn=new FileInputStream("c:/1.txt");  //待传送的文件			
				
				byte[] buffer=new byte[1024]; //缓冲区大小
				int length;
				while ((length=fileIn.read(buffer))!=-1){  //读取数据
					gout.write(buffer,0,length);  //写入数据到文件
				} 
				gout.close(); //关闭输出流
				fileIn.close();
				client.close(); //关闭端口
				System.out.println("Send Success.");
			}
		}
		catch (IOException ex){
				ex.printStackTrace(); //输出错误信息
		}					
	}
	
	public static void main(String[] args){
		new FileServer();
	}
}