package com.smq.service;

import javax.jms.TextMessage;

/**
 * Created by madong on 16/10/20.
 */
public interface TextMessageDelegate {
    void receive(TextMessage message);

}
