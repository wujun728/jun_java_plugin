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

import net.ymate.platform.core.beans.IBeanFactory;
import net.ymate.platform.core.beans.IBeanHandler;
import net.ymate.platform.core.beans.IBeanInjector;
import net.ymate.platform.core.beans.annotation.Injector;
import net.ymate.platform.core.util.ClassUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/9/19 下午1:41
 * @version 1.0
 */
public class InjectorHandler implements IBeanHandler {

    private IBeanFactory __beanFactory;

    public InjectorHandler(IBeanFactory beanFactory) {
        __beanFactory = beanFactory;
        __beanFactory.registerExcludedClass(IBeanInjector.class);
    }

    @Override
    public Object handle(Class<?> targetClass) throws Exception {
        if (ClassUtils.isInterfaceOf(targetClass, IBeanInjector.class)) {
            __beanFactory.registerInjector(targetClass.getAnnotation(Injector.class).value(), (IBeanInjector) targetClass.newInstance());
        }
        return null;
    }
}
