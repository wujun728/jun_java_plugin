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
package net.ymate.platform.webmvc.impl;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.*;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.CookieHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 默认(基于标准WEB)控制器请求处理器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/27 下午5:42
 * @version 1.0
 */
public class DefaultRequestProcessor implements IRequestProcessor {

    private static final Log _LOG = LogFactory.getLog(DefaultRequestProcessor.class);

    public Map<String, Object> processRequestParams(IWebMvc owner, RequestMeta requestMeta) throws Exception {
        Map<String, Object> _returnValues = new LinkedHashMap<String, Object>();
        // 非单例控制器类将不执行类成员的参数处理
        if (!requestMeta.isSingleton()) {
            _returnValues.putAll(__doGetParamValueFromParameterMetas(owner, requestMeta, requestMeta.getClassParameterMetas()));
        }
        // 处理控制器方法参数
        _returnValues.putAll(__doGetParamValueFromParameterMetas(owner, requestMeta, requestMeta.getMethodParameterMetas()));
        //
        return _returnValues;
    }

    protected Map<String, Object> __doGetParamValueFromParameterMetas(IWebMvc owner, RequestMeta requestMeta, Collection<ParameterMeta> metas) throws Exception {
        Map<String, Object> _resultMap = new HashMap<String, Object>();
        Map<String, Object> _resultEscapedMap = new HashMap<String, Object>();
        boolean _escapeBeforeFlag = Type.EscapeOrder.BEFORE.equals(owner.getModuleCfg().getParameterEscapeOrder());
        if (owner.getModuleCfg().isParameterEscapeMode() && !_escapeBeforeFlag) {
            // 将转义预处理的参数暂存至WebContext中
            WebContext.getContext().addAttribute(Type.EscapeOrder.class.getName(), _resultEscapedMap);
        }
        for (ParameterMeta _meta : metas) {
            Object _result = __doGetParamValue(owner, requestMeta, _meta, _meta.getParamName(), _meta.getParamType(), _meta.getParamAnno());
            if (_result != null) {
                if (owner.getModuleCfg().isParameterEscapeMode()) {
                    // 若执行转义顺序为before时, 直接处理
                    if (_escapeBeforeFlag) {
                        _result = _meta.doParamEscape(_meta, _result);
                    } else {
                        // 排除掉@ModelBind参数, 因为内部已特殊处理
                        if (!_meta.isModelBind()) {
                            // 否则, 进行参数转义预处理
                            Object _resultEscaped = _meta.doParamEscape(_meta, _result);
                            //
                            _resultEscapedMap.put(_meta.getFieldName(), _resultEscaped);
                            if (StringUtils.isNotBlank(_meta.getParamName())) {
                                _resultEscapedMap.put(_meta.getParamName(), _resultEscaped);
                            }
                        }
                    }
                }
                //
                _resultMap.put(_meta.getFieldName(), _result);
                if (StringUtils.isNotBlank(_meta.getParamName())) {
                    _resultMap.put(_meta.getParamName(), _result);
                }
            }
        }
        return _resultMap;
    }

    /**
     * 分析请求参数的值
     *
     * @param owner       Owner
     * @param requestMeta 请求元描述对象
     * @param paramMeta   参数元描述对象
     * @param paramName   参数名称
     * @param paramType   参数类型
     * @param _annotation 参数上声明的参数绑定注解
     * @return 返回参数名称与值对象
     * @throws Exception 可能产生的异常
     */
    protected Object __doGetParamValue(IWebMvc owner, RequestMeta requestMeta, ParameterMeta paramMeta, String paramName, Class<?> paramType, Annotation _annotation) throws Exception {
        Object _pValue = null;
        if (_annotation instanceof CookieVariable) {
            CookieVariable _anno = (CookieVariable) _annotation;
            String _v = CookieHelper.bind(owner).getCookie(paramName).toStringValue();
            _pValue = __doSafeGetParamValue(owner, paramName, paramType, _v, StringUtils.trimToNull(_anno.defaultValue()), _anno.fullScope());
        } else if (_annotation instanceof PathVariable) {
            String _v = WebContext.getRequestContext().getAttribute(paramName);
            _pValue = __doSafeGetParamValue(owner, paramName, paramType, _v, null, false);
        } else if (_annotation instanceof RequestHeader) {
            RequestHeader _anno = (RequestHeader) _annotation;
            String _v = WebContext.getRequest().getHeader(paramName);
            _pValue = __doSafeGetParamValue(owner, paramName, paramType, _v, StringUtils.trimToNull(_anno.defaultValue()), _anno.fullScope());
        } else if (_annotation instanceof RequestParam) {
            RequestParam _anno = (RequestParam) _annotation;
            _pValue = this.__doParseRequestParam(owner, paramName, StringUtils.trimToNull(_anno.defaultValue()), paramType, _anno.fullScope());
        } else if (_annotation instanceof ModelBind) {
            _pValue = __doParseModelBind(owner, requestMeta, paramMeta, paramType);
        }
        return _pValue;
    }

