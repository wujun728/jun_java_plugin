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
package net.ymate.platform.plugin.impl;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.*;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.impl.DefaultBeanFactory;
import net.ymate.platform.core.beans.impl.DefaultBeanLoader;
import net.ymate.platform.core.handle.ProxyHandler;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.plugin.*;
import net.ymate.platform.plugin.annotation.Handler;
import net.ymate.platform.plugin.annotation.Plugin;
import net.ymate.platform.plugin.handle.BeanHandler;
import net.ymate.platform.plugin.handle.PluginHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认插件工厂接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-2 下午3:22:17
 * @version 1.0
 */
public class DefaultPluginFactory implements IPluginFactory {

    private static final Log _LOG = LogFactory.getLog(DefaultPluginFactory.class);

    private IBeanFactory __innerBeanFactory;

    private IBeanFactory __outerBeanFactory;

    private IPluginConfig __config;

    private IPluginEventListener __event;

    private PluginClassLoader __pluginClassLoader;

    /**
     * PluginID -&gt; PluginMeta
     */
    private Map<String, PluginMeta> __pluginMetaWithId;

    /**
     * PluginClass -&gt; PluginMeta
     */
    private Map<Class<? extends IPlugin>, PluginMeta> __pluginMetaWithClass;

    private YMP __owner;

    private boolean __inited;

    public DefaultPluginFactory() {
        this(null);
    }

    public DefaultPluginFactory(YMP owner) {
        __owner = owner;
        //
        __pluginMetaWithId = new ConcurrentHashMap<String, PluginMeta>();
        __pluginMetaWithClass = new ConcurrentHashMap<Class<? extends IPlugin>, PluginMeta>();
        //
        __innerBeanFactory = __doBuildBeanFactory(this, true);
        __outerBeanFactory = __doBuildBeanFactory(this, false);
        __innerBeanFactory.registerHandler(Plugin.class, new PluginHandler(this));
        __outerBeanFactory.registerHandler(Plugin.class, new PluginHandler(this));
    }

    @SuppressWarnings("unchecked")
    private List<Class<? extends IBeanHandler>> __doLoadBeanHandles() throws Exception {
        List<Class<? extends IBeanHandler>> _returnValues = new ArrayList<Class<? extends IBeanHandler>>();
        IBeanLoader _loader = new DefaultBeanLoader();
        //
        IBeanFilter _beanFilter = new IBeanFilter() {
            public boolean filter(Class<?> targetClass) {
                return !(targetClass.isInterface() || targetClass.isAnnotation() || targetClass.isEnum()) && (targetClass.isAnnotationPresent(Handler.class) && ClassUtils.isInterfaceOf(targetClass, IBeanHandler.class));
            }
        };
        //
        for (String _package : __config.getAutoscanPackages()) {
            for (Class<?> _targetClass : _loader.load(_package, _beanFilter)) {
                _returnValues.add((Class<? extends IBeanHandler>) _targetClass);
            }
        }
        return _returnValues;
    }

