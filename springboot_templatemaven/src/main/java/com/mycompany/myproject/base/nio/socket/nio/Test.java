package com.mycompany.myproject.base.nio.socket.nio;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

/**
 * 测试方法
 *
 * @author Wujun
 * @version 1.0
 */
public class Test {

    //测试主方法
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {

        //运行服务器
        Server.start();

        //避免客户端先于服务器启动前执行代码
        Thread.sleep(1000);

        //运行客户端
        Client.start();
        //while (Client.sendMsg(new Scanner(System.in).nextLine())) ;

        Thread.sleep(1000);
        char operators[] = {'+','-','*','/'};
        String ss = "Server , this is package message from NioSocketClient! please calc :";
        for(int i=0 ; i<10; i++){

            //随机产生算术表达式
            Random random = new Random(System.currentTimeMillis());
            String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);

            StringBuilder stringBuilder = new StringBuilder("-" +i + "-");
            Client.sendMsg(stringBuilder.append(ss).append(expression).toString());

        }
    }
}

