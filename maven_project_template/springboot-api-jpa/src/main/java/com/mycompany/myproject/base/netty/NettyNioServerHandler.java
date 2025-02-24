package com.mycompany.myproject.base.netty;

import com.mycompany.myproject.base.netty.msg.Receive;
import com.mycompany.myproject.base.netty.msg.Send;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyNioServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Send send = (Send) msg;
        System.out.println("receive client message："+send);

        Receive receive = new Receive();
        receive.setId(send.getId());
        receive.setMessage(send.getMessage());
        receive.setName(send.getName());
        ctx.writeAndFlush(receive);
    }

}
