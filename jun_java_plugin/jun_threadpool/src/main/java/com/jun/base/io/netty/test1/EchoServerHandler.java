package com.jun.base.io.netty.test1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.nio.ByteBuffer;




/**
 *
 * 一个服务器handler,这个组件实现了服务器的业务逻辑。决定了连接创建后和接收到信息后该如何处理
 *
 */
@ChannelHandler.Sharable  //@Sharable 标识这类的实例之间可以在 channel 里面共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    /**
     * 每个信息入站都会调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        ByteBuffer in = (ByteBuffer)msg;
        System.out.println("Server received: " + in.toString()); //日志消息输出到控制台
        ctx.write(in);//将所接收的消息返回给发送者。注意，这还没有冲刷数据
    }

    /**
     * 通知处理器最后的changeRead()完成调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//冲刷所有待审消息到远程节点。关闭通道后，操作完成
                .addListener(ChannelFutureListener.CLOSE);



    }

    /**
     * 读操作时捕捉的异常调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();//打印异常堆栈跟踪
        ctx.close();//关闭通道
    }
}
