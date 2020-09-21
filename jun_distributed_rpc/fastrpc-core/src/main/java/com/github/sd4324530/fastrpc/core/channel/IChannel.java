package com.github.sd4324530.fastrpc.core.channel;

import com.github.sd4324530.fastrpc.core.message.IMessage;

import java.io.Closeable;
import java.io.Serializable;

/**
 * @author peiyu
 */
public interface IChannel extends Closeable, Serializable {

    /**
     * 获取通道ID
     *
     * @return 通道ID
     */
    String id();

    /**
     * 连接是否开启
     *
     * @return 是否开启
     */
    boolean isOpen();

    /**
     * 读取数据
     *
     * @param messageClazz 数据类型
     * @param <M>          泛型
     * @return 读取到的数据
     */
    <M extends IMessage> M read(Class<M> messageClazz);

    /**
     * 写出数据
     *
     * @param message 需要写出去的数据
     */
    void write(IMessage message);

}
