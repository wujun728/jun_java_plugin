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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午10:10
 * @version 1.0
 */
public abstract class AbstractEventGroup<CODEC extends ICodec, LISTENER extends IListener<SESSION>, SESSION extends ISession>
        implements IEventGroup<CODEC, LISTENER, SESSION> {

    private String __name;

    private ExecutorService __executorService;

    private CODEC __codec;
    private LISTENER __listener;

    private SESSION __session;

    private int __bufferSize = 4096;

    private int __executorCount = Runtime.getRuntime().availableProcessors();

    private int __connectionTimeout = 5000;

    private boolean __isStarted = false;

    private boolean __isServer;

    public AbstractEventGroup(IServerCfg cfg, LISTENER listener, CODEC codec) throws IOException {
        __name = cfg.getServerName();
        if (cfg.getBufferSize() > 0) {
            __bufferSize = cfg.getBufferSize();
        }
        __executorCount = cfg.getExecutorCount();
        //
        __codec = codec;
        __listener = listener;
        //
        __isServer = true;
    }

    public AbstractEventGroup(IClientCfg cfg, LISTENER listener, CODEC codec) throws IOException {
        __name = cfg.getClientName();
        if (cfg.getBufferSize() > 0) {
            __bufferSize = cfg.getBufferSize();
        }
        //
        __codec = codec;
        __listener = listener;
        //
        if (cfg.getConnectionTimeout() > 0) {
            __connectionTimeout = cfg.getConnectionTimeout();
        }
        __session = __doSessionCreate(cfg);
    }

    public synchronized void start() throws IOException {
        if (__isStarted) {
            return;
        }
        __executorService = Executors.newFixedThreadPool(__executorCount);
        //
        __isStarted = true;
    }

    public synchronized void stop() throws IOException {
        if (!__isStarted) {
            return;
        }
        __isStarted = false;
        if (__session != null) {
            __session.close();
        }
        __executorService.shutdown();
    }

    public void close() throws IOException {
        stop();
    }

    protected abstract SESSION __doSessionCreate(IClientCfg cfg) throws IOException;

    public CODEC codec() {
        return __codec;
    }

    public LISTENER listener() {
        return __listener;
    }

    public SESSION session() {
        return __session;
    }

    protected boolean isServer() {
        return __isServer;
    }

    public boolean isStarted() {
        return __isStarted;
    }

    public String name() {
        return __name;
    }

    public void name(String name) {
        __name = name;
    }

    public int bufferSize() {
        return __bufferSize;
    }

    public int executorCount() {
        return __executorCount;
    }

    public int connectionTimeout() {
        return __connectionTimeout;
    }

    public ExecutorService executorService() {
        return __executorService;
    }
}
