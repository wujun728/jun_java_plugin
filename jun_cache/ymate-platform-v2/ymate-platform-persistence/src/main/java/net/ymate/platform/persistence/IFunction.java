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
 * 函数接口
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/22 上午2:14
 * @version 1.0
 */
public interface IFunction {

    // addition, subtract, multiply and divide

    IFunction addition(Number param);

    IFunction addition(String param);

    IFunction addition(IFunction param);

    //

    IFunction subtract(Number param);

    IFunction subtract(String param);

    IFunction subtract(IFunction param);

    //

    IFunction multiply(Number param);

    IFunction multiply(String param);

    IFunction multiply(IFunction param);

    //

    IFunction divide(Number param);

    IFunction divide(String param);

    IFunction divide(IFunction param);

    // ------

    IFunction param(Number param);

    IFunction param(Number[] params);

    IFunction separator();

    IFunction space();

    IFunction bracketBegin();

    IFunction bracketEnd();

    IFunction param(IFunction param);

    IFunction param(String param);

    IFunction param(String[] params);

    IFunction param(String prefix, String field);

    IFunction paramWS(Object... params);

    /**
     * @return 构建函数表达式
     */
    String build();
}
