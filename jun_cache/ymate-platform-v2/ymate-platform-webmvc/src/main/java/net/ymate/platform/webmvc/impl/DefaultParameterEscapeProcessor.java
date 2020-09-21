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
package net.ymate.platform.webmvc.impl;

import net.ymate.platform.webmvc.IParameterEscapeProcessor;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.StringEscapeUtils;

/**
 * 默认参数字符串转义处理器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/30 下午11:52
 * @version 1.0
 */
public class DefaultParameterEscapeProcessor implements IParameterEscapeProcessor {

    public String processEscape(Type.EscapeScope scope, String original) {
        switch (scope) {
            case JAVA:
                original = StringEscapeUtils.escapeJava(original);
                break;
            case JS:
                original = StringEscapeUtils.escapeJavaScript(original);
                break;
            case HTML:
                original = StringEscapeUtils.escapeHtml(original);
                break;
            case XML:
                original = StringEscapeUtils.escapeXml(original);
                break;
            case SQL:
                original = StringEscapeUtils.escapeSql(original);
                break;
            case CSV:
                original = StringEscapeUtils.escapeCsv(original);
                break;
            case DEFAULT:
                original = StringEscapeUtils.escapeSql(original);
                original = StringEscapeUtils.escapeHtml(original);
        }
        return original;
    }
}
