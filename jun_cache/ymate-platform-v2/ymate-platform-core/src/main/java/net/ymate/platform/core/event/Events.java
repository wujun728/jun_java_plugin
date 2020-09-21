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
package net.ymate.platform.core.event;

import net.ymate.platform.core.event.impl.DefaultEventConfig;

/**
 * 事件管理器
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/16 上午2:15
 * @version 1.0
 */
public final class Events {

    /**
     * 事件触发模式枚举：<br>
     * NORMAL - 同步执行<br>
     * ASYNC  - 异步执行
     */
    public enum MODE {
        NORMAL, ASYNC
    }

    private IEventProvider __provider;

    public static Events create() {
        return new Events();
    }

    public static Events create(IEventConfig eventConfig) {
        return new Events(eventConfig);
    }

    private Events() {
        this(new DefaultEventConfig());
    }

    private Events(IEventConfig eventConfig) {
        __provider = eventConfig.getEventProvider();
        __provider.init(eventConfig);
    }

    public void destroy() {
        __provider.destroy();
    }

    @SuppressWarnings("unchecked")
    public <EVENT extends IEvent> Events registerEvent(Class<EVENT> event) {
        __provider.registerEvent(event);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <CONTEXT extends EventContext> Events registerListener(Class<? extends IEvent> eventClass, IEventListener<CONTEXT> eventListener) {
        __provider.registerListener(eventClass, eventListener);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <CONTEXT extends EventContext> Events registerListener(Events.MODE mode, Class<? extends IEvent> eventClass, IEventListener<CONTEXT> eventListener) {
        __provider.registerListener(mode, eventClass, eventListener);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <CONTEXT extends EventContext> Events fireEvent(CONTEXT context) {
        __provider.fireEvent(context);
        return this;
    }
}
