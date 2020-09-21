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
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.AbstractView;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity视图
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/28 下午8:20
 * @version 1.0
 */
public class VelocityView extends AbstractView {

    protected static Properties __velocityConfig = new Properties();

    protected VelocityContext __velocityContext;

    private boolean __inited;

    protected String __path;

    public static VelocityView bind() {
        return new VelocityView();
    }

    public static VelocityView bind(String path) {
        return new VelocityView(WebContext.getContext().getOwner(), path);
    }

    public static VelocityView bind(IWebMvc owner, String path) {
        return new VelocityView(owner, path);
    }

    /**
     * 构造器
     *
     * @param owner 所属MVC框架管理器
     * @param path  FTL文件路径
     */
    public VelocityView(IWebMvc owner, String path) {
        __doViewInit(owner);
        __path = path;
    }

    public VelocityView() {
        __doViewInit(WebContext.getContext().getOwner());
    }

    @Override
    protected void __doViewInit(IWebMvc owner) {
        super.__doViewInit(owner);
        // 初始化Velocity模板引擎配置
        if (!__inited) {
            __velocityConfig.setProperty(Velocity.ENCODING_DEFAULT, owner.getModuleCfg().getDefaultCharsetEncoding());
            __velocityConfig.setProperty(Velocity.INPUT_ENCODING, owner.getModuleCfg().getDefaultCharsetEncoding());
            __velocityConfig.setProperty(Velocity.OUTPUT_ENCODING, owner.getModuleCfg().getDefaultCharsetEncoding());
            //
            if (__baseViewPath.startsWith("/WEB-INF")) {
                __velocityConfig.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, new File(RuntimeUtils.getRootPath(), StringUtils.substringAfter(__baseViewPath, "/WEB-INF/")).getPath());
            } else {
                __velocityConfig.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, __baseViewPath);
            }
            //
            Velocity.init(__velocityConfig);
            //
            __inited = true;
        }
    }

    public static void properties(String key, String value) {
        __velocityConfig.setProperty(key, value);
    }

    protected void __doProcessPath() {
        if (StringUtils.isNotBlank(__contentType)) {
            WebContext.getResponse().setContentType(__contentType);
        }
        __velocityContext = new VelocityContext();
        for (Map.Entry<String, Object> _entry : __attributes.entrySet()) {
            __velocityContext.put(_entry.getKey(), _entry.getValue());
        }
        if (StringUtils.isBlank(__path)) {
            String _mapping = WebContext.getRequestContext().getRequestMapping();
            if (_mapping.endsWith("/")) {
                _mapping = _mapping.substring(0, _mapping.length() - 1);
            }
            __path = _mapping + ".vm";
        } else {
            if (__path.startsWith(__baseViewPath)) {
                __path = StringUtils.substringAfter(__path, __baseViewPath);
            }
            if (!__path.endsWith(".vm")) {
                __path += ".vm";
            }
        }
    }

    protected void __doRenderView() throws Exception {
        __doProcessPath();
        Velocity.getTemplate(__path).merge(__velocityContext, WebContext.getResponse().getWriter());
    }

    public void render(OutputStream output) throws Exception {
        __doProcessPath();
        Velocity.getTemplate(__path).merge(__velocityContext, new BufferedWriter(new OutputStreamWriter(output)));
    }

}
