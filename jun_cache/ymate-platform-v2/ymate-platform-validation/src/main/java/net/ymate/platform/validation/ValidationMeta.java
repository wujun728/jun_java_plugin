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

import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.validation.annotation.VField;
import net.ymate.platform.validation.annotation.VModel;
import net.ymate.platform.validation.annotation.Validation;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 目标类型验证配置描述
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/23 上午1:55
 * @version 1.0
 */
public class ValidationMeta {

    private IValidation __validation;

    private Validation.MODE __mode;

    private String __resourcesName;

    private Class<?> __targetClass;

    private Map<String, Annotation[]> __fields;

    private Map<String, String> __labels;

    private Map<Method, Map<String, String>> __methodLabels;

    private Map<Method, Validation> __methods;

    private Map<Method, Map<String, Annotation[]>> __methodParams;

    public ValidationMeta(IValidation validation, Class<?> targetClass) {
        __validation = validation;
        // 处理targetClass声明的@Validation注解，提取默认验证模式
        Validation _classValidation = targetClass.getAnnotation(Validation.class);
        if (_classValidation != null) {
            __mode = _classValidation.mode();
            __resourcesName = _classValidation.resourcesName();
        } else {
            __mode = Validation.MODE.NORMAL;
        }
        __targetClass = targetClass;
        __fields = new LinkedHashMap<String, Annotation[]>();
        __labels = new LinkedHashMap<String, String>();
        __methodLabels = new LinkedHashMap<Method, Map<String, String>>();
        __methods = new LinkedHashMap<Method, Validation>();
        __methodParams = new LinkedHashMap<Method, Map<String, Annotation[]>>();

        // 处理targetClass所有Field成员属性
        __fields.putAll(__doGetMetaFromFields(null, targetClass, __labels));

        // 处理targetClass所有Method方法
        for (Method _method : targetClass.getDeclaredMethods()) {
            Map<String, String> _paramLabels = new LinkedHashMap<String, String>();
            __methodLabels.put(_method, _paramLabels);
            // 处理每个方法上有@Validation的注解
            Validation _methodValidation = _method.getAnnotation(Validation.class);
            if (_methodValidation != null) {
                __methods.put(_method, _methodValidation);
            }
            // 处理每个方法参数上有关验证的注解
            Map<String, Annotation[]> _paramAnnos = new LinkedHashMap<String, Annotation[]>();
            String[] _paramNames = ClassUtils.getMethodParamNames(_method);
            if (_paramNames != null && _paramNames.length > 0) {
                Annotation[][] _params = _method.getParameterAnnotations();
                for (int _idx = 0; _idx < _paramNames.length; _idx++) {
                    List<Annotation> _tmpAnnoList = new ArrayList<Annotation>();
                    String _paramName = _paramNames[_idx];
                    // 尝试获取自定义的参数别名
                    for (Annotation _vField : _params[_idx]) {
                        if (_vField instanceof VField) {
                            VField _vF = (VField) _vField;
                            if (StringUtils.isNotBlank(_vF.name())) {
                                _paramName = ((VField) _vField).name();
                            }
                            if (StringUtils.isNotBlank(_vF.label())) {
                                _paramLabels.put(_paramName, _vF.label());
                            }
                            break;
                        }
                    }
                    for (Annotation _annotation : _params[_idx]) {
                        if (__doIsValid(_annotation)) {
                            _tmpAnnoList.add(_annotation);
                        } else if (_annotation instanceof VModel) {
                            // 递归处理@VModel
                            _paramAnnos.putAll(__doGetMetaFromFields(_paramName, _method.getParameterTypes()[_idx], _paramLabels));
                        }
                    }
                    if (!_tmpAnnoList.isEmpty()) {
                        _paramAnnos.put(_paramName, _tmpAnnoList.toArray(new Annotation[_tmpAnnoList.size()]));
                    }
                }
                if (!_paramAnnos.isEmpty()) {
                    __methodParams.put(_method, _paramAnnos);
                }
            }
        }
    }

