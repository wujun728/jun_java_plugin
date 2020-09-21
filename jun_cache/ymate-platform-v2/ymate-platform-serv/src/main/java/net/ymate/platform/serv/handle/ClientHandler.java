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
package net.ymate.platform.serv.handle;

import net.ymate.platform.core.beans.IBeanHandler;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.serv.IListener;
import net.ymate.platform.serv.IServ;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/6 下午8:37
 * @version 1.0
 */
public class ClientHandler implements IBeanHandler {

    private IServ __owner;

    public ClientHandler(IServ owner) throws Exception {
        __owner = owner;
    }

    @SuppressWarnings("unchecked")
    public Object handle(Class<?> targetClass) throws Exception {
        if (ClassUtils.isInterfaceOf(targetClass, IListener.class)) {
            __owner.registerClient((Class<? extends IListener>) targetClass);
        }
        return null;
    }
}
