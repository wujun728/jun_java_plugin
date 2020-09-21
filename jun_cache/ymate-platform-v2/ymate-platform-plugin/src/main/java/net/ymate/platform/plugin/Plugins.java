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
package net.ymate.platform.plugin;

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.event.Events;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.plugin.annotation.PluginFactory;
import net.ymate.platform.plugin.impl.DefaultPluginConfig;
import net.ymate.platform.plugin.impl.DefaultPluginEventListener;
import net.ymate.platform.plugin.impl.DefaultPluginFactory;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 插件框架模块管理器及插件工厂相关工具方法
 *
 * @author 刘镇 (suninformation@163.com) on 2012-11-30 下午6:28:20
 * @version 1.0
 */
@Module
public class Plugins implements IModule, IPlugins {

    public static final Version VERSION = new Version(2, 0, 3, Plugins.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(Plugins.class);

    private YMP __owner;

    private boolean __inited;

    private static volatile IPlugins __instance;

    private IPluginFactory __pluginFactory;

    /**
     * @return 返回默认插件框架管理器实例对象
     */
    public static IPlugins get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Plugins.class);
                }
            }
        }
        return __instance;
    }

    public String getName() {
        return IPlugins.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(MODULE_NAME);
            __owner = owner;
            __owner.getEvents().registerEvent(PluginEvent.class);
            //
            _LOG.info("Initializing ymate-platform-plugin-" + VERSION);
            //
            DefaultPluginConfig _config = new DefaultPluginConfig();
            _config.setPluginHome(new File(RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(_moduleCfgs.get("plugin_home"), "${root}/plugins"))));
            _config.setAutoscanPackages(new ArrayList<String>(Arrays.asList(StringUtils.split(StringUtils.trimToEmpty(_moduleCfgs.get("autoscan_packages")), "|"))));
            //
            for (String _package : __owner.getConfig().getAutoscanPackages()) {
                if (!_config.getAutoscanPackages().contains(_package)) {
                    _config.getAutoscanPackages().add(_package);
                }
            }
            //
            _config.setAutomatic(BlurObject.bind(StringUtils.defaultIfBlank(_moduleCfgs.get("automatic"), "true")).toBooleanValue());
            _config.setIncludedClassPath(BlurObject.bind(StringUtils.defaultIfBlank(_moduleCfgs.get("included_classpath"), "true")).toBooleanValue());
            //
            _config.setPluginEventListener(new IPluginEventListener() {
                public void onInited(IPluginContext context, IPlugin plugin) {
                    __owner.getEvents().fireEvent(new PluginEvent(plugin, PluginEvent.EVENT.PLUGIN_INITED));
                }

                public void onStarted(IPluginContext context, IPlugin plugin) {
                    __owner.getEvents().fireEvent(new PluginEvent(plugin, PluginEvent.EVENT.PLUGIN_STARTED));
                }

                public void onShutdown(IPluginContext context, IPlugin plugin) {
                    __owner.getEvents().fireEvent(new PluginEvent(plugin, PluginEvent.EVENT.PLUGIN_SHUTDOWN));
                }

                public void onDestroy(IPluginContext context, IPlugin plugin) {
                    __owner.getEvents().fireEvent(new PluginEvent(plugin, PluginEvent.EVENT.PLUGIN_DESTROYED));
                }
            });
            //
            __pluginFactory = new DefaultPluginFactory(__owner);
            __pluginFactory.init(_config);
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public YMP getOwner() {
        return __owner;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            __pluginFactory.destroy();
            __pluginFactory = null;
            //
            __owner = null;
        }
    }

    public IPluginFactory getPluginFactory() {
        return __pluginFactory;
    }

    //---------------------=================------------------------

    /**
     * @param pluginHome   插件根路径
     * @param autoPackages 自动扫描包路径
     * @return 创建默认插件工厂初始化配置
     * @throws Exception 加载配置可能产生的异常
     */
    private static DefaultPluginConfig loadConfig(String pluginHome, String[] autoPackages) throws Exception {
        DefaultPluginConfig _config = new DefaultPluginConfig();
        _config.setPluginHome(new File(pluginHome));
        _config.setAutoscanPackages(Arrays.asList(autoPackages));
        return _config;
    }

    /**
     * @param clazz 插件工厂类
     * @return 通过注解分析插件工厂初始化配置
     * @throws Exception 加载配置可能产生的异常
     */
    private static DefaultPluginConfig loadConfig(Class<? extends IPluginFactory> clazz) throws Exception {
        DefaultPluginConfig _config = null;
        if (clazz != null && clazz.isAnnotationPresent(PluginFactory.class)) {
            _config = new DefaultPluginConfig();
            //
            PluginFactory _factoryAnno = clazz.getAnnotation(PluginFactory.class);
            if (StringUtils.isNotBlank(_factoryAnno.pluginHome())) {
                _config.setPluginHome(new File(_factoryAnno.pluginHome()));
            }
            String[] _packages = _factoryAnno.autoscanPackages();
            if (ArrayUtils.isEmpty(_packages)) {
                _packages = new String[]{clazz.getPackage().getName()};
            }
            _config.setAutoscanPackages(Arrays.asList(_packages));
            _config.setIncludedClassPath(_factoryAnno.includedClassPath());
            _config.setAutomatic(_factoryAnno.automatic());
            //
            IPluginEventListener _listener = ClassUtils.impl(_factoryAnno.listenerClass(), IPluginEventListener.class);
            if (_listener != null) {
                _config.setPluginEventListener(_listener);
            } else {
                _config.setPluginEventListener(new DefaultPluginEventListener());
            }
        }
        return _config;
    }

    /**
     * @param pluginHome   插件根路径
     * @param autoPackages 自动扫描包路径
     * @return 创建并返回默认插件工厂实例
     * @throws Exception 创建插件工厂时可能产生的异常
     */
    public static IPluginFactory createFactory(String pluginHome, String[] autoPackages) throws Exception {
        IPluginFactory _factory = new DefaultPluginFactory();
        _factory.init(loadConfig(pluginHome, autoPackages));
        return _factory;
    }

    /**
     * @param clazz 指定的插件工厂类型
     * @return 创建并返回由clazz指定类型的插件工厂实例
     * @throws Exception 创建插件工厂时可能产生的异常
     */
    public static IPluginFactory createFactory(Class<? extends IPluginFactory> clazz) throws Exception {
        IPluginFactory _factory = ClassUtils.impl(clazz, IPluginFactory.class);
        if (_factory != null) {
            if (clazz.isAnnotationPresent(PluginFactory.class)) {
                _factory.init(loadConfig(clazz));
            }
        }
        return _factory;
    }

    /**
     * @param clazz  指定的插件工厂类型
     * @param config 指定的插件工厂初始化配置
     * @return 采用指定的初始化配置创建并返回由clazz指定类型的插件工厂实例
     * @throws Exception 创建插件工厂时可能产生的异常
     */
    public static IPluginFactory createFactory(Class<? extends IPluginFactory> clazz, IPluginConfig config) throws Exception {
        IPluginFactory _factory = ClassUtils.impl(clazz, IPluginFactory.class);
        if (_factory != null) {
            if (config != null) {
                _factory.init(config);
            } else if (clazz.isAnnotationPresent(PluginFactory.class)) {
                _factory.init(loadConfig(clazz));
            }
        }
        return _factory;
    }
}
