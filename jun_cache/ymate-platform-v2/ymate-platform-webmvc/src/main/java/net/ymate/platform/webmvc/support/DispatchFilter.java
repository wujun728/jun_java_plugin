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

import net.ymate.platform.webmvc.IRequestContext;
import net.ymate.platform.webmvc.WebMVC;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.impl.DefaultRequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 基于Filter实现的WebMVC请求分发器
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-23 下午11:19:39
 * @version 1.0
 */
public class DispatchFilter implements Filter {

    private FilterConfig __filterConfig;

    private Pattern __ignorePatern;

    private String __charsetEncoding;
    private String __requestMethodParam;
    private String __requestPrefix;

    public void init(FilterConfig filterConfig) throws ServletException {
        __filterConfig = filterConfig;
        String _regex = WebMVC.get().getModuleCfg().getRequestIgnoreRegex();
        if (!"false".equalsIgnoreCase(_regex)) {
            __ignorePatern = Pattern.compile(_regex, Pattern.CASE_INSENSITIVE);
        }
        __charsetEncoding = WebMVC.get().getModuleCfg().getDefaultCharsetEncoding();
        __requestMethodParam = WebMVC.get().getModuleCfg().getRequestMethodParam();
        __requestPrefix = WebMVC.get().getModuleCfg().getRequestPrefix();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (StringUtils.equalsIgnoreCase(((HttpServletRequest) request).getHeader("Connection"), "Upgrade")
                && StringUtils.equalsIgnoreCase(((HttpServletRequest) request).getHeader("Upgrade"), "websocket")) {
            chain.doFilter(request, response);
        } else {
            request.setCharacterEncoding(__charsetEncoding);
            response.setCharacterEncoding(__charsetEncoding);
            //
            response.setContentType(Type.ContentType.HTML.getContentType().concat("; charset=").concat(__charsetEncoding));
            //
            HttpServletRequest _request = new RequestMethodWrapper((HttpServletRequest) request, __requestMethodParam);
            HttpServletResponse _response = new GenericResponseWrapper((HttpServletResponse) response);
            IRequestContext _requestContext = new DefaultRequestContext(_request, __requestPrefix);
            if (null == __ignorePatern || !__ignorePatern.matcher(_requestContext.getOriginalUrl()).find()) {
                GenericDispatcher.create(WebMVC.get()).execute(_requestContext, __filterConfig.getServletContext(), _request, _response);
            } else {
                chain.doFilter(_request, _response);
            }
        }
    }

    public void destroy() {
    }
}
