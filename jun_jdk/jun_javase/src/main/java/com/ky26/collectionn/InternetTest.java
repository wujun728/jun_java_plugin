package com.ky26.collectionn;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InternetTest {
	public static void main(String[] args) throws UnknownHostException {
		InetAddress ip;
		ip=InetAddress.getLocalHost();
		String localname=ip.getHostName();
		String localip=ip.getHostAddress();
		System.out.println("本机名："+localname);
		System.out.println("本机ip："+localip);
	}
}
