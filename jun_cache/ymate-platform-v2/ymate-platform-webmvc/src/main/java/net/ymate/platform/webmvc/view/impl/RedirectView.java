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

import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.AbstractView;

/**
 * 重定向视图
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-28 下午03:49:52
 * @version 1.0
 */
public class RedirectView extends AbstractView {

    /**
     * 重定向URL
     */
    protected String __path;

    public static RedirectView bind(String path) {
        return new RedirectView(path);
    }

    /**
     * 构造器
     *
     * @param path 重定向URL
     */
    public RedirectView(String path) {
        this.__path = path;
    }

    protected void __doRenderView() throws Exception {
        // 重定向到其它站点
        if (!__path.startsWith("http://") && !__path.startsWith("https://")) {
            // 重定向决对路径
            if (__path.length() > 0 && __path.charAt(0) == '/') {
                __path = WebContext.getRequest().getContextPath() + __path;
            }
            // 重定向相对路径
            else {
                __path = RuntimeUtils.getRootPath() + "/" + __path;
            }
        }
        WebContext.getResponse().sendRedirect(__doBuildURL(__path));
    }
}
