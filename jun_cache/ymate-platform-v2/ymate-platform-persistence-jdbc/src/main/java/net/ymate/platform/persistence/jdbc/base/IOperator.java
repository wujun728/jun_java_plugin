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

import net.ymate.platform.persistence.jdbc.IConnectionHolder;

import java.util.List;

/**
 * 数据库操作器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-22 下午03:46:12
 * @version 1.0
 */
public interface IOperator {

    /**
     * 执行操作
     *
     * @throws Exception 可能产生的异常
     */
    void execute() throws Exception;

    /**
     * @return 返回当前操作器是否已执行
     */
    boolean isExecuted();

    /**
     * @return 获取预执行SQL字符串
     */
    String getSQL();

    /**
     * @return 获取访问器配置
     */
    IAccessorConfig getAccessorConfig();

    /**
     * 设置访问器配置
     *
     * @param accessorConfig 访问器配置对象
     */
    void setAccessorConfig(IAccessorConfig accessorConfig);

    /**
     * @return 获取当前使用的数据库连接对象
     */
    IConnectionHolder getConnectionHolder();

    /**
     * @return 获取本次操作所消耗的时间（单位：毫秒值）
     */
    long getExpenseTime();

    /**
     * @return 返回SQL参数集合
     */
    List<SQLParameter> getParameters();

    /**
     * @param parameter SQL参数对象
     * @return 添加SQL参数，若参数为NULL则忽略
     */
    IOperator addParameter(SQLParameter parameter);

    /**
     * @param parameter SQL参数值
     * @return 添加SQL参数，若参数为NULL则将默认向SQL传递NULL值对象
     */
    IOperator addParameter(Object parameter);
}
