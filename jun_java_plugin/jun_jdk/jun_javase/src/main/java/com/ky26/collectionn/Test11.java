package com.ky26.collectionn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Test11 {
	public static void main(String[] args) throws IOException {
		MyTcp tcp=new MyTcp();
		tcp.getServer();
	}
}
class MyTcp{
	private BufferedReader reader;
	private ServerSocket server;
	private Socket socket;
	void getServer() throws IOException{
		server=new ServerSocket(8989);
		System.out.println("服务器套接字已经创建成功");
		while(true){
			System.out.println("等待客户机的连接");
			socket=server.accept();
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			getClientMessage();
		}
	}
	
	private void getClientMessage(){
		try{
			while(true){
				System.out.println("客户机："+reader.readLine());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(reader!=null){
				reader.close();
			}
			if(socket!=null){
				socket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
