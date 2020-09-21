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

import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.AbstractView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * HTML文件内容视图
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/28 下午5:49
 * @version 1.0
 */
public class HtmlView extends AbstractView {

    /**
     * HTML内容
     */
    protected String __content;

    public static HtmlView bind(IWebMvc owner, String htmlFile) throws Exception {
        if (StringUtils.isNotBlank(htmlFile)) {
            if (htmlFile.charAt(0) == '/') {
                htmlFile = htmlFile.substring(1);
            }
            if (!htmlFile.endsWith(".html")) {
                htmlFile += ".html";
            }
            return bind(new File(owner.getModuleCfg().getAbstractBaseViewPath(), htmlFile));
        }
        return null;
    }

    public static HtmlView bind(File htmlFile) throws Exception {
        if (htmlFile != null && htmlFile.exists() && htmlFile.isFile() && htmlFile.canRead()) {
            return new HtmlView(IOUtils.toString(new FileInputStream(htmlFile), WebContext.getResponse().getCharacterEncoding()));
        }
        return null;
    }

    public static HtmlView bind(String content) throws Exception {
        return new HtmlView(content);
    }

    /**
     * 构造器
     *
     * @param content 输出HTML内容
     */
    public HtmlView(String content) {
        __content = content;
        __contentType = Type.ContentType.HTML.getContentType();
    }

    protected void __doRenderView() throws Exception {
        HttpServletResponse _response = WebContext.getResponse();
        IOUtils.write(__content, _response.getOutputStream(), _response.getCharacterEncoding());
    }

    @Override
    public void render(OutputStream output) throws Exception {
        IOUtils.write(__content, output, WebContext.getResponse().getCharacterEncoding());
    }
}
