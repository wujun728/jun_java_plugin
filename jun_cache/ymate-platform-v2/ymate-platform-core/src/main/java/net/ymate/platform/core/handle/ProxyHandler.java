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
import net.ymate.platform.core.beans.intercept.InterceptProxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.util.ClassUtils;

/**
 * 代理对象处理器
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/12 下午5:10
 * @version 1.0
 */
public class ProxyHandler implements IBeanHandler {

    private YMP __owner;

    public ProxyHandler(YMP owner) {
        __owner = owner;
        __owner.registerExcludedClass(IProxy.class);
    }

    public Object handle(Class<?> targetClass) throws Exception {
        if (ClassUtils.isInterfaceOf(targetClass, IProxy.class)) {
            if (!targetClass.equals(InterceptProxy.class)) { // 排除框架内部拦截器代理类，因为YMP框架已经注册了它
                __owner.registerProxy((IProxy) targetClass.newInstance());
            }
        }
        return null;
    }
}
