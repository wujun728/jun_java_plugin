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
package net.ymate.platform.webmvc.support;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HTTP请求Method重定义包装类，用于模拟RESTFul的请求方法
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-25 下午9:12:49
 * @version 1.0
 */
public class RequestMethodWrapper extends HttpServletRequestWrapper {

    private final String method;

    public RequestMethodWrapper(HttpServletRequest request, String methodParam) {
        super(request);
        if ("POST".equals(request.getMethod())) {
            String _methodName = request.getParameter(StringUtils.defaultIfBlank(methodParam, "_method"));
            if (StringUtils.isNotBlank(_methodName)) {
                method = _methodName.toUpperCase();
            } else {
                method = null;
            }
        } else {
            method = null;
        }
    }

    @Override
    public String getMethod() {
        if (method == null) {
            return super.getMethod();
        }
        return method;
    }
}
