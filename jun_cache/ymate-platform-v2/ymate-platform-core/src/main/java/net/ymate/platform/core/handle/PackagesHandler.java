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

/**
 * @author 刘镇 (suninformation@163.com) on 2017/8/3 下午6:45
 * @version 1.0
 */
public class PackagesHandler implements IBeanHandler {

    private YMP __owner;

    public PackagesHandler(YMP owner) {
        __owner = owner;
    }

    public Object handle(Class<?> targetClass) throws Exception {
        if (targetClass.isInterface() && targetClass.getSimpleName().equalsIgnoreCase("package-info")) {
            __owner.getConfig().getInterceptSettings().registerInterceptPackage(targetClass);
        }
        return null;
    }
}
