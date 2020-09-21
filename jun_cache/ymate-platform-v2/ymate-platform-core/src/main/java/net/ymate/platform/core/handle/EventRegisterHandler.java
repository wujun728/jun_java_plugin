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
package net.ymate.platform.core.handle;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.IBeanHandler;
import net.ymate.platform.core.event.IEventRegister;
import net.ymate.platform.core.util.ClassUtils;

/**
 * 事件注册器对象处理器
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/20 上午12:11
 * @version 1.0
 */
public class EventRegisterHandler implements IBeanHandler {

    private YMP __owner;

    public EventRegisterHandler(YMP owner) {
        __owner = owner;
        __owner.registerExcludedClass(IEventRegister.class);
    }

    public Object handle(Class<?> targetClass) throws Exception {
        if (ClassUtils.isInterfaceOf(targetClass, IEventRegister.class)) {
            IEventRegister _register = (IEventRegister) targetClass.newInstance();
            _register.register(__owner.getEvents());
        }
        return null;
    }
}
