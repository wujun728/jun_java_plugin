package com.gs.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Wang Genshen on 2017-04-18.
 */
public class ServerHandler extends IoHandlerAdapter {
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("一个客户端连接上，IP:" + session.getRemoteAddress());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("一个连接打开了");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("一个连接关闭了");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println(session.getId() + "连接空闲" + session.getIdleCount(status) + ", 状态： " + status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("异常" + cause.getMessage());
        session.closeNow();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("读取到消息：" + message.toString());
        if (message.toString().equalsIgnoreCase("quit")) {
            session.closeNow();
        } else {
            session.write("你好！");
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("已发送消息： " + message.toString());
    }
}
