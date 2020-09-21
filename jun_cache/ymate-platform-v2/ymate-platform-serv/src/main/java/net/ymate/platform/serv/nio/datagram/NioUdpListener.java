/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.serv.nio.datagram;

import net.ymate.platform.serv.AbstractListener;
import net.ymate.platform.serv.nio.INioSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/17 下午4:44
 * @version 1.0
 */
public abstract class NioUdpListener extends AbstractListener<INioSession> {

    /**
     * 客户端与服务端连接已建立并准备就绪
     *
     * @return 预发送的消息对象, 返回null表示不发送消息
     * @throws IOException 可能产生的异常
     */
    public abstract Object onSessionReady() throws IOException;

    /**
     * 消息到达事件处理
     *
     * @param sourceAddr 目标来源IP地址及端口
     * @param message    消息对象
     * @return 预回应的消息对象, 返回null表示不发送回应消息
     * @throws IOException 可能产生的异常
     */
    public abstract Object onMessageReceived(InetSocketAddress sourceAddr, Object message) throws IOException;

    /**
     * 捕获异常事件处理
     *
     * @param sourceAddr 目标来源IP地址及端口, 若为null则代表本端产生的异常
     * @param e          异常对象
     * @throws IOException 可能产生的异常
     */
    public abstract void onExceptionCaught(InetSocketAddress sourceAddr, Throwable e) throws IOException;

    public final void onSessionRegisted(INioSession session) throws IOException {
        Object _result = onSessionReady();
        if (_result != null) {
            session.send(_result);
        }
    }

    public final void onSessionConnected(INioSession session) throws IOException {
    }

    public final void onSessionAccepted(INioSession session) throws IOException {
    }

    public final void onBeforeSessionClosed(INioSession session) throws IOException {
    }

    public final void onAfterSessionClosed(INioSession session) throws IOException {
    }

    public final void onMessageReceived(Object message, INioSession session) throws IOException {
        InetSocketAddress _sourceAddr = session.attr(SocketAddress.class.getName());
        Object _result = onMessageReceived(_sourceAddr, message);
        if (_result != null) {
            session.send(_result);
        }
    }

    @Override
    public final void onExceptionCaught(Throwable e, INioSession session) throws IOException {
        InetSocketAddress _sourceAddr = session.attr(SocketAddress.class.getName());
        onExceptionCaught(_sourceAddr, e);
    }
}
