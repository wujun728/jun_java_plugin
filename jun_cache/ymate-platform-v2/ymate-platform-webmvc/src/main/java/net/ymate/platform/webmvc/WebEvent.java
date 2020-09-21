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
package net.ymate.platform.webmvc;

import net.ymate.platform.core.event.EventContext;
import net.ymate.platform.core.event.IEvent;

/**
 * WEB事件对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/19 下午11:45
 * @version 1.0
 */
public class WebEvent extends EventContext<IWebMvc, WebEvent.EVENT> implements IEvent {

    /**
     * WEB事件枚举：<br>
     * SERVLET_CONTEXT_INITED       - 容器初始化事件<br>
     * SERVLET_CONTEXT_DESTROYED    - 容器销毁事件<br>
     * SERVLET_CONTEXT_ATTR_ADDED
     * SERVLET_CONTEXT_ATTR_REMOVEED
     * SERVLET_CONTEXT_ATTR_REPLACED
     * SESSION_CREATED              - 会话创建事件<br>
     * SESSION_DESTROYED            - 会话销毁事件<br>
     * SESSION_ATTR_ADDED
     * SESSION_ATTR_REMOVEED
     * SESSION_ATTR_REPLACED
     * REQUEST_INITED               - 请求初始化事件<br>
     * REQUEST_DESTROYED            - 请求销毁事件<br>
     * REQUEST_ATTR_ADDED
     * REQUEST_ATTR_REMOVEED
     * REQUEST_ATTR_REPLACED
     * REQUEST_RECEIVED             - 接收控制器方法请求事件<br>
     * REQUEST_COMPLETED            - 完成控制器方法请求事件<br>
     */
    public enum EVENT {
        SERVLET_CONTEXT_INITED,
        SERVLET_CONTEXT_DESTROYED,
        SERVLET_CONTEXT_ATTR_ADDED,
        SERVLET_CONTEXT_ATTR_REMOVEED,
        SERVLET_CONTEXT_ATTR_REPLACED,
        SESSION_CREATED,
        SESSION_DESTROYED,
        SESSION_ATTR_ADDED,
        SESSION_ATTR_REMOVEED,
        SESSION_ATTR_REPLACED,
        REQUEST_INITED,
        REQUEST_DESTROYED,
        REQUEST_ATTR_ADDED,
        REQUEST_ATTR_REMOVEED,
        REQUEST_ATTR_REPLACED,
        REQUEST_RECEIVED,
        REQUEST_COMPLETED
    }

    public WebEvent(IWebMvc owner, EVENT eventName) {
        super(owner, WebEvent.class, eventName);
    }
}
