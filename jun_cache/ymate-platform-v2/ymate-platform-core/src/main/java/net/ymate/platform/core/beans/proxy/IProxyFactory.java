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

import net.ymate.platform.core.YMP;

import java.util.Collection;
import java.util.List;

/**
 * 代理工厂接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-3 下午4:38
 * @version 1.0
 */
public interface IProxyFactory {

    /**
     * @return 返回代理工厂所属YMP框架管理器
     */
    YMP getOwner();

    IProxyFactory registerProxy(IProxy proxy);

    IProxyFactory registerProxy(Collection<? extends IProxy> proxies);

    /**
     * @return 获取当前工厂已注册的代理类对象集合
     */
    List<IProxy> getProxies();

    /**
     * @param filter 代理过滤器
     * @return 返回通过filter过滤后的代理对象集合
     */
    List<IProxy> getProxies(IProxyFilter filter);

    <T> T createProxy(Class<?> targetClass);

    <T> T createProxy(Class<?> targetClass, List<IProxy> proxies);
}
