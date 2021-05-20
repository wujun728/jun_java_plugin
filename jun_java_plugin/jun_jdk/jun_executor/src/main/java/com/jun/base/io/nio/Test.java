package com.jun.base.io.nio;


import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        //Thread.sleep(3000);
        //System.out.println("运行客户端");
        //运行客户端
        //Client.start();

       // while(Client.sendMsg(new Scanner(System.in).nextLine()));
//        Thread.sleep(3000);
//        System.out.println("向服务器发送：");
        //cliens();
        //Client.sendMsg("1+1");
        /*for(int i = 0; i <100; i++){

            Client.sendMsg("0001+"+i);
        }*/


    }

    public static void cliens() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);


        for(int i = 0; i <=2; i++){

            final int j = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {

                        Client.sendMsg("1+"+j);
                        Thread.sleep(1);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
