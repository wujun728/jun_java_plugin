package com.github.sd4324530.fastrpc.core.serializer;

import com.github.sd4324530.fastrpc.core.message.IMessage;

import java.io.IOException;

/**
 * 序列化器
 *
 * @author peiyu
 */
public interface ISerializer {

    /**
     * 反序列化
     *
     * @param bytes        序列化数据
     * @param messageClass 序列化对象类型
     * @return 序列化对象
     * @throws IOException            异常
     * @throws ClassNotFoundException 异常
     */
    <M extends IMessage> M encoder(byte[] bytes, Class<M> messageClass) throws IOException, ClassNotFoundException;

    /**
     * 序列化ß
     *
     * @param message 序列化对象
     * @return 序列化数据
     * @throws IOException 异常
     */
    byte[] decoder(IMessage message) throws IOException;
}
