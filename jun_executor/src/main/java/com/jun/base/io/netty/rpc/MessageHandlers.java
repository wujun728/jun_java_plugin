package com.jun.base.io.netty.rpc;

import java.util.HashMap;
import java.util.Map;

import com.jun.base.io.netty.rpc.common.DefaultHandler;
import com.jun.base.io.netty.rpc.common.IMessageHandler;

public class MessageHandlers {
    private static Map<String, IMessageHandler<?>> handlers = new HashMap<>();
    public static DefaultHandler defaultHandler = new DefaultHandler();

    public static void register(String type, IMessageHandler<?> handler) {
        handlers.put(type, handler);
    }

    public static IMessageHandler<?> get(String type) {
        IMessageHandler<?> handler = handlers.get(type);
        return handler;
    }

}
