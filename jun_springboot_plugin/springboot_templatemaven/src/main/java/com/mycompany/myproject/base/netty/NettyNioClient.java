package com.mycompany.myproject.base.netty;

import com.mycompany.myproject.base.netty.msg.Send;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class NettyNioClient {

    private static class SingletonHolder {
        static final NettyNioClient instance = new NettyNioClient();
    }

    public static NettyNioClient getInstance() {
        return SingletonHolder.instance;
    }

    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf;

    private NettyNioClient() {
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        //超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                        sc.pipeline().addLast(new ReadTimeoutHandler(5));
                        sc.pipeline().addLast(new NettyNioClientHandler());  //客户端业务处理类
                    }
                });
    }

    public void connect() {
        try {
            this.cf = b.connect("127.0.0.1", 8765).sync();
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChannelFuture getChannelFuture() {
        //如果管道没有被开启或者被关闭了，那么重连
        if (this.cf == null) {
            this.connect();
        }
        if (!this.cf.channel().isActive()) {
            this.connect();
        }
        return this.cf;
    }

    public static void main(String[] args) throws Exception {
        final NettyNioClient c = NettyNioClient.getInstance();

        ChannelFuture cf = c.getChannelFuture();
        for (int i = 1; i <= 3; i++) {
            //客户端发送的数据
            Send request = new Send();
            request.setId(i);
            request.setName("pro" + i);
            request.setMessage("数据信息" + i);

            cf.channel().writeAndFlush(request);
            TimeUnit.SECONDS.sleep(4);
        }
        //当5s没有交互，就会异步关闭channel
        cf.channel().closeFuture().sync();

        //再模拟一次传输
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ChannelFuture cf = c.getChannelFuture();
                    //System.out.println(cf.channel().isActive());
                    //System.out.println(cf.channel().isOpen());

                    //再次发送数据
                    Send request = new Send();
                    request.setId(4);
                    request.setName("pro" + 4);
                    request.setMessage("数据信息" + 4);

                    cf.channel().writeAndFlush(request);


                    cf.channel().closeFuture().sync();

                    System.out.println("子线程结束.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("断开连接,主线程结束..");
    }
}

