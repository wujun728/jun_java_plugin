package com.jun.plugin.fastnetty.handler;

/**
 * 消息处理器接口
 * @author peiyu
 */
public interface MessageHandler {

    /**
     * 处理消息
     * @param bytes 需要处理的消息数据
     * @param sender 消息发送器 用于消息处理完后的响应回复
     */
    void handler(byte[] bytes, MessageSender sender);
}
