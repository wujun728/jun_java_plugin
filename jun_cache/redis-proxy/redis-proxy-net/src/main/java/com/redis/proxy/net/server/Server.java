/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.net.server;

import com.redis.proxy.net.codec.RedisCmdDecoder;
import com.redis.proxy.net.codec.RedisCmdEncoder;
import com.redis.proxy.net.handler.RdsMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * tcp server
 *
 * @author zhanggaofeng
 */
public class Server {

        private EventLoopGroup boss;

        public Server(final int port, final int minThreads, final int maxThreads, final RdsMsgHandler handler) {
                boss = new NioEventLoopGroup(1);
                ServerBootstrap b = new ServerBootstrap();
                b.group(boss);
                b.channel(NioServerSocketChannel.class);
                b.childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                                // 消息编码
                                ch.pipeline().addLast(new RedisCmdEncoder());
                                // 消息解码
                                ch.pipeline().addLast(new RedisCmdDecoder());
                                /**
                                 * 钝化时间
                                 */
                                ch.pipeline().addLast(new ServerIdleHandler());
                                ch.pipeline().addLast(new ServerHandler(minThreads, maxThreads, handler));
                        }
                });
                b.option(ChannelOption.SO_BACKLOG, 128);
                // 设置通讯不能延迟
                b.option(ChannelOption.TCP_NODELAY, true);
                b.childOption(ChannelOption.SO_KEEPALIVE, true);
                try {
                        b.bind(port).sync();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

        }

        public void destory() {
                try {
                        boss.shutdownGracefully();
                } catch (Exception e) {

                }
        }
}
