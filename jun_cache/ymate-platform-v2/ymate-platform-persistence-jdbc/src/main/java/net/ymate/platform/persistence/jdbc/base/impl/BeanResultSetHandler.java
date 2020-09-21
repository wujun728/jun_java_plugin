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
package net.ymate.platform.persistence.jdbc.base.impl;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.jdbc.base.AbstractResultSetHandler;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * 将数据直接映射到类成员属性的结果集处理接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 17/1/7 下午10:20
 * @version 1.0
 */
public class BeanResultSetHandler<T> extends AbstractResultSetHandler<T> {

    private Class<T> __beanClass;

    @SuppressWarnings("unchecked")
    public BeanResultSetHandler() {
        __beanClass = (Class<T>) ClassUtils.getParameterizedTypes(getClass()).get(0);
    }

    public BeanResultSetHandler(Class<T> beanClass) {
        __beanClass = beanClass;
    }

    protected T __doProcessResultRow(ResultSet resultSet) throws Exception {
        ClassUtils.BeanWrapper<T> _targetWrapper = ClassUtils.wrapper(__beanClass.newInstance());
        for (int _idx = 0; _idx < __doGetColumnCount(); _idx++) {
            Field _field = _targetWrapper.getField(StringUtils.uncapitalize(EntityMeta.propertyNameToFieldName(_doGetColumnMeta(_idx).getName())));
            if (_field != null) {
                _field.set(_targetWrapper.getTargetObject(), BlurObject.bind(resultSet.getObject(_idx + 1)).toObjectValue(_field.getType()));
            }
        }
        return _targetWrapper.getTargetObject();
    }
}