    public void init(IPluginConfig pluginConfig) throws Exception {
        if (!__inited) {
            this.__config = pluginConfig;
            //
            if (__owner != null) {
                __owner.bindBeanFactory(__innerBeanFactory);
                //
                __outerBeanFactory.registerHandler(Bean.class, new BeanHandler(this));
                __outerBeanFactory.registerHandler(Proxy.class, new ProxyHandler(__owner));
                //
                for (Class<? extends IBeanHandler> _targetClass : __doLoadBeanHandles()) {
                    __outerBeanFactory.registerHandler(_targetClass.getAnnotation(Handler.class).value(), _targetClass.getConstructor(YMP.class).newInstance(__owner));
                }
            }
            //
            __event = __config.getPluginEventListener();
            if (__event == null) {
                __event = new DefaultPluginEventListener();
            }
            //
            __pluginClassLoader = __buildPluginClassLoader();
            __outerBeanFactory.setLoader(new DefaultBeanLoader(__owner != null ? __owner.getConfig().getExcudedFiles() : null) {
                @Override
                public ClassLoader getClassLoader() {
                    return __pluginClassLoader;
                }
            });
            __outerBeanFactory.setParent(__innerBeanFactory);
            //
            for (String _package : __config.getAutoscanPackages()) {
                __innerBeanFactory.registerPackage(_package);
                __outerBeanFactory.registerPackage(_package);
            }
            if (__config.isIncludedClassPath()) {
                __innerBeanFactory.init();
            }
            __outerBeanFactory.init();
            __outerBeanFactory.initIoC();
            __inited = true;
            //
            for (Map.Entry<Class<? extends IPlugin>, PluginMeta> _meta : __pluginMetaWithClass.entrySet()) {
                IPlugin _plugin = __outerBeanFactory.getBean(_meta.getKey());
                _plugin.init(new DefaultPluginContext(this, _meta.getValue()));
                __event.onInited(_plugin.getPluginContext(), _plugin);
                //
                if (__config.isAutomatic() && _meta.getValue().isAutomatic()) {
                    _plugin.startup();
                    __event.onStarted(_plugin.getPluginContext(), _plugin);
                }
            }
        }
    }

    private IBeanFactory __doBuildBeanFactory(final IPluginFactory factory, final boolean inner) {
        return new DefaultBeanFactory() {
            @Override
            protected void __addClass(BeanMeta beanMeta) {
                PluginMeta _meta = (PluginMeta) beanMeta.getBeanObject();
                //
                if (inner && !factory.getPluginConfig().isIncludedClassPath()) {
                    if (StringUtils.isNotBlank(_meta.getPath())) {
                        return;
                    }
                }
                //
                IPluginContext _context = new DefaultPluginContext(factory, _meta);
                IPlugin _plugin = ClassUtils.impl(beanMeta.getBeanClass(), IPlugin.class);
                if (_plugin != null) {
                    // 尝试通过IPluginExtend接口方式获取扩展对象
                    if (_meta.getExtendObject() == null && ClassUtils.isInterfaceOf(beanMeta.getBeanClass(), IPluginExtend.class)) {
                        IPluginExtend _extend = (IPluginExtend) _plugin;
                        _meta.setExtendObject(_extend.getExtendObject(_context));
                    }
                    //
                    boolean _emptyPath = StringUtils.isBlank(_meta.getPath());
                    if ((inner && _emptyPath) || (!inner && !_emptyPath)) {
                        super.__addClass(BeanMeta.create(_plugin, beanMeta.getBeanClass()));
                        //
                        __pluginMetaWithId.put(_meta.getId(), _meta);
                        __pluginMetaWithClass.put(_meta.getInitClass(), _meta);
                    }
                }
            }
        };
    }

