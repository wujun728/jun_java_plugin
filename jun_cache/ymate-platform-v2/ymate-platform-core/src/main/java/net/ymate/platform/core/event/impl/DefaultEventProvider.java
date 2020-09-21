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
package net.ymate.platform.core.event.impl;

import net.ymate.platform.core.event.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 默认事件管理提供者接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/16 上午2:38
 * @version 1.0
 */
public class DefaultEventProvider<T, E extends Enum<E>, EVENT extends Class<IEvent>, CONTEXT extends EventContext<T, E>> implements IEventProvider<T, E, EVENT, CONTEXT> {

    private IEventConfig __eventConfig;

    private ExecutorService __eventExecPool;

    private List<String> __events;

    private Map<String, List<IEventListener<CONTEXT>>> __asyncListeners;

    private Map<String, List<IEventListener<CONTEXT>>> __normalListeners;

    public void init(IEventConfig eventConfig) {
        __eventConfig = eventConfig;
        //
        int _poolSize = eventConfig.getThreadPoolSize();
        if (_poolSize <= 0) {
            _poolSize = Runtime.getRuntime().availableProcessors();
        }
        __eventExecPool = Executors.newFixedThreadPool(_poolSize);
        //
        __events = new ArrayList<String>();
        __asyncListeners = new ConcurrentHashMap<String, List<IEventListener<CONTEXT>>>();
        __normalListeners = new ConcurrentHashMap<String, List<IEventListener<CONTEXT>>>();
    }

    public IEventConfig getEventConfig() {
        return __eventConfig;
    }

    public void destroy() {
        if (__eventExecPool != null) {
            __eventExecPool.shutdown();
            __eventExecPool = null;
        }
        __events = null;
        __asyncListeners = null;
        __normalListeners = null;
    }

    public void registerEvent(EVENT event) {
        __events.add(event.getName());
    }

    private void __doRegisterEventListeners(Map<String, List<IEventListener<CONTEXT>>> listenersMap, EVENT eventClass, IEventListener<CONTEXT> eventListener) {
        if (listenersMap.containsKey(eventClass.getName())) {
            listenersMap.get(eventClass.getName()).add(eventListener);
        } else {
            List<IEventListener<CONTEXT>> _listeners = new ArrayList<IEventListener<CONTEXT>>();
            _listeners.add(eventListener);
            listenersMap.put(eventClass.getName(), _listeners);
        }
    }

    public void registerListener(EVENT eventClass, IEventListener<CONTEXT> eventListener) {
        registerListener(__eventConfig.getDefaultMode(), eventClass, eventListener);
    }

    public void registerListener(Events.MODE mode, EVENT eventClass, IEventListener<CONTEXT> eventListener) {
        switch (mode) {
            case ASYNC:
                __doRegisterEventListeners(__asyncListeners, eventClass, eventListener);
                break;
            default:
                __doRegisterEventListeners(__normalListeners, eventClass, eventListener);
        }
    }

    public void fireEvent(final CONTEXT context) {
        String _eventKey = context.getEventClass().getName();
        if (__events.contains(_eventKey)) {
            // 先执行同步事件
            Collection<IEventListener<CONTEXT>> _listeners = __normalListeners.get(_eventKey);
            if (_listeners != null && !_listeners.isEmpty()) {
                for (IEventListener<CONTEXT> _listener : _listeners) {
                    if (_listener.handle(context)) {
                        // 返回值若为true则表示终止同步事件广播并结束执行
                        break;
                    }
                }
            }
            // 再触发异步事件
            _listeners = __asyncListeners.get(_eventKey);
            if (_listeners != null && !_listeners.isEmpty()) {
                for (final IEventListener<CONTEXT> _listener : _listeners) {
                    if (__eventExecPool != null) {
                        __eventExecPool.execute(new Runnable() {
                            public void run() {
                                _listener.handle(context);
                            }
                        });
                    }
                }
            }
        }
    }
}
