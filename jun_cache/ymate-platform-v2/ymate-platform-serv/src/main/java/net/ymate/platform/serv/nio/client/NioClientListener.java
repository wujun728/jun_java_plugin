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
package net.ymate.platform.serv.nio.client;

import net.ymate.platform.serv.AbstractListener;
import net.ymate.platform.serv.nio.INioSession;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午5:59
 * @version 1.0
 */
public class NioClientListener extends AbstractListener<INioSession> {

    public void onSessionRegisted(INioSession session) throws IOException {
    }

    public void onSessionConnected(INioSession session) throws IOException {
        session.selectionKey().interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    public final void onSessionAccepted(INioSession session) throws IOException {
    }

    public void onBeforeSessionClosed(INioSession session) throws IOException {
    }

    public void onAfterSessionClosed(INioSession session) throws IOException {
    }

    public void onMessageReceived(Object message, INioSession session) throws IOException {
    }
}
