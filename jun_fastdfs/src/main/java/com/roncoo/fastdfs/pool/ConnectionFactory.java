package com.roncoo.fastdfs.pool;

import java.net.InetSocketAddress;
import java.net.Socket;

import com.roncoo.common.MyException;
import com.roncoo.fastdfs.ClientGlobal;

public class ConnectionFactory {

	/**
	 * create from InetSocketAddress
	 * 
	 * @param socketAddress socketAddress
	 * @return Connection
	 * @throws MyException 自定义异常
	 */
	public static Connection create(InetSocketAddress socketAddress) throws MyException {
		try {
			Socket sock = new Socket();
			sock.setReuseAddress(true);
			sock.setSoTimeout(ClientGlobal.g_network_timeout);
			sock.connect(socketAddress, ClientGlobal.g_connect_timeout);
			return new Connection(sock, socketAddress);
		} catch (Exception e) {
			throw new MyException("connect to server " + socketAddress.getAddress().getHostAddress() + ":" + socketAddress.getPort() + " fail, emsg:" + e.getMessage());
		}
	}
}
