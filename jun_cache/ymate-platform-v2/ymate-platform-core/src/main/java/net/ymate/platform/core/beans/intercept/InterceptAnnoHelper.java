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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 16/1/9 上午12:18
 * @version 1.0
 */
public class InterceptAnnoHelper {

    public static List<Class<? extends IInterceptor>> getBeforeIntercepts(Class<?> targetClass, Method targetMethod) {
        List<Class<? extends IInterceptor>> _classes = new ArrayList<Class<? extends IInterceptor>>();
        if (targetClass.isAnnotationPresent(Around.class)) {
            __doParseIntercept(targetMethod, IInterceptor.CleanType.BEFORE, targetClass.getAnnotation(Around.class).value(), _classes);
        }
        if (targetClass.isAnnotationPresent(Before.class)) {
            __doParseIntercept(targetMethod, IInterceptor.CleanType.BEFORE, targetClass.getAnnotation(Before.class).value(), _classes);
        }
        //
        if (targetMethod.isAnnotationPresent(Around.class)) {
            Collections.addAll(_classes, targetMethod.getAnnotation(Around.class).value());
        }
        if (targetMethod.isAnnotationPresent(Before.class)) {
            Collections.addAll(_classes, targetMethod.getAnnotation(Before.class).value());
        }
        //
        return _classes;
    }

    public static List<Class<? extends IInterceptor>> getAfterIntercepts(Class<?> targetClass, Method targetMethod) {
        List<Class<? extends IInterceptor>> _classes = new ArrayList<Class<? extends IInterceptor>>();
        if (targetClass.isAnnotationPresent(Around.class)) {
            __doParseIntercept(targetMethod, IInterceptor.CleanType.AFTER, targetClass.getAnnotation(Around.class).value(), _classes);
        }
        if (targetClass.isAnnotationPresent(After.class)) {
            __doParseIntercept(targetMethod, IInterceptor.CleanType.AFTER, targetClass.getAnnotation(After.class).value(), _classes);
        }
        //
        if (targetMethod.isAnnotationPresent(Around.class)) {
            Collections.addAll(_classes, targetMethod.getAnnotation(Around.class).value());
        }
        if (targetMethod.isAnnotationPresent(After.class)) {
            Collections.addAll(_classes, targetMethod.getAnnotation(After.class).value());
        }
        //
        return _classes;
    }

    public static Clean getCleanIntercepts(Method targetMethod) {
        if (targetMethod.isAnnotationPresent(Clean.class)) {
            return targetMethod.getAnnotation(Clean.class);
        }
        return null;
    }

    private static void __doParseIntercept(Method targetMethod, IInterceptor.CleanType cleanType, Class<? extends IInterceptor>[] interceptors, List<Class<? extends IInterceptor>> results) {
        Clean _clean = getCleanIntercepts(targetMethod);
        if (_clean != null &&
                (_clean.type().equals(IInterceptor.CleanType.ALL) || _clean.type().equals(cleanType))) {
            if (_clean.value().length > 0) {
                for (Class<? extends IInterceptor> _clazz : interceptors) {
                    if (ArrayUtils.contains(_clean.value(), _clazz)) {
                        continue;
                    }
                    results.add(_clazz);
                }
            }
        } else {
            Collections.addAll(results, interceptors);
        }
    }

    public static void parseContextParamValue(YMP owner, ContextParam contextParam, Map<String, String> paramsMap) {
        if (contextParam != null) {
            for (ParamItem _item : contextParam.value()) {
                String _key = _item.key();
                String _value = _item.value();
                boolean _flag = _value.length() > 1 && _value.charAt(0) == '$';
                if (StringUtils.isBlank(_key)) {
                    if (_flag) {
                        _key = _value.substring(1);
                        _value = StringUtils.trimToEmpty(owner.getConfig().getParam(_key));
                    } else {
                        _key = _value;
                    }
                } else if (_flag) {
                    _value = StringUtils.trimToEmpty(owner.getConfig().getParam(_value.substring(1)));
                }
                paramsMap.put(_key, _value);
            }
        }
    }

    public static Map<String, String> getContextParams(YMP owner, Class<?> targetClass, Method targetMethod) {
        Map<String, String> _contextParams = new HashMap<String, String>();
        //
        if (targetClass != null) {
            parseContextParamValue(owner, targetClass.getAnnotation(ContextParam.class), _contextParams);
        }
        if (targetMethod != null) {
            parseContextParamValue(owner, targetMethod.getAnnotation(ContextParam.class), _contextParams);
        }
        //
        return _contextParams;
    }
}
