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
package net.ymate.platform.webmvc.context;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.webmvc.IMultipartRequestWrapper;
import net.ymate.platform.webmvc.IRequestContext;
import net.ymate.platform.webmvc.IUploadFileWrapper;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.base.Type;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.*;

/**
 * Web环境上下文封装类，为了能够方便代码移植并脱离Web环境依赖进行开发测试(功能参考Struts2)
 *
 * @author 刘镇 (suninformation@163.com) on 2011-7-24 下午10:31:48
 * @version 1.0
 */
public final class WebContext {

    private static ThreadLocal<WebContext> __LOCAL_CONTEXT = new ThreadLocal<WebContext>();

    private Map<String, Object> __attributes;

    public static WebContext getContext() {
        return __LOCAL_CONTEXT.get();
    }

    public static void destroy() {
        __LOCAL_CONTEXT.remove();
    }

    public static WebContext create(IWebMvc webMvc,
                                    IRequestContext requestContext,
                                    ServletContext servletContext,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        HashMap<String, Object> _contextMap = new HashMap<String, Object>();
        _contextMap.put(Type.Context.WEB_REQUEST_CONTEXT, requestContext);
        _contextMap.put(Type.Context.PARAMETERS, request.getParameterMap());
        _contextMap.put(Type.Context.REQUEST, new RequestMap(request));
        _contextMap.put(Type.Context.SESSION, new SessionMap(request));
        _contextMap.put(Type.Context.APPLICATION, new ApplicationMap(servletContext));
        _contextMap.put(Type.Context.LOCALE, webMvc.getOwner().getConfig().getDefaultLocale());
        _contextMap.put(Type.Context.HTTP_REQUEST, request);
        _contextMap.put(Type.Context.HTTP_RESPONSE, response);
        _contextMap.put(Type.Context.SERVLET_CONTEXT, servletContext);
        _contextMap.put(Type.Context.WEB_CONTEXT_OWNER, webMvc);
        //
        WebContext _context = new WebContext(_contextMap);
        __LOCAL_CONTEXT.set(_context);
        //
        return _context;
    }

    public static ServletContext getServletContext() {
        return (ServletContext) WebContext.getContext().getAttribute(Type.Context.SERVLET_CONTEXT);
    }

    public static PageContext getPageContext() {
        return WebContext.getContext().getAttribute(Type.Context.PAGE_CONTEXT);
    }

    public static IRequestContext getRequestContext() {
        return WebContext.getContext().getAttribute(Type.Context.WEB_REQUEST_CONTEXT);
    }

    public static HttpServletRequest getRequest() {
        return WebContext.getContext().getAttribute(Type.Context.HTTP_REQUEST);
    }

    public static HttpServletResponse getResponse() {
        return WebContext.getContext().getAttribute(Type.Context.HTTP_RESPONSE);
    }

    private WebContext(Map<String, Object> contextMap) {
        __attributes = contextMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        return (T) __attributes.get(name);
    }

    public WebContext addAttribute(String name, Object value) {
        __attributes.put(name, value);
        return this;
    }

    public Map<String, Object> getAttributes() {
        return __attributes;
    }

    public Locale getLocale() {
        Locale _locale = getAttribute(Type.Context.LOCALE);
        if (_locale == null) {
            _locale = Locale.getDefault();
        }
        return _locale;
    }

    public Map<String, Object> getApplication() {
        return getAttribute(Type.Context.APPLICATION);
    }

    public Map<String, Object> getSession() {
        return getAttribute(Type.Context.SESSION);
    }

    public Map<String, Object> getParameters() {
        return getAttribute(Type.Context.PARAMETERS);
    }

    public IWebMvc getOwner() {
        return getAttribute(Type.Context.WEB_CONTEXT_OWNER);
    }

    // -----------------

    @SuppressWarnings("unchecked")
    public <T> T getApplicationAttributeToObject(String name) {
        return (T) getApplication().get(name);
    }

    public String getApplicationAttributeToString(String name) {
        return BlurObject.bind(getApplication().get(name)).toStringValue();
    }

    public int getApplicationAttributeToInt(String name) {
        return BlurObject.bind(getApplication().get(name)).toIntValue();
    }

    public long getApplicationAttributeToLong(String name) {
        return BlurObject.bind(getApplication().get(name)).toLongValue();
    }

    public boolean getApplicationAttributeToBoolean(String name) {
        return BlurObject.bind(getApplication().get(name)).toBooleanValue();
    }

    public WebContext addApplicationAttribute(String name, Object value) {
        getApplication().put(name, value);
        return this;
    }

    // -----------------

    @SuppressWarnings("unchecked")
    public <T> T getSessionAttributeToObject(String name) {
        return (T) getSession().get(name);
    }

    public String getSessionAttributeToString(String name) {
        return BlurObject.bind(getSession().get(name)).toStringValue();
    }

    public int getSessionAttributeToInt(String name) {
        return BlurObject.bind(getSession().get(name)).toIntValue();
    }

    public long getSessionAttributeToLong(String name) {
        return BlurObject.bind(getSession().get(name)).toLongValue();
    }

    public boolean getSessionAttributeToBoolean(String name) {
        return BlurObject.bind(getSession().get(name)).toBooleanValue();
    }

    public WebContext addSessionAttribute(String name, Object value) {
        getSession().put(name, value);
        return this;
    }

    // -----------------

    @SuppressWarnings("unchecked")
    public <T> T getRequestAttributeToObject(String name) {
        return (T) getRequest().getAttribute(name);
    }

    public String getRequestAttributeToString(String name) {
        return BlurObject.bind(getRequest().getAttribute(name)).toStringValue();
    }

    public int getRequestAttributeToInt(String name) {
        return BlurObject.bind(getRequest().getAttribute(name)).toIntValue();
    }

    public long getRequestAttributeToLong(String name) {
        return BlurObject.bind(getRequest().getAttribute(name)).toLongValue();
    }

    public boolean getRequestAttributeToBoolean(String name) {
        return BlurObject.bind(getRequest().getAttribute(name)).toBooleanValue();
    }

    public WebContext addRequestAttribute(String name, Object value) {
        getRequest().setAttribute(name, value);
        return this;
    }

    // -----------------

    public String getParameterToString(String name) {
        String[] _values = (String[]) getParameters().get(name);
        if (_values != null && _values.length > 0) {
            return _values[0];
        }
        return null;
    }

    public int getParameterToInt(String name) {
        return BlurObject.bind(getParameterToString(name)).toIntValue();
    }

    public long getParameterToLong(String name) {
        return BlurObject.bind(getParameterToString(name)).toLongValue();
    }

    public boolean getParameterToBoolean(String name) {
        return BlurObject.bind(getParameterToString(name)).toBooleanValue();
    }

    // MultipartRequestWrapper

    public IUploadFileWrapper getUploadFile(String name) {
        if (getRequest() instanceof IMultipartRequestWrapper) {
            return ((IMultipartRequestWrapper) getRequest()).getUploadFile(name);
        }
        return null;
    }

    public IUploadFileWrapper[] getUploadFiles(String name) {
        if (getRequest() instanceof IMultipartRequestWrapper) {
            return ((IMultipartRequestWrapper) getRequest()).getUploadFiles(name);
        }
        return null;
    }

    public Set<IUploadFileWrapper> getUploadFiles() {
        if (getRequest() instanceof IMultipartRequestWrapper) {
            return ((IMultipartRequestWrapper) getRequest()).getUploadFiles();
        }
        return Collections.emptySet();
    }
}
