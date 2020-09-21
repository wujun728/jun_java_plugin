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
package net.ymate.platform.core.beans;

import net.ymate.platform.core.beans.proxy.IProxyFactory;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 对象工厂接口
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-5 下午1:18
 * @version 1.0
 */
public interface IBeanFactory {

    /**
     * 注册自定义注解类处理器
     *
     * @param annoClass 注解类型
     * @param handler   注解处理器
     */
    void registerHandler(Class<? extends Annotation> annoClass, IBeanHandler handler);

    void registerHandler(Class<? extends Annotation> annoClass);

    void registerInjector(Class<? extends Annotation> annoClass, IBeanInjector injector);

    /**
     * 注册扫描包路径(仅在工厂对象执行初始化前有效)
     *
     * @param packageName 包名称
     */
    void registerPackage(String packageName);

    /**
     * 注册排除的接口类
     *
     * @param excludedClass 预排除接口类型
     */
    void registerExcludedClass(Class<?> excludedClass);

    /**
     * @param clazz 目标类型
     * @param <T>   类型
     * @return 提取类型为clazz的对象实例，可能返回null
     */
    <T> T getBean(Class<T> clazz);

    /**
     * @return 返回当前工厂管理的所有类对象映射
     */
    Map<Class<?>, BeanMeta> getBeans();

    /**
     * 注册一个类到工厂
     *
     * @param clazz 预注册类型
     */
    void registerBean(Class<?> clazz);

    void registerBean(Class<?> clazz, Object object);

    void registerBean(BeanMeta beanMeta);

    /**
     * 初始化对象工厂
     *
     * @throws Exception 工厂初始化时可能产生的异常
     */
    void init() throws Exception;

    /**
     * 销毁对象工厂
     *
     * @throws Exception 工厂销毁时可能产生的异常
     */
    void destroy() throws Exception;

    /**
     * @return 返回Parent对象工厂
     */
    IBeanFactory getParent();

    /**
     * 设置Parent对象工厂
     *
     * @param parent 父类工厂对象
     */
    void setParent(IBeanFactory parent);

    /**
     * @return 返回当前工厂使用的对象加载器
     */
    IBeanLoader getLoader();

    /**
     * 设置自定义对象加载器
     *
     * @param loader 自定义对象加载器
     */
    void setLoader(IBeanLoader loader);

    /**
     * 初始化代理工厂
     *
     * @param proxyFactory 目标代码工厂对象
     * @throws Exception 初始化时可能产生的异常
     */
    void initProxy(IProxyFactory proxyFactory) throws Exception;

    /**
     * 初始化依赖注入
     *
     * @throws Exception 初始化IoC时可能产生的异常
     */
    void initIoC() throws Exception;
}
