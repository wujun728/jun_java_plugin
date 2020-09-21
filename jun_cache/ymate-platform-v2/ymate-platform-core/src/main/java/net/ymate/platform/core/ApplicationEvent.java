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
package net.ymate.platform.core;

import net.ymate.platform.core.event.EventContext;
import net.ymate.platform.core.event.IEvent;

/**
 * 框架事件对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/17 下午6:35
 * @version 1.0
 */
public class ApplicationEvent extends EventContext<YMP, ApplicationEvent.EVENT> implements IEvent {

    /**
     * 框架事件枚举：<br>
     * APPLICATION_INITED - 框架初始化事件<br>
     * APPLICATION_DESTROYED - 框架销毁事件
     */
    public enum EVENT {
        APPLICATION_INITED, APPLICATION_DESTROYED
    }

    public ApplicationEvent(YMP owner, EVENT eventName) {
        super(owner, ApplicationEvent.class, eventName);
    }
}
