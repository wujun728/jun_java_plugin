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

import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.webmvc.annotation.ResponseCache;
import net.ymate.platform.webmvc.view.IView;

/**
 * 请求拦截规则处理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 16/1/8 下午10:47
 * @version 1.0
 */
public interface IInterceptorRuleProcessor {

    /**
     * 初始化
     *
     * @param owner 所属模块管理器对象
     * @throws Exception 可能产生的异常
     */
    void init(IWebMvc owner) throws Exception;

    /**
     * 注册拦截器规则配置
     *
     * @param targetClass 目标类型
     * @throws Exception 可能产生的异常
     */
    void registerInterceptorRule(Class<? extends IInterceptorRule> targetClass) throws Exception;

    /**
     * 处理请求执行拦截规则
     *
     * @param owner          Owner
     * @param requestContext 请求上下文
     * @return 返回执行结果视图对象及缓存标识
     * @throws Exception 可能产生的异常
     */
    PairObject<IView, ResponseCache> processRequest(IWebMvc owner, IRequestContext requestContext) throws Exception;
}
