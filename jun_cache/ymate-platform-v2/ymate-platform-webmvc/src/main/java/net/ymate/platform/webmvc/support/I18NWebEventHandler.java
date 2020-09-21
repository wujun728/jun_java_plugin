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

import net.ymate.platform.core.i18n.II18NEventHandler;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.WebMVC;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.CookieHelper;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * @author 刘镇 (suninformation@163.com) on 15/7/20 上午10:02
 * @version 1.0
 */
public class I18NWebEventHandler implements II18NEventHandler {

    public static final String I18N_LANG_KEY = "_lang";

    private String __i18nHome;

    public Locale onLocale() {
        String _langStr = null;
        // 先尝试取URL参数变量
        if (WebContext.getContext() != null) {
            _langStr = WebContext.getRequestContext().getAttribute(I18N_LANG_KEY);
            if (StringUtils.trimToNull(_langStr) == null) {
                // 再尝试从请求参数中获取
                _langStr = WebContext.getRequest().getParameter(I18N_LANG_KEY);
                if (StringUtils.trimToNull(_langStr) == null) {
                    // 最后一次机会，尝试读取Cookies
                    _langStr = CookieHelper.bind(WebContext.getContext().getOwner()).getCookie(I18N_LANG_KEY).toStringValue();
                }
            }
        }
        Locale _locale = null;
        try {
            _locale = LocaleUtils.toLocale(StringUtils.trimToNull(_langStr));
        } catch (IllegalArgumentException e) {
            _locale = WebContext.getContext().getLocale();
        }
        return _locale;
    }

    public void onChanged(Locale locale) {
        if (WebContext.getContext() != null && locale != null) {
            CookieHelper.bind(WebContext.getContext().getOwner()).setCookie(I18N_LANG_KEY, locale.toString());
        }
    }

    public InputStream onLoad(String resourceName) throws IOException {
        if (__i18nHome == null) {
            __i18nHome = RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(WebMVC.get().getOwner().getConfig().getParam("i18n_resources_home"), "${root}/i18n/"));
        }
        if (StringUtils.trimToNull(resourceName) != null) {
            File _resFile = new File(__i18nHome, resourceName);
            if (_resFile.exists() && _resFile.isFile() && _resFile.canRead()) {
                return new FileInputStream(_resFile);
            }
        }
        return null;
    }
}
