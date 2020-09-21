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

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.annotation.*;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拦截器代理，支持@Before、@After和@Around方法注解
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/19 下午12:01
 * @version 1.0
 */
@Proxy(order = @Order(-999))
public class InterceptProxy implements IProxy {

    private static Map<String, InterceptMeta> __interceptMetasCache;

    private static final Object __cacheLocker = new Object();

    private static final Set<String> __excludedMethodNames;

    static {
        __interceptMetasCache = new ConcurrentHashMap<String, InterceptMeta>();
        //
        __excludedMethodNames = new HashSet<String>();
        for (Method _method : Object.class.getDeclaredMethods()) {
            __excludedMethodNames.add(_method.getName());
        }
    }

    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        // 方法声明了@Ignored注解或非PUBLIC方法和Object类方法将被排除
        boolean _ignored = proxyChain.getTargetMethod().isAnnotationPresent(Ignored.class);
        if (_ignored || __excludedMethodNames.contains(proxyChain.getTargetMethod().getName())
                || proxyChain.getTargetMethod().getDeclaringClass().equals(Object.class)
                || proxyChain.getTargetMethod().getModifiers() != Modifier.PUBLIC) {
            return proxyChain.doProxyChain();
        }
        //
        InterceptMeta _interceptMeta = __doGetInterceptMeta(proxyChain.getProxyFactory().getOwner(), proxyChain.getTargetClass(), proxyChain.getTargetMethod());
        //
        if (_interceptMeta.hasBeforeIntercepts()) {
            InterceptContext _context = new InterceptContext(IInterceptor.Direction.BEFORE,
                    proxyChain.getProxyFactory().getOwner(),
                    proxyChain.getTargetObject(),
                    proxyChain.getTargetMethod(),
                    proxyChain.getMethodParams(), _interceptMeta.getContextParams());
            //
            for (Class<? extends IInterceptor> _interceptClass : _interceptMeta.getBeforeIntercepts()) {
                IInterceptor _interceptor = _interceptClass.newInstance();
                // 执行前置拦截器，若其结果对象不为空则返回并停止执行
                Object _resultObj = _interceptor.intercept(_context);
                if (_resultObj != null) {
                    return _resultObj;
                }
            }
        }
        //
        Object _returnValue = proxyChain.doProxyChain();
        //
        if (_interceptMeta.hasAfterIntercepts()) {
            InterceptContext _context = new InterceptContext(IInterceptor.Direction.AFTER,
                    proxyChain.getProxyFactory().getOwner(),
                    proxyChain.getTargetObject(),
                    proxyChain.getTargetMethod(),
                    proxyChain.getMethodParams(), _interceptMeta.getContextParams());
            // 初始化拦截器上下文对象，并将当前方法的执行结果对象赋予后置拦截器使用
            _context.setResultObject(_returnValue);
            //
            for (Class<? extends IInterceptor> _interceptClass : _interceptMeta.getAfterIntercepts()) {
                IInterceptor _interceptor = _interceptClass.newInstance();
                // 执行后置拦截器，所有后置拦截器的执行结果都将被忽略
                _interceptor.intercept(_context);
            }
        }
        return _returnValue;
    }

    private InterceptMeta __doGetInterceptMeta(YMP owner, Class<?> targetClass, Method targetMethod) {
        String _id = DigestUtils.md5Hex(targetClass.toString() + targetMethod.toString());
        //
        if (__interceptMetasCache.containsKey(_id)) {
            return __interceptMetasCache.get(_id);
        }
        if (targetClass.isAnnotationPresent(Before.class) || targetClass.isAnnotationPresent(After.class) || targetClass.isAnnotationPresent(Around.class)
                || targetMethod.isAnnotationPresent(Before.class) || targetMethod.isAnnotationPresent(After.class) || targetMethod.isAnnotationPresent(Around.class)
                || owner.getConfig().getInterceptSettings().hasInterceptPackages(targetClass)) {
            synchronized (__cacheLocker) {
                InterceptMeta _meta = __interceptMetasCache.get(_id);
                if (_meta == null) {
                    _meta = new InterceptMeta(owner, _id, targetClass, targetMethod);
                    __interceptMetasCache.put(_id, _meta);
                }
                return _meta;
            }
        }
        return InterceptMeta.__DEFAULT;
    }

    static class InterceptMeta {

        static InterceptMeta __DEFAULT = new InterceptMeta("default");

        private String id;
        //
        private List<Class<? extends IInterceptor>> beforeIntercepts;
        private List<Class<? extends IInterceptor>> afterIntercepts;
        //
        private Map<String, String> contextParams;

        InterceptMeta(String id) {
            this.id = id;
            this.beforeIntercepts = Collections.emptyList();
            this.afterIntercepts = Collections.emptyList();
            this.contextParams = Collections.emptyMap();
        }

        InterceptMeta(YMP owner, String id, Class<?> targetClass, Method targetMethod) {
            this.id = id;
            this.contextParams = new HashMap<String, String>();
            //
            this.beforeIntercepts = InterceptAnnoHelper.getBeforeIntercepts(targetClass, targetMethod);
            this.afterIntercepts = InterceptAnnoHelper.getAfterIntercepts(targetClass, targetMethod);
            //
            InterceptSettings _interceptSettings = owner.getConfig().getInterceptSettings();
            //
            for (InterceptSettings.InterceptPackageMeta _item : _interceptSettings.getInterceptPackages(targetClass)) {
                if (!_item.getBeforeIntercepts().isEmpty()) {
                    this.beforeIntercepts.addAll(0, _item.getBeforeIntercepts());
                }
                if (!_item.getAfterIntercepts().isEmpty()) {
                    this.afterIntercepts.addAll(0, _item.getAfterIntercepts());
                }
                //
                for (ContextParam _ctxParam : _item.getContextParams()) {
                    InterceptAnnoHelper.parseContextParamValue(owner, _ctxParam, this.contextParams);
                }
            }
            //
            this.contextParams.putAll(InterceptAnnoHelper.getContextParams(owner, targetClass, targetMethod));
            //
            if (owner.getConfig().isInterceptSettingsEnabled()) {
                this.beforeIntercepts = _interceptSettings.doBeforeSet(this.beforeIntercepts, targetClass, targetMethod);
                this.afterIntercepts = _interceptSettings.doAfterSet(this.afterIntercepts, targetClass, targetMethod);
            }
        }

        String getId() {
            return id;
        }

        List<Class<? extends IInterceptor>> getBeforeIntercepts() {
            return beforeIntercepts;
        }

        List<Class<? extends IInterceptor>> getAfterIntercepts() {
            return afterIntercepts;
        }

        Map<String, String> getContextParams() {
            return contextParams;
        }

        boolean hasBeforeIntercepts() {
            return !beforeIntercepts.isEmpty();
        }

        boolean hasAfterIntercepts() {
            return !afterIntercepts.isEmpty();
        }
    }
}
