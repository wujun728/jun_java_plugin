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
package net.ymate.platform.webmvc.view.impl;

import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.AbstractView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * HTTP返回码视图
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-29 上午02:26:25
 * @version 1.0
 */
public class HttpStatusView extends AbstractView {

    /**
     * HTTP返回码
     */
    private final int __status;
    private final String __msg;
    private final boolean __error;
    //
    private String __body;

    /**
     * STATUS: 405
     */
    public static HttpStatusView METHOD_NOT_ALLOWED = new HttpStatusView(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

    /**
     * STATUS: 404
     */
    public static HttpStatusView NOT_FOUND = new HttpStatusView(HttpServletResponse.SC_NOT_FOUND);

    public static HttpStatusView bind(int status) {
        return new HttpStatusView(status);
    }

    public static HttpStatusView bind(int status, String msg) {
        return new HttpStatusView(status, msg);
    }

    /**
     * 构造器
     *
     * @param status HTTP返回码
     */
    public HttpStatusView(int status) {
        __status = status;
        __msg = null;
        __error = true;
    }

    /**
     * 构造器
     *
     * @param status   HTTP返回码
     * @param useError 是否使用sendError方法
     */
    public HttpStatusView(int status, boolean useError) {
        __status = status;
        __error = useError;
        __msg = null;
    }

    /**
     * 构造器
     *
     * @param status HTTP返回码
     * @param msg    错误提示信息
     */
    public HttpStatusView(int status, String msg) {
        __status = status;
        __msg = msg;
        __error = false;
    }

    /**
     * 将文本内容写入回应数据流(注:调用此方法需采用useError=false设置)
     *
     * @param bodyStr 写入的内容
     * @return 当前视图对象
     */
    public HttpStatusView writeBody(String bodyStr) {
        __body = bodyStr;
        return this;
    }

    protected void __doRenderView() throws Exception {
        HttpServletResponse _response = WebContext.getResponse();
        if (StringUtils.isNotBlank(__body)) {
            IOUtils.write(__body, _response.getOutputStream(), _response.getCharacterEncoding());
        }
        if (StringUtils.isNotBlank(__msg)) {
            _response.sendError(__status, __msg);
        } else {
            if (__error) {
                _response.sendError(__status);
            } else {
                _response.setStatus(__status);
            }
        }
    }
}
