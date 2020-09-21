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
package net.ymate.platform.core.beans.intercept;

import net.ymate.platform.core.beans.annotation.After;
import net.ymate.platform.core.beans.annotation.Around;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.beans.annotation.ContextParam;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 拦截器全局规则设置
 *
 * @author 刘镇 (suninformation@163.com) on 17/4/9 上午12:18
 * @version 1.0
 */
public class InterceptSettings {

    private static final Log _LOG = LogFactory.getLog(InterceptSettings.class);

    private Map<String, InterceptPackageMeta> __packages;

    private List<Class<? extends IInterceptor>> __globals;

    private Map<String, InterceptSettingMeta> __settings;

    public InterceptSettings() {
        __packages = new HashMap<String, InterceptPackageMeta>();
        __globals = new ArrayList<Class<? extends IInterceptor>>();
        __settings = new HashMap<String, InterceptSettingMeta>();
    }

    public void registerInterceptPackage(Class<?> targetClass) {
        if (targetClass != null) {
            Package _package = targetClass.getPackage();
            if (_package != null) {
                Around _around = _package.getAnnotation(Around.class);
                Before _before = _package.getAnnotation(Before.class);
                After _after = _package.getAnnotation(After.class);
                if (_around != null || _before != null || _after != null) {
                    InterceptPackageMeta _meta = __packages.get(_package.getName());
                    if (_meta == null) {
                        _meta = new InterceptPackageMeta(_package.getName());
                        __packages.put(_package.getName(), _meta);
                    }
                    if (_around != null && _around.value().length > 0) {
                        List<Class<? extends IInterceptor>> _intercepts = Arrays.asList(_around.value());
                        _meta.beforeIntercepts.addAll(_intercepts);
                        _meta.afterIntercepts.addAll(_intercepts);
                    }
                    if (_before != null && _before.value().length > 0) {
                        _meta.beforeIntercepts.addAll(Arrays.asList(_before.value()));
                    }
                    if (_after != null && _after.value().length > 0) {
                        _meta.afterIntercepts.addAll(Arrays.asList(_after.value()));
                    }
                    //
                    ContextParam _ctxParam = _package.getAnnotation(ContextParam.class);
                    if (_ctxParam != null) {
                        _meta.getContextParams().add(_ctxParam);
                    }
                }
            }
        }
    }

