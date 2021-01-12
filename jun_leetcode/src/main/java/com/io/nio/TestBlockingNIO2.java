package com.io.nio;



import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 阻塞式NIO
 * @author BaoZhou
 * @date 2018/7/24
 */
public class TestBlockingNIO2 {

    @Test
    public void client() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (inChannel.read(buf) != -1) {
                buf.flip();
                socketChannel.write(buf);
                buf.clear();
            }
            //接受服务端的反馈
            int len = 0;
            while ((len = socketChannel.read(buf))!=-1)
            {
                buf.flip();
                System.out.println(new String(buf.array(),0,len));
                buf.clear();
            }

            inChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void server() {
        try {
            //获取通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //绑定连接
            serverSocketChannel.bind(new InetSocketAddress(9898));
            //文件输出通道
            FileChannel outChannel = FileChannel.open(Paths.get("2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            //获取客户端连接的通道（阻塞等待远程连接）
            SocketChannel socketChannel = serverSocketChannel.accept();

            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (socketChannel.read(buf) != -1) {
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }

            //发送反馈给客户端
            buf.put("服务端接受数据成功".getBytes());
            buf.flip();
            socketChannel.write(buf);

            serverSocketChannel.close();
            outChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
