package com.mycompany.myproject.base.nio.socket.io;

import java.io.IOException;
import java.util.Random;

/**
 * 测试方法
 * @author Wujun
 * @version 1.0
 */
public class Test {
    //测试主方法
    public static void main(String[] args) throws InterruptedException {

        //运行服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerNormal.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);

        //运行客户端
        char operators[] = {'+','-','*','/'};
        Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            @SuppressWarnings("static-access")
            @Override
            public void run() {
                while(true){
                    //随机产生算术表达式
                    String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);
                    Client.send(expression);
                    try {
                        Thread.currentThread().sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

