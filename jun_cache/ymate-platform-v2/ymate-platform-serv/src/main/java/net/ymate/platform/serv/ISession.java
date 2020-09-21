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

import java.io.Closeable;
import java.io.IOException;

/**
 * 会话接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/11/9 上午9:32
 * @version 1.0
 */
public interface ISession extends Closeable {

    /**
     * 会话状态枚举
     */
    enum Status {
        NEW, CONNECTED, CLOSED, ERROR
    }

    /**
     * @return 返回会话唯一标识
     */
    String id();

    /**
     * @return 返回会话状态是否为NEW
     */
    boolean isNew();

    /**
     * @return 返回会话状态是否为CONNECTED
     */
    boolean isConnected();

    /**
     * @return 返回当前会话状态
     */
    Status status();

    /**
     * 设置当前会话状态
     *
     * @param status 会话状态对象
     */
    void status(Status status);

    /**
     * 更新会话活动状态
     */
    void touch();

    /**
     * @return 返回最后更新会话状态的时间(毫秒)
     */
    long lastTouchTime();

    <T> T attr(String key);

    void attr(String key, Object value);

    /**
     * 向会话发送消息
     *
     * @param message 消息对象
     * @throws IOException 可能产生的异常
     */
    void send(Object message) throws IOException;
}
