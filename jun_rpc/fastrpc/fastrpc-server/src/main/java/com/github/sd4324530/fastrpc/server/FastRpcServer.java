package com.github.sd4324530.fastrpc.server;

import com.github.sd4324530.fastrpc.core.channel.FastChannel;
import com.github.sd4324530.fastrpc.core.channel.IChannel;
import com.github.sd4324530.fastrpc.core.exception.FastrpcException;
import com.github.sd4324530.fastrpc.core.message.RequestMessage;
import com.github.sd4324530.fastrpc.core.message.ResponseMessage;
import com.github.sd4324530.fastrpc.core.message.ResultCode;
import com.github.sd4324530.fastrpc.core.serializer.ISerializer;
import com.github.sd4324530.fastrpc.core.serializer.JdkSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * RPC服务端实现
 *
 * @author peiyu
 */
public final class FastRpcServer implements IServer {

    private final Logger          log             = LoggerFactory.getLogger(getClass());
    private       int             threadSize      = Runtime.getRuntime().availableProcessors() * 2;
    private       ISerializer     serializer      = new JdkSerializer();
    private       long            timeout         = 5000;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10000);

    private       int                             port;
    private       AsynchronousChannelGroup        group;
    private       AsynchronousServerSocketChannel channel;
    private final Map<String, Object>             serverMap;

    public FastRpcServer() {
        this.serverMap = new HashMap<>();
    }

    @Override
    public IServer bind(final int port) {
        this.port = port;
        return this;
    }

    @Override
    public IServer threadSize(final int threadSize) {
        if (0 < threadSize) {
            this.threadSize = threadSize;
        } else {
            log.warn("threadSize must > 0!");
        }
        return this;
    }

    @Override
    public IServer timeout(final long timeout) {
        if (0 < timeout) {
            this.timeout = timeout;
        } else {
            log.warn("timeout must > 0");
        }
        return this;
    }

    @Override
    public IServer register(final String name, final Object object) {
        Objects.requireNonNull(name, "server'name is null");
        Objects.requireNonNull(object, "server " + name + " is null");
        this.serverMap.put(name, object);
        return this;
    }

    @Override
    public IServer register(final Object object) {
        Objects.requireNonNull(object, "server is null");
        this.serverMap.put(object.getClass().getSimpleName(), object);
        return this;
    }

    @Override
    public IServer register(final Map<String, Object> serverMap) {
        Objects.requireNonNull(serverMap, "serverMap is null");
        serverMap.forEach(this::register);
        return this;
    }

    @Override
    public IServer serializer(final ISerializer serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public void start() throws IOException {
        log.debug("开始启动RPC服务端......");
        this.group = AsynchronousChannelGroup.withFixedThreadPool(this.threadSize, Executors.defaultThreadFactory());
        this.channel = AsynchronousServerSocketChannel
                .open(this.group)
                .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                .bind(new InetSocketAddress("localhost", this.port));

        this.channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(final AsynchronousSocketChannel result, final Void attachment) {
                channel.accept(null, this);
                String localAddress = null;
                String remoteAddress = null;
                try {
                    localAddress = result.getLocalAddress().toString();
                    remoteAddress = result.getRemoteAddress().toString();
                    log.debug("创建连接 {} <-> {}", localAddress, remoteAddress);
                } catch (final IOException e) {
                    log.error("", e);
                }
                final IChannel channel = new FastChannel(result, serializer, timeout);
                while (channel.isOpen()) {
                    handler(channel);
                }
                log.debug("断开连接 {} <-> {}", localAddress, remoteAddress);
            }

            @Override
            public void failed(final Throwable exc, final Void attachment) {
                log.error("通信失败", exc);
                try {
                    close();
                } catch (final IOException e) {
                    log.error("关闭通道异常", e);
                }
            }
        });
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
        this.group.shutdownNow();
    }

    private void handler(final IChannel channel) {
        try {
            final RequestMessage request = channel.read(RequestMessage.class);
            if (Objects.nonNull(request)) {
                final String serverName = request.getServerName();
                final Object obj = this.serverMap.get(serverName);
                final Method method = obj.getClass().getMethod(request.getMethodName(), request.getArgsClassTypes());
                this.executorService.execute(() -> {
                    Object response = null;
                    try {
                        response = method.invoke(obj, request.getArgs());
                    } catch (final Exception ignored) {
                    }
                    final ResponseMessage responseMessage = new ResponseMessage();
                    responseMessage.setSeq(request.getSeq());
                    responseMessage.setResultCode(ResultCode.SUCCESS);
                    responseMessage.setResponseObject(response);
                    channel.write(responseMessage);
                });
            }
        } catch (final Exception e) {
            if (e instanceof FastrpcException) {
                if (channel.isOpen()) {
                    try {
                        channel.close();
                    } catch (final IOException ignored) {
                    }
                }
            }
        }
    }
}
