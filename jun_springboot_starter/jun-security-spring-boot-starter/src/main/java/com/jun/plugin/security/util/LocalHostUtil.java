package com.jun.plugin.security.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <p>
 * java获取本地虚拟机IP
 * </p>
 *
 * @since 2023-04-06 15:11:53
 */
public class LocalHostUtil {
    private static final Logger log = LoggerFactory.getLogger(LocalHostUtil.class);

    /**
     * 获取本机ip
     *
     * @return String
     */
    public static String getLocalHost() {
        Enumeration<NetworkInterface> allNetInterfaces;
        String ipLocalHost = null;
        InetAddress ip = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address) {
                        String hostAddress = ip.getHostAddress();
                        if (!hostAddress.equals("127.0.0.1") && !hostAddress.equals("/127.0.0.1")) {
                            // 得到本地IP
                            ipLocalHost = ip.toString().split("[/]")[1];
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error("getLocalHost SocketException：", e);
        }
        return ipLocalHost;
    }


    /**
     * 获取本机InetAddress
     *
     * @return String
     */
    public static InetAddress getInetAddress() {
        Enumeration<NetworkInterface> allNetInterfaces;
        InetAddress inetAddress = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress temp = addresses.nextElement();
                    if (temp instanceof Inet4Address && !temp.isLoopbackAddress()) {
                        String host = temp.getHostAddress();
                        if (host != null && !"0.0.0.0".equals(host) && !"127.0.0.1".equals(host)) {
                            inetAddress = temp;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error("getInetAddress SocketException：", e);
        }
        return inetAddress;
    }


    public static void main(String[] args) {
        System.out.println(getLocalHost());
        System.out.println(getInetAddress().getHostAddress());
    }
}
