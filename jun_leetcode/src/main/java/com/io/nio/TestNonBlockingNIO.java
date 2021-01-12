package com.io.nio;



import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 非阻塞NIO TCP
 * @author BaoZhou
 * @date 2018/7/24
 */

public class TestNonBlockingNIO {
    @Test
    public void client() {
        try {
            //1.获取通道
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            //2.开启非阻塞模式
            socketChannel.configureBlocking(false);
            //3.分配缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            //4.发送数据给服务端
            buf.put(LocalDateTime.now().toString().getBytes());
            buf.flip();
            socketChannel.write(buf);
            socketChannel.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void server() {
        try {
            //1.获取通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //2.开启非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //3.绑定连接
            serverSocketChannel.bind(new InetSocketAddress(9898));


            //4.获取选择器
            Selector selector = Selector.open();
            //5.将通道注册到选择器上,指定监听事件
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            //6.轮询式的获取选择器上已经“准备就绪”的事件
            while(selector.select()>0)
            {
                //返回所有的Selector监听的事件
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
           while(keyIterator.hasNext())
           {
               //获取准备就绪的事件
               SelectionKey selectionKey = keyIterator.next();

               //判断具体是什么事件就绪
               if(selectionKey.isAcceptable())
               {
                   //获取客户端的连接
                   SocketChannel socketChannel = serverSocketChannel.accept();
                   //切换成非阻塞模式
                   socketChannel.configureBlocking(false);
                   //注册到选择器上
                   socketChannel.register(selector,SelectionKey.OP_READ);
               }
               else if(selectionKey.isReadable())
               {
                   SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                   //读取数据
                   ByteBuffer buf =ByteBuffer.allocate(1024);
                   int len = 0;
                   while((len = socketChannel.read(buf))!=-1)
                   {
                       buf.flip();
                       System.out.println(new String(buf.array(),0,len));
                       buf.clear();
                   }
               }
               //使用完毕后取消Key
               keyIterator.remove();
           }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
