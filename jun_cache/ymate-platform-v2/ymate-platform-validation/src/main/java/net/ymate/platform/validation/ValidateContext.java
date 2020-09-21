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
package net.ymate.platform.validation;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 验证器上下文环境
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-13 上午11:28:22
 * @version 1.0
 */
public class ValidateContext {

    private YMP __owner;

    private String __resourceName;

    private Annotation annotation;

    private String paramName;

    private String paramLabel;

    private Map<String, Object> paramValues;

    private Map<String, String> contextParams;

    public ValidateContext(YMP owner, Annotation annotation, String paramName, String paramLabel, Map<String, Object> paramValues, Map<String, String> contextParams, String resourceName) {
        this.__owner = owner;
        //
        this.annotation = annotation;
        this.paramName = paramName;
        this.paramLabel = paramLabel;
        this.paramValues = paramValues;
        //
        this.contextParams = contextParams;
        //
        this.__resourceName = resourceName;
    }

    public YMP getOwner() {
        return __owner;
    }

    public String getResourceName() {
        return __resourceName;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamLabel() {
        return paramLabel;
    }

    public Object getParamValue() {
        return getParamValue(this.paramName);
    }

    public Object getParamValue(String paramName) {
        Object _targetValue = this.paramValues.get(paramName);
        if (_targetValue == null) {
            // 修正对JavaBean对象验证时无法正确获取属性参数值的问题:
            // 先以'.'拆分参数名称并按层级关系尝试获取参数值
            String[] _pNames = StringUtils.split(paramName, '.');
            if (_pNames.length > 1) {
                try {
                    for (String _pName : _pNames) {
                        if (_targetValue == null) {
                            _targetValue = this.paramValues.get(_pName);
                        } else {
                            _targetValue = ClassUtils.wrapper(_targetValue).getValue(_pName);
                        }
                    }
                } catch (Exception e) {
                    // 出现任何异常都将返回null
                    _targetValue = null;
                } finally {
                    // 上述过程无论取值是否为空都将被缓存, 防止多次执行
                    this.paramValues.put(this.paramName, _targetValue);
                }
            }
        }
        return _targetValue;
    }

    public Map<String, Object> getParamValues() {
        return paramValues;
    }

    /**
     * @return 返回上下文参数映射
     */
    public Map<String, String> getContextParams() {
        return contextParams;
    }
}
