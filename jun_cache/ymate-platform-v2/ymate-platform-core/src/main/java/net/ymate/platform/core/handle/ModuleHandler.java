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
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.util.ClassUtils;

/**
 * 模块对象处理器
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/12 上午11:59
 * @version 1.0
 */
public class ModuleHandler implements IBeanHandler {

    private YMP __owner;

    public ModuleHandler(YMP owner) {
        __owner = owner;
        __owner.registerExcludedClass(IModule.class);
    }

    public Object handle(Class<?> targetClass) throws Exception {
        // 首先判断当前预加载的模块是否存在于被排除列表中，若存在则忽略它
        if (!__owner.getConfig().getExcludedModules().contains(targetClass.getName())) {
            if (ClassUtils.isInterfaceOf(targetClass, IModule.class)) {
                IModule _module = (IModule) targetClass.newInstance();
                if (!__owner.getConfig().getExcludedModules().contains(_module.getName())) {
                    __owner.registerModule(_module);
                }
            }
        }
        return null;
    }
}
