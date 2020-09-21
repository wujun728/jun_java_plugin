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
package net.ymate.platform.webmvc.impl;

import net.ymate.platform.webmvc.IRequestContext;
import net.ymate.platform.webmvc.IWebCacheProcessor;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.annotation.ResponseCache;
import net.ymate.platform.webmvc.view.IView;

/**
 * 空视图缓存处理器实现
 *
 * @author 刘镇 (suninformation@163.com) on 16/3/10 下午6:28
 * @version 1.0
 */
public final class NullWebCacheProcessor implements IWebCacheProcessor {
    public boolean processResponseCache(IWebMvc owner, ResponseCache responseCache, IRequestContext requestContext, IView resultView) throws Exception {
        return false;
    }
}
