package com.gs.nio;

import com.gs.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by Wang Genshen on 2017-05-18.
 */
public class Server {

    private Charset charset = Charset.forName("UTF-8");

    public void init() {
        try {
            // 打开选择器
            Selector selector = Selector.open();
            // 建立服务器通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 构建服务器地址
            InetSocketAddress isa = new InetSocketAddress(Constants.PORT);
            // 服务器通道绑定地址
            serverSocketChannel.socket().bind(isa);
            // 服务器通道设置为非阻塞式
            serverSocketChannel.configureBlocking(false);
            // 注册选择器到通道，可连接
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > 0) {
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    // 如果通道可连接
                    if (selectionKey.isAcceptable()) {
                        connect(selector, serverSocketChannel);
                    }
                    // 如果通道可读
                    if (selectionKey.isReadable()) {
                        read(selectionKey);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void read(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        String content = "";
        try {
            while (socketChannel.read(buff) > 0) {
                buff.flip();
                content += charset.decode(buff);
            }
            if (content != "") {
                System.out.println("读取的数据：" + content);
                write(socketChannel, content);
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
        } catch (IOException io) {
            selectionKey.cancel();
            if (selectionKey.channel() != null) {
                selectionKey.channel().close();
            }
        }
    }

    public void write(SocketChannel socketChannel, String msg) {
        try {
            socketChannel.write(charset.encode(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.init();
    }
}