    private synchronized PluginClassLoader __buildPluginClassLoader() throws Exception {
        if (__pluginClassLoader == null) {
            if (__config.getPluginHome() != null && __config.getPluginHome().exists() && __config.getPluginHome().isDirectory()) {
                List<URL> _libs = new ArrayList<URL>();
                // 扫描并分析插件通用类路径
                File _commonFile = new File(__config.getPluginHome(), ".plugin");
                if (_commonFile.exists() && _commonFile.isDirectory()) {
                    try {
                        // 设置通用JAR包路径
                        File _tempFile = new File(_commonFile, "lib");
                        if (_tempFile.exists() && _tempFile.isDirectory()) {
                            File[] _libFiles = _tempFile.listFiles();
                            for (File _libFile : _libFiles != null ? _libFiles : new File[0]) {
                                if (_libFile.getPath().endsWith("jar")) {
                                    _libs.add(_libFile.toURI().toURL());
                                }
                            }
                        }
                        // 设置通用类文件路径
                        _tempFile = new File(_commonFile, "classes");
                        if (_tempFile.exists() && _tempFile.isDirectory()) {
                            _libs.add(_tempFile.toURI().toURL());
                        }
                    } catch (MalformedURLException e) {
                        _LOG.warn("", e);
                    }
                }
                // 扫描所有正式插件目录(即目录名称不以'.'开头的)
                File[] _pluginDirs = __config.getPluginHome().listFiles();
                if (_pluginDirs != null) for (File _pluginDir : _pluginDirs) {
                    if (_pluginDir.isDirectory() && _pluginDir.getName().charAt(0) != '.') {
                        // 设置JAR包路径
                        File _pluginLibDir = new File(_pluginDir, "lib");
                        if (_pluginLibDir.exists() && _pluginLibDir.isDirectory()) {
                            File[] _libFiles = _pluginLibDir.listFiles();
                            if (_libFiles != null) for (File _libFile : _libFiles) {
                                if (_libFile.isFile() && _libFile.getAbsolutePath().endsWith("jar")) {
                                    _libs.add(_libFile.toURI().toURL());
                                }
                            }
                        }
                        // 设置类文件路径
                        _pluginLibDir = new File(_pluginDir, "classes");
                        if (_pluginLibDir.exists() && _pluginLibDir.isDirectory()) {
                            _libs.add(_pluginLibDir.toURI().toURL());
                        }
                    }
                }
                //
                __pluginClassLoader = new PluginClassLoader(__config.getPluginHome().getPath(), _libs.toArray(new URL[_libs.size()]), this.getClass().getClassLoader());
            } else {
                throw new IllegalArgumentException("The pluginHome parameter is invalid");
            }
        }
        return __pluginClassLoader;
    }

    public boolean isInited() {
        return __inited;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            for (Map.Entry<Class<? extends IPlugin>, PluginMeta> _meta : __pluginMetaWithClass.entrySet()) {
                IPlugin _plugin = __outerBeanFactory.getBean(_meta.getKey());
                if (_plugin != null) {
                    _plugin.shutdown();
                    __event.onShutdown(_plugin.getPluginContext(), _plugin);
                    //
                    __event.onDestroy(_plugin.getPluginContext(), _plugin);
                    _plugin.destroy();
                }
            }
            //
            __config = null;
            __pluginClassLoader = null;
            //
            __pluginMetaWithId = null;
            __pluginMetaWithClass = null;
            //
            __outerBeanFactory.destroy();
            __outerBeanFactory = null;
            //
            __innerBeanFactory.destroy();
            __innerBeanFactory = null;
        }
    }

    public void addExcludedInterfaceClass(Class<?> interfaceClass) {
        __innerBeanFactory.registerExcludedClass(interfaceClass);
        __outerBeanFactory.registerExcludedClass(interfaceClass);
    }

    public YMP getOwner() {
        return __owner;
    }

    public IPluginConfig getPluginConfig() {
        return __config;
    }

    private void __pluginStatusChecker(IPlugin plugin) {
        if (plugin.isInited() && !plugin.isStarted()) {
            try {
                plugin.startup();
                __event.onStarted(plugin.getPluginContext(), plugin);
            } catch (Exception e) {
                throw new RuntimeException(RuntimeUtils.unwrapThrow(e));
            }
        }
    }

    public IPlugin getPlugin(String id) {
        IPlugin _plugin = null;
        if (__pluginMetaWithId.containsKey(id)) {
            _plugin = __outerBeanFactory.getBean(__pluginMetaWithId.get(id).getInitClass());
            __pluginStatusChecker(_plugin);
        }
        return _plugin;
    }

    public <T> T getPlugin(Class<T> clazz) {
        T _target = __outerBeanFactory.getBean(clazz);
        __pluginStatusChecker((IPlugin) _target);
        return _target;
    }

    public PluginMeta getPluginMeta(String id) {
        return __pluginMetaWithId.get(id);
    }

    public PluginMeta getPluginMeta(Class<? extends IPlugin> clazz) {
        return __pluginMetaWithClass.get(clazz);
    }
}
