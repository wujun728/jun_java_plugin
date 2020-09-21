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
package net.ymate.platform.core.beans.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.ymate.platform.core.beans.annotation.PropertyState;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 16/7/3 上午2:05
 * @version 1.0
 */
public class PropertyStateSupport<T> {

    private T __source;
    private T __bound;
    private final Class<?> __targetClass;

    private List<PropertyStateMeta> __stateMetas;
    private Map<String, PropertyStateMeta> __propertyStates;

    public static <T> PropertyStateSupport<T> create(T source) throws Exception {
        return new PropertyStateSupport<T>(source);
    }

    public PropertyStateSupport(T source) throws Exception {
        __source = source;
        __targetClass = source.getClass();
        __stateMetas = new ArrayList<PropertyStateMeta>();
        __propertyStates = new HashMap<String, PropertyStateMeta>();
        //
        ClassUtils.BeanWrapper<T> _wrapper = ClassUtils.wrapper(source);
        for (String _fieldName : _wrapper.getFieldNames()) {
            PropertyState _state = _wrapper.getField(_fieldName).getAnnotation(PropertyState.class);
            if (_state != null) {
                PropertyStateMeta _stateMeta = new PropertyStateMeta(StringUtils.defaultIfBlank(_state.propertyName(), _fieldName), _state.aliasName(), _wrapper.getValue(_fieldName));
                __stateMetas.add(_stateMeta);
                __propertyStates.put("set" + StringUtils.capitalize(_fieldName), _stateMeta);
                if (StringUtils.isNotBlank(_state.setterName())) {
                    __propertyStates.put(_state.setterName(), _stateMeta);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T bind() {
        if (__bound == null) {
            __bound = (T) ClassUtils.wrapper(__source).duplicate(Enhancer.create(__targetClass, new MethodInterceptor() {
                public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                    Object _result = methodProxy.invokeSuper(targetObject, methodParams);
                    PropertyStateMeta _meta = __propertyStates.get(targetMethod.getName());
                    if (_meta != null) {
                        _meta.setNewValue(methodParams != null ? methodParams[0] : null);
                    }
                    return _result;
                }
            }));
        }
        return __bound;
    }

    public T unbind() {
        return ClassUtils.wrapper(__bound).duplicate(__source);
    }

    public T duplicate(Object source) {
        return duplicate(source, false);
    }

    public T duplicate(Object source, boolean ignoreNull) {
        ClassUtils.BeanWrapper<Object> _wrapperSource = ClassUtils.wrapper(source);
        for (String _fieldName : _wrapperSource.getFieldNames()) {
            Field _field = _wrapperSource.getField(_fieldName);
            try {
                Object _obj = _field.get(source);
                if (ignoreNull && _obj == null) {
                    continue;
                }
                Method _method = __bound.getClass().getMethod("set" + StringUtils.capitalize(_fieldName), _field.getType());
                _method.invoke(__bound, _field.get(source));
            } catch (Exception e) {
                // Nothing...
            }
        }
        return __bound;
    }

    public String[] getChangedPropertyNames() {
        Set<String> _states = new HashSet<String>();
        for (PropertyStateMeta _meta : __stateMetas) {
            if (_meta.isChanged()) {
                _states.add(_meta.getPropertyName());
            }
        }
        return _states.toArray(new String[_states.size()]);
    }

    public Set<PropertyStateMeta> getChangedProperties() {
        Set<PropertyStateMeta> _states = new HashSet<PropertyStateMeta>();
        for (PropertyStateMeta _meta : __stateMetas) {
            if (_meta.isChanged()) {
                _states.add(_meta);
            }
        }
        return _states;
    }

    public static class PropertyStateMeta {
        private String propertyName;
        private String aliasName;
        private Object originalValue;
        private Object newValue;

        private boolean changed;

        public PropertyStateMeta(String propertyName, String aliasName, Object originalValue) {
            this.propertyName = propertyName;
            this.aliasName = aliasName;
            this.originalValue = originalValue;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public String getAliasName() {
            return aliasName;
        }

        public Object getOriginalValue() {
            return originalValue;
        }

        public Object getNewValue() {
            return newValue;
        }

        public void setNewValue(Object newValue) {
            this.newValue = newValue;
            if (originalValue != null) {
                changed = !originalValue.equals(newValue);
            } else {
                changed = newValue != null;
            }
        }

        public boolean isChanged() {
            return changed;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        }
    }
}
