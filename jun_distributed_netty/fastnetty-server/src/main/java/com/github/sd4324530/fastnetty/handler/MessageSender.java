package com.github.sd4324530.fastnetty.handler;

import com.github.sd4324530.fastnetty.core.message.OutputMessage;

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
