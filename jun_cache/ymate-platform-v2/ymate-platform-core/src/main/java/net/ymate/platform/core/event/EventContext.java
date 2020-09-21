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

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件上下文接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/16 上午2:58
 * @version 1.0
 */
public class EventContext<T, E extends Enum> extends EventObject {

    private Class<? extends IEvent> __eventClass;

    private E __eventName;

    private Map<String, Object> __params;

    private final long __timestamp;

    public EventContext(T owner, Class<? extends IEvent> eventClass, E eventName) {
        super(owner);
        __eventClass = eventClass;
        __eventName = eventName;
        __params = new HashMap<String, Object>();
        //
        __timestamp = System.currentTimeMillis();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getSource() {
        return (T) super.getSource();
    }

    public Class<? extends IEvent> getEventClass() {
        return __eventClass;
    }

    public E getEventName() {
        return __eventName;
    }

    @SuppressWarnings("unchecked")
    public <EVENT_SOURCE> EVENT_SOURCE getEventSource() {
        return (EVENT_SOURCE) __params.get(IEvent.EVENT_SOURCE);
    }

    public EventContext<T, E> setEventSource(Object eventSource) {
        __params.put(IEvent.EVENT_SOURCE, eventSource);
        return this;
    }

    public EventContext<T, E> addParamExtend(String paramName, Object paramObject) {
        __params.put(paramName, paramObject);
        return this;
    }

    public Object getParamExtend(String paramName) {
        return __params.get(paramName);
    }

    public long getTimestamp() {
        return __timestamp;
    }
}
