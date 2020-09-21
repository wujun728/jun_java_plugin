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
package net.ymate.platform.serv.nio;

import net.ymate.platform.serv.ISession;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午6:10
 * @version 1.0
 */
public interface INioSession extends ISession {

    /**
     * 注册事件
     *
     * @param ops 事件参数
     * @throws IOException 可能产生的异常
     */
    void registerEvent(int ops) throws IOException;

    void selectionKey(SelectionKey key);

    SelectionKey selectionKey();

    boolean connectSync(long time);

    void finishConnect();

    void closeNow() throws IOException;

    void read() throws IOException;

    void write() throws IOException;
}
