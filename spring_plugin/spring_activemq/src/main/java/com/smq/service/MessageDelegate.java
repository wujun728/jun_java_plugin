package com.smq.service;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by madong on 16/10/20.
 */
public interface MessageDelegate {

    void handleMessage(String message);

    void handleMessage(Map message);

    void handleMessage(byte[] message);

    void handleMessage(Serializable message);
}
