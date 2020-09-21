/*   
 * Project: OSMP
 * FileName: FrameConst.java
 * version: V1.0
 */
package com.osmp.intf.define.config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Description:
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:25:30上午10:51:30
 */
public class FrameConst {
	/***************************** 对外接口 **************************************/
	/**
	 * 接口source参数属性名
	 */
	public final static String SOURCE_NAME = "source";
	/**
	 * 接口传入服务参数属性名
	 */
	public final static String PARAMETER_NAME = "parameter";

	/***************************** 访问客户端解析 **********************************/
	/**
	 * 访问客户端ip属性名
	 */
	public final static String CLIENT_IP = "ip";

	/**
	 * 访问客户端项目属性名
	 */
	public final static String CLIENT_FROM = "from";
	/**
	 * 访问客户端代理属性名
	 */
	public final static String CLIENT_AGENT = "agent";
	/**
	 * 访问请求唯一标识属性名
	 */
	public final static String CLIENT_REQ_ID = "requestId";

	/**************************************************************************/
	/***************************** 服务常量 **************************************/
	/************************************************************************/
	/**
	 * 区分服务的属性键名
	 */
	public final static String SERVICE_NAME = "name";
	/**
	 * 服务描述
	 */
	public final static String SERVICE_MARK = "mark";

	/***************************************************************************/
	/******************************** 服务器信息 ***********************************/
	/**************************************************************************/
	private static String loadIp;
	private static String loadName;

	/**
	 * 获取服务器ip地址
	 * 
	 * @return
	 */
	public static final String getLoadIp() {
		if (loadIp == null) {
			InetAddress addr = getLocalInetAddress();
			loadIp = "";
			loadName = "";
			if (addr != null) {
				loadIp = addr.getHostAddress();
				loadName = addr.getHostName();
			}
		}
		return loadIp;
	}

	/**
	 * 获取服务器名称
	 * 
	 * @return
	 */
	public static final String getLoadName() {
		if (loadIp == null) {
			InetAddress addr = getLocalInetAddress();
			loadIp = "";
			loadName = "";
			if (addr != null) {
				loadIp = addr.getHostAddress();
				loadName = addr.getHostName();
			}
		}
		return loadName;
	}

	private static InetAddress getLocalInetAddress() {
		InetAddress ip = null;
		if (System.getProperty("os.name").indexOf("Windows") > 0) {
			try {
				ip = InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		} else {
			Enumeration<NetworkInterface> netInterfaces = null;
			try {
				netInterfaces = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			if (netInterfaces != null) {
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> iaddress = ni.getInetAddresses();
					if (iaddress != null) {
						while (iaddress.hasMoreElements()) {
							ip = iaddress.nextElement();
							if (ip != null && !ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1
									&& ip instanceof Inet4Address) {
								return ip;
							} else {
								ip = null;
							}
						}
					}
				}
			}
		}
		return ip;
	}

}
