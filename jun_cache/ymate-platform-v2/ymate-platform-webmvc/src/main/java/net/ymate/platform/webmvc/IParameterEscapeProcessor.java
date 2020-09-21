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

/**
 * 参数字符串转义处理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/30 下午11:48
 * @version 1.0
 */
public interface IParameterEscapeProcessor {

    /**
     * @param scope    参数字符串转义范围
     * @param original 原始参数字符串
     * @return 处理参数转义并返回转义后字符串
     */
    String processEscape(Type.EscapeScope scope, String original);
}
