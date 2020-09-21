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
 * 链路维护(心跳)服务接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/11/19 下午1:28
 * @version 1.0
 */
public interface IHeartbeatService extends Closeable {

    final class NONE implements IHeartbeatService {
        public void init(IClient client) {
        }

        public boolean isInited() {
            return false;
        }

        public void start() {
        }

        public boolean isStarted() {
            return false;
        }

        public void close() throws IOException {
        }
    }

    /**
     * 服务初始化
     *
     * @param client 客户端接口对象
     */
    void init(IClient client);

    /**
     * @return 是否已初始化
     */
    boolean isInited();

    /**
     * 启动服务
     */
    void start();

    /**
     * @return 是否已启动
     */
    boolean isStarted();
}
