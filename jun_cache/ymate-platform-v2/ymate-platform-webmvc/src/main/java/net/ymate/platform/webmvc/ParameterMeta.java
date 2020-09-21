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
package net.ymate.platform.webmvc;

import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.impl.DefaultParameterEscapeProcessor;
import net.ymate.platform.webmvc.impl.DefaultRequestProcessor;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author 刘镇 (suninformation@163.com) on 15/10/31 上午11:04
 * @version 1.0
 */
public class ParameterMeta {

    /**
     * 请求参数映射名称
     */
    private String paramName;

    /**
     * 成员属性或方法参数名称
     */
    private String fieldName;

    /**
     * 参数类型
     */
    private Class<?> paramType;

    /**
     * 参数名称前缀
     */
    private String prefix;

    /**
     * 参数绑定的注解
     */
    private Annotation paramAnno;

    /**
     * 参数的Field对象,若参数为非类成员则该值为null
     */
    private Field paramField;

    /**
     * 是否为数组
     */
    private boolean isArray;

    /**
     * 是否为上传文件类型
     */
    private boolean isUploadFile;

    /**
     * 是否为ModelBind模式
     */
    private boolean isModelBind;

    /**
     * 参数转义注解, 若未设置则该值为null
     */
    private ParameterEscape parameterEscape;

    private boolean isParamField;

    public ParameterMeta(RequestMeta parent, Class<?> paramType, String fieldName, Annotation[] fieldAnnos) {
        this.fieldName = fieldName;
        this.paramType = paramType;
        this.isArray = paramType.isArray();
        this.isUploadFile = paramType.equals(IUploadFileWrapper.class);
        //
        for (Annotation _anno : fieldAnnos) {
            if (_anno instanceof ParameterEscape) {
                this.parameterEscape = (ParameterEscape) _anno;
            } else {
                this.isParamField = __doParseAnnotation(_anno);
                if (this.isParamField) {
                    break;
                }
            }
        }
        //
        if (this.parameterEscape == null) {
            this.parameterEscape = parent.getParameterEscape();
        } else if (parent.getParameterEscape() != null && this.parameterEscape.skiped()) {
            this.parameterEscape = null;
        }
    }

    public ParameterMeta(RequestMeta parent, Field paramField) {
        this.fieldName = paramField.getName();
        this.paramType = paramField.getType();
        this.isArray = this.paramType.isArray();
        this.isUploadFile = this.paramType.equals(IUploadFileWrapper.class);
        //
        this.parameterEscape = paramField.getAnnotation(ParameterEscape.class);
        if (this.parameterEscape == null) {
            this.parameterEscape = parent.getParameterEscape();
        }
        if (this.parameterEscape != null && this.parameterEscape.skiped()) {
            this.parameterEscape = null;
        }
        //
        for (Annotation _anno : paramField.getAnnotations()) {
            this.isParamField = __doParseAnnotation(_anno);
            if (this.isParamField) {
                break;
            }
        }
    }

    private boolean __doParseAnnotation(Annotation anno) {
        boolean _flag = false;
        if (anno != null) {
            if (anno instanceof CookieVariable) {
                CookieVariable _anno = (CookieVariable) anno;
                this.paramAnno = _anno;
                this.paramName = doBuildParamName(StringUtils.defaultIfBlank(_anno.prefix(), prefix), _anno.value(), fieldName);
                _flag = true;
            } else if (anno instanceof PathVariable) {
                PathVariable _anno = (PathVariable) anno;
                this.paramAnno = _anno;
                this.paramName = doBuildParamName("", _anno.value(), fieldName);
                _flag = true;
            } else if (anno instanceof RequestHeader) {
                RequestHeader _anno = (RequestHeader) anno;
                this.paramAnno = _anno;
                this.paramName = doBuildParamName(StringUtils.defaultIfBlank(_anno.prefix(), prefix), _anno.value(), fieldName);
                _flag = true;
            } else if (anno instanceof RequestParam) {
                RequestParam _anno = (RequestParam) anno;
                this.paramAnno = _anno;
                this.paramName = doBuildParamName(StringUtils.defaultIfBlank(_anno.prefix(), prefix), _anno.value(), fieldName);
                _flag = true;
            } else if (anno instanceof ModelBind) {
                ModelBind _mBind = (ModelBind) anno;
                this.paramAnno = anno;
                this.prefix = _mBind.prefix();
                this.isModelBind = true;
                _flag = true;
            }
        }
        return _flag;
    }

    /**
     * @param prefix      前缀
     * @param paramName   参数名称
     * @param defaultName 默认名称
     * @return 根据前缀生成有效的参数名称
     */
    public String doBuildParamName(String prefix, String paramName, String defaultName) {
        String _name = StringUtils.defaultIfBlank(paramName, defaultName);
        if (StringUtils.isNotBlank(prefix)) {
            _name = prefix.trim().concat(".").concat(_name);
        }
        return _name;
    }

    public Object doParamEscape(ParameterMeta _meta, Object originalResult) {
        Object _resultValue = originalResult;
        if (_meta.getParameterEscape() != null && _meta.getParamType().equals(String.class)) {
            // 获取参数转义处理器接口实例
            IParameterEscapeProcessor _escapeProc = null;
            if (!_meta.getParameterEscape().processor().equals(DefaultRequestProcessor.class)) {
                _escapeProc = ClassUtils.impl(_meta.getParameterEscape().processor(), IParameterEscapeProcessor.class);
            } else {
                _escapeProc = new DefaultParameterEscapeProcessor();
            }
            // 分别处理数组类型和字符串类型数据
            if (_meta.isArray()) {
                String[] _resultArr = (String[]) originalResult;
                for (int _idx = 0; _idx < _resultArr.length; _idx++) {
                    _resultArr[_idx] = _escapeProc.processEscape(_meta.getParameterEscape().scope(), _resultArr[_idx]);
                }
            } else {
                _resultValue = _escapeProc.processEscape(_meta.getParameterEscape().scope(), (String) originalResult);
            }
        }
        return _resultValue;
    }

    public String getParamName() {
        return paramName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public String getPrefix() {
        return prefix;
    }

    public Annotation getParamAnno() {
        return paramAnno;
    }

    public Field getParamField() {
        return paramField;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isUploadFile() {
        return isUploadFile;
    }

    public boolean isModelBind() {
        return isModelBind;
    }

    public ParameterEscape getParameterEscape() {
        return parameterEscape;
    }

    public boolean isParamField() {
        return this.isParamField;
    }
}
