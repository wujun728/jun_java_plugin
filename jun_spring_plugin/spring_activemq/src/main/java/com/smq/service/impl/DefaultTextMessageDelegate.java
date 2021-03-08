package com.smq.service.impl;

import com.smq.service.TextMessageDelegate;

import javax.jms.TextMessage;

/**
 * Created by madong on 16/10/20.
 */
public class DefaultTextMessageDelegate implements TextMessageDelegate {

    public void receive(TextMessage message) {
        System.out.printf("text内容");
    }
}
