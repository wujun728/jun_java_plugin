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
 * 事件注册器接口，框架自动扫描后并执行，提供向事件管理器注册自定义事件监听的扩展支持
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/20 上午12:05
 * @version 1.0
 */
public interface IEventRegister {

    /**
     * 执行自定义事件注册
     *
     * @param events 事件管理器
     * @throws Exception 事件注册过程可能产生的异常
     */
    void register(Events events) throws Exception;
}
