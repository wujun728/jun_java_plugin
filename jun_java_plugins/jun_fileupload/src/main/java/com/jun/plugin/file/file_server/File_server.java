package com.jun.plugin.file.file_server;
import java.net.*;
import java.io.*;

public class File_server {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		File file = new File("server.txt");
        ServerSocket welcomeSocket = new ServerSocket(6789);
		
		while(true)
        {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String content = "";
			StringBuilder sb = new StringBuilder();
			  
			while(content != null){
			   content = bf.readLine();
			   
			   if(content == null){
			    break;
			   }
			   
			   sb.append(content.trim());
			}
			  
			bf.close();
			System.out.print(sb.toString());
			
			Socket connectionSocket = welcomeSocket.accept();
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(sb.toString());
			outToClient.close();
        }
	}

}
