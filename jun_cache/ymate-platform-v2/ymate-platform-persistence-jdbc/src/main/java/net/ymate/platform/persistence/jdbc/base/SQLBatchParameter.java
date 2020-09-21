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
package net.ymate.platform.persistence.jdbc.base;

import net.ymate.platform.persistence.base.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量处理的SQL参数对象
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-28 上午01:52:39
 * @version 1.0
 */
public final class SQLBatchParameter {

    /**
     * 存放SQL参数的集合
     */
    private List<SQLParameter> __parameters;

    public static SQLBatchParameter create() {
        return new SQLBatchParameter();
    }

    private SQLBatchParameter() {
        this.__parameters = new ArrayList<SQLParameter>();
    }

    /**
     * @return 返回SQL参数集合
     */
    public List<SQLParameter> getParameters() {
        return this.__parameters;
    }

    /**
     * @param parameter SQL参数对象
     * @return 添加SQL参数，若参数为NULL则忽略
     */
    public SQLBatchParameter addParameter(SQLParameter parameter) {
        if (parameter != null) {
            this.__parameters.add(parameter);
        }
        return this;
    }

    /**
     * @param parameter SQL参数值
     * @return 添加SQL参数，若参数为NULL则将默认向SQL传递NULL值对象
     */
    public SQLBatchParameter addParameter(Object parameter) {
        if (parameter == null) {
            this.__parameters.add(new SQLParameter(Type.FIELD.UNKNOW, null));
        } else if (parameter instanceof SQLParameter) {
            this.__parameters.add((SQLParameter) parameter);
        } else {
            this.__parameters.add(new SQLParameter(parameter));
        }
        return this;
    }

    @Override
    public String toString() {
        return __parameters.toString();
    }
}
