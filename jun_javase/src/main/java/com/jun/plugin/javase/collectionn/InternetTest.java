package com.jun.plugin.javase.collectionn;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InternetTest {
	public static void main(String[] args) throws UnknownHostException {
		InetAddress ip;
		ip=InetAddress.getLocalHost();
		String localname=ip.getHostName();
		String localip=ip.getHostAddress();
		System.out.println("��������"+localname);
		System.out.println("����ip��"+localip);
	}
}
