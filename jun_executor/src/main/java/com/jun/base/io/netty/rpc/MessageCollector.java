package com.jun.base.io.netty.rpc;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.jun.base.io.netty.rpc.common.IMessageHandler;

@ChannelHandler.Sharable
public class MessageCollector extends ChannelInboundHandlerAdapter {
    // 业务线程池
    private ThreadPoolExecutor executor;

    public MessageCollector(int workerThreads) {
        // 业务队列最大1000，避免堆积
        // 如果子线程处理不过来，io线程也会加入处理业务逻辑(callerRunsPolicy)
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);
        // 给业务线程命名
        ThreadFactory factory = new ThreadFactory() {

            AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("rpc-" + seq.getAndIncrement());
                return t;
            }

        };
        // 闲置时间超过30秒的线程自动销毁
        this.executor = new ThreadPoolExecutor(1, workerThreads, 30, TimeUnit.SECONDS, queue, factory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public void closeGracefully() {
        // 优雅一点关闭，先通知，再等待，最后强制关闭
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        this.executor.shutdownNow();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端来了一个新链接
        System.out.println("connection comes");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端走了一个
        System.out.println("connection leaves");
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageInput) {
            System.out.println("read a message");
            // 用业务线程池处理消息
            this.executor.execute(() -> {
                this.handleMessage(ctx, (MessageInput) msg);
            });
        }
    }
    private void handleMessage(ChannelHandlerContext ctx, MessageInput input) {
        // 业务逻辑在这里
        Class<?> clazz = MessageRegistry.get(input.getType());
        if (clazz == null) {
            // 没注册的消息用默认的处理器处理
            MessageHandlers.defaultHandler.handle(ctx, input.getRequestId(), input);
            return;
        }
        Object o = input.getPayload(clazz);
        // 这里是小鲜的瑕疵，代码外观上比较难看，但是大厨表示才艺不够，很无奈
        // 读者如果感兴趣可以自己想办法解决
        @SuppressWarnings("unchecked")
        IMessageHandler<Object> handler = (IMessageHandler<Object>) MessageHandlers.get(input.getType());
        if (handler != null) {
            handler.handle(ctx, input.getRequestId(), o);
        } else {
            // 用默认的处理器处理吧
            MessageHandlers.defaultHandler.handle(ctx, input.getRequestId(), input);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 此处可能因为客户端机器突发重启
        // 也可能是客户端链接闲置时间超时，后面的ReadTimeoutHandler抛出来的异常
        // 也可能是消息协议错误，序列化异常
        // etc.
        // 不管它，链接统统关闭，反正客户端具备重连机制
        System.out.println("connection error");
        cause.printStackTrace();
        ctx.close();
    }

}
