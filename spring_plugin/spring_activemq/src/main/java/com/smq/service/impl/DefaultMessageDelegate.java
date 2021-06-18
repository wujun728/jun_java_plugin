package com.smq.service.impl;

import com.smq.service.MessageDelegate;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by madong on 16/10/20.
 * MessageDelegate接口(上文中DefaultMessageDelegate类)的实现完全不依赖于JMS。
 * 它是一个真正的POJO，我们可以通过如下配置把它设置成MDP。
 */
public class DefaultMessageDelegate implements MessageDelegate {

    public void handleMessage(String message) {
        System.out.println(message+"----message");

    }

    public void handleMessage(Map message) {
        System.out.println(message+"----Map");

    }

    public void handleMessage(byte[] message) {
        System.out.println(message+"----byte");

    }

    public void handleMessage(Serializable message) {
        System.out.println(message+"----Serializable");

    }
}