    public void registerInterceptPackage(String name, String setting) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(setting)) {
            InterceptPackageMeta _meta = __packages.get(name);
            if (_meta == null) {
                _meta = new InterceptPackageMeta(name);
                __packages.put(name, _meta);
            }
            String[] _settingArr = StringUtils.split(setting, "|");
            if (_settingArr != null) {
                for (String _item : _settingArr) {
                    String[] _itemArr = StringUtils.split(_item, ":");
                    if (_itemArr != null) {
                        if (_itemArr.length == 1) {
                            Class<? extends IInterceptor> _class = __doGetInterceptorClass(_itemArr[0]);
                            if (_class != null) {
                                _meta.beforeIntercepts.add(_class);
                                _meta.afterIntercepts.add(_class);
                            }
                        } else if (StringUtils.equalsIgnoreCase(_itemArr[0], "before")) {
                            Class<? extends IInterceptor> _class = __doGetInterceptorClass(_itemArr[1]);
                            if (_class != null) {
                                _meta.beforeIntercepts.add(_class);
                            }
                        } else if (StringUtils.equalsIgnoreCase(_itemArr[0], "after")) {
                            Class<? extends IInterceptor> _class = __doGetInterceptorClass(_itemArr[1]);
                            if (_class != null) {
                                _meta.afterIntercepts.add(_class);
                            }
                        }
                    }
                }
            }
        }
    }

    public void registerInterceptGlobal(String className) {
        Class<? extends IInterceptor> _interceptor = __doGetInterceptorClass(className);
        if (_interceptor != null) {
            __globals.add(_interceptor);
        }
    }

    public void registerInterceptSetting(String name, String setting) {
        String[] _nameArr = StringUtils.split(name, "#");
        if (_nameArr != null) {
            InterceptSettingMeta _meta = new InterceptSettingMeta();
            _meta.className = _nameArr[0];
            if (_nameArr.length > 1) {
                _meta.methodName = _nameArr[1];
            }
            //
            String[] _settingArr = StringUtils.split(setting, "|");
            if (_settingArr != null) {
                for (String _item : _settingArr) {
                    String[] _itemArr = StringUtils.split(_item, ":");
                    if (_itemArr != null) {
                        if (_itemArr.length == 1) {
                            if (StringUtils.equals(_itemArr[0], "*")) {
                                // 表示当前类或方法上的所有拦截器禁用, 则后续规则中只处理增加拦截器逻辑
                                _meta.cleanAll = true;
                                //
                                _meta.beforeCleanIntercepts.clear();
                                _meta.afterCleanIntercepts.clear();
                            } else if (StringUtils.endsWith(_itemArr[0], "+")) {
                                Class<? extends IInterceptor> _class = __doGetInterceptorClass(StringUtils.substringBefore(_itemArr[0], "+"));
                                if (_class != null) {
                                    // 增加新的前置和后置拦截器
                                    _meta.beforeIntercepts.add(_class);
                                    _meta.afterIntercepts.add(_class);
                                }
                            } else if (!_meta.cleanAll && StringUtils.endsWith(_itemArr[0], "-")) {
                                Class<? extends IInterceptor> _class = __doGetInterceptorClass(StringUtils.substringBefore(_itemArr[0], "-"));
                                if (_class != null) {
                                    // 禁止前置和后置拦截器
                                    _meta.beforeCleanIntercepts.add(_class.getName());
                                    _meta.afterCleanIntercepts.add(_class.getName());
                                }
                            } else {
                                Class<? extends IInterceptor> _class = __doGetInterceptorClass(_itemArr[0]);
                                if (_class != null) {
                                    // 增加新的前置和后置拦截器
                                    _meta.beforeIntercepts.add(_class);
                                    _meta.afterIntercepts.add(_class);
                                }
                            }
                        } else if (StringUtils.equalsIgnoreCase(_itemArr[0], "before")) {
                            if (StringUtils.equals(_itemArr[1], "*")) {
                                _meta.beforeCleanAll = true;
                                //
                                _meta.beforeCleanIntercepts.clear();
                            } else if (!_meta.beforeCleanAll) {
                                if (StringUtils.endsWith(_itemArr[1], "+")) {
                                    Class<? extends IInterceptor> _class = __doGetInterceptorClass(StringUtils.substringBefore(_itemArr[1], "+"));
                                    if (_class != null) {
                                        // 增加新的前置拦截器
                                        _meta.beforeIntercepts.add(_class);
                                    }
                                } else if (!_meta.cleanAll && !_meta.beforeCleanAll && StringUtils.endsWith(_itemArr[1], "-")) {
                                    Class<? extends IInterceptor> _class = __doGetInterceptorClass(StringUtils.substringBefore(_itemArr[1], "-"));
                                    if (_class != null) {
                                        // 禁止前置拦截器
                                        _meta.beforeCleanIntercepts.add(_class.getName());
                                    }
                                } else {
                                    Class<? extends IInterceptor> _class = __doGetInterceptorClass(_itemArr[1]);
                                    if (_class != null) {
                                        // 增加新的前置拦截器
                                        _meta.beforeIntercepts.add(_class);
                                    }
                                }
                            }
                        } else if (StringUtils.equalsIgnoreCase(_itemArr[0], "after")) {
                            if (StringUtils.equals(_itemArr[1], "*")) {
                                _meta.afterCleanAll = true;
                                //
                                _meta.afterCleanIntercepts.clear();
                            } else if (!_meta.afterCleanAll) {
                                if (StringUtils.endsWith(_itemArr[1], "+")) {
                                    Class<? extends IInterceptor> _class = __doGetInterceptorClass(StringUtils.substringBefore(_itemArr[1], "+"));
                                    if (_class != null) {
                                        // 增加新的后置拦截器
                                        _meta.afterIntercepts.add(_class);
                                    }
                                } else if (!_meta.cleanAll && !_meta.afterCleanAll && StringUtils.endsWith(_itemArr[1], "-")) {
                                    Class<? extends IInterceptor> _class = __doGetInterceptorClass(StringUtils.substringBefore(_itemArr[1], "-"));
                                    if (_class != null) {
                                        // 禁止后置拦截器
                                        _meta.afterCleanIntercepts.add(_class.getName());
                                    }
                                } else {
                                    Class<? extends IInterceptor> _class = __doGetInterceptorClass(_itemArr[1]);
                                    if (_class != null) {
                                        // 增加新的后置拦截器
                                        _meta.afterIntercepts.add(_class);
                                    }
                                }
                            }
                        }
                    }
                }
                //
                __settings.put(_meta.toString(), _meta);
            }
        }
    }

    public List<InterceptPackageMeta> getInterceptPackages(Class<?> targetClass) {
        List<InterceptPackageMeta> _returnValue = new ArrayList<InterceptPackageMeta>();
        String _packageName = targetClass.getPackage().getName();
        if (__packages.containsKey(_packageName)) {
            _returnValue.add(0, __packages.get(_packageName));
        }
        while (StringUtils.contains(_packageName, '.')) {
            _packageName = StringUtils.substringBeforeLast(_packageName, ".");
            if (__packages.containsKey(_packageName)) {
                _returnValue.add(0, __packages.get(_packageName));
            }
        }
        return _returnValue;
    }

    public boolean hasInterceptPackages(Class<?> targetClass) {
        String _packageName = targetClass.getPackage().getName();
        if (__packages.containsKey(_packageName)) {
            return true;
        }
        while (StringUtils.contains(_packageName, '.')) {
            _packageName = StringUtils.substringBeforeLast(_packageName, ".");
            if (__packages.containsKey(_packageName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasInterceptSettings(Class<?> targetClass, Method targetMethod) {
        String _className = targetClass.getName().concat("#");
        String _methodName = _className.concat(targetMethod.getName());
        return __settings.containsKey(_className) || __settings.containsKey(_methodName);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends IInterceptor> __doGetInterceptorClass(String className) {
        try {
            Class<?> _class = ClassUtils.loadClass(className, this.getClass());
            if (ClassUtils.isInterfaceOf(_class, IInterceptor.class)) {
                return (Class<? extends IInterceptor>) _class;
            }
        } catch (ClassNotFoundException e) {
            _LOG.warn("", RuntimeUtils.unwrapThrow(e));
        }
        return null;
    }

    public List<Class<? extends IInterceptor>> doBeforeSet(List<Class<? extends IInterceptor>> classes, Class<?> targetClass, Method targetMethod) {
        List<Class<? extends IInterceptor>> _results = new ArrayList<Class<? extends IInterceptor>>();
        boolean _flag = false;
        //
        String _className = targetClass.getName().concat("#");
        String _methodName = _className.concat(targetMethod.getName());
        // 先加载类级别的配置
        InterceptSettingMeta _classMeta = __settings.get(_className);
        if (_classMeta != null) {
            _flag = true;
            // 若有新增的前置拦截器先添加到集合中
            if (!_classMeta.getBeforeIntercepts().isEmpty()) {
                for (Class<? extends IInterceptor> _interceptor : _classMeta.getBeforeIntercepts()) {
                    if (!__globals.contains(_interceptor) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
            // 判断并尝试过滤类级别前置拦截器
            if (!_classMeta.isCleanAll() && !_classMeta.isBeforeCleanAll()) {
                for (Class<? extends IInterceptor> _interceptor : classes) {
                    if (!__globals.contains(_interceptor) && _classMeta.beforeCleanIntercepts.contains(_interceptor.getName()) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
        }
        InterceptSettingMeta _methodMeta = __settings.get(_methodName);
        if (_methodMeta != null) {
            _flag = true;
            // 若有新增的前置拦截器先添加到集合中
            if (!_methodMeta.getBeforeIntercepts().isEmpty()) {
                for (Class<? extends IInterceptor> _interceptor : _methodMeta.getBeforeIntercepts()) {
                    if (!__globals.contains(_interceptor) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
            // 判断并尝试过滤方法级别前置拦截器
            if ((_classMeta != null && !_classMeta.isCleanAll()) && !_methodMeta.isBeforeCleanAll()) {
                for (Class<? extends IInterceptor> _interceptor : classes) {
                    if (!__globals.contains(_interceptor) && _methodMeta.beforeCleanIntercepts.contains(_interceptor.getName()) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
        }
        //
        return _flag ? _results : classes;
    }

    public List<Class<? extends IInterceptor>> doAfterSet(List<Class<? extends IInterceptor>> classes, Class<?> targetClass, Method targetMethod) {
        List<Class<? extends IInterceptor>> _results = new ArrayList<Class<? extends IInterceptor>>();
        boolean _flag = false;
        //
        String _className = targetClass.getName().concat("#");
        String _methodName = _className.concat(targetMethod.getName());
        // 先加载类级别的配置
        InterceptSettingMeta _classMeta = __settings.get(_className);
        if (_classMeta != null) {
            _flag = true;
            // 若有新增的后置拦截器先添加到集合中
            if (!_classMeta.getAfterIntercepts().isEmpty()) {
                for (Class<? extends IInterceptor> _interceptor : _classMeta.getAfterIntercepts()) {
                    if (!__globals.contains(_interceptor) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
            // 判断并尝试过滤类级别后置拦截器
            if (!_classMeta.isCleanAll() && !_classMeta.isAfterCleanAll()) {
                for (Class<? extends IInterceptor> _interceptor : classes) {
                    if (!__globals.contains(_interceptor) && _classMeta.afterCleanIntercepts.contains(_interceptor.getName()) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
        }
        InterceptSettingMeta _methodMeta = __settings.get(_methodName);
        if (_methodMeta != null) {
            _flag = true;
            // 若有新增的后置拦截器先添加到集合中
            if (!_methodMeta.getAfterIntercepts().isEmpty()) {
                for (Class<? extends IInterceptor> _interceptor : _methodMeta.getAfterIntercepts()) {
                    if (!_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
            // 判断并尝试过滤方法级别后置拦截器
            if ((_classMeta != null && !_classMeta.isCleanAll()) && !_methodMeta.isAfterCleanAll()) {
                for (Class<? extends IInterceptor> _interceptor : classes) {
                    if (!__globals.contains(_interceptor) && _methodMeta.afterCleanIntercepts.contains(_interceptor.getName()) && !_results.contains(_interceptor)) {
                        _results.add(_interceptor);
                    }
                }
            }
        }
        //
        return _flag ? _results : classes;
    }

    static class InterceptPackageMeta {
        private String packageName;
        private List<Class<? extends IInterceptor>> beforeIntercepts;
        private List<Class<? extends IInterceptor>> afterIntercepts;

        private List<ContextParam> contextParams;

        public InterceptPackageMeta(String packageName) {
            this.packageName = packageName;
            beforeIntercepts = new ArrayList<Class<? extends IInterceptor>>();
            afterIntercepts = new ArrayList<Class<? extends IInterceptor>>();
            //
            contextParams = new ArrayList<ContextParam>();
        }

        public String getPackageName() {
            return packageName;
        }

        public List<Class<? extends IInterceptor>> getBeforeIntercepts() {
            return beforeIntercepts;
        }

        public List<Class<? extends IInterceptor>> getAfterIntercepts() {
            return afterIntercepts;
        }

        public List<ContextParam> getContextParams() {
            return contextParams;
        }
    }

    static class InterceptSettingMeta {
        private String className;
        private String methodName;
        private boolean cleanAll;
        private boolean beforeCleanAll;
        private boolean afterCleanAll;
        //
        private List<String> beforeCleanIntercepts;
        private List<String> afterCleanIntercepts;
        //
        private List<Class<? extends IInterceptor>> beforeIntercepts;
        private List<Class<? extends IInterceptor>> afterIntercepts;

        public InterceptSettingMeta() {
            beforeCleanIntercepts = new ArrayList<String>();
            afterCleanIntercepts = new ArrayList<String>();
            //
            beforeIntercepts = new ArrayList<Class<? extends IInterceptor>>();
            afterIntercepts = new ArrayList<Class<? extends IInterceptor>>();
        }

        public String getClassName() {
            return className;
        }

        public String getMethodName() {
            return methodName;
        }

        public boolean isCleanAll() {
            return cleanAll;
        }

        public boolean isBeforeCleanAll() {
            return beforeCleanAll;
        }

        public boolean isAfterCleanAll() {
            return afterCleanAll;
        }

        public List<String> getBeforeCleanIntercepts() {
            return beforeCleanIntercepts;
        }

        public List<String> getAfterCleanIntercepts() {
            return afterCleanIntercepts;
        }

        public List<Class<? extends IInterceptor>> getBeforeIntercepts() {
            return beforeIntercepts;
        }

        public List<Class<? extends IInterceptor>> getAfterIntercepts() {
            return afterIntercepts;
        }

        @Override
        public String toString() {
            return className.concat("#").concat(StringUtils.trimToEmpty(methodName));
        }
    }
}
