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

import net.ymate.platform.validation.ValidateResult;
import net.ymate.platform.webmvc.view.IView;

import java.util.Map;

/**
 * 基于Web应用的MVC框架异常错误处理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-9 下午3:27:11
 * @version 1.0
 */
public interface IWebErrorProcessor {

    /**
     * 异常时将执行事件回调
     *
     * @param owner 所属YMP框架管理器实例
     * @param e     异常对象
     */
    void onError(IWebMvc owner, Throwable e);

    /**
     * @param owner   所属YMP框架管理器实例
     * @param results 验证器执行结果集合
     * @return 处理结果数据并返回视图对象，若返回null则由框架默认处理
     */
    IView onValidation(IWebMvc owner, Map<String, ValidateResult> results);

    /**
     * 自定义处理URL请求过程
     *
     * @param owner          所属YMP框架管理器实例
     * @param requestContext 请求上下文
     * @return 可用视图对象，若为空则采用系统默认
     * @throws Exception 可能产生的异常
     */
    IView onConvention(IWebMvc owner, IRequestContext requestContext) throws Exception;
}
