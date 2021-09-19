package com.jun.base.io.nio;

public class TestClient {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 12345;

    public static void main(String[] args) throws Exception {


        for (int i=0;i<10;i++){

            ClientHandle clientHandle = new ClientHandle(DEFAULT_HOST,DEFAULT_PORT);
            new Thread(clientHandle,"Server").start();
            Thread.sleep(20);
            clientHandle.sendMsg("1+"+i);



        }
    }
}
