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
package net.ymate.platform.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串表达式工具类，用于处理${variable}字符替换;
 * 例: I am ${name},and sex is ${sex}. <br>
 * name=Henry,sex=M <br>
 * result:I am Henry,and sex is M.
 *
 * @author 刘镇 (suninformation@163.com) on 2010-12-20 上午11:37:00
 * @version 1.0
 */
public final class ExpressionUtils {

    private final static String __pre = "\\$\\{";
    private final static String __suf = "\\}";
    private String __result;

    /**
     * @param expressionStr 目标字符串
     * @return 创建表达式工具类实例对象
     */
    public static ExpressionUtils bind(String expressionStr) {
        return new ExpressionUtils(expressionStr);
    }

    private ExpressionUtils(String expressionStr) {
        this.__result = expressionStr;
    }

    /**
     * @return 获取结果
     */
    public String getResult() {
        return this.__result;
    }

    /**
     * 设置变量值
     *
     * @param key   变量名称
     * @param value 变量值
     * @return 当前表达式工具类实例
     */
    public ExpressionUtils set(String key, String value) {
        String namePattern = __pre + key + __suf;
        this.__result = this.__result.replaceAll(namePattern, Matcher.quoteReplacement(value));
        return this;
    }

    /**
     * @return 返回expressionStr中变量名称集合, 返回的数量将受set方法影响
     */
    public List<String> getVariables() {
        List<String> _vars = new ArrayList<String>();
//        Matcher _match = Pattern.compile("\\$\\{([\\s\\S]*?)\\}").matcher("I am ${name},and sex is ${sex}.");
        Matcher _match = Pattern.compile("(?<=\\$\\{)(.+?)(?=\\})").matcher(this.__result);
        boolean _result = _match.find();
        if (_result) {
            do {
                _vars.add(_match.group());
                _result = _match.find();
            } while (_result);
        }
        return _vars;
    }

    /**
     * @return 清理所有变量并返回当前表达式工具类实例
     */
    public ExpressionUtils clean() {
        return set("(.+?)", "");
    }
}
