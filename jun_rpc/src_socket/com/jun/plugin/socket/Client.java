/**
 * 
 */
package com.jun.plugin.socket;

import java.io.*;
import java.net.*;

public class Client {
	
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	String msg = null;
	String msg1 = null;
	public Client() {
		try {
				socket = new Socket("192.168.3.6", 1255);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println("wangwu");
				new Thread(new Runnable(){
					public void run() {
						try {
							while((msg1 = in.readLine())!=null){
								System.out.println(msg1);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		        while (true) {  
		            String msg = reader.readLine();  
		            out.println(msg);  
		            if (msg.equals("bye")) {  
		                break;  
		            }  
		        }  
		}catch (IOException e) {
			
		}finally{
			out.close();
			try {
				in.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client();
	}
}
