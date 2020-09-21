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
package net.ymate.platform.core;

import net.ymate.platform.core.beans.BeanMeta;
import net.ymate.platform.core.beans.IBeanFactory;
import net.ymate.platform.core.beans.IBeanHandler;
import net.ymate.platform.core.beans.IBeanInjector;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.beans.annotation.Injector;
import net.ymate.platform.core.beans.annotation.Packages;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.impl.DefaultBeanFactory;
import net.ymate.platform.core.beans.impl.DefaultBeanLoader;
import net.ymate.platform.core.beans.impl.proxy.DefaultProxyFactory;
import net.ymate.platform.core.beans.intercept.InterceptProxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyFactory;
import net.ymate.platform.core.event.Events;
import net.ymate.platform.core.event.annotation.EventRegister;
import net.ymate.platform.core.event.impl.DefaultEventConfig;
import net.ymate.platform.core.handle.*;
import net.ymate.platform.core.i18n.I18N;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.ModuleEvent;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.support.ConfigBuilder;
import net.ymate.platform.core.support.IInitializable;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * YMP框架核心管理器
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-23 下午5:52:44
 * @version 1.0
 */
public class YMP {

    public static final Version VERSION = new Version(2, 0, 3, Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(YMP.class);

    private static final String __YMP_BASE_PACKAGE = "net.ymate.platform";

    private static final YMP __instance = new YMP(ConfigBuilder.system().build());

    private IConfig __config;

    private boolean __inited;

    private boolean __errorFlag;

    private IBeanFactory __moduleFactory;

    private IBeanFactory __beanFactory;

    private IProxyFactory __proxyFactory;

    private Map<Class<? extends IModule>, IModule> __modules;

    private Events __events;

    /**
     * @return 返回默认YMP框架核心管理器对象实例，若未实例化或已销毁则重新创建对象实例
     */
    public static YMP get() {
        return __instance;
    }

    /**
     * 构造方法
     *
     * @param config YMP框架初始化配置
     */
    public YMP(IConfig config) {
        __config = config;
    }

    private void __registerScanPackages(IBeanFactory factory) {
        factory.registerPackage(__YMP_BASE_PACKAGE);
        for (String _packageName : __config.getAutoscanPackages()) {
            factory.registerPackage(_packageName);
        }
    }

    /**
     * 初始化YMP框架
     *
     * @return 返回当前YMP核心框架管理器对象
     * @throws Exception 框架初始化失败时将抛出异常
     */
    public YMP init() throws Exception {
        if (!__inited) {
            //
            _LOG.info("\n__   ____  __ ____          ____  \n" +
                    "\\ \\ / /  \\/  |  _ \\  __   _|___ \\ \n" +
                    " \\ V /| |\\/| | |_) | \\ \\ / / __) |\n" +
                    "  | | | |  | |  __/   \\ V / / __/ \n" +
                    "  |_| |_|  |_|_|       \\_/ |_____|  Website: http://www.ymate.net/");
            //
            StopWatch _watch = new StopWatch();
            _watch.start();
            //
            _LOG.info("Initializing ymate-platform-core-" + VERSION + " - debug:" + __config.isDevelopMode());

            // 初始化I18N
            I18N.initialize(__config.getDefaultLocale(), __config.getI18NEventHandlerClass());
            // 初始化事件管理器，并注册框架、模块事件
            __events = Events.create(new DefaultEventConfig(__config.getEventConfigs()));
            __events.registerEvent(ApplicationEvent.class);
            __events.registerEvent(ModuleEvent.class);
            // 创建根对象工厂
            __beanFactory = new DefaultBeanFactory();
            __beanFactory.setLoader(new DefaultBeanLoader(__config.getExcudedFiles()));
            __beanFactory.registerExcludedClass(IInitializable.class);
            __beanFactory.registerHandler(Bean.class);
            __beanFactory.registerHandler(Packages.class, new PackagesHandler(this));
            // 创建模块对象引用集合
            __modules = new HashMap<Class<? extends IModule>, IModule>();
            // 创建模块对象工厂
            __moduleFactory = new BeanFactory(this);
            __moduleFactory.setLoader(new DefaultBeanLoader(__config.getExcudedFiles()));
            __moduleFactory.registerExcludedClass(IInitializable.class);
            __moduleFactory.registerHandler(Module.class, new ModuleHandler(this));
            __moduleFactory.registerHandler(Proxy.class, new ProxyHandler(this));
            __moduleFactory.registerHandler(EventRegister.class, new EventRegisterHandler(this));
            __moduleFactory.registerHandler(Injector.class, new InjectorHandler(__beanFactory));
            // 设置自动扫描应用包路径
            __registerScanPackages(__moduleFactory);
            __registerScanPackages(__beanFactory);
            // 创建代理工厂并初始化
            __proxyFactory = new DefaultProxyFactory(this).registerProxy(new InterceptProxy());
            // 初始化根对象工厂
            __moduleFactory.init();
            //
            for (IModule _module : __modules.values()) {
                if (!_module.isInited()) {
                    try {
                        _module.init(this);
                        // 触发模块初始化完成事件
                        __events.fireEvent(new ModuleEvent(_module, ModuleEvent.EVENT.MODULE_INITED));
                    } catch (Exception e) {
                        _LOG.error("Module '" + _module.getName() + "' initialization error: ", RuntimeUtils.unwrapThrow(e));
                        //
                        __errorFlag = true;
                        __inited = true;
                        break;
                    }
                }
            }
            if (!__errorFlag) {
                // 初始化对象工厂
                __beanFactory.init();
                // 初始化对象代理
                __beanFactory.initProxy(__proxyFactory);
                // IoC依赖注入
                __beanFactory.initIoC();
                //
                __inited = true;
                //
                _watch.stop();
                _LOG.info("Initialization completed, Total time: " + _watch.getTime() + "ms");
                // 触发框架初始化完成事件
                __events.fireEvent(new ApplicationEvent(this, ApplicationEvent.EVENT.APPLICATION_INITED));
            }
        }
        return this;
    }

    /**
     * 销毁YMP框架
     *
     * @throws Exception 框架销毁失败时将抛出异常
     */
    public void destroy() throws Exception {
        if (__inited) {
            if (!__errorFlag) {
                // 触发框架销毁事件
                __events.fireEvent(new ApplicationEvent(this, ApplicationEvent.EVENT.APPLICATION_DESTROYED));
            }
            //
            __inited = false;
            // 销毁所有已加载模块
            for (IModule _module : __modules.values()) {
                if (_module.isInited()) {
                    // 触发模块销毁事件
                    __events.fireEvent(new ModuleEvent(_module, ModuleEvent.EVENT.MODULE_DESTROYED));
                    //
                    _module.destroy();
                }
            }
            __modules = null;
            // 销毁代理工厂
            __proxyFactory = null;
            // 销毁根对象工厂
            __moduleFactory.destroy();
            __moduleFactory = null;
            //
            __beanFactory.destroy();
            __beanFactory = null;
            // 销毁事件管理器
            __events.destroy();
        }
    }

    /**
     * @return 返回当前配置对象
     */
    public IConfig getConfig() {
        return __config;
    }

    /**
     * @return 返回YMP框架是否已初始化
     */
    public boolean isInited() {
        return __inited;
    }

    /**
     * 注册自定义注解类处理器，重复注册将覆盖前者
     *
     * @param annoClass 自定义注解类型
     * @param handler   注解对象处理器
     */
    public void registerHandler(Class<? extends Annotation> annoClass, IBeanHandler handler) {
        if (annoClass.equals(Module.class) || annoClass.equals(Proxy.class) || annoClass.equals(EventRegister.class) || annoClass.equals(Injector.class)) {
            _LOG.warn("Handler [" + annoClass.getSimpleName() + "] duplicate registration is not allowed");
            return;
        }
        __beanFactory.registerHandler(annoClass, handler);
    }

    public void registerHandler(Class<? extends Annotation> annoClass) {
        registerHandler(annoClass, IBeanHandler.DEFAULT_HANDLER);
    }

    public void registerInjector(Class<? extends Annotation> annoClass, IBeanInjector injector) {
        __beanFactory.registerInjector(annoClass, injector);
    }

    /**
     * 注册排除的接口类
     *
     * @param excludedClass 预排除的接口类型
     */
    public void registerExcludedClass(Class<?> excludedClass) {
        __beanFactory.registerExcludedClass(excludedClass);
    }

    /**
     * 注册类
     *
     * @param clazz 目标类
     */
    public void registerBean(Class<?> clazz) {
        __beanFactory.registerBean(clazz);
    }

    public void registerBean(Class<?> clazz, Object object) {
        __beanFactory.registerBean(clazz, object);
    }

    public void registerBean(BeanMeta beanMeta) {
        __beanFactory.registerBean(beanMeta);
    }

    /**
     * @param <T>   返回类型
     * @param clazz 接口类型
     * @return 提取类型为clazz的对象实例
     */
    public <T> T getBean(Class<T> clazz) {
        return __beanFactory.getBean(clazz);
    }

    /**
     * 向工厂注册代理类对象
     *
     * @param proxy 目标代理类
     */
    public void registerProxy(IProxy proxy) {
        __proxyFactory.registerProxy(proxy);
    }

    public void registerProxy(Collection<? extends IProxy> proxies) {
        __proxyFactory.registerProxy(proxies);
    }

    /**
     * 注册模块实例(此方法仅在YMP框架核心管理器未初始化前有效)
     *
     * @param module 目标模块
     */
    public void registerModule(IModule module) {
        if (!__inited) {
            if (module != null) {
                __moduleFactory.registerBean(module.getClass(), module);
                __modules.put(module.getClass(), module);
            }
        }
    }

    /**
     * @param moduleClass 模块类型
     * @param <T>         模块类型
     * @return 获取模块类实例对象
     */
    public <T extends IModule> T getModule(Class<T> moduleClass) {
        return __moduleFactory.getBean(moduleClass);
    }

    /**
     * @return 获取事件管理器
     */
    public Events getEvents() {
        return __events;
    }

    /**
     * @param targetFactory 目标对象工厂
     * @param <T>           对象工厂类型
     * @return 将目标对象工厂的Parent设置为当前YMP容器的对象工厂
     */
    public <T extends IBeanFactory> T bindBeanFactory(T targetFactory) {
        targetFactory.setParent(__beanFactory);
        return targetFactory;
    }

    /**
     * YMP框架根对象工厂类
     */
    private static class BeanFactory extends DefaultBeanFactory {
        private final YMP __owner;

        public BeanFactory(YMP owner) {
            this.__owner = owner;
        }

        @Override
        public <T> T getBean(Class<T> clazz) {
            T _bean = super.getBean(clazz);
            // 重写此方法是为了在获取模块对象时始终保证其已被初始化
            if (_bean instanceof IModule) {
                IModule _module = (IModule) _bean;
                if (!_module.isInited()) {
                    if (__owner.getConfig().getExcludedModules().contains(_module.getName())) {
                        return null;
                    }
                    try {
                        _module.init(__owner);
                        // 触发模块初始化完成事件
                        __owner.getEvents().fireEvent(new ModuleEvent(_module, ModuleEvent.EVENT.MODULE_INITED));
                    } catch (Exception e) {
                        throw new RuntimeException(RuntimeUtils.unwrapThrow(e));
                    }
                }
            }
            return _bean;
        }
    }
}
