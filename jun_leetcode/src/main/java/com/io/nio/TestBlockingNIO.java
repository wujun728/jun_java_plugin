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
 *
 * 客户端发送图片给服务端
 *
 * NIO三大核心
 *
 * 1.通道（Channel）:负责连接
 *
 * 2.缓冲区（Buffer）:负责数据连接
 *
 * 3.选择器（Selector）:是SelectableChannel的多路复用器，用于监控SelectableChannel 的IO状况
 *
 * @author BaoZhou
 * @date 2018/7/24
 */
public class TestBlockingNIO {

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
            //获取客户端连接的通道
            SocketChannel socketChannel = serverSocketChannel.accept();

            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (socketChannel.read(buf) != -1) {
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
            serverSocketChannel.close();
            outChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
