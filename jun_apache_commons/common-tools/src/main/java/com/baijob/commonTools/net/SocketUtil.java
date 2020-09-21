package com.baijob.commonTools.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.baijob.commonTools.RegexUtil;

/**
 * 套接字相关工具类
 * 
 * @author luxiaolei@baijob.com
 * 
 */
public class SocketUtil {
	public final static String LOCAL_IP = "127.0.0.1";

	/**
	 * 检测本地端口可用性
	 * 
	 * @param port 被检测的端口
	 * @return 是否可用
	 */
	public static boolean isUsableLocalPort(int port) {
		if (! isValidPort(port)) {
			// 给定的IP未在指定端口范围中
			return false;
		}
		try {
			new Socket(LOCAL_IP, port).close();
			// socket链接正常，说明这个端口正在使用
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	/**
	 * 是否为有效的端口
	 * @param port 端口号
	 * @return 是否有效
	 */
	public static boolean isValidPort(int port) {
		//有效端口是0～65535
		return port >= 0 && port <= 0xFFFF;
	}

	/**
	 * 根据long值获取ip v4地址
	 * 
	 * @param longIP IP的long表示形式
	 * @return IP V4 地址
	 */
	public static String longToIpv4(long longIP) {
		StringBuffer sb = new StringBuffer();
		// 直接右移24位
		sb.append(String.valueOf(longIP >>> 24));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIP & 0x000000FF));
		return sb.toString();
	}

	/**
	 * 根据ip地址计算出long型的数据
	 * @param strIP IP V4 地址
	 * @return long值
	 */
	public static long ipv4ToLong(String strIP) {
		if(RegexUtil.isIpv4(strIP)){
			long[] ip = new long[4];
			// 先找到IP地址字符串中.的位置
			int position1 = strIP.indexOf(".");
			int position2 = strIP.indexOf(".", position1 + 1);
			int position3 = strIP.indexOf(".", position2 + 1);
			// 将每个.之间的字符串转换成整型
			ip[0] = Long.parseLong(strIP.substring(0, position1));
			ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
			ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
			ip[3] = Long.parseLong(strIP.substring(position3 + 1));
			return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
		}
		return 0;
	}

	/**
	 * 简易的使用Socket发送数据
	 * 
	 * @param host Server主机
	 * @param port Server端口
	 * @param isBlock 是否阻塞方式
	 * @param data 需要发送的数据
	 * @throws IOException
	 */
	public static void netCat(String host, int port, boolean isBlock, ByteBuffer data) throws IOException {
		SocketChannel channel = SocketChannel.open(new InetSocketAddress(host, port));
		channel.configureBlocking(isBlock);
		channel.write(data);
		channel.close();
	}

	/**
	 * 使用普通Socket发送数据
	 * 
	 * @param host Server主机
	 * @param port Server端口
	 * @param data 数据
	 * @throws IOException
	 */
	public static void netCat(String host, int port, byte[] data) throws IOException {
		Socket socket = new Socket(host, port);
		OutputStream out = socket.getOutputStream();
		out.write(data);
		out.flush();
		out.close();
		socket.close();
	}

}
