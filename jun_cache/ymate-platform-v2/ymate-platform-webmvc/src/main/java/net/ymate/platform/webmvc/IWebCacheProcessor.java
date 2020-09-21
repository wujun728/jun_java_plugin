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
package net.ymate.platform.webmvc;

import net.ymate.platform.webmvc.annotation.ResponseCache;
import net.ymate.platform.webmvc.view.IView;

/**
 * 控制器视图缓存处理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 16/2/1 上午12:00
 * @version 1.0
 */
public interface IWebCacheProcessor {

    /**
     * 对控制器方法返回视图进行缓存处理
     *
     * @param owner          所属WebMVC管理器
     * @param responseCache  控制器方法回应结果缓存注解
     * @param requestContext 请求上下文
     * @param resultView     视图对象, 若为空则表示该接口方法仅尝试读取缓存
     * @return 返回处理结果是否成功
     * @throws Exception 可能产生的异常
     */
    boolean processResponseCache(IWebMvc owner, ResponseCache responseCache, IRequestContext requestContext, IView resultView) throws Exception;
}
