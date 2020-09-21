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
package net.ymate.platform.core.module;

import net.ymate.platform.core.event.EventContext;
import net.ymate.platform.core.event.IEvent;

/**
 * 模块事件对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/17 下午8:51
 * @version 1.0
 */
public class ModuleEvent extends EventContext<IModule, ModuleEvent.EVENT> implements IEvent {

    /**
     * 模块事件枚举：<br>
     * MODULE_INITED - 模块初始化事件<br>
     * MODULE_DESTROYED - 模块销毁事件
     */
    public enum EVENT {
        MODULE_INITED, MODULE_DESTROYED
    }

    public ModuleEvent(IModule owner, EVENT eventName) {
        super(owner, ModuleEvent.class, eventName);
    }
}
