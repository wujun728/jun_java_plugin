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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.IUploadFileWrapper;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;

/**
 * 基于JSON作为协议格式的控制器请求处理器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/28 上午11:51
 * @version 1.0
 */
public class JSONRequestProcessor extends DefaultRequestProcessor {

    private static final Log _LOG = LogFactory.getLog(JSONRequestProcessor.class);

    private JSONObject __doGetProtocol(IWebMvc owner) {
        JSONObject _protocol = WebContext.getRequestContext().getAttribute(JSONRequestProcessor.class.getName());
        if (_protocol == null) {
            try {
                _protocol = JSON.parseObject(StringUtils.defaultIfBlank(IOUtils.toString(WebContext.getRequest().getInputStream(), owner.getModuleCfg().getDefaultCharsetEncoding()), "{}"));
            } catch (Exception e) {
                _protocol = JSON.parseObject("{}");
                //
                if (WebContext.getContext().getOwner().getOwner().getConfig().isDevelopMode()) {
                    _LOG.warn("Invalid protocol", RuntimeUtils.unwrapThrow(e));
                }
            }
            WebContext.getRequestContext().addAttribute(JSONRequestProcessor.class.getName(), _protocol);
        }
        return _protocol;
    }

    protected Object __doParseRequestParam(IWebMvc owner, String paramName, String defaultValue, Class<?> paramType, boolean fullScope) {
        Object _returnValue = null;
        JSONObject _protocol = __doGetProtocol(owner);
        String[] _paramNameArr = StringUtils.split(paramName, ".");
        if (paramType.isArray()) {
            if (!paramType.equals(IUploadFileWrapper[].class)) {
                Object[] _values = null;
                if (_paramNameArr.length > 1) {
                    JSONObject _jsonObj = _protocol.getJSONObject(_paramNameArr[0]);
                    if (_jsonObj != null) {
                        JSONArray _jsonArr = _jsonObj.getJSONArray(_paramNameArr[1]);
                        if (_jsonArr != null) {
                            _values = _jsonArr.toArray();
                        }
                    }
                } else {
                    JSONArray _jsonArr = _protocol.getJSONArray(paramName);
                    if (_jsonArr != null) {
                        _values = _jsonArr.toArray();
                    }
                }
                if (_values != null && _values.length > 0) {
                    Class<?> _arrayClassType = ClassUtils.getArrayClassType(paramType);
                    Object[] _tempParams = (Object[]) Array.newInstance(_arrayClassType, _values.length);
                    for (int _tempIdx = 0; _tempIdx < _values.length; _tempIdx++) {
                        String _value = BlurObject.bind(_values[_tempIdx]).toStringValue();
                        _tempParams[_tempIdx] = __doSafeGetParamValue(owner, paramName, _arrayClassType, _value, null, false);
                    }
                    _returnValue = _tempParams;
                }
            }
        } else if (!paramType.equals(IUploadFileWrapper.class)) {
            if (_paramNameArr.length > 1) {
                JSONObject _jsonObj = _protocol.getJSONObject(_paramNameArr[0]);
                if (_jsonObj != null) {
                    _returnValue = __doSafeGetParamValue(owner, paramName, paramType, _jsonObj.getString(_paramNameArr[1]), defaultValue, fullScope);
                }
            } else {
                _returnValue = __doSafeGetParamValue(owner, paramName, paramType, _protocol.getString(paramName), defaultValue, fullScope);
            }
        }
        return _returnValue;
    }
}
