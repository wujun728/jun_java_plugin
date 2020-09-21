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
package net.ymate.platform.persistence;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/22 下午8:40
 * @version 1.0
 */
public interface ISessionBase {

    /**
     * @return 获取会话对象唯一标识ID
     */
    String getId();

    /**
     * 设置会话事件处理器
     *
     * @param event 事件处理器接口
     * @return 返回当前会话对象
     */
    ISessionBase setSessionEvent(ISessionEvent event);

    /**
     * 关闭并释放会话
     */
    void close();
}
