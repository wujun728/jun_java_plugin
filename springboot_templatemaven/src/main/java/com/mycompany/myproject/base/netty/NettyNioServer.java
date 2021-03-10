package com.mycompany.myproject.base.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyNioServer {

    public static void main(String[] args){

        // Group：群组，Loop：循环，Event：事件，这几个东西联在一起，相比大家也大概明白它的用途了。
        // Netty内部都是通过线程在处理各种数据，EventLoopGroup就是用来管理调度他们的，注册Channel，管理他们的生命周期。
        //线程组：仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好
        EventLoopGroup pEventLoopGroup = new NioEventLoopGroup(1);
        //线程组：用来进行网络通讯读写
        EventLoopGroup cEventLoopGroup = new NioEventLoopGroup();

        //Bootstrap用来配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 绑定两个线程池
        serverBootstrap.group(pEventLoopGroup, cEventLoopGroup)
                //注册服务端channel
                .channel(NioServerSocketChannel.class)
                /**
                 * BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
                 * 用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，将使用默认值50。
                 * 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
                 * 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                 */
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 32*1024)//设置发送缓冲的大小
                .option(ChannelOption.SO_RCVBUF, 32*1024)    //设置接收缓冲区大小
                //.option(ChannelOption.SO_KEEPALIVE, true)     //保持连续
                // 设置日志
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {


                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {



                        //marshaliing的编解码操作,要传输对象，必须编解码
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

                        //5s没有交互，就会关闭channel
                        sc.pipeline().addLast(new ReadTimeoutHandler(5));

                        // ChannelHandler和ChannelPipeline看着差不多，不仔细看还可能会混淆，那么它们是什么关系呢？
                        /**** 其实，ChannelPipeline 就是 ChannelHandler 链的容器。*/
                        sc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));

                        //服务端业务处理类
                        sc.pipeline().addLast(new NettyNioServerHandler());
                    }
                });


                // 绑定端口,随后调用它的同步阻塞方法 sync 等等绑定操作成功,完成之后 Netty 会返回一个 ChannelFuture
                // 它的功能类似于的 Future,主要用于异步操作的通知回调.
                try{
                    ChannelFuture channelFuture =  serverBootstrap.bind(8765).sync();

                    // Wait until the server socket is closed.
                    // In this example, this does not happen, but you can do that to gracefully
                    // shut down your server.
                    // sync()会同步等待连接操作结果，用户线程将在此wait()，直到连接操作完成之后，线程被notify(),用户代码继续执行
                    // closeFuture()当Channel关闭时返回一个ChannelFuture,用于链路检测
                    // 等待服务端监听端口关闭,调用 sync 方法进行阻塞,等待服务端链路关闭之后 main 函数才退出.
                    channelFuture.channel().closeFuture().sync();
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    pEventLoopGroup.shutdownGracefully();
                    cEventLoopGroup.shutdownGracefully();
                }

    }
}
