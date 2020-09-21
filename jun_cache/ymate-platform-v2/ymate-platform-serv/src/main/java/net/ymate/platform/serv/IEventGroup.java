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
import java.util.concurrent.ExecutorService;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午6:28
 * @version 1.0
 */
public interface IEventGroup<CODEC extends ICodec, LISTENER extends IListener<SESSION>, SESSION extends ISession> extends Closeable {

    void start() throws IOException;

    void stop() throws IOException;

    CODEC codec();

    LISTENER listener();

    SESSION session();

    boolean isStarted();

    String name();

    void name(String name);

    int bufferSize();

    int executorCount();

    int connectionTimeout();

    ExecutorService executorService();
}
