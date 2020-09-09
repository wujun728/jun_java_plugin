package com.github.sd4324530.fastrpc.client;

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
import java.lang.reflect.Proxy;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author peiyu
 */
public final class FastRpcClient implements IClient {

    private final Logger      log        = LoggerFactory.getLogger(getClass());
    private       int         threadSize = Runtime.getRuntime().availableProcessors() * 2;
    private       ISerializer serializer = new JdkSerializer();
    private       long        timeout    = 5000;
    private       boolean     retry      = true;

    private AsynchronousChannelGroup group;
    private IChannel                 channel;
    private SocketAddress            socketAddress;


    @Override
    public void connect(final SocketAddress address) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        this.connect(address, false);
    }

    @Override
    public void connect(final SocketAddress address, final boolean retry) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        this.group = AsynchronousChannelGroup.withFixedThreadPool(this.threadSize, Executors.defaultThreadFactory());
        final AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open(this.group);
        asynchronousSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        asynchronousSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        asynchronousSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
        this.retry = retry;
        this.socketAddress = address;
        try {
            asynchronousSocketChannel.connect(address).get(5, TimeUnit.SECONDS);
        } catch (final InterruptedException | TimeoutException e) {
            log.error("", e);
        } catch (final ExecutionException e) {
            log.error("连接失败");
            log.warn("是否重试:{}", this.retry);
            if (this.retry) {
                retry();
            }
        }
        this.channel = new FastChannel(asynchronousSocketChannel, this.serializer, timeout);
    }

    @Override
    public IClient threadSize(final int threadSize) {
        if (0 < threadSize) {
            this.threadSize = threadSize;
        } else {
            log.warn("threadSize must > 0!");
        }
        return this;
    }

    @Override
    public IClient serializer(final ISerializer serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public IClient timeout(final long timeout) {
        if (0 < timeout) {
            this.timeout = timeout;
        } else {
            log.warn("timeout must > 0!");
        }
        return this;
    }

    @Override
    public <T> T getService(final Class<T> clazz) {
        return this.getService(clazz.getSimpleName(), clazz);
    }

    @Override
    public <T> T getService(final String name, final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            final RequestMessage requestMessage = new RequestMessage();
            requestMessage.setSeq(UUID.randomUUID().toString().replaceAll("-", ""));
            requestMessage.setServerName(name);
            requestMessage.setMethodName(method.getName());
            if (Objects.nonNull(args) && 0 != args.length) {
                requestMessage.setArgs(args);
                final Class[] argsClass = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    argsClass[i] = args[i].getClass();
                }
                requestMessage.setArgsClassTypes(argsClass);
            }
            final ResponseMessage responseMessage = this.invoke(requestMessage);
            if (null == responseMessage) {
                log.warn("RPC调用返回null....");
                return null;
            }
            if (responseMessage.getResultCode() != ResultCode.SUCCESS) {
                throw new RuntimeException(responseMessage.getErrorMessage());
            }
            return responseMessage.getResponseObject();
        });
    }

    @Override
    public ResponseMessage invoke(final RequestMessage requestMessage) {
        try {
            this.channel.write(requestMessage);
            return this.channel.read(ResponseMessage.class);
        } catch (final Exception e) {
            log.error("Rpc调用异常:", e);
            log.debug("是否重试:" + this.retry);
            if (e instanceof FastrpcException) {
                if (!this.retry) {
                    if (this.channel.isOpen()) {
                        try {
                            this.channel.close();
                        } catch (IOException ignored) {
                        }
                    }
                    return null;
                }
                retry();
                return invoke(requestMessage);
            }
            final ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setSeq(requestMessage.getSeq());
            responseMessage.setResultCode(ResultCode.OTHER);
            responseMessage.setErrorMessage(e.toString());
            return responseMessage;
        }
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
        this.group.shutdownNow();
    }

    private void retry() {
        try {
            TimeUnit.SECONDS.sleep(1);
            if (null != this.channel && this.channel.isOpen()) {
                this.channel.close();
            }
            log.debug("连接:{}", this.socketAddress.toString());
            final AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open(this.group);
            asynchronousSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
            asynchronousSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            asynchronousSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            asynchronousSocketChannel.connect(this.socketAddress).get(5, TimeUnit.SECONDS);
            this.channel = new FastChannel(asynchronousSocketChannel, this.serializer, timeout);
        } catch (final Exception e) {
            retry();
        }
    }
}
