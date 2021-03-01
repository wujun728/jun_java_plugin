package com.jun.base.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test1 {

    public static void main(String[] args) {


        //获取本机的InetAddress实例
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();

            String name = address.getHostName();//获取计算机名
            String ip = address.getHostAddress();//获取IP地址
            byte[] bytes = address.getAddress();//获取字节数组形式的IP地址,以点分隔的四部分

            System.out.println("name="+name);
            System.out.println("ip="+ip);
            //获取其他主机的InetAddress实例
            // InetAddress address2 =InetAddress.getByName("其他主机名");
            //  InetAddress address3 =InetAddress.getByName("IP地址");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }





    }
}
