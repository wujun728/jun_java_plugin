package book.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取IP地址和机器名
 */
public class GetIPAddress {

	/**
	 * 获取本机的IP地址
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalIP() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
	}

	/**
	 * 获取本机的机器名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostName();
	}
	/**
	 * 根据域名获得主机的IP地址
	 * @param hostName	域名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getIPByName(String hostName)
			throws UnknownHostException {
		InetAddress addr = InetAddress.getByName(hostName);
		return addr.getHostAddress();
	}
	/**
	 * 根据域名获得主机所有的IP地址
	 * @param hostName	域名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String[] getAllIPByName(String hostName)
			throws UnknownHostException {
		InetAddress[] addrs = InetAddress.getAllByName(hostName);
		String[] ips = new String[addrs.length];
		for (int i = 0; i < addrs.length; i++) {
			ips[i] = addrs[i].getHostAddress();
		}
		return ips;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		// 获取本机的IP地址和机器名
		System.out.println("Local IP: " + GetIPAddress.getLocalIP());
		System.out.println("Local HostName: " + GetIPAddress.getLocalHostName());

		// 获得微软网站的IP
		String hostName = "www.microsoft.com";
		System.out.println("域名为" + hostName + "的主机的IP地址：");
		System.out.println(GetIPAddress.getIPByName(hostName));

		System.out.println("域名为" + hostName + "的主机的所有IP地址：");
		String[] ips = GetIPAddress.getAllIPByName(hostName);
		for (int i = 0; i < ips.length; i++) {
			System.out.println(ips[i]);
		}
	}
}
