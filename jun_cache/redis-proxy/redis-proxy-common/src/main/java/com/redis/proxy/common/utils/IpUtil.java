package com.redis.proxy.common.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * IP地址常用类
 *
 * @author zhanggaofeng
 */
public class IpUtil {

        public static String getHostInfo(int port) {
                return getLocalAddr() + ":" + port;
        }

        /**
         * 获取本机ip地址
         *
         * @return ip字符串
         */
        public static String getLocalAddr() {

                String localip = null;// 本地IP，如果没有配置外网IP则返回它
                String netip = null;// 外网IP
                try {
                        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                        InetAddress ip = null;
                        boolean finded = false;// 是否找到外网IP
                        while (netInterfaces.hasMoreElements() && !finded) {
                                NetworkInterface ni = netInterfaces.nextElement();
                                Enumeration<InetAddress> address = ni.getInetAddresses();
                                while (address.hasMoreElements()) {
                                        ip = address.nextElement();
                                        if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                                                netip = ip.getHostAddress();
                                                finded = true;
                                                break;
                                        } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                                                localip = ip.getHostAddress();
                                        }
                                }
                        }
                } catch (SocketException e) {
                        e.printStackTrace();
                }
                if (netip != null && !"".equals(netip)) {
                        return netip;
                } else {
                        return localip;
                }

        }

        /**
         * 获取可用的端口
         *
         * @param port
         * @return
         */
        public static int getAvailablePort(int port) {
                while (port < Integer.MAX_VALUE) {
                        if (enablePort(port)) {
                                return port;
                        }
                        port++;
                }
                return 0;
        }

        /**
         * 端口是否可用
         *
         * @param host
         * @param port
         * @throws UnknownHostException
         */
        private static boolean enablePort(int port) {
                ServerSocket sskt = null;
                try {
                        sskt = new ServerSocket(port);
                } catch (Exception e) {
                        return false;
                } finally {
                        if (sskt != null) {
                                try {
                                        sskt.close();
                                } catch (IOException ex) {
                                }
                        }
                }
                return true;
        }

}
