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
package net.ymate.platform.core.beans;

import net.ymate.platform.core.beans.annotation.Bean;

/**
 * 对象处理器
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-5 下午1:43
 * @version 1.0
 */
public interface IBeanHandler {

    IBeanHandler DEFAULT_HANDLER = new IBeanHandler() {
        public Object handle(Class<?> targetClass) throws Exception {
            Bean _bean = targetClass.getAnnotation(Bean.class);
            if (_bean.singleton()) {
                return BeanMeta.create(targetClass.newInstance(), targetClass);
            }
            return BeanMeta.create(targetClass);
        }
    };

    /**
     * 执行Bean处理过程
     *
     * @param targetClass 目标类型
     * @return 返回实例化对象，若返回null则表示丢弃当前类对象
     * @throws Exception 处理过程可能产生的异常
     */
    Object handle(Class<?> targetClass) throws Exception;
}
