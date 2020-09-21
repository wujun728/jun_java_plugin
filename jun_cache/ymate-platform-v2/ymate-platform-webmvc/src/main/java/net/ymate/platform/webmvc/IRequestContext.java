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

import net.ymate.platform.webmvc.base.Type;

import java.util.Map;

/**
 * WebMVC请求上下文接口，分析请求路径，仅返回控制器请求映射
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-17下午11:39:56
 * @version 1.0
 */
public interface IRequestContext {

    /**
     * @return 返回请求映射字符串(注:必须以字符'/'开始且不以'/'结束)
     */
    String getRequestMapping();

    /**
     * @return 返回原始URL请求路径
     */
    String getOriginalUrl();

    /**
     * @return 返回URL前缀
     */
    String getPrefix();

    /**
     * @return 返回URL后缀(扩展名称)
     */
    String getSuffix();

    /**
     * @return 返回当前请求方式
     */
    Type.HttpMethod getHttpMethod();

    //

    <T> T getAttribute(String name);

    IRequestContext addAttribute(String name, Object value);

    Map<String, Object> getAttributes();
}