    /**
     * @param parentFieldName 父类属性名称(用于递归)
     * @param targetClass     目标类
     * @param paramLabels     自定义参数标签名称映射
     * @return 处理targetClass所有Field成员属性
     */
    public Map<String, Annotation[]> __doGetMetaFromFields(String parentFieldName, Class<?> targetClass, Map<String, String> paramLabels) {
        Map<String, Annotation[]> _returnValues = new LinkedHashMap<String, Annotation[]>();
        //
        parentFieldName = StringUtils.trimToEmpty(parentFieldName);
        ClassUtils.BeanWrapper<?> _beanWrapper = ClassUtils.wrapper(targetClass);
        if (_beanWrapper != null) {
            for (String _fieldName : _beanWrapper.getFieldNames()) {
                // 尝试获取自定义的Field别名
                VField _vField = null;
                Field _field = _beanWrapper.getField(_fieldName);
                if (_field.isAnnotationPresent(VField.class)) {
                    _vField = _field.getAnnotation(VField.class);
                    if (StringUtils.isNotBlank(_vField.name())) {
                        _fieldName = _vField.name();
                    }
                    if (StringUtils.isNotBlank(_vField.label())) {
                        __labels.put(_fieldName, _vField.label());
                        paramLabels.put(__doGetFieldName(parentFieldName, _fieldName), _vField.label());
                    }
                }
                List<Annotation> _annotations = new ArrayList<Annotation>();
                for (Annotation _annotation : _beanWrapper.getFieldAnnotations(_field.getName())) {
                    if (__doIsValid(_annotation)) {
                        _annotations.add(_annotation);
                    } else if (_annotation instanceof VModel) {
                        // 拼装带层级关系的Field名称
                        String _fieldNamePR = __doGetFieldName(parentFieldName, _fieldName);
                        if (_vField != null && StringUtils.isNotBlank(_vField.label())) {
                            paramLabels.put(_fieldNamePR, _vField.label());
                        }
                        // 递归处理@VModel
                        _returnValues.putAll(__doGetMetaFromFields(_fieldNamePR, _field.getType(), paramLabels));
                    }
                }
                if (!_annotations.isEmpty()) {
                    // 拼装带层级关系的Field名称
                    String _fieldNamePR = __doGetFieldName(parentFieldName, _fieldName);
                    _returnValues.put(_fieldNamePR, _annotations.toArray(new Annotation[_annotations.size()]));
                }
            }
        }
        return _returnValues;
    }

    /**
     * @param parentFieldName 父类属性名称
     * @param fieldName       属性名称
     * @return 返回带层级关系的Field名称
     */
    private String __doGetFieldName(String parentFieldName, String fieldName) {
        if (StringUtils.isNotBlank(parentFieldName)) {
            return parentFieldName.concat(".").concat(fieldName);
        }
        return fieldName;
    }

    private boolean __doIsValid(Annotation _annotation) {
        // 判断是否包含验证器中声明的注解
        return (__validation.containsValidator(_annotation.annotationType()));
    }

    public Validation.MODE getMode() {
        return __mode;
    }

    public String getResourcesName() {
        return __resourcesName;
    }

    public Class<?> getTargetClass() {
        return __targetClass;
    }

    public Set<String> getFieldNames() {
        return Collections.unmodifiableSet(__fields.keySet());
    }

    public String getFieldLabel(String fieldName) {
        return __labels.get(fieldName);
    }

    public String getFieldLabel(Method method, String fieldName) {
        return __methodLabels.get(method).get(fieldName);
    }

    public Annotation[] getFieldAnnotations(String fieldName) {
        return __fields.get(fieldName);
    }

    public Validation getMethodValidation(Method method) {
        return __methods.get(method);
    }

    public Map<String, Annotation[]> getMethodParamAnnotations(Method method) {
        if (__methodParams.containsKey(method)) {
            return Collections.unmodifiableMap(__methodParams.get(method));
        }
        return Collections.emptyMap();
    }
}
