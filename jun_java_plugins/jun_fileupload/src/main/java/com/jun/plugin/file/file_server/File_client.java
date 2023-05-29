package com.jun.plugin.file.file_server;
import java.io.*;
import java.net.*;

public class File_client {

	
	public static void main(String[] args) throws Exception {
		
		Socket clientSocket = new Socket("localhost", 6789);
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//get the string from server
		String content = "";
		StringBuilder sb = new StringBuilder();
		while(content != null){
		   content = inFromServer.readLine();
		   
		   if(content == null){
		    break;
		   }
		   
		   sb.append(content.trim());
		   break;
		}
		//print the string in buffer.
		System.out.print("the string from the server is :\n");
		System.out.print(sb.toString());
		//safe in the file.
		try {
			File file = new File("client.txt");
			if (!file.exists()) {
                file.createNewFile();
            }
			FileOutputStream os = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			bos.write(sb.toString().getBytes());
			bos.close();
			os.close();
		} catch (Exception e) {
			   e.printStackTrace();
		}
		
		System.out.println("\nsafe finish.");
		clientSocket.close();
		
	}

}
