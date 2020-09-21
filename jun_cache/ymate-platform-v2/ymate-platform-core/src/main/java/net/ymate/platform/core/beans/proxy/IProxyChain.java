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
package net.ymate.platform.core.beans.proxy;

import java.lang.reflect.Method;

/**
 * 代理链接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-3 下午4:05
 * @version 1.0
 */
public interface IProxyChain {

    /**
     * @return 获取所属代理工厂对象
     */
    IProxyFactory getProxyFactory();

    /**
     * @return 获取方法参数集合
     */
    Object[] getMethodParams();

    /**
     * @return 获取被代理目标类型
     */
    Class<?> getTargetClass();

    /**
     * @return 获取代理目标对象
     */
    Object getTargetObject();

    /**
     * @return 获取被代理目标方法对象
     */
    Method getTargetMethod();

    /**
     * @return 执行代理链
     * @throws Throwable 执行过程中可能产生的异常
     */
    Object doProxyChain() throws Throwable;
}
