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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/9/19 下午1:54
 * @version 1.0
 */
public interface IBeanInjector {

    /**
     * @param beanFactory  所属对象工厂实例
     * @param annotation   注解对象
     * @param targetClass  目标类型
     * @param field        目标成员对象
     * @param originInject 已获取的注入对象, 可能为空
     * @return 返回最终注入对象
     */
    Object inject(IBeanFactory beanFactory, Annotation annotation, Class<?> targetClass, Field field, Object originInject);
}
