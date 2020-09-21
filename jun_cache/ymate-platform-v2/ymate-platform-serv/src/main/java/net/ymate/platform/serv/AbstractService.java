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
 * @author 刘镇 (suninformation@163.com) on 15/11/19 下午4:55
 * @version 1.0
 */
public abstract class AbstractService {

    protected IReconnectService __reconnectService;

    protected IHeartbeatService __heartbeatService;

    protected void __doSetReconnectService(IReconnectService reconnectService) {
        this.__reconnectService = reconnectService;
    }

    protected void __doStartReconnectService() {
        if (__reconnectService != null && __reconnectService.isInited()) {
            __reconnectService.start();
        }
    }

    protected void __doStopReconnectService() throws IOException {
        if (__reconnectService != null && __reconnectService.isStarted()) {
            __reconnectService.close();
        }
    }

    //

    protected void __doSetHeartbeatService(IHeartbeatService heartbeatService) {
        this.__heartbeatService = heartbeatService;
    }

    protected void __doStartHeartbeatService() {
        if (__heartbeatService != null && __heartbeatService.isInited()) {
            __heartbeatService.start();
        }
    }

    protected void __doStopHeartbeatService() throws IOException {
        if (__heartbeatService != null && __heartbeatService.isStarted()) {
            __heartbeatService.close();
        }
    }
}
