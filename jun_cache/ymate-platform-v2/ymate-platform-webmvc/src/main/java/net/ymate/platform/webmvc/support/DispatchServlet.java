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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于HttpServlet实现的WebMVC请求分发器
 *
 * @author 刘镇 (suninformation@163.com) on 2013年8月18日 下午7:04:30
 * @version 1.0
 */
public class DispatchServlet extends HttpServlet {

    private ServletContext __servletContext;

    private String __charsetEncoding;
    private String __requestMethodParam;
    private String __requestPrefix;

    @Override
    public void init(ServletConfig config) throws ServletException {
        __servletContext = config.getServletContext();
        //
        __charsetEncoding = WebMVC.get().getModuleCfg().getDefaultCharsetEncoding();
        __requestMethodParam = WebMVC.get().getModuleCfg().getRequestMethodParam();
        __requestPrefix = WebMVC.get().getModuleCfg().getRequestPrefix();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(__charsetEncoding);
        response.setCharacterEncoding(__charsetEncoding);
        //
        response.setContentType(Type.ContentType.HTML.getContentType().concat("; charset=").concat(__charsetEncoding));
        //
        HttpServletRequest _request = new RequestMethodWrapper(request, __requestMethodParam);
        HttpServletResponse _response = new GenericResponseWrapper(response);
        IRequestContext _requestContext = new DefaultRequestContext(_request, __requestPrefix);
        GenericDispatcher.create(WebMVC.get()).execute(_requestContext, __servletContext, _request, _response);
    }
}
