package com.jun.plugin.fastnetty.handler;

import com.jun.plugin.fastnetty.core.message.OutputMessage;

/**
 * 消息发送器
 * @author peiyu
 */
public interface MessageSender {

    /**
     * 发送消息
     * @param message 需要发送的消息
     */
    void send(OutputMessage message);
}
