package com.io.nio;



import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 非阻塞NIO UDP
 * @author BaoZhou
 * @date 2018/7/24
 */

public class TestNonBlockingNIO2 {
    @Test
    public void client() {
        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(LocalDateTime.now().toString().getBytes());
            byteBuffer.flip();
            datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 9898));
            byteBuffer.clear();
            datagramChannel.close();
    } catch(IOException e)
    {
        e.printStackTrace();
    }

}

    @Test
    public void server() {
        try {
            //1.获取通道
            DatagramChannel datagramChannel = DatagramChannel.open();
            //2.开启非阻塞模式
            datagramChannel.configureBlocking(false);
            //3.绑定连接
            datagramChannel.bind(new InetSocketAddress(9898));

            Selector selector = Selector.open();

            datagramChannel.register(selector, SelectionKey.OP_READ);

            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        datagramChannel.receive(byteBuffer);
                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                    //使用完毕后取消Key
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
