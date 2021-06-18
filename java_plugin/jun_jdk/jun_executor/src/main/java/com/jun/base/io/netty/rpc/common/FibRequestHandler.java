package com.jun.base.io.netty.rpc.common;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import com.jun.base.io.netty.rpc.MessageOutput;

public class FibRequestHandler implements IMessageHandler<Integer>{

    private List<Long> fibs = new ArrayList<>();

    {
        fibs.add(1L); // fib(0) = 1
        fibs.add(1L); // fib(1) = 1
    }
    @Override
    public void handle(ChannelHandlerContext ctx, String requestId, Integer n) {
        for (int i = fibs.size(); i < n + 1; i++) {
            long value = fibs.get(i - 2) + fibs.get(i - 1);
            fibs.add(value);
        }
        // 输出响应
        ctx.writeAndFlush(new MessageOutput(requestId, "fib_res", fibs.get(n)));
    }
}
