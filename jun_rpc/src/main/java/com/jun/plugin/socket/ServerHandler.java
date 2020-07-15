/**
 * 
 */
package com.jun.plugin.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhangHao 
 * @date  2013-4-12
 */
public class ServerHandler implements Runnable {
	
	private Socket socket = null;
	private Vector<Socket> vector = null;
	private ConcurrentHashMap<String, Socket> map = new ConcurrentHashMap<String, Socket>();
	private String msg = null;
	BufferedReader reader = null;
	PrintWriter writer = null;
	boolean flag = true;
	public ServerHandler() {
		
	}	
	
	public ServerHandler(Socket socket, Vector vector, ConcurrentHashMap<String,Socket> map) {
		this.socket = socket;
		this.vector = vector;
		this.map = map;
	}

	@Override
	public void run() {
		String name = null;
		try {
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String strr = reader.readLine();
			name = strr+socket.getInetAddress()+"="+socket.getPort();
			System.out.println(name+"连接到服务器！");
			map.put(strr, socket);
			for(int i=0;i<vector.size();i++){
				writer = new PrintWriter(vector.get(i).getOutputStream(),true);
				writer.println(name+"上线了");
			}
			while(flag && ((msg=reader.readLine())!=null)){
				System.out.println(msg);
				if("to-".equals(msg.substring(0,3))){
					String str = msg.substring(3,msg.indexOf("]"));
					System.out.println(str);
					writer = new PrintWriter(map.get(str).getOutputStream(),true);
					writer.println(name+"说:"+msg.substring(msg.indexOf("]")));
				}else{
					for(int i=0;i<vector.size();i++){
						writer = new PrintWriter(vector.get(i).getOutputStream(),true);
						writer.println(name+"说:"+msg);
					}
				}
                if (msg.equals("bye")) {
                    break;  
                } 
			}
		}catch(SocketException e){
			vector.remove(socket);
			flag = false;
			for(int i=0;i<vector.size();i++){
				try {
					writer = new PrintWriter(vector.get(i).getOutputStream(),true);
					writer.println(name+"非正常离开");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