    protected Object __doParseRequestParam(IWebMvc owner, String paramName, String defaultValue, Class<?> paramType, boolean fullScope) {
        if (paramType.isArray()) {
            if (paramType.equals(IUploadFileWrapper[].class)) {
                if (WebContext.getRequest() instanceof IMultipartRequestWrapper) {
                    return ((IMultipartRequestWrapper) WebContext.getRequest()).getUploadFiles(paramName);
                }
                return null;
            }
            String[] _values = (String[]) WebContext.getRequest().getParameterMap().get(paramName);
            if (_values == null || _values.length == 0) {
                _values = StringUtils.split(defaultValue, ",");
            }
            if (_values != null && _values.length > 0) {
                Class<?> _arrayClassType = ClassUtils.getArrayClassType(paramType);
                Object[] _tempParams = (Object[]) Array.newInstance(_arrayClassType, _values.length);
                for (int _tempIdx = 0; _tempIdx < _values.length; _tempIdx++) {
                    _tempParams[_tempIdx] = __doSafeGetParamValue(owner, paramName, _arrayClassType, _values[_tempIdx], null, false);
                }
                return _tempParams;
            }
            return null;
        } else if (paramType.equals(IUploadFileWrapper.class)) {
            if (WebContext.getRequest() instanceof IMultipartRequestWrapper) {
                return ((IMultipartRequestWrapper) WebContext.getRequest()).getUploadFile(paramName);
            }
            return null;
        }
        return __doSafeGetParamValue(owner, paramName, paramType, WebContext.getRequest().getParameter(paramName), defaultValue, fullScope);
    }

    protected Object __doSafeGetParamValue(IWebMvc owner, String paramName, Class<?> paramType, String paramValue, String defaultValue, boolean fullScope) {
        Object _returnValue = null;
        try {
            if (paramValue == null) {
                if (fullScope) {
                    _returnValue = new BlurObject(WebContext.getRequest().getParameter(paramName)).toObjectValue(paramType);
                    if (_returnValue == null) {
                        _returnValue = new BlurObject(WebContext.getContext().getSession().get(paramName)).toObjectValue(paramType);
                        if (_returnValue == null) {
                            _returnValue = new BlurObject(WebContext.getContext().getApplication().get(paramName)).toObjectValue(paramType);
                        }
                    }
                }
            }
            if (_returnValue == null) {
                _returnValue = new BlurObject(StringUtils.defaultIfBlank(paramValue, defaultValue)).toObjectValue(paramType);
            }
        } catch (Throwable e) {
            if (owner.getOwner().getConfig().isDevelopMode()) {
                _LOG.warn("Invalid '" + paramName + "' value: " + paramValue, RuntimeUtils.unwrapThrow(e));
            }
        }
        return _returnValue;
    }

    protected Object __doParseModelBind(IWebMvc owner, RequestMeta requestMeta, ParameterMeta paramMeta, Class<?> paramType) throws Exception {
        ClassUtils.BeanWrapper<?> _wrapper = ClassUtils.wrapper(paramType);
        //
        ClassUtils.BeanWrapper<?> _wrapperEscaped = null;
        boolean _escapeBeforeFlag = Type.EscapeOrder.BEFORE.equals(owner.getModuleCfg().getParameterEscapeOrder());
        if (!_escapeBeforeFlag) {
            _wrapperEscaped = ClassUtils.wrapper(paramType);
        }
        if (_wrapper != null) {
            for (String _fName : _wrapper.getFieldNames()) {
                ParameterMeta _meta = new ParameterMeta(requestMeta, _wrapper.getField(_fName));
                if (_meta.isParamField()) {
                    Object _result = __doGetParamValue(owner, requestMeta, _meta, _meta.doBuildParamName(paramMeta.getPrefix(), _meta.getParamName(), _fName), _meta.getParamType(), _meta.getParamAnno());
                    if (_result != null) {
                        if (owner.getModuleCfg().isParameterEscapeMode()) {
                            if (_escapeBeforeFlag) {
                                _result = _meta.doParamEscape(_meta, _result);
                            } else {
                                Object _resultEscaped = _meta.doParamEscape(_meta, _result);
                                if (_wrapperEscaped != null) {
                                    _wrapperEscaped.setValue(_fName, _resultEscaped);
                                }
                            }
                        }
                        _wrapper.setValue(_fName, _result);
                    }
                }
            }
            if (owner.getModuleCfg().isParameterEscapeMode() && !_escapeBeforeFlag) {
                Map<String, Object> _resultEscapedMap = WebContext.getContext().getAttribute(Type.EscapeOrder.class.getName());
                if (_wrapperEscaped != null) {
                    _resultEscapedMap.put(paramMeta.getFieldName(), _wrapperEscaped.getTargetObject());
                }
            }
            return _wrapper.getTargetObject();
        }
        return null;
    }
}
