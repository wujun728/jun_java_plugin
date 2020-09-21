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
package net.ymate.platform.webmvc.view;

import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象MVC视图接口
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-20 下午6:56:56
 * @version 1.0
 */
public abstract class AbstractView implements IView {

    protected static String __baseViewPath;

    ////

    protected Map<String, Object> __attributes;

    protected String __contentType;

    public AbstractView() {
        __attributes = new HashMap<String, Object>();
    }

    public IView addAttribute(String name, Object value) {
        __attributes.put(name, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        return (T) __attributes.get(name);
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(__attributes);
    }

    public String getContentType() {
        return __contentType;
    }

    public IView setContentType(String contentType) {
        __contentType = contentType;
        return this;
    }

    public IView addDateHeader(String name, long date) {
        HttpServletResponse _response = WebContext.getResponse();
        if (_response.containsHeader(name)) {
            _response.addDateHeader(name, date);
        } else {
            _response.setDateHeader(name, date);
        }
        return this;
    }

    public IView addHeader(String name, String value) {
        HttpServletResponse _response = WebContext.getResponse();
        if (_response.containsHeader(name)) {
            _response.addHeader(name, value);
        } else {
            _response.setHeader(name, value);
        }
        return this;
    }

    public IView addIntHeader(String name, int value) {
        HttpServletResponse _response = WebContext.getResponse();
        if (_response.containsHeader(name)) {
            _response.addIntHeader(name, value);
        } else {
            _response.setIntHeader(name, value);
        }
        return this;
    }

    public void render() throws Exception {
        HttpServletResponse _response = WebContext.getResponse();
        if (_response.isCommitted()) {
            return;
        }
        if (StringUtils.isNotBlank(__contentType)) {
            _response.setContentType(__contentType);
        }
        __doRenderView();
    }

    /**
     * 视图渲染具体操作
     *
     * @throws Exception 抛出任何可能异常
     */
    protected abstract void __doRenderView() throws Exception;

    public void render(OutputStream output) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * @param url 原始URL路径
     * @return 将参数与URL地址进行绑定
     * @throws UnsupportedEncodingException URL编码异常
     */
    protected String __doBuildURL(String url) throws UnsupportedEncodingException {
        if (__attributes.isEmpty()) {
            return url;
        }
        StringBuilder _paramSB = new StringBuilder(url);
        if (!url.contains("?")) {
            _paramSB.append("?");
        } else {
            _paramSB.append("&");
        }
        boolean _flag = true;
        for (Map.Entry<String, Object> _entry : __attributes.entrySet()) {
            if (_flag) {
                _flag = false;
            } else {
                _paramSB.append("&");
            }
            _paramSB.append(_entry.getKey()).append("=");
            if (_entry.getValue() != null && StringUtils.isNotEmpty(_entry.getValue().toString())) {
                _paramSB.append(URLEncoder.encode(_entry.getValue().toString(), WebContext.getRequest().getCharacterEncoding()));
            }
        }
        return _paramSB.toString();
    }

    /**
     * 初始化配置参数(全局唯一)
     *
     * @param owner 所属WebMVC框架管理器
     */
    protected void __doViewInit(IWebMvc owner) {
        // 模板基准路径并以'/WEB-INF'开始，以'/'结束
        if (__baseViewPath == null) {
            String _vPath = StringUtils.trimToNull(owner.getModuleCfg().getBaseViewPath());
            if (!_vPath.endsWith("/")) {
                _vPath += "/";
            }
            __baseViewPath = _vPath;
        }
    }

}
