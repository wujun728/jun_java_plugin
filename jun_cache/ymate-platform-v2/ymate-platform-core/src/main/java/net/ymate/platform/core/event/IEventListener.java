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

/**
 * 事件监听器接口，用于接收并处理一种事件类型
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/16 上午2:20
 * @version 1.0
 */
public interface IEventListener<CONTEXT extends EventContext> {

    /**
     * 处理事件监听，其返回值将影响事件队列是否继续执行(仅支持同步事件，异步事件将忽略此返回值)
     *
     * @param context 事件上下文对象
     * @return 返回true将停止事件同步队列继续执行
     */
    boolean handle(CONTEXT context);
}
