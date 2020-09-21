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
package net.ymate.platform.core.beans.impl.proxy;

import net.sf.cglib.proxy.MethodProxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import net.ymate.platform.core.beans.proxy.IProxyFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15-3-3 下午4:55
 * @version 1.0
 */
public class DefaultProxyChain implements IProxyChain {

    private final IProxyFactory __owner;
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<IProxy> proxies;
    private int __index = 0;

    public DefaultProxyChain(IProxyFactory owner,
                             Class<?> targetClass,
                             Object targetObject,
                             Method targetMethod,
                             MethodProxy methodProxy,
                             Object[] methodParams,
                             List<IProxy> proxies) {
        this.__owner = owner;
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxies = proxies;
    }

    public IProxyFactory getProxyFactory() {
        return __owner;
    }

    public Object[] getMethodParams() {
        return this.methodParams;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public Method getTargetMethod() {
        return this.targetMethod;
    }

    public Object doProxyChain() throws Throwable {
        Object _result;
        if (__index < proxies.size()) {
            _result = proxies.get(__index++).doProxy(this);
        } else {
            _result = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return _result;
    }
}
