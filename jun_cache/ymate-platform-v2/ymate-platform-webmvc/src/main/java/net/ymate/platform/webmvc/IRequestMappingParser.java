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

/**
 * WebMVC请求映射路径分析器接口
 *
 * @author 刘镇 (suninformation@163.com) on 17/3/17 下午10:45
 * @version 1.0
 */
public interface IRequestMappingParser {

    void registerRequestMeta(RequestMeta requestMeta);

    /**
     * @param context 请求上下文对象
     * @return 分析请求映射串，匹配成功则返回对应映射集合的键值，同时处理请求串中的参数变量存入WebContext容器中的PathVariable参数池
     */
    RequestMeta doParse(IRequestContext context);
}
