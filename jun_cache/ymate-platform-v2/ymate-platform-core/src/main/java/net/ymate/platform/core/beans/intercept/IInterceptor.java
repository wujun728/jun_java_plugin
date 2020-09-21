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
package net.ymate.platform.core.beans.intercept;

/**
 * 拦截器接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/19 上午11:55
 * @version 1.0
 */
public interface IInterceptor {

    enum Direction {
        BEFORE, AFTER
    }

    enum CleanType {
        BEFORE, AFTER, ALL
    }

    /**
     * @param context 拦截器环境上下文对象
     * @return 执行拦截动作并返回执行结果，返回结果将影响前置拦截器组是否继续执行，后置拦截器将忽略返回值
     * @throws Exception 执行拦截逻辑可能产生的异常
     */
    Object intercept(InterceptContext context) throws Exception;
}
