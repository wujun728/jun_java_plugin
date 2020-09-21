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

import net.ymate.platform.webmvc.IRequestContext;
import net.ymate.platform.webmvc.base.Type;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认WebMVC请求上下文接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-22 上午2:17:10
 * @version 1.0
 */
public class DefaultRequestContext implements IRequestContext {

    /**
     * 原始URL
     */
    private String originalUrl;

    /**
     * 控制器请求映射
     */
    private String requestMapping;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 后缀名称
     */
    private String suffix;

    private Type.HttpMethod __httpMethod;

    private Map<String, Object> __attributes;

    public DefaultRequestContext(HttpServletRequest request, String prefix) {
        this.requestMapping = this.originalUrl = StringUtils.defaultIfBlank(request.getPathInfo(), request.getServletPath());
        if (StringUtils.isNotBlank(prefix)) {
            this.requestMapping = StringUtils.substringAfter(this.requestMapping, prefix);
            this.prefix = prefix;
        }
        int _pos = 0;
        if (!this.requestMapping.endsWith("/")) {
            _pos = this.requestMapping.lastIndexOf('.');
            if (_pos < this.requestMapping.lastIndexOf('/')) {
                _pos = -1;
            }
        } else {
            // 请求映射字符串(注:必须以字符'/'开始且不以'/'结束)
            this.requestMapping = this.requestMapping.substring(0, this.requestMapping.length() - 1);
        }
        if (_pos > 0) {
            this.suffix = this.requestMapping.substring(_pos + 1);
            this.requestMapping = this.requestMapping.substring(0, _pos);
        } else {
            this.suffix = "";
        }
        __httpMethod = Type.HttpMethod.valueOf(request.getMethod());
        //
        __attributes = new HashMap<String, Object>();
    }

    public String getRequestMapping() {
        return requestMapping;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Type.HttpMethod getHttpMethod() {
        return __httpMethod;
    }

    ////

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        return (T) __attributes.get(name);
    }

    public IRequestContext addAttribute(String name, Object value) {
        __attributes.put(name, value);
        return this;
    }

    public Map<String, Object> getAttributes() {
        return __attributes;
    }
}
