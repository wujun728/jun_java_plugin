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
package net.ymate.platform.serv.impl;

import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.serv.IClient;
import net.ymate.platform.serv.IReconnectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/19 下午3:06
 * @version 1.0
 */
public class DefaultReconnectService extends Thread implements IReconnectService {

    private static final Log _LOG = LogFactory.getLog(DefaultReconnectService.class);

    private static AtomicLong __counter = new AtomicLong(0);

    private IClient __client;

    private boolean __inited;
    private boolean __flag;

    private long __timeout = 5;

    public void init(IClient client) {
        __client = client;
        __inited = true;
    }

    public boolean isInited() {
        return __inited;
    }

    public boolean isStarted() {
        return __flag;
    }

    @Override
    public synchronized void start() {
        __flag = true;
        setName("ReconnectService-" + __client.listener().getClass().getSimpleName());
        if (__client.clientCfg().getConnectionTimeout() > 0) {
            __timeout = __client.clientCfg().getConnectionTimeout();
        }
        super.start();
    }

    @Override
    public void run() {
        while (__flag) {
            try {
                if (!__client.isConnected() && __counter.getAndIncrement() > 1) {
                    __client.reconnect();
                    //
                    __counter.set(0);
                } else {
                    sleep(__timeout * DateTimeUtils.SECOND);
                }
            } catch (Exception e) {
                if (__flag) {
                    _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(e));
                } else {
                    _LOG.debug(e.getMessage(), RuntimeUtils.unwrapThrow(e));
                }
            }
        }
    }

    @Override
    public void interrupt() {
        try {
            __flag = false;
            join();
        } catch (Exception e) {
            _LOG.debug(e.getMessage(), RuntimeUtils.unwrapThrow(e));
        }
        super.interrupt();
    }

    public void close() throws IOException {
        interrupt();
    }
}
