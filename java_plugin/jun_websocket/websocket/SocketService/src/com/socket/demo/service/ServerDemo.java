package com.socket.demo.service;

import com.socket.server.container.SocketContainer;

public class ServerDemo {
	
	public static void main(String[] args) {
		SocketContainer socketContainer = new SocketContainer("G:/workgit/SocketService/src/com/socket/demo/service/config.xml");
		socketContainer.start();
	}
}
