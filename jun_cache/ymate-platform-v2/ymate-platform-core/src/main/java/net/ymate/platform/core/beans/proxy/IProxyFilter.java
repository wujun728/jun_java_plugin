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
package net.ymate.platform.core.beans.proxy;

/**
 * 代理过滤器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-10 上午11:51
 * @version 1.0
 */
public interface IProxyFilter {

    /**
     * 执行类过滤
     *
     * @param targetProxy 目标代理
     * @return 返回过滤结果，true表示通过，false则表示丢弃当前类对象
     */
    boolean filter(IProxy targetProxy);
}
