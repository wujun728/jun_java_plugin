package com.mycompany.myproject.base.nio.socket.aio;

import com.mycompany.myproject.base.nio.socket.aio.client.Client;
import com.mycompany.myproject.base.nio.socket.aio.server.Server;

import java.util.Scanner;


/**
 * 测试方法
 * @author Wujun
 * @version 1.0
 */
public class Test {
    //测试主方法
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception{

        //运行服务器
        Server.start();

        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);

        //运行客户端
        Client.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(Client.sendMsg(scanner.nextLine()));
    }
}

