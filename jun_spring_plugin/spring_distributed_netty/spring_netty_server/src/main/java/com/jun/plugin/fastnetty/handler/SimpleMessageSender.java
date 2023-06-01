package com.jun.plugin.fastnetty.handler;

import com.jun.plugin.fastnetty.core.message.OutputMessage;

import io.netty.channel.Channel;

/**
 * 简单的消息发送器
 * @author Wujun
 */
public class SimpleMessageSender implements MessageSender {

    private final Channel channel;

    public SimpleMessageSender(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(OutputMessage message) {
        if(null != this.channel && this.channel.isOpen()) {
            this.channel.writeAndFlush(message);
        }
    }
}
