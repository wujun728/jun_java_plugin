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
package net.ymate.platform.core.support;

/**
 * 指定对象具备初始化及销毁能力, 需调用者自行处理判断逻辑
 *
 * @author 刘镇 (suninformation@163.com) on 2017/10/17 上午10:51
 * @version 1.0
 */
public interface IInitializable<T> {

    /**
     * 初始化
     *
     * @param owner 指定所属容器对象
     * @throws Exception 初始过程中产生的任何异常
     */
    void init(T owner) throws Exception;

    /**
     * 销毁
     *
     * @throws Exception 销毁过程中产生的任何异常
     */
    void destroy() throws Exception;
}
