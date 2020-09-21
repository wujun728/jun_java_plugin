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

import org.apache.commons.lang.NullArgumentException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ioc受控类描述对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/21 上午10:50
 * @version 1.0
 */
public class BeanMeta implements Serializable {

    private Class<?> __beanClass;

    private Object __beanObject;

    private boolean __singleton;

    public static BeanMeta create(Object beanObject, Class<?> beanClass) {
        return new BeanMeta(beanClass, true, beanObject);
    }

    public static BeanMeta create(Class<?> beanClass) {
        return new BeanMeta(beanClass, false, null);
    }

    /**
     * 构造方法
     *
     * @param beanClass  受控Bean类型
     * @param singleton  是否单例模式
     * @param beanObject 若为单例模式则此参数可为空
     */
    public BeanMeta(Class<?> beanClass, boolean singleton, Object beanObject) {
        __beanClass = beanClass;
        __singleton = singleton;
        __beanObject = beanObject;
        if (singleton && beanObject == null) {
            throw new NullArgumentException("beanObject");
        }
    }

    public boolean isSingleton() {
        return __singleton;
    }

    public Class<?> getBeanClass() {
        return __beanClass;
    }

    public Object getBeanObject() {
        return __beanObject;
    }

    public void setBeanObject(Object beanObject) {
        if (!__singleton || beanObject == null) {
            throw new NullArgumentException("Not singleton Or beanObject was null");
        }
        __beanObject = beanObject;
    }

    public List<Class<?>> getBeanInterfaces(List<Class<?>> excludedClassSet) {
        List<Class<?>> _returnValues = new ArrayList<Class<?>>();
        for (Class<?> _interface : __beanClass.getInterfaces()) {
            // 排除自定义接口
            if (excludedClassSet != null && excludedClassSet.contains(_interface)) {
                continue;
            }
            _returnValues.add(_interface);
        }
        return _returnValues;
    }
}
