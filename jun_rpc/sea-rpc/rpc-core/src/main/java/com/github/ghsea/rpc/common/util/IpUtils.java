package com.github.ghsea.rpc.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtils {
	
	private static String serverIp = "";

	static {
		try {
			serverIp = "" + getLocalIPList().get(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getLocalIp() {
		return serverIp;
	}

	private static List<String> getLocalIPList() {
		List<String> res1 = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();

			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) continue;
				
				Enumeration<InetAddress> ids = ni.getInetAddresses();
				while (ids.hasMoreElements()) {
					InetAddress ip = ids.nextElement();
					if (!(ip.isLoopbackAddress() || (ip.getHostAddress().indexOf(':') >= 0))) {
						res1.add(ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		return res1;
	}
	
}
