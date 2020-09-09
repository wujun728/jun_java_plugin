package com.github.sd4324530.fastrpc.core.channel;

import com.github.sd4324530.fastrpc.core.exception.FastrpcException;
import com.github.sd4324530.fastrpc.core.message.IMessage;
import com.github.sd4324530.fastrpc.core.serializer.ISerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 通信通道
 * 消息报文规则
 * 前4字节表示本次报文长度
 * 根据此长度,读取剩余的数据报文
 *
 * @author peiyu
 */
public class FastChannel implements IChannel {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private       AsynchronousSocketChannel channel;
    private final String                    id;
    private final ISerializer               serializer;
    private final long                      timeout;


    public FastChannel(final AsynchronousSocketChannel channel, final ISerializer serializer, final long timeout) {
        this.channel = channel;
        this.serializer = serializer;
        this.timeout = timeout;
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }

    @Override
    public <M extends IMessage> M read(final Class<M> messageClazz) {
        if (this.isOpen()) {
            final ByteBuffer messageLength = ByteBuffer.allocate(4);
            try {
                final Integer integer = this.channel.read(messageLength).get(timeout, TimeUnit.MILLISECONDS);
                if (-1 == integer) {
                    log.debug("关闭连接 {} <-> {}", this.channel.getLocalAddress(), this.channel.getRemoteAddress());
                    close();
                    return null;
                }
                messageLength.flip();
                final int length = messageLength.getInt();
                final ByteBuffer message = ByteBuffer.allocate(length);
                this.channel.read(message).get();
                message.flip();
                return this.serializer.encoder(message.array(), messageClazz);
            } catch (final TimeoutException | ExecutionException e) {
                throw new FastrpcException(e);
            } catch (final Exception e) {
                log.error("读取数据异常", e);
            }
        }
        return null;
    }

    @Override
    public void write(final IMessage message) {
        try {
            if (this.isOpen()) {
                final byte[] bytes = this.serializer.decoder(message);
                final ByteBuffer byteBuffer = ByteBuffer.allocate(4 + bytes.length);
                byteBuffer.putInt(bytes.length);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                final Integer integer = this.channel.write(byteBuffer).get(timeout, TimeUnit.MILLISECONDS);
                if (-1 == integer) {
                    log.warn("连接断了....");
                    log.warn("open:{}", this.isOpen());
                }
            }
        } catch (final ExecutionException e) {
            log.warn("连接断了....");
            throw new FastrpcException(e);
        } catch (final Exception e) {
            log.error("写出数据异常", e);
            log.warn("open:{}", this.isOpen());
        }
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
