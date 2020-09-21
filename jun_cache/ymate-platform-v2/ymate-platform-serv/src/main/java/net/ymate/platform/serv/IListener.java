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
package net.ymate.platform.serv;

import java.io.IOException;

/**
 * 事件监听器
 *
 * @author 刘镇 (suninformation@163.com) on 15/11/6 下午3:41
 * @version 1.0
 */
public interface IListener<T extends ISession> {

    /**
     * 会话注册成功事件处理方法
     *
     * @param session 当前会话对象
     * @throws IOException 可能产生的异常
     */
    void onSessionRegisted(T session) throws IOException;

    /**
     * 会话连接事件处理方法
     *
     * @param session 当前会话对象
     * @throws IOException 可能产生的异常
     */
    void onSessionConnected(T session) throws IOException;

    /**
     * 会话被接受事件处理方法
     *
     * @param session 当前会话对象
     * @throws IOException 可能产生的异常
     */
    void onSessionAccepted(T session) throws IOException;

    /**
     * 会话关闭事件处理方法
     *
     * @param session 当前会话对象
     * @throws IOException 可能产生的异常
     */
    void onBeforeSessionClosed(T session) throws IOException;

    void onAfterSessionClosed(T session) throws IOException;

    /**
     * 消息到达事件处理方法
     *
     * @param message 消息对象
     * @param session 会话对象
     * @throws IOException 可能产生的异常
     */
    void onMessageReceived(Object message, T session) throws IOException;

    /**
     * 异常事件处理方法
     *
     * @param e       异常对象
     * @param session 会话对象
     * @throws IOException 可能产生的异常
     */
    void onExceptionCaught(Throwable e, T session) throws IOException;
}
