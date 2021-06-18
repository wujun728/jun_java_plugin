package com.mycompany.myproject.base.nio.socket.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO服务端源码
 *  从以上代码，很容易看出，BIO主要的问题在于每当有一个新的客户端请求接入时，
 *  服务端必须创建一个新的线程来处理这条链路，在需要满足高性能、高并发的场景是没法应用的（大量创建新的线程会严重影响服务器性能，甚至罢工）。
 * @author Wujun
 * @version 1.0
 */
public final class ServerNormal {

    //默认的端口号
    private static int DEFAULT_PORT = 12345;

    //单例的ServerSocket
    private static ServerSocket server;
    //根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值

    public static void start() throws IOException{
        //使用默认值
        start(DEFAULT_PORT);
    }

    //这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
    public synchronized static void start(int port) throws IOException{

        if(server != null) return;
        try{
            //通过构造函数创建ServerSocket
            //如果端口合法且空闲，服务端就监听成功
            server = new ServerSocket(port);
            System.out.println("服务器已启动，端口号：" + port);

            //通过无线循环监听客户端连接
            //如果没有客户端接入，将阻塞在accept操作上。
            while(true){
                Socket socket = server.accept();
                //当有新的客户端接入时，会执行下面的代码
                //然后创建一个新的线程处理这条Socket链路
                new Thread(new ServerHandler(socket)).start();
            }

        }finally{

            //一些必要的清理工作
            if(server != null){
                System.out.println("服务器已关闭。");
                server.close();
                server = null;
            }
        }
    }
}

